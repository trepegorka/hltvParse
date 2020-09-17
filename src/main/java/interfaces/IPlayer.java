package interfaces;

import org.jsoup.nodes.Document;

public interface IPlayer {

    Document getPlayerDoc();

    double getKDRatio();

    double getHeadshot();

    double getDamagePerRound();

    double getAssistsPerRound();

    double getImpact();

    double getKast();

    double getOpeningKillRatio();

    double getRating3m();

    double getRatingVStop5();

    double getRatingVStop10();

    double getRatingVStop20();

    double getRatingVStop30();

    double getRatingVStop50();

    int getTotalKills();

    int getMapsPlayed();

}
