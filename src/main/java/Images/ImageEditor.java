package Images;

import java.awt.*;
import java.io.*;


import net.coobird.thumbnailator.Thumbnails;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageEditor {
    public static void main(String[] args) throws IOException {
        //convert SVG to PNG -> Resize Team Images -> Stick Team Images -> Stick Text.
        String leftTeamName = "FORZE";
        String rightTeamName = "VIRTUS.PRO";
        String dateOfMatch = "6th of November 2020".toUpperCase();
        String timeOfMatch = "12:50";

        String[] maps1 = {"DUST2", "NUKE", "TRAIN", "OVERPASS", "MIRAGE"};
        //key = map, value = [0.66 | 0.24]
        Map<String, String[]> map1 = new LinkedHashMap<>();
        map1.put(maps1[0], new String[]{"0,67", "0,23"});
        map1.put(maps1[1], new String[]{"0,17", "0,83"});
        map1.put(maps1[2], new String[]{"0,54", "0,46"});
        map1.put(maps1[3], new String[]{"0,60", "0,40"});
        map1.put(maps1[4], new String[]{"0,55", "0,45"});

        String[] maps2 = {"DUST2", "TRAIN", "MIRAGE"};
        Map<String, String[]> map2 = new LinkedHashMap<>();
        map2.put(maps2[0], new String[]{"0,17", "0,83"});
        map2.put(maps2[1], new String[]{"0,54", "0,46"});
        map2.put(maps2[2], new String[]{"0,60", "0,40"});

        String[] maps3 = {"OVERPASS"};
        Map<String, String[]> map3 = new LinkedHashMap<>();
        map3.put(maps3[0], new String[]{"0,67", "0,23"});

        fillImage(leftTeamName, rightTeamName, dateOfMatch, timeOfMatch, map1);


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
        printTextToImage("result", leftTeamName, x1TName, 470);
        printTextToImage("result", rightTeamName, x2TName, 470);

        //PRINT DATE AND TIME OF MATCH
        printTextToImage("result", timeOfMatch, 360, 350);
        printTextToImage("result", dateOfMatch, 390 - (dateOfMatch.length() * 10), 400);

        //PRINT MAPS TO CENTER
        int yMap = 570;
        int xMap = 400;
        for (Map.Entry<String, String[]> entry : mapName_perWin.entrySet()) {
            printTextToImage("result", entry.getKey(), (xMap - (11 * entry.getKey().length())), yMap);
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

    private static void printTextToImage(String whereToWrite, String whatToWrite, int x, int y) throws IOException {
        BufferedImage image = ImageIO.read(new File("src/main/java/Images/imageLibrary/" + whereToWrite + ".png"));

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setFont(new Font("Uroob", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(whatToWrite, x, y);
        g.dispose();

        ImageIO.write(image, "png", new File("src/main/java/Images/imageLibrary/result.png"));
    }
}
