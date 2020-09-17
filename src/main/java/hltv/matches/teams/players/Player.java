package hltv.matches.teams.players;

import general.General;
import interfaces.IPlayer;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Player implements IPlayer {
    /**
     * All stats for the last 3 month before date of match
     **/

    private final Document playerDoc;

    //stats per round
    private final double KDRatio;
    private double headshot; //0.0 - 1.0 ; percents*0.01
    private final double damagePerRound;
    private final double assistsPerRound;
    private final double impact; //multikills, clutches, opening kills
    private double kast; //0.0 - 1.0 rounds where player had kills, assists, was traded etc.
    private double openingKillRatio;

    //total stats
    private final double rating3m; // average stats per 3 month
    private final double ratingVStop5;
    private final double ratingVStop10;
    private final double ratingVStop20;
    private final double ratingVStop30;
    private final double ratingVStop50;
    private int totalKills;
    private int mapsPlayed;

    public Document getPlayerDoc() {
        return playerDoc;
    }

    public double getKDRatio() {
        return KDRatio;
    }

    public double getHeadshot() {
        return headshot;
    }

    public double getDamagePerRound() {
        return damagePerRound;
    }

    public double getAssistsPerRound() {
        return assistsPerRound;
    }

    public double getImpact() {
        return impact;
    }

    public double getKast() {
        return kast;
    }

    public double getOpeningKillRatio() {
        return openingKillRatio;
    }

    public double getRating3m() {
        return rating3m;
    }

    public double getRatingVStop5() {
        return ratingVStop5;
    }

    public double getRatingVStop10() {
        return ratingVStop10;
    }

    public double getRatingVStop20() {
        return ratingVStop20;
    }

    public double getRatingVStop30() {
        return ratingVStop30;
    }

    public double getRatingVStop50() {
        return ratingVStop50;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getMapsPlayed() {
        return mapsPlayed;
    }

    public Player(Document playerDoc) {
        this.playerDoc = playerDoc;

        Elements statBlock = playerDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.statistics > div");
        Elements highStatContainer = playerDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.playerSummaryStatBox > div.summaryBreakdownContainer");
        Elements ratingsBlock = playerDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.featured-ratings-container > div");

        KDRatio = getStat(statBlock, "div:nth-child(1) > div:nth-child(4) > span:nth-child(2)");

        damagePerRound = getStat(statBlock, "div:nth-child(1) > div:nth-child(5) > span:nth-child(2)");

        assistsPerRound = getStat(statBlock, "div:nth-child(2) > div:nth-child(3) > span:nth-child(2)");

        impact = getStat(highStatContainer, "div:nth-child(3) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue");

        try {
            openingKillRatio = Double.parseDouble(General.getHtml("https://www.hltv.org" + playerDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-overview > div.gtSmartphone-only > div > div > a:nth-child(2)")
                    .attr("href")).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.stats-section.stats-player.stats-player-individual > div.statistics > div > div:nth-child(1) > div:nth-child(5) > div:nth-child(3) > span:nth-child(2)").text());
        } catch (Exception e) {
            System.out.println("Player has no stat OKR");
            openingKillRatio = 0.0;
        }

        rating3m = getStat(highStatContainer, "div:nth-child(2) > div:nth-child(1) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue");

        /**rating may be = '-' **/

        ratingVStop5 = getStat(ratingsBlock, "div:nth-child(1) > div > div.rating-value");

        ratingVStop10 = getStat(ratingsBlock, "div:nth-child(2) > div > div.rating-value");

        ratingVStop20 = getStat(ratingsBlock, "div:nth-child(3) > div > div.rating-value");

        ratingVStop30 = getStat(ratingsBlock, "div:nth-child(4) > div > div.rating-value");

        ratingVStop50 = getStat(ratingsBlock, "div:nth-child(5) > div > div.rating-value");

        try {
            totalKills = Integer.parseInt(statBlock.select("div:nth-child(1) > div:nth-child(1) > span:nth-child(2)").text());
        } catch (Exception e) {
            System.out.println("Player has no TotalKills");
            totalKills = 0;
        }

        try {
            mapsPlayed = Integer.parseInt(statBlock.select("div:nth-child(1) > div:nth-child(7) > span:nth-child(2)").text());
        } catch (Exception e) {
            System.out.println("Player has no PlayedMaps");
            mapsPlayed = 0;
        }

        try {
            headshot = Double.parseDouble(statBlock.select("div:nth-child(1) > div:nth-child(2) > span:nth-child(2)")
                    .text().substring(0, statBlock.select("div:nth-child(1) > div:nth-child(2) > span:nth-child(2)").text().length() - 1));
        } catch (Exception e) {
            System.out.println("Player has no Headshot percentage");
            headshot = 0.0;
        }

        try {
            kast = Double.parseDouble(highStatContainer.select("div:nth-child(2) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue")
                    .text().substring(0, highStatContainer.select("div:nth-child(2) > div:nth-child(3) > div.summaryStatBreakdownData > div.summaryStatBreakdownDataValue").text().length() - 1));
        } catch (Exception e) {
            System.out.println("Player has no kast");
            kast = 0;
        }

    }

    private Double getStat(Elements from, String selector) {
        try {
            return Double.parseDouble(from.select(selector).text());
        } catch (Exception e) {
            System.out.println("Player has no stat");
            return 0.0;
        }
    }

}

