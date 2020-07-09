package HltvPath;

import HltvPath.teams.Team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Logic {
//    public static String calculateSumAdvantage(String advantage) {
//
//
//        try {
//            double kast = 0.0;
//
//            double avarege_hs_perc = 0.0;
//            double avarage_winfb = 0.0;
//            double kill_per_round = 0.0;
//            double avarege_damage_round = 0.0;
//
//
//            ArrayList<Double> sum_advantage_list = new ArrayList<>();
//
//            int val = 0;
//            for (String parametr : advantage.split(",")) {
//                val++;
//
//
//                if (val == 11) {
//                    break;
//                } else if (val == 2) {
//                    //dpr
//                } else if (val == 3) {
//                    kast = Double.parseDouble(parametr);
//                    //kill assist round
//                } else if (val == 5) {
//                    //adr
//                    avarege_damage_round = Double.parseDouble(parametr);
//
//                } else if (val == 6) {
//                    //Kill Per Rond
//                    kill_per_round = Double.parseDouble(parametr);
//
//                } else if (val == 7) {
//                    //head shot perc
//                    avarege_hs_perc = Double.parseDouble(parametr);
//
//                } else if (val == 10) {
//                    avarage_winfb = Double.parseDouble(parametr);
//                    //win after fb perc
//                } else sum_advantage_list.add(Double.parseDouble(parametr));
//
//            }
//
//            double sum = 0;
//            for (Double i : sum_advantage_list) {
//                sum += i;
//            }
//            String sum_rad2 = String.format("%.3f", sum).replace(",", ".");
//
//            System.out.println(sum_advantage_list.size());
//
//            System.out.println("H/S : " + avarege_hs_perc + "%");
//            System.out.println("Win After FB: " + avarage_winfb + "%");
//            System.out.println("Kill P/R: " + kill_per_round);
//            System.out.println("KAST : " + kast);
//            System.out.println("Avarage D/R: " + avarege_damage_round);
//
//            return "static dvantage:" + sum_rad2;
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public static String calculateAverageParam(ArrayList<String> player_list, String current_map, String main_path) throws IOException {
        ArrayList<ArrayList<Double>> players_matrix = new ArrayList<>();
        ArrayList<Double> rating = new ArrayList<>();
        ArrayList<Double> dpr = new ArrayList<>();
        ArrayList<Double> kast = new ArrayList<>();
        ArrayList<Double> impact = new ArrayList<>();
        ArrayList<Double> adr = new ArrayList<>();
        ArrayList<Double> kpr = new ArrayList<>();
        ArrayList<Double> hs = new ArrayList<>();
        ArrayList<Double> kd_ratio = new ArrayList<>();
        ArrayList<Double> ok_ratio = new ArrayList<>();
        ArrayList<Double> win_after_fb = new ArrayList<>();
        for (String i : player_list) {

            BufferedReader br = new BufferedReader(new FileReader(main_path + i + ".txt"));

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String map = line.substring(0, line.lastIndexOf(" Rating:"));
                    if (current_map.equals(map)) {
                        String str_rating = line.substring(line.lastIndexOf("Rating: ") + 8, line.lastIndexOf(" DPR:"));
                        if (!(Double.parseDouble(str_rating) == 0.00)) {
                            rating.add(Double.parseDouble(str_rating));
                            String str_dpr = line.substring(line.lastIndexOf("DPR: ") + 5, line.lastIndexOf(" KAST:"));
                            dpr.add(Double.parseDouble(str_dpr));
                            String str_kast = line.substring(line.lastIndexOf("KAST: ") + 6, line.lastIndexOf("% IMPACT:"));
                            kast.add(Double.parseDouble(str_kast));
                            String str_impact = line.substring(line.lastIndexOf("IMPACT: ") + 8, line.lastIndexOf(" ADR"));
                            impact.add(Double.parseDouble(str_impact));
                            String str_adr = line.substring(line.lastIndexOf("ADR: ") + 5, line.lastIndexOf(" KPR:"));
                            adr.add(Double.parseDouble(str_adr));
                            String str_kpr = line.substring(line.lastIndexOf("KPR: ") + 5, line.lastIndexOf(" Headshot:"));
                            kpr.add(Double.parseDouble(str_kpr));
                            String str_hs = line.substring(line.lastIndexOf("Headshot: ") + 10, line.lastIndexOf("% K/D Ratio:"));
                            hs.add(Double.parseDouble(str_hs));
                            String str_kd_ratio = line.substring(line.lastIndexOf("K/D Ratio: ") + 11, line.lastIndexOf(" OpenKillRati"));
                            kd_ratio.add(Double.parseDouble(str_kd_ratio));
                            String str_ok_ratio = line.substring(line.lastIndexOf("KillRatio: ") + 11, line.lastIndexOf(" WinAfterFB: "));
                            ok_ratio.add(Double.parseDouble(str_ok_ratio));
                            String str_win_fb = line.substring(line.lastIndexOf("WinAfterFB: ") + 11, line.lastIndexOf("%"));
                            win_after_fb.add(Double.parseDouble(str_win_fb));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            br.close();
        }

        players_matrix.add(rating);
        players_matrix.add(dpr);
        players_matrix.add(kast);
        players_matrix.add(impact);
        players_matrix.add(adr);
        players_matrix.add(kpr);
        players_matrix.add(hs);
        players_matrix.add(kd_ratio);
        players_matrix.add(ok_ratio);
        players_matrix.add(win_after_fb);

        StringBuilder output = new StringBuilder();
        for (ArrayList<Double> playersMatrix : players_matrix) {
            double a1 = 0;
            for (Double matrix : playersMatrix) {
                a1 += matrix;
            }
            double sum_a = a1 / playersMatrix.size();
            String sum_rad2 = String.format("%.3f", sum_a).replace(",", ".");
            output.append(sum_rad2).append(", ");
        }
        return output.toString();
    }


    public static String calculateAdvantage(Team firstTeam, Team secondTeam, String map, boolean isLive, String filePath) throws Exception {

        String str_map = map.substring(0, map.length() - 1);

        String calc1 = calculateAverageParam(firstTeam.getPlayersNickNames(), str_map, filePath);
        String calc2 = calculateAverageParam(secondTeam.getPlayersNickNames(), str_map, filePath);

        ArrayList<Double> sumTeam1 = new ArrayList<>();
        ArrayList<Double> sumTeam2 = new ArrayList<>();

        try {
            int val = 0;
            for (String parametr : calc1.split(",")) {
                val++;
                if (val == 11) {
                    break;
                }
                sumTeam1.add(Double.parseDouble(parametr));
            }
            int val1 = 0;
            for (String parametr2 : calc2.split(",")) {
                val1++;
                if (val1 == 11) {
                    break;
                }
                sumTeam2.add(Double.parseDouble(parametr2));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ArrayList<Double> advantage = new ArrayList<>();

        for (int i = 0; i < sumTeam1.size(); i++) {
            advantage.add(sumTeam1.get(i) - sumTeam2.get(i));
        }

        StringBuilder builder = new StringBuilder();
        for (Double aDouble : advantage) {
            String sum = String.format("%.3f", aDouble).replace(",", ".");
            builder.append(sum).append(", ");
        }

        String returner = RatingKast(firstTeam, secondTeam, builder.toString(), map, isLive);
        if (returner.length() < 1) {
            return "";
        } else return returner;
    }


    private static String RatingKast(Team firstTeam, Team secondTeam, String statLine, String map, boolean isLive) throws Exception {
        int value = 0;

        String rating = "";
        String kast = "";

        for (String parametr : statLine.split(",")) {
            if (value == 0) {
                rating = parametr;
            } else if (value == 2) {
                kast = parametr;
            }
            value++;
        }

        int a = Hltv.mapPick(isLive).indexOf(map) + 1;
        boolean firstWinMap = Double.parseDouble(rating) > 0 && Double.parseDouble(kast) > 0;
        boolean secondWinMap = Double.parseDouble(rating) < 0 && Double.parseDouble(kast) < 0;
        if (isLive) {
            if (firstWinMap) {
                return getLiveMes(firstTeam.getTeamName(Team.matchDoc), map, rating, kast, a);

            } else if (secondWinMap) {
                return getLiveMes(secondTeam.getTeamName(Team.matchDoc), map, rating, kast, a);
            }
        } else {
            if (firstWinMap) {
                return getFutureMes(firstTeam.getTeamName(Team.matchDoc), map, rating, kast);

            } else if (secondWinMap) {
                return getFutureMes(secondTeam.getTeamName(Team.matchDoc), map, rating, kast);
            }
        }

        return null;
    }

    private static String getFutureMes(String team1Name, String map, String rating, String kast) {
        String thisMap = Hltv.mapPick(false).get(Hltv.mapPick(false).indexOf(map));
        return ("\uD83D\uDD38" + "<b><em>" + team1Name + " win" + " " + thisMap.substring(0, thisMap.length() - 1) + "</em></b>" +
                "\n\t\t\t\t\t(" + String.format("%.2f", Double.parseDouble(rating)) + ", " + String.format("%.2f", Double.parseDouble(kast)) + ")");
    }

    private static String getLiveMes(String team1Name, String map, String rating, String kast, int a) {
        String thisMap = Hltv.mapPick(true).get(Hltv.mapPick(true).indexOf(map));
        return ("\uD83D\uDD38" + "<b><em>" + team1Name + " win" + " " + thisMap.substring(0, thisMap.length() - 1) + "</em>" + "[" + a + "]" + "</b>" +
                "\n\t\t\t\t\t(" + String.format("%.2f", Double.parseDouble(rating)) + ", " + String.format("%.2f", Double.parseDouble(kast)) + ")");
    }
}
