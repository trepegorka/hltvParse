package HltvPath;

import org.jsoup.nodes.Document;
import starter.HltvBuilder;

import java.io.*;
import java.util.ArrayList;

public class Logic {
    public String calculateSumAdvantage(String advantage) {


        try {
            double kast = 0.0;

            double avarege_hs_perc = 0.0;
            double avarage_winfb = 0.0;
            double kill_per_round = 0.0;
            double avarege_damage_round = 0.0;


            ArrayList<Double> sum_advantage_list = new ArrayList<Double>();

            int val = 0;
            for (String parametr : advantage.split(",")) {
                val++;


                if (val == 11) {
                    break;
                } else if (val == 2) {
                    //dpr
                } else if (val == 3) {
                    kast = Double.parseDouble(parametr);
                    //kill assist round
                } else if (val == 5) {
                    //adr
                    avarege_damage_round = Double.parseDouble(parametr);

                } else if (val == 6) {
                    //Kill Per Rond
                    kill_per_round = Double.parseDouble(parametr);

                } else if (val == 7) {
                    //head shot perc
                    avarege_hs_perc = Double.parseDouble(parametr);

                } else if (val == 10) {
                    avarage_winfb = Double.parseDouble(parametr);
                    //win after fb perc
                } else sum_advantage_list.add(Double.parseDouble(parametr));

            }

            double sum = 0;
            for (Double i : sum_advantage_list) {
                sum += i;
            }
            String sum_rad2 = String.format("%.3f", sum).replace(",", ".");

            System.out.println(sum_advantage_list.size());

            System.out.println("H/S : " + avarege_hs_perc + "%");
            System.out.println("Win After FB: " + avarage_winfb + "%");
            System.out.println("Kill P/R: " + kill_per_round);
            System.out.println("KAST : " + kast);
            System.out.println("Avarage D/R: " + avarege_damage_round);

            return "static dvantage:" + sum_rad2;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String calculateAverageParam(ArrayList<String> player_list, String current_map, String main_path) throws IOException, IOException {
        ArrayList<ArrayList<Double>> players_matrix = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> rating = new ArrayList<Double>();
        ArrayList<Double> dpr = new ArrayList<Double>();
        ArrayList<Double> kast = new ArrayList<Double>();
        ArrayList<Double> impact = new ArrayList<Double>();
        ArrayList<Double> adr = new ArrayList<Double>();
        ArrayList<Double> kpr = new ArrayList<Double>();
        ArrayList<Double> hs = new ArrayList<Double>();
        ArrayList<Double> kd_ratio = new ArrayList<Double>();
        ArrayList<Double> ok_ratio = new ArrayList<Double>();
        ArrayList<Double> win_after_fb = new ArrayList<Double>();
        for (String i : player_list) {

            BufferedReader br = new BufferedReader(new FileReader(main_path + i + ".txt"));

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String map = line.substring(0, line.lastIndexOf(" Rating:"));
                    if (current_map.equals(map)) {
                        String str_rating = line.substring(line.lastIndexOf("Rating: ") + 8, line.lastIndexOf(" DPR:"));
                        if (Double.parseDouble(str_rating) == 0.00) {
                        } else {
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
                } catch (StringIndexOutOfBoundsException ignored) {
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


    public String calculateAdvantage(Hltv hltv, Document matchDoc, Document team1Doc, Document team2Doc, String map, boolean isLive, String filePath) throws Exception {

        String str_map = map.substring(0, map.length() - 1);

        String calc1 = calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(team1Doc)), str_map, filePath);
        String calc2 = calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(team2Doc)), str_map, filePath);

        ArrayList<Double> sumTeam1 = new ArrayList<Double>();
        ArrayList<Double> sumTeam2 = new ArrayList<Double>();

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

        ArrayList<Double> advantage = new ArrayList<Double>();

        for (int i = 0; i < sumTeam1.size(); i++) {
            advantage.add(sumTeam1.get(i) - sumTeam2.get(i));
        }

        StringBuilder builder = new StringBuilder();
        for (Double aDouble : advantage) {
            String sum = String.format("%.3f", aDouble).replace(",", ".");
            builder.append(sum).append(", ");
        }
        String returner = RatingKast(hltv, matchDoc, builder.toString(), hltv.getTeam1Name(matchDoc), hltv.getTeam2Name(matchDoc), map, isLive);
        if (returner.length() < 1) {
            return "";
        } else return returner;
    }


    private String RatingKast(Hltv hltv, Document matchDoc, String statLine, String team1Name, String team2Name, String map, boolean isLive) throws Exception {
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

        int a = hltv.mapPick(matchDoc, isLive).indexOf(map) + 1;
        if (isLive) {
            if (Double.parseDouble(rating) > 0 && Double.parseDouble(kast) > 0) {
                return getLiveMes(hltv, matchDoc, team1Name, map, isLive, rating, kast, a);

            } else if (Double.parseDouble(rating) < 0 && Double.parseDouble(kast) < 0) {
                return getLiveMes(hltv, matchDoc, team2Name, map, isLive, rating, kast, a);
            }
        } else {
            if (Double.parseDouble(rating) > 0 && Double.parseDouble(kast) > 0) {
                return getFutureMes(hltv, matchDoc, team1Name, map, isLive, rating, kast);

            } else if (Double.parseDouble(rating) < 0 && Double.parseDouble(kast) < 0) {
                return getFutureMes(hltv, matchDoc, team2Name, map, isLive, rating, kast);
            }
        }

        return null;
    }

    private String getFutureMes(Hltv hltv, Document matchDoc, String team1Name, String map, boolean isLive, String rating, String kast) throws Exception {
        String thisMap = hltv.mapPick(matchDoc, isLive).get(hltv.mapPick(matchDoc, isLive).indexOf(map));
        return ("\uD83D\uDD38" + "<b><em>" + team1Name + " win" + " " + thisMap.substring(0, thisMap.length() - 1) + "</em></b>" +
                "\n\t\t\t\t\t(" + String.format("%.2f", Double.parseDouble(rating)) + ", " + String.format("%.2f", Double.parseDouble(kast)) + ")");
    }

    private String getLiveMes(Hltv hltv, Document matchDoc, String team1Name, String map, boolean isLive, String rating, String kast, int a) throws Exception {
        String thisMap = hltv.mapPick(matchDoc, isLive).get(hltv.mapPick(matchDoc, isLive).indexOf(map));
        return ("\uD83D\uDD38"  + "<b><em>" + team1Name + " win" + " " + thisMap.substring(0, thisMap.length() - 1) + "</em>" + "[" + a + "]" + "</b>" +
                "\n\t\t\t\t\t(" + String.format("%.2f", Double.parseDouble(rating)) + ", " + String.format("%.2f", Double.parseDouble(kast)) + ")");
    }
}
