package HltvPath;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import proxy.UserParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private final Hltv hltv = new Hltv();

    private final String playerStatLink;

    public Player(String playerStatLinkLink) {
        this.playerStatLink = playerStatLinkLink;
    }

    private String RATING;
    private String DPR;
    private String KAST;
    private String IMPACT;
    private String ADR;
    private String KPR;
    private String HeadshotPercent;
    private String KillDeathRatio;

    // с 3 го линка
    private String TeamWinPercentAfterFirstKill;
    private String OpeningKillRatio;


    public String getPlayerStatLink() {
        return playerStatLink;
    }

    public String getRATING() {
        return RATING;
    }

    public void setRATING(String RATING) {
        this.RATING = RATING;
    }

    public String getDPR() {
        return DPR;
    }

    public void setDPR(String DPR) {
        this.DPR = DPR;
    }

    public String getKAST() {
        return KAST;
    }

    public void setKAST(String KAST) {
        this.KAST = KAST;
    }

    public String getIMPACT() {
        return IMPACT;
    }

    public void setIMPACT(String IMPACT) {
        this.IMPACT = IMPACT;
    }

    public String getADR() {
        return ADR;
    }

    public void setADR(String ADR) {
        this.ADR = ADR;
    }

    public String getKPR() {
        return KPR;
    }

    public void setKPR(String KPR) {
        this.KPR = KPR;
    }

    public String getHeadshotPercent() {
        return HeadshotPercent;
    }

    public void setHeadshotPercent(String headshotPercent) {
        HeadshotPercent = headshotPercent;
    }

    public String getKillDeathRatio() {
        return KillDeathRatio;
    }

    public void setKillDeathRatio(String killDeathRatio) {
        KillDeathRatio = killDeathRatio;
    }

    public String getTeamWinPercentAfterFirstKill() {
        return TeamWinPercentAfterFirstKill;
    }

    public void setTeamWinPercentAfterFirstKill(String teamWinPercentAfterFirstKill) {
        TeamWinPercentAfterFirstKill = teamWinPercentAfterFirstKill;
    }

    public String getOpeningKillRatio() {
        return OpeningKillRatio;
    }

    public void setOpeningKillRatio(String openingKillRatio) {
        OpeningKillRatio = openingKillRatio;
    }

    public void loadPlayerMapsStatsToFile(ArrayList<String> map, Document documentPlayerStatLink) throws IOException {
        File file = new File("src\\main\\java\\players\\" + hltv.getNickName(playerStatLink) + ".txt");
        FileWriter writer = new FileWriter(file);
        for (String i : map) {
            Scanner scanner = new Scanner(file);
            if (file.length() != 0) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains(i)) {
                    } else {
                        appender(writer, i);

                    }
                }
            } else {
                appender(writer, i);

            }
        }
        writer.flush();
        writer.close();
    }

    private void appender(FileWriter writer, String i) throws IOException {
        Document documentPlayerStatLink;
        documentPlayerStatLink = Jsoup.connect((playerStatLink + "?maps=de_" + i)).userAgent(UserParser.userAgent).get();
        Elements elements = documentPlayerStatLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview");
        writer.append("\n").append(i).append("Rating: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" DPR: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(2) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" KAST: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" IMPACT: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" ADR: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(2) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" KPR: ").append(elements.select("> div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text())
                .append(" Headshot: ").append(elements.select("> div.statistics > div > div:nth-child(1) > div:nth-child(2) > span:nth-child(2)").text())
                .append(" K/D Ratio: ").append(elements.select("> div.statistics > div > div:nth-child(1) > div:nth-child(4) > span:nth-child(2)").text());
    }


}

