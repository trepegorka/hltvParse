package general.logics;

import hltv.matches.teams.Team;
import hltv.matches.teams.players.Player;
import interfaces.IAdvantageGenerator;

import java.util.ArrayList;

public class AdvantageGenerator implements IAdvantageGenerator {
    private final Team firstTeam;
    private final Team secondTeam;
    private final ArrayList<Player> firstListOfPlayers;
    private final ArrayList<Player> secondListOfPlayers;

    public AdvantageGenerator(Team firstTeam, Team secondTeam) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstListOfPlayers = firstTeam.getListOfPlayers();
        this.secondListOfPlayers = secondTeam.getListOfPlayers();
    }

    @Override
    public double KDRatioAttitude() {
        double KD1 = 0.0;
        double KD2 = 0.0;
        for (Player player : firstListOfPlayers) {
            KD1 = KD1 + player.getKDRatio();
        }
        for (Player player : secondListOfPlayers) {
            KD2 = KD2 + player.getKDRatio();
        }
        if (KD1 == 0.0 || KD2 == 0.0) {
            return 0.0;
        } else
            return KD1 / KD2;

    }

    @Override
    public double headshotAttitude() {
        double headRatio1 = 0.0;
        double headRatio2 = 0.0;
        for (Player player : firstListOfPlayers) {
            headRatio1 = headRatio1 + player.getHeadshot();
        }
        for (Player player : secondListOfPlayers) {
            headRatio2 = headRatio2 + player.getHeadshot();
        }
        if (headRatio1 == 0.0 || headRatio2 == 0.0) {
            return 0.0;
        } else
            return headRatio1 / headRatio2;
    }

    @Override
    public double damagePerRoundAttitude() {
        double dm1 = 0.0;
        double dm2 = 0.0;
        for (Player player : firstListOfPlayers) {
            dm1 = dm1 + player.getDamagePerRound();
        }
        for (Player player : secondListOfPlayers) {
            dm2 = dm2 + player.getDamagePerRound();
        }
        if (dm1 == 0.0 || dm2 == 0.0) {
            return 0.0;
        } else
            return dm1 / dm2;
    }

    @Override
    public double assistsPerRoundAttitude() {
        double am1 = 0.0;
        double am2 = 0.0;
        for (Player player : firstListOfPlayers) {
            am1 = am1 + player.getAssistsPerRound();
        }
        for (Player player : secondListOfPlayers) {
            am2 = am2 + player.getAssistsPerRound();
        }
        if (am1 == 0.0 || am2 == 0.0) {
            return 0.0;
        } else
            return am1 / am2;
    }

    @Override
    public double impactAttitude() {
        double im1 = 0.0;
        double im2 = 0.0;
        for (Player player : firstListOfPlayers) {
            im1 = im1 + player.getImpact();
        }
        for (Player player : secondListOfPlayers) {
            im2 = im2 + player.getImpact();
        }
        if (im1 == 0.0 || im2 == 0.0) {
            return 0.0;
        } else
            return im1 / im2;
    }

    @Override
    public double kastAttitude() {
        double ka1 = 0.0;
        double ka2 = 0.0;
        for (Player player : firstListOfPlayers) {
            ka1 = ka1 + player.getKast();
        }
        for (Player player : secondListOfPlayers) {
            ka2 = ka2 + player.getKast();
        }
        if (ka1 == 0.0 || ka2 == 0.0) {
            return 0.0;
        } else
            return ka1 / ka2;
    }

    @Override
    public double openingKillRatioAttitude() {
        double okr1 = 0.0;
        double okr2 = 0.0;
        for (Player player : firstListOfPlayers) {
            okr1 = okr1 + player.getOpeningKillRatio();
        }
        for (Player player : secondListOfPlayers) {
            okr2 = okr2 + player.getOpeningKillRatio();
        }
        if (okr1 == 0.0 || okr2 == 0.0) {
            return 0.0;
        } else
            return okr1 / okr2;
    }

    @Override
    public double rating3mAttitude() {
        double r3m1 = 0.0;
        double r3m2 = 0.0;
        for (Player player : firstListOfPlayers) {
            r3m1 = r3m1 + player.getRating3m();
        }
        for (Player player : secondListOfPlayers) {
            r3m2 = r3m2 + player.getRating3m();
        }
        if (r3m1 == 0.0 || r3m2 == 0.0) {
            return 0.0;
        } else
            return r3m1 / r3m2;
    }

    @Override
    public double ratingVStop5Attitude() {
        double rat5VS1 = 0.0;
        double rat5VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat5VS1 = rat5VS1 + player.getRatingVStop5();
        }
        for (Player player : secondListOfPlayers) {
            rat5VS2 = rat5VS2 + player.getRatingVStop5();
        }
        if (rat5VS1 == 0.0 || rat5VS2 == 0.0) {
            return 0.0;
        } else return rat5VS1 / rat5VS2;
    }

    @Override
    public double ratingVStop10Attitude() {
        double rat10VS1 = 0.0;
        double rat10VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat10VS1 = rat10VS1 + player.getRatingVStop10();
        }
        for (Player player : secondListOfPlayers) {
            rat10VS2 = rat10VS2 + player.getRatingVStop10();
        }
        if (rat10VS1 == 0.0 || rat10VS2 == 0.0) {
            return 0.0;
        } else
            return rat10VS1 / rat10VS2;
    }

    @Override
    public double ratingVStop20Attitude() {
        double rat20VS1 = 0.0;
        double rat20VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat20VS1 = rat20VS1 + player.getRatingVStop20();
        }
        for (Player player : secondListOfPlayers) {
            rat20VS2 = rat20VS2 + player.getRatingVStop20();
        }
        if (rat20VS1 == 0.0 || rat20VS2 == 0.0) {
            return 0.0;
        } else
            return rat20VS1 / rat20VS2;
    }

    @Override
    public double ratingVStop30Attitude() {
        double rat30VS1 = 0.0;
        double rat30VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat30VS1 = rat30VS1 + player.getRatingVStop30();
        }
        for (Player player : secondListOfPlayers) {
            rat30VS2 = rat30VS2 + player.getRatingVStop30();
        }
        if (rat30VS1 == 0.0 || rat30VS2 == 0.0) {
            return 0.0;
        } else
            return rat30VS1 / rat30VS2;
    }

    @Override
    public double ratingVStop50Attitude() {
        double rat50VS1 = 0.0;
        double rat50VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat50VS1 = rat50VS1 + player.getRatingVStop50();
        }
        for (Player player : secondListOfPlayers) {
            rat50VS2 = rat50VS2 + player.getRatingVStop50();
        }
        if (rat50VS1 == 0.0 || rat50VS2 == 0.0) {
            return 0.0;
        } else
            return rat50VS1 / rat50VS2;
    }

    @Override
    public double totalKillsAttitude() {
        double tk1 = 0.0;
        double tk2 = 0.0;
        for (Player player : firstListOfPlayers) {
            tk1 = tk1 + player.getTotalKills();
        }
        for (Player player : secondListOfPlayers) {
            tk2 = tk2 + player.getTotalKills();
        }
        if (tk1 == 0.0 || tk2 == 0.0) {
            return 0.0;
        } else
            return tk1 / tk2;
    }

    @Override
    public double mapsPlayedAttitude() {
        double mapsPlayed1 = 0.0;
        double mapsPlayed2 = 0.0;
        for (Player player : firstListOfPlayers) {
            mapsPlayed1 = mapsPlayed1 + player.getMapsPlayed();
        }
        for (Player player : secondListOfPlayers) {
            mapsPlayed2 = mapsPlayed2 + player.getMapsPlayed();
        }
        if (mapsPlayed1 == 0.0 || mapsPlayed2 == 0.0) {
            return 0.0;
        } else
            return mapsPlayed1 / mapsPlayed2;
    }

    @Override
    public int rankingDifference() {
        return (firstTeam.getCurrentRanking() - secondTeam.getCurrentRanking())*(-1);
    }
}
