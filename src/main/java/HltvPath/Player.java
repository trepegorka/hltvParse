package HltvPath;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import proxy.UserParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Player {
    private final Hltv hltv = new Hltv();
    UserParser iPparser = new UserParser();

    private Document getHltvPlayerStatHtml(String playerStatLink) throws IOException {
        return Jsoup.connect(playerStatLink)
                .userAgent(iPparser.getRandomIp())
                .referrer("http://www.google.com")
                .get();
    }

    private final String playerStatLink;

    public Player(String playerStatLinkLink) throws IOException {
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

    public void loadPlayerMapsStatsToFile() throws IOException {
        File file = new File("src\\main\\java\\players\\" + hltv.getNickName(playerStatLink) + ".txt");
        FileWriter writer = new FileWriter(file);
        writer.append("Rating: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue")).text())
                .append("\nDPR: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div.summaryStatBreakdown.belowAverage > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue")).text())
                .append("\nKAST: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(2) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue")).text())
                .append("\nIMPACT: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue")).text())
                .append("\nADR: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(2) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue")).text())
                .append("\nKPR: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.playerSummaryStatBox > div.summaryBreakdownContainer > div:nth-child(3) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue")).text())
                .append("\nHeadshot %: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.statistics > div > div:nth-child(1) > div:nth-child(2) > span:nth-child(2)")).text())
                .append("\nK/D Ratio %: ").append(new Elements(getHltvPlayerStatHtml(getPlayerStatLink()).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.statistics > div > div:nth-child(1) > div:nth-child(4) > span:nth-child(2)")).text());
        writer.flush();
    }

}
