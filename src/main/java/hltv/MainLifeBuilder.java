package hltv;

import Abstraction.Match;
import Abstraction.Team;
import Images.ImageEditor;
import general.ThreadMatch;
import general.logics.AdvantageGenerator;
import hltv.matches.LifeMatch;
import hltv.matches.teams.LifeTeam;
import hltv.matches.upcoming.UpcomingMatch;
import org.apache.commons.math3.util.Precision;
import resultMatches.MatchResult;
import resultMatches.pastTeams.PastTeam;
import telegramBot.Bot;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainLifeBuilder {
    private double leftPerc;
    private double rightPerc;

    public void loadAllLife(String lifeMatchLink) throws Exception {
        FileWriter writer = new FileWriter("src/main/java/resultMatches/resultLinks.txt", true);
        writer.append("\n").append(lifeMatchLink).flush();

        Match match = new LifeMatch(lifeMatchLink);
        if (match.mapPick().get(0).equals("TBA")) {
            ThreadMatch threadMatch = new ThreadMatch(match);
            threadMatch.start();
        } else {
            Team leftTeam;
            Team rightTeam;

            double[] attributes = new double[17];
            Map<String, String[]> map_perWin = new LinkedHashMap<>();
            int mapNumber = 1;
            for (String map : match.mapPick()) {
                if (map.equals("Default")) {
                    mapNumber++;
                    continue;
                }
                System.out.println("\nFirst team download...");
                leftTeam = new LifeTeam(map, match, "left");
                System.out.println("\nSecond team download...");
                rightTeam = new LifeTeam(map, match, "right");
                AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);

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
                attributes[9] = Precision.round(generator.ratingVStop10Attitude(), 3);
                attributes[10] = Precision.round(generator.ratingVStop20Attitude(), 3);
                attributes[11] = Precision.round(generator.ratingVStop30Attitude(), 3);
                attributes[12] = Precision.round(generator.ratingVStop50Attitude(), 3);
                attributes[13] = Precision.round(generator.totalKillsAttitude(), 3);
                attributes[14] = Precision.round(generator.mapsPlayedAttitude(), 3);
                attributes[15] = generator.rankingDifference();
                attributes[16] = match.mapPicker(mapNumber);
                mapNumber++;

                System.out.println("\nPython calling...");
                callPython(attributes);
                System.out.println("\nNext Map");
                map_perWin.put(map, new String[]{String.valueOf(getLeftPerc()), String.valueOf(getRightPerc())});
            }
            ImageEditor.fillImage(match, map_perWin);
        }
    }

    public void loadPast(String pastMatchLink) throws Exception {
        Match match = new MatchResult(pastMatchLink);

        Team leftTeam;
        Team rightTeam;

        double[] attributes = new double[17];
        Map<String, String[]> map_perWin = new LinkedHashMap<>();

        int mapNumber = 1;

        for (String map : match.mapPick()) {
            if (map.equals("Default")) {
                mapNumber++;
                continue;
            }
            System.out.println("\nFirst team download...");
            leftTeam = new PastTeam(map, match, "left");
            System.out.println("\nSecond team download...");
            rightTeam = new PastTeam(map, match, "right");
            AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);

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
            attributes[9] = Precision.round(generator.ratingVStop10Attitude(), 3);
            attributes[10] = Precision.round(generator.ratingVStop20Attitude(), 3);
            attributes[11] = Precision.round(generator.ratingVStop30Attitude(), 3);
            attributes[12] = Precision.round(generator.ratingVStop50Attitude(), 3);
            attributes[13] = Precision.round(generator.totalKillsAttitude(), 3);
            attributes[14] = Precision.round(generator.mapsPlayedAttitude(), 3);
            attributes[15] = generator.rankingDifference();
            attributes[16] = match.mapPicker(mapNumber);
            mapNumber++;
            System.out.println("\nPython calling...");
            callPython(attributes);
            System.out.println("\nNext Map");
            map_perWin.put(map, new String[]{String.valueOf(getLeftPerc()), String.valueOf(getRightPerc())});
        }
        ImageEditor.fillImage(match, map_perWin);

    }

    public void loadAllUpcoming(String lifeMatchLink) throws Exception {
        FileWriter writer = new FileWriter("src/main/java/resultMatches/resultLinks.txt", true);
        writer.append("\n").append(lifeMatchLink).flush();

        Match match = new UpcomingMatch(lifeMatchLink);
        Team leftTeam;
        Team rightTeam;

        double[] attributes = new double[8];
        StringBuilder builder = new StringBuilder();
        for (String map : match.mapPick()) {
            System.out.println("\nFirst team download...");
            leftTeam = new LifeTeam(map, match, "left");
            System.out.println("\nSecond team download...");
            rightTeam = new LifeTeam(map, match, "right");
            AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);
            System.out.println("Generating attitude...");
            attributes[0] = Precision.round(generator.KDRatioAttitude(), 3);
            attributes[1] = Precision.round(generator.headshotAttitude(), 3);
            attributes[2] = Precision.round(generator.damagePerRoundAttitude(), 3);
            attributes[3] = Precision.round(generator.assistsPerRoundAttitude(), 3);
            attributes[4] = Precision.round(generator.impactAttitude(), 3);
            attributes[5] = Precision.round(generator.kastAttitude(), 3);
            attributes[6] = Precision.round(generator.openingKillRatioAttitude(), 3);
            attributes[7] = Precision.round(generator.rating3mAttitude(), 3);

            System.out.println("\nPython calling...");
            callPython(attributes);
            System.out.println("\nNext Map");
            builder.append(map.toUpperCase()).append("\n").append(leftTeam.getTeamName()).append(": ").append(getLeftPerc())
                    .append("\n").append(rightTeam.getTeamName()).append(": ").append(getRightPerc()).append("\n");
        }
        Bot.getBot().sendMessage(builder.toString());
    }

    public synchronized void callPython(double[] attributes) {
        String caller = "sh src/main/java/python/bashscript.sh "
                + attributes[0] + " "
                + attributes[1] + " "
                + attributes[2] + " "
                + attributes[3] + " "
                + attributes[4] + " "
                + attributes[5] + " "
                + attributes[6] + " "
                + attributes[7] + " "
                + attributes[8] + " "
                + attributes[9] + " "
                + attributes[10] + " "
                + attributes[11] + " "
                + attributes[12] + " "
                + attributes[13] + " "
                + attributes[14] + " "
                + attributes[15] + " "
                + attributes[16];

        if (attributes[0] == 0) {
            setLeftPerc("0.0", 1);
            setRightPerc("0.0", 1);
        } else {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", caller);

            try {
                Process process = processBuilder.start();
                StringBuilder output = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                int exitVal = process.waitFor();
                if (exitVal == 0) {
                    System.out.println(output);
                    setLeftPerc(output.toString(), 0);
                    setRightPerc(output.toString(), 0);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLeftPerc(String pyOutput, int a) {
        switch (a) {
            case 0:
                leftPerc = Double.parseDouble(pyOutput.substring(0, pyOutput.indexOf(':')));
                break;
            case 1:
                leftPerc = Double.parseDouble(pyOutput);
                break;
        }
    }

    private void setRightPerc(String pyOutput, int a) {
        switch (a) {
            case 0:
                rightPerc = Double.parseDouble(pyOutput.substring(pyOutput.indexOf(':') + 1));
                break;
            case 1:
                rightPerc = Double.parseDouble(pyOutput);
                break;
        }
    }

    private double getLeftPerc() {
        return leftPerc;
    }

    private double getRightPerc() {
        return rightPerc;
    }
}
