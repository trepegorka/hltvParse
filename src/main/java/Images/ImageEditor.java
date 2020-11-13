package Images;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import gui.ava.html.image.generator.HtmlImageGenerator;
import hltv.matches.Match;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.math3.util.Precision;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.io.File;

import java.util.Map;

public class ImageEditor {

    public static void main(String[] args) throws Exception {
        convertSVGToPNG("paiN");

    }

    private static Image makeWhiteBGToTransparent(final BufferedImage im) {
        final ImageFilter filter = new RGBImageFilter() {
            public final int markerRGB = 0xFFFFFFFF;

            public final int filterRGB(final int x, final int y, final int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };

        final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bImage;
    }

    // Before call method DOWNLOAD IMAGES OF TEAM
    public static void fillImage(Match match, Map<String, String[]> mapName_perWin) throws Exception {
        String leftTeamName = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > a > div").text();
        String rightTeamName = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a > div").text();
        String dateOfMatch = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.date").text();
        String timeOfMatch = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.time").text();

        downloadImages(match);

        //RESIZE LOGO TO 200x200
        resizeImage(leftTeamName);
        resizeImage(rightTeamName);

        //PRINT LOGO TEAMS
        stickImageTeam(leftTeamName, "clearWall", 50, 100);
        stickImageTeam(rightTeamName, "result", 500, 100);

        int x1TName = 180 - (int) (leftTeamName.length() * 21.5 / 2);
        int x2TName = 620 - (int) (rightTeamName.length() * 21.5 / 2);
        //PRINT NAMES OF TEAMS
        //FONT 0 = PLAIN, 1 = BOLD
        printTextToImage("result", leftTeamName, x1TName, 470, 1, 50);
        printTextToImage("result", rightTeamName, x2TName, 470, 1, 50);

        //PRINT DATE AND TIME OF MATCH
        printTextToImage("result", dateOfMatch, 420 - (dateOfMatch.length() * 10), 40, 0, 50);
        printTextToImage("result", timeOfMatch, 360, 90, 0, 50);

        //PRINT MAPS TO CENTER AND DIGITS
        int yMap = 570;
        int xMap = 415;
        for (Map.Entry<String, String[]> entry : mapName_perWin.entrySet()) {
//            xMap = 415;
//            if (entry.getKey().contains("i")) {
//                xMap = xMap + 7;
//            }
            double leftPercentage = Double.parseDouble(entry.getValue()[0].replace(',', '.')) * 100;
            double rightPercentage = Double.parseDouble(entry.getValue()[1].replace(',', '.')) * 100;
            leftPercentage = Precision.round(leftPercentage, 1);
            rightPercentage = Precision.round(rightPercentage, 1);
            double leftCoeff = Precision.round(100 / leftPercentage, 2);
            double rightCoeff = Precision.round(100 / rightPercentage, 2);
            String leftPerc = String.valueOf(leftPercentage);
            String rightPerc = String.valueOf(rightPercentage);
            String leftCoefficient;
            if (leftPercentage == 0.0) leftCoefficient = "-";
            else leftCoefficient = String.valueOf(leftCoeff);
            String rightCoefficient;
            if (rightPercentage == 0.0) rightCoefficient = "-";
            else rightCoefficient = String.valueOf(rightCoeff);
            if (leftPerc.length() == 3) leftPerc = leftPerc + "0";
            if (rightPerc.length() == 3) rightPerc = rightPerc + "0";
            if (leftCoefficient.length() == 3) leftCoefficient = leftCoefficient + "0";
            if (rightCoefficient.length() == 3) rightCoefficient = rightCoefficient + "0";

            //PRINT MAPS
            printTextToImage("result", entry.getKey(), (xMap - (12 * entry.getKey().length())), yMap, 0, 45);

            //PRINT DIGITS PERCENTAGE
            int xPercL = 70;
            if (leftPerc.contains("1")) {
                xPercL = xPercL + 5;
            }
            int xPercR = 670;
            if (rightPerc.contains("1")) {
                xPercR = xPercR + 5;
            }
            if (leftPerc.equals("0.0")) printTextToImage("result", "-", xPercL, yMap, 0, 45);
            else printTextToImage("result", leftPerc, xPercL, yMap, 0, 45);
            if (rightPerc.equals("0.0")) printTextToImage("result", "-", xPercR, yMap, 0, 45);
            else printTextToImage("result", rightPerc, xPercR, yMap, 0, 45);

            //PRINT DIGITS COEFF.
            int xCoeffL = 230;
            if (leftCoefficient.contains("1")) {
                xCoeffL = xCoeffL + 5;
            }
            int xCoeffR = 520;
            if (rightCoefficient.contains("1")) {
                xCoeffR = xCoeffR + 5;
            }
            printTextToImage("result", leftCoefficient, xCoeffL, yMap, 0, 45);
            printTextToImage("result", rightCoefficient, xCoeffR, yMap, 0, 45);

            yMap = yMap + 50;
        }
    }

    // imageName = teamName
    // path = src/main/java/Images/imageLibrary/(imageName).svg
    private static void convertSVGToPNG(String imageName) throws IOException, TranscoderException {
        File file = new File("src/main/java/Images/imageLibrary/" + imageName + ".svg");
        String svg_URI_input = Paths.get("src/main/java/Images/imageLibrary/" + imageName + ".svg").toUri().toURL().toString();
        TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);
        OutputStream png_ostream = new FileOutputStream("src/main/java/Images/imageLibrary/" + imageName + ".png");
        TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
        PNGTranscoder my_converter = new PNGTranscoder();
        my_converter.transcode(input_svg_image, output_png_image);
        png_ostream.flush();
        png_ostream.close();
        file.delete();
    }

