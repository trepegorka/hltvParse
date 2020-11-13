package general;

import Images.ImageEditor;
import general.logics.AdvantageGenerator;
import hltv.matches.Match;
import hltv.matches.teams.Team;
import org.apache.commons.math3.util.Precision;
import telegramBot.Bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


public class ThreadMatch extends Thread {
    private Match match;
    private static double leftPerc;
    private static double rightPerc;

    public ThreadMatch(Match match) {
        this.match = match;
    }

    @Override
    public void run() {
        Team leftTeam;
        Team rightTeam;

        double[] attributes = new double[8];
        Map<String, String[]> map_perWin = new LinkedHashMap<>();

        StringBuilder info = new StringBuilder();

        try {
            while (true) {
                match = match.updatedDoc();
                if (match.mapPick().get(0).equals("TBA")) {
                    Thread.sleep(10000);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (String map : match.mapPick()) {

                System.out.println("\nTHREAD First team download...");
                leftTeam = new Team(match.getFirstTeamLink(), map); // Team 0 downloading left
                System.out.println("\nTHREAD Second team download...");
                rightTeam = new Team(match.getSecondTeamLink(), map); // Team 1 downloading right
                AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);

                System.out.println("THREAD Generating attitude...");
                attributes[0] = Precision.round(generator.KDRatioAttitude(), 3);
                attributes[1] = Precision.round(generator.headshotAttitude(), 3);
                attributes[2] = Precision.round(generator.damagePerRoundAttitude(), 3);
                attributes[3] = Precision.round(generator.assistsPerRoundAttitude(), 3);
                attributes[4] = Precision.round(generator.impactAttitude(), 3);
                attributes[5] = Precision.round(generator.kastAttitude(), 3);
                attributes[6] = Precision.round(generator.openingKillRatioAttitude(), 3);
                attributes[7] = Precision.round(generator.rating3mAttitude(), 3);

                System.out.println("\nTHREAD Python calling...");
                callPython(attributes);
                System.out.println("\nTHREAD Next Map");
                map_perWin.put(map, new String[]{String.valueOf(getLeftPerc()), String.valueOf(getRightPerc())});
                info.append(map).append(": ").append(Arrays.toString(attributes)).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ImageEditor.fillImage(match, map_perWin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bot.getBot().setMessage(info.toString());
        Bot.getBot().sendMessage();
        Bot.getBot().sendPhoto("src/main/java/Images/imageLibrary/result.png");
    }


    private static void callPython(double[] attributes) {
        String caller = "sh src/main/java/bashscript.sh "
                + attributes[0] + " "
                + attributes[1] + " "
                + attributes[2] + " "
                + attributes[3] + " "
                + attributes[4] + " "
                + attributes[5] + " "
                + attributes[6] + " "
                + attributes[7];

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

    private static void setLeftPerc(String pyOutput, int a) {
        switch (a) {
            case 0:
                leftPerc = Double.parseDouble(pyOutput.substring(0, pyOutput.indexOf(':')));
                break;
            case 1:
                leftPerc = Double.parseDouble(pyOutput);
                break;
        }
    }

    private static void setRightPerc(String pyOutput, int a) {
        switch (a) {
            case 0:
                rightPerc = Double.parseDouble(pyOutput.substring(pyOutput.indexOf(':') + 1));
                break;
            case 1:
                rightPerc = Double.parseDouble(pyOutput);
                break;
        }
    }

    private static double getLeftPerc() {
        return leftPerc;
    }

    private static double getRightPerc() {
        return rightPerc;
    }
}
