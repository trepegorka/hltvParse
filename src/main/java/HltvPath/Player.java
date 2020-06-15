package HltvPath;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PrimitiveIterator;

public class Player {
    private final Hltv hltv = new Hltv();

    private Document getHltvPlayerStatHtml(String playerStatLink) throws IOException {
        return Jsoup.connect(playerStatLink)
                .userAgent("OPR/68.0.3618.125")
                .referrer("http://www.google.com")
                .get();
    }
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

    public void loadPlayerMapsStatsToFile() throws IOException {
        File file = new File("src\\main\\java\\players\\" + hltv.getNickName(playerStatLink));
        FileWriter writer = new FileWriter(file);
        writer.append("Rating: ");
        writer.flush();
    }

}
