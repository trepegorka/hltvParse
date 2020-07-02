package HltvPath;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import starter.HltvBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Player {

    private final Hltv hltv = new Hltv();

    private final String playerStatLink;

    public Player(String playerStatLink) throws IOException {
        this.playerStatLink = playerStatLink;
    }

    public void loadPlayerMapsStatsToFile(ArrayList<String> maps) throws IOException, InterruptedException {
        String path = "src/main/java/players/" + hltv.getNickName(playerStatLink) + ".txt";
        File file = new File(path);
        FileWriter writer = new FileWriter(file, true);

        String m3path = "src/main/java/players3month/" + hltv.getNickName(playerStatLink) + ".txt";
        File file3m = new File(m3path);
        FileWriter writer3m = new FileWriter(file3m, true);

        Set<String> fileMaps = new HashSet<>();
        for (String map : maps) {
            Scanner scanner = new Scanner(file);
            if (file.length() != 0 && file3m.length() != 0) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    fileMaps.add(line.substring(0, line.lastIndexOf("Rating:")));
                }
                if (!fileMaps.contains(map)) {
                    appender(writer, map);
                    appender3m(writer3m, map);
                }
            } else {
                appender(writer, map);
                appender3m(writer3m, map);
            }
        }
    }

    private void appender(FileWriter writer, String i) throws IOException, InterruptedException {
        Thread.sleep(1000);
        Document documentPlayerStatLink = HltvBuilder.getHtml(playerStatLink + "?maps=de_" + i);

        flusher(writer, i, documentPlayerStatLink);
    }

    private void appender3m(FileWriter writer, String i) throws IOException, InterruptedException {
        Thread.sleep(1000);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date m3ago = cal.getTime();

        Document documentPlayerStatLink = HltvBuilder.getHtml(playerStatLink + "?startDate=" + dateFormat.format(m3ago) + "&endDate=" + dateFormat.format(today) + "&maps=de_" + i);

        flusher(writer, i, documentPlayerStatLink);
    }

    private void flusher(FileWriter writer, String i, Document documentPlayerStatLink) throws IOException {
        Elements elements = documentPlayerStatLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview");
        writer.append(i).append("Rating: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" DPR: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(2) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" KAST: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" IMPACT: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" ADR: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(2) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" KPR: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" Headshot: ").append(elements.select("> div.statistics > div > div:nth-child(1) > div:nth-child(2) > span:nth-child(2)").text())
                .append(" K/D Ratio: ").append(elements.select("> div.statistics > div > div:nth-child(1) > div:nth-child(4) > span:nth-child(2)").text()).append("\n");
        writer.flush();
    }
}

