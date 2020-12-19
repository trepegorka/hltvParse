package general.logics;

import Abstraction.Team;
import hltv.matches.teams.players.Player;

import java.util.ArrayList;

public class AdvantageGenerator {
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

    public double KDRatioAttitude() {
        double KD1 = 0.0;
        double KD2 = 0.0;
        for (Player player : firstListOfPlayers) {
            KD1 = KD1 + player.getKDRatio();
            if (player.getKDRatio() == 0.0) {
                KD1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            KD2 = KD2 + player.getKDRatio();
            if (player.getKDRatio() == 0.0) {
                KD2 = 0.0;
                break;
            }
        }
        if (KD1 == 0.0 || KD2 == 0.0) {
            return 0.0;
        } else if (KD1 / KD2 < 1) {
            return (2 - (1 / (KD1 / KD2)));
        } else
            return KD1 / KD2;
    }

    public double headshotAttitude() {
        double headRatio1 = 0.0;
        double headRatio2 = 0.0;
        for (Player player : firstListOfPlayers) {
            headRatio1 = headRatio1 + player.getHeadshot();
            if (player.getHeadshot() == 0.0) {
                headRatio1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            headRatio2 = headRatio2 + player.getHeadshot();
            if (player.getHeadshot() == 0.0) {
                headRatio2 = 0.0;
                break;
            }
        }
        if (headRatio1 == 0.0 || headRatio2 == 0.0) {
            return 0.0;
        } else if (headRatio1 / headRatio2 < 1) {
            return (2 - (1 / (headRatio1 / headRatio2)));
        } else
            return headRatio1 / headRatio2;
    }

    public double damagePerRoundAttitude() {
        double dm1 = 0.0;
        double dm2 = 0.0;
        for (Player player : firstListOfPlayers) {
            dm1 = dm1 + player.getDamagePerRound();
            if (player.getDamagePerRound() == 0.0) {
                dm1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            dm2 = dm2 + player.getDamagePerRound();
            if (player.getDamagePerRound() == 0.0) {
                dm2 = 0.0;
                break;
            }
        }
        if (dm1 == 0.0 || dm2 == 0.0) {
            return 0.0;
        } else if (dm1 / dm2 < 1) {
            return (2 - (1 / (dm1 / dm2)));
        } else
            return dm1 / dm2;
    }

    public double assistsPerRoundAttitude() {
        double am1 = 0.0;
        double am2 = 0.0;
        for (Player player : firstListOfPlayers) {
            am1 = am1 + player.getAssistsPerRound();
            if (player.getAssistsPerRound() == 0.0) {
                am1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            am2 = am2 + player.getAssistsPerRound();
            if (player.getAssistsPerRound() == 0.0) {
                am2 = 0.0;
                break;
            }
        }
        if (am1 == 0.0 || am2 == 0.0) {
            return 0.0;
        } else if (am1 / am2 < 1) {
            return (2 - (1 / (am1 / am2)));
        } else
            return am1 / am2;
    }

    public double impactAttitude() {
        double im1 = 0.0;
        double im2 = 0.0;
        for (Player player : firstListOfPlayers) {
            im1 = im1 + player.getImpact();
            if (player.getImpact() == 0.0) {
                im1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            im2 = im2 + player.getImpact();
            if (player.getImpact() == 0.0) {
                im2 = 0.0;
                break;
            }
        }
        if (im1 == 0.0 || im2 == 0.0) {
            return 0.0;
        } else if (im1 / im2 < 1) {
            return (2 - (1 / (im1 / im2)));
        } else
            return im1 / im2;
    }

    public double kastAttitude() {
        double ka1 = 0.0;
        double ka2 = 0.0;
        for (Player player : firstListOfPlayers) {
            ka1 = ka1 + player.getKast();
            if (player.getKast() == 0.0) {
                ka1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            ka2 = ka2 + player.getKast();
            if (player.getKast() == 0.0) {
                ka2 = 0.0;
                break;
            }
        }
        if (ka1 == 0.0 || ka2 == 0.0) {
            return 0.0;
        } else if (ka1 / ka2 < 1) {
            return (2 - (1 / (ka1 / ka2)));
        } else
            return ka1 / ka2;
    }

    public double openingKillRatioAttitude() {
        double okr1 = 0.0;
        double okr2 = 0.0;
        for (Player player : firstListOfPlayers) {
            okr1 = okr1 + player.getOpeningKillRatio();
            if (player.getOpeningKillRatio() == 0.0) {
                okr1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            okr2 = okr2 + player.getOpeningKillRatio();
            if (player.getOpeningKillRatio() == 0.0) {
                okr2 = 0.0;
                break;
            }
        }
        if (okr1 == 0.0 || okr2 == 0.0) {
            return 0.0;
        } else if (okr1 / okr2 < 1) {
            return (2 - (1 / (okr1 / okr2)));
        } else
            return okr1 / okr2;
    }

    public double rating3mAttitude() {
        double r3m1 = 0.0;
        double r3m2 = 0.0;
        for (Player player : firstListOfPlayers) {
            r3m1 = r3m1 + player.getRating3m();
            if (player.getRating3m() == 0.0) {
                r3m1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            r3m2 = r3m2 + player.getRating3m();
            if (player.getRating3m() == 0.0) {
                r3m2 = 0.0;
                break;
            }
        }
        if (r3m1 == 0.0 || r3m2 == 0.0) {
            return 0.0;
        } else if (r3m1 / r3m2 < 1) {
            return (2 - (1 / (r3m1 / r3m2)));
        } else
            return r3m1 / r3m2;
    }

    public double ratingVStop5Attitude() {
        double rat5VS1 = 0.0;
        double rat5VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat5VS1 = rat5VS1 + player.getRatingVStop5();
            if (player.getRatingVStop5() == 0.0) {
                rat5VS1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            rat5VS2 = rat5VS2 + player.getRatingVStop5();
            if (player.getRatingVStop5() == 0.0) {
                rat5VS2 = 0.0;
                break;
            }
        }
        if (rat5VS1 == 0.0 || rat5VS2 == 0.0) {
            return 0.0;
        } else if (rat5VS1 / rat5VS2 < 1) {
            return (2 - (1 / (rat5VS1 / rat5VS2)));
        } else
            return rat5VS1 / rat5VS2;
    }

    public double ratingVStop10Attitude() {
        double rat10VS1 = 0.0;
        double rat10VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat10VS1 = rat10VS1 + player.getRatingVStop10();
            if (player.getRatingVStop10() == 0.0) {
                rat10VS1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            rat10VS2 = rat10VS2 + player.getRatingVStop10();
            if (player.getRatingVStop10() == 0.0) {
                rat10VS2 = 0.0;
                break;
            }
        }
        if (rat10VS1 == 0.0 || rat10VS2 == 0.0) {
            return 0.0;
        } else if (rat10VS1 / rat10VS2 < 1) {
            return (2 - (1 / (rat10VS1 / rat10VS2)));
        } else
            return rat10VS1 / rat10VS2;
    }

    public double ratingVStop20Attitude() {
        double rat20VS1 = 0.0;
        double rat20VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat20VS1 = rat20VS1 + player.getRatingVStop20();
            if (player.getRatingVStop20() == 0.0) {
                rat20VS1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            rat20VS2 = rat20VS2 + player.getRatingVStop20();
            if (player.getRatingVStop20() == 0.0) {
                rat20VS2 = 0.0;
                break;
            }
        }
        if (rat20VS1 == 0.0 || rat20VS2 == 0.0) {
            return 0.0;
        } else if (rat20VS1 / rat20VS2 < 1) {
            return (2 - (1 / (rat20VS1 / rat20VS2)));
        } else
            return rat20VS1 / rat20VS2;
    }

    public double ratingVStop30Attitude() {
        double rat30VS1 = 0.0;
        double rat30VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat30VS1 = rat30VS1 + player.getRatingVStop30();
            if (player.getRatingVStop30() == 0.0) {
                rat30VS1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            rat30VS2 = rat30VS2 + player.getRatingVStop30();
            if (player.getRatingVStop30() == 0.0) {
                rat30VS2 = 0.0;
                break;
            }
        }
        if (rat30VS1 == 0.0 || rat30VS2 == 0.0) {
            return 0.0;
        } else if (rat30VS1 / rat30VS2 < 1) {
            return (2 - (1 / (rat30VS1 / rat30VS2)));
        } else
            return rat30VS1 / rat30VS2;
    }

    public double ratingVStop50Attitude() {
        double rat50VS1 = 0.0;
        double rat50VS2 = 0.0;
        for (Player player : firstListOfPlayers) {
            rat50VS1 = rat50VS1 + player.getRatingVStop50();
            if (player.getRatingVStop50() == 0.0) {
                rat50VS1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            rat50VS2 = rat50VS2 + player.getRatingVStop50();
            if (player.getRatingVStop50() == 0.0) {
                rat50VS2 = 0.0;
                break;
            }
        }
        if (rat50VS1 == 0.0 || rat50VS2 == 0.0) {
            return 0.0;
        } else if (rat50VS1 / rat50VS2 < 1) {
            return (2 - (1 / (rat50VS1 / rat50VS2)));
        } else
            return rat50VS1 / rat50VS2;
    }

    public double totalKillsAttitude() {
        double tk1 = 0.0;
        double tk2 = 0.0;
        for (Player player : firstListOfPlayers) {
            tk1 = tk1 + player.getTotalKills();
            if (player.getTotalKills() == 0.0) {
                tk1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            tk2 = tk2 + player.getTotalKills();
            if (player.getTotalKills() == 0.0) {
                tk2 = 0.0;
                break;
            }
        }
        if (tk1 == 0.0 || tk2 == 0.0) {
            return 0.0;
        } else if (tk1 / tk2 < 1) {
            return (2 - (1 / (tk1 / tk2)));
        } else
            return tk1 / tk2;
    }

    public double mapsPlayedAttitude() {
        double mapsPlayed1 = 0.0;
        double mapsPlayed2 = 0.0;
        for (Player player : firstListOfPlayers) {
            mapsPlayed1 = mapsPlayed1 + player.getMapsPlayed();
            if (player.getMapsPlayed() == 0.0) {
                mapsPlayed1 = 0.0;
                break;
            }
        }
        for (Player player : secondListOfPlayers) {
            mapsPlayed2 = mapsPlayed2 + player.getMapsPlayed();
            if (player.getMapsPlayed() == 0.0) {
                mapsPlayed2 = 0.0;
                break;
            }
        }
        if (mapsPlayed1 == 0.0 || mapsPlayed2 == 0.0) {
            return 0.0;
        } else if (mapsPlayed1 / mapsPlayed2 < 1) {
            return (2 - (1 / (mapsPlayed1 / mapsPlayed2)));
        } else
            return mapsPlayed1 / mapsPlayed2;
    }

    public int rankingDifference() {
        return (firstTeam.getCurrentRanking() - secondTeam.getCurrentRanking()) * (-1);
    }
}