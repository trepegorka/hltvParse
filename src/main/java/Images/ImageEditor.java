package Images;

import java.awt.*;
import java.io.*;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.math3.util.Precision;

import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageEditor {
    public static void main(String[] args) throws IOException {

        String leftTeamName = "FORZE";
        String rightTeamName = "VIRTUS.PRO";
        String dateOfMatch = "6th of May 2020";
        String timeOfMatch = "12:50";

        String[] maps = {"DUST2", "TRAIN", "MIRAGE"};
        Map<String, String[]> map = new LinkedHashMap<>();
        map.put(maps[0], new String[]{"0,17", "0,83"});
        map.put(maps[1], new String[]{"0,54", "0,46"});
        map.put(maps[2], new String[]{"0,60", "0,40"});

        fillImage(leftTeamName, rightTeamName, dateOfMatch, timeOfMatch, map);
    }

    // Before call method DOWNLOAD IMAGES OF TEAM
    public static void fillImage(String leftTeamName, String rightTeamName, String dateOfMatch, String timeOfMatch, Map<String, String[]> mapName_perWin) throws IOException {
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
            if (entry.getKey().contains("i")) {
                xMap = xMap + 7;
            }
            double leftPercentage = Double.parseDouble(entry.getValue()[0].replace(',', '.')) * 100;
            double rightPercentage = Double.parseDouble(entry.getValue()[1].replace(',', '.')) * 100;
            leftPercentage = Precision.round(leftPercentage, 1);
            rightPercentage = Precision.round(rightPercentage, 1);
            double leftCoeff = Precision.round(100 / leftPercentage, 2);
            double rightCoeff = Precision.round(100 / rightPercentage, 2);
            String leftPerc = String.valueOf(leftPercentage);
            String rightPerc = String.valueOf(rightPercentage);
            String leftCoefficient = String.valueOf(leftCoeff);
            String rightCoefficient = String.valueOf(rightCoeff);
            if (leftPerc.length() == 3) leftPerc = leftPerc + "0";
            if (rightPerc.length() == 3) rightPerc = rightPerc + "0";
            if (leftCoefficient.length() == 3) leftCoefficient = leftCoefficient + "0";
            if (rightCoefficient.length() == 3) rightCoefficient = rightCoefficient + "0";

            //PRINT MAPS
            printTextToImage("result", entry.getKey(), (xMap - (11 * entry.getKey().length())), yMap, 0, 45);

            //PRINT DIGITS PERCENTAGE
            int xPercL = 70;
            if (leftPerc.contains("1")) {
                xPercL = xPercL + 5;
            }
            int xPercR = 670;
            if (rightPerc.contains("1")) {
                xPercR = xPercR + 5;
            }
            printTextToImage("result", leftPerc, xPercL, yMap, 0, 45);
            printTextToImage("result", rightPerc, xPercR, yMap, 0, 45);

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
        g.drawString(whatToWrite, x, y);
        g.dispose();

        ImageIO.write(image, "png", new File("src/main/java/Images/imageLibrary/result.png"));
    }
}
