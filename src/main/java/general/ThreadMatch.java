package general;

import general.logics.AdvantageGenerator;
import hltv.matches.Match;
import hltv.matches.teams.Team;
import org.apache.commons.math3.util.Precision;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ThreadMatch extends Thread {
    String link;

    public ThreadMatch(String link) {
        this.link = link;
    }

    @Override
    public void run() {
        Match match;
        Team leftTeam;
        Team rightTeam;
        try {
            match = new Match(this.link);
            for (String map : match.mapPick()) {
                leftTeam = new Team(match.getSecondTeamLink(), map); // Team 0 downloading left
                rightTeam = new Team(match.getFirstTeamLink(), map); // Team 1 downloading right
                AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);

                double[] attributes = new double[11];
                System.out.println("Generating attitude...");
                attributes[0] = Precision.round(generator.KDRatioAttitude(), 3);
                attributes[1] = Precision.round(generator.headshotAttitude(), 3);
                attributes[2] = Precision.round(generator.damagePerRoundAttitude(), 3);
                attributes[3] = Precision.round(generator.assistsPerRoundAttitude(), 3);
                attributes[4] = Precision.round(generator.impactAttitude(), 3);
                attributes[5] = Precision.round(generator.kastAttitude(), 3);
                attributes[6] = Precision.round(generator.openingKillRatioAttitude(), 3);
                attributes[7] = Precision.round(generator.rating3mAttitude(), 3);
                attributes[8] = Precision.round(generator.ratingVStop5Attitude(), 3);
                attributes[9] = Precision.round(generator.ratingVStop20Attitude(), 3);
                attributes[10] = Precision.round(generator.mapsPlayedAttitude(), 3);

//                callPython(attributes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