    private static void downloadImages(Match match) throws Exception {
        String leftTeamName = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > a > div").text();
        String leftImageLink = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > a").first().child(0).attr("src");
        imageWriter(leftTeamName, leftImageLink, match, 1);

        String rightTeamName = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a > div").text();
        String rightImageLink = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a").first().child(0).attr("src");
        imageWriter(rightTeamName, rightImageLink, match, 3);
    }

    private static void imageWriter(String TeamName, String ImageLink, Match match, int leftOrRight) throws IOException {
        if (!new File("src/main/java/Images/imageLibrary/" + TeamName + ".png").exists()) {
            try {
                File file = new File("src/main/java/Images/imageLibrary/" + TeamName + ".svg");
                Document Doc = Jsoup.connect(ImageLink).ignoreContentType(true).get();
                Elements el1 = Doc.select("#Layer_1");
                String a1 = "";
                if (el1.size() == 0) {
                    el1 = Doc.select("#Слой_1");
                    a1 = String.valueOf(el1.get(0));
                }
                a1 = a1.replaceAll("\\bviewbox\\b", "viewBox");
                a1 = a1.replaceAll("\\bclippath\\b", "clipPath");
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(file));
                writer1.write(a1);
                writer1.close();
                convertSVGToPNG(TeamName);
            } catch (Exception e) {
                Elements el1 = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(" + leftOrRight + ") > div > a > img");
                String a1 = String.valueOf(el1.get(0));
                HtmlImageGenerator hig = new HtmlImageGenerator();
                hig.loadHtml(a1);
                hig.saveAsImage(new File("src/main/java/Images/imageLibrary/" + TeamName + ".png"));
                BufferedImage image = ImageIO.read(new File("src/main/java/Images/imageLibrary/" + TeamName + ".png"));
                ImageIO.write(toBufferedImage(makeWhiteBGToTransparent(image)), "png", new File("src/main/java/Images/imageLibrary/" + TeamName + ".png"));
            }
        }
    }

    private static void stickImageTeam(String imageName, String whereToStick, int x, int y) {
        try {
            BufferedImage wall = ImageIO.read(new File("src/main/java/Images/imageLibrary/" + whereToStick + ".png"));

            BufferedImage image = ImageIO.read(new File("src/main/java/Images/imageLibrary/" + imageName + ".png"));

            BufferedImage im = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
            im.getGraphics().drawImage(wall, 0, 0, null);
            im.getGraphics().drawImage(image, x, y, null);

            ImageIO.write(im, "png", new File("src/main/java/Images/imageLibrary/result.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void resizeImage(String imageName) throws IOException {
        BufferedImage thumbnail = Thumbnails.of(new File("src/main/java/Images/imageLibrary/" + imageName + ".png"))
                .height(250)
                .asBufferedImage();
        ImageIO.write(thumbnail, "png", new File("src/main/java/Images/imageLibrary/" + imageName + ".png"));
    }

    private static void printTextToImage(String whereToWrite, String whatToWrite, int x, int y, int font, int fontSize) throws IOException {
        BufferedImage image = ImageIO.read(new File("src/main/java/Images/imageLibrary/" + whereToWrite + ".png"));
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(new Font("Uroob", font, fontSize));
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (whatToWrite.equals("Train") | whatToWrite.equals("Overpass") | whatToWrite.equals("Mirage") | whatToWrite.equals("Vertigo") |
                whatToWrite.equals("Nuke") | whatToWrite.equals("Inferno") | whatToWrite.equals("Dust2")) {
            x = g.getFontMetrics(new Font("Uroob", font, fontSize)).stringWidth(whatToWrite);
            g.drawString(whatToWrite, 400 - x / 2, y);
            g.dispose();
        } else {
            g.drawString(whatToWrite, x, y);
            g.dispose();
        }

        ImageIO.write(image, "png", new File("src/main/java/Images/imageLibrary/result.png"));
    }
}
