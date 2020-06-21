package HltvPath;

import java.io.*;
import java.util.ArrayList;

public class Logic {
    public String calculateSumAdvantage(String advantage){


        try {
            double kast = 0.0;

            double avarege_hs_perc = 0.0;
            double avarage_winfb = 0.0;
            double kill_per_round = 0.0;
            double avarege_damage_round = 0.0;


            ArrayList<Double> sum_advantage_list  = new ArrayList<>();

            int val = 0;
            for (String parametr : advantage.split(",")) {
                val++;


                if (val==11){
                    break;
                }
                else if (val==2){
                    //dpr
                    continue;
                }
                else if (val==3){
                    kast=Double.parseDouble(parametr);
                    //kill assist round
                    continue;
                }
                else if (val==5){
                    //adr
                    avarege_damage_round=Double.parseDouble(parametr);
                    continue;

                }
                else if (val==6){
                    //Kill Per Rond
                    kill_per_round=Double.parseDouble(parametr);
                    continue;

                }
                else if (val==7){
                    //head shot perc
                    avarege_hs_perc=Double.parseDouble(parametr);

                    continue;
                }
                else if (val==10){
                    avarage_winfb=Double.parseDouble(parametr);
                    //win after fb perc
                    continue;
                }
                else sum_advantage_list.add(Double.parseDouble(parametr));

            }

            double sum = 0;
            for (Double i:sum_advantage_list){
                sum+=i;
            }
            String sum_rad2 = String.format("%.3f", sum).replace(",", ".");

            System.out.println(sum_advantage_list.size());

            System.out.println("H/S : "+avarege_hs_perc+"%");
            System.out.println("Win After FB: "+avarage_winfb+"%");
            System.out.println("Kill P/R: "+kill_per_round);
            System.out.println("KAST : "+kast);


            System.out.println("Avarage D/R: "+avarege_damage_round);



            return "static dvantage:"+sum_rad2;

        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }

        return null;
    }
    public String calculateAverageParam(ArrayList<String> player_list,String current_map) throws IOException, IOException {
        String main_path = "src/main/java/players/";
        ArrayList<ArrayList<Double>> players_matrix  = new ArrayList<>();
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
        for (String i: player_list){

            BufferedReader br = new BufferedReader(new FileReader(main_path+i+".txt"));

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String map = line.substring(0, line.lastIndexOf(" Rating:"));
                    if (current_map.equals(map)) {
                        String str_rating = line.substring(line.lastIndexOf("Rating: ") + 8, line.lastIndexOf(" DPR:"));
                        rating.add(Double.parseDouble(str_rating));
                        String str_dpr = line.substring(line.lastIndexOf("DPR: ")+5, line.lastIndexOf(" KAST:"));
                        dpr.add(Double.parseDouble(str_dpr));
                        String str_kast = line.substring(line.lastIndexOf("KAST: ")+6, line.lastIndexOf("% IMPACT:"));
                        kast.add(Double.parseDouble(str_kast));
                        String str_impact = line.substring(line.lastIndexOf("IMPACT: ")+8, line.lastIndexOf(" ADR"));
                        impact.add(Double.parseDouble(str_impact));
                        String str_adr = line.substring(line.lastIndexOf("ADR: ")+5, line.lastIndexOf(" KPR:"));
                        adr.add(Double.parseDouble(str_adr));
                        String str_kpr = line.substring(line.lastIndexOf("KPR: ")+5, line.lastIndexOf(" Headshot:"));
                        kpr.add(Double.parseDouble(str_kpr));
                        String str_hs = line.substring(line.lastIndexOf("Headshot: ")+10, line.lastIndexOf("% K/D Ratio:"));
                        hs.add(Double.parseDouble(str_hs));
                        String str_kd_ratio = line.substring(line.lastIndexOf("K/D Ratio: ")+11,line.lastIndexOf(" OpenKillRati"));
                        kd_ratio.add(Double.parseDouble(str_kd_ratio));
                        String str_ok_ratio = line.substring(line.lastIndexOf("KillRatio: ")+11,line.lastIndexOf(" WinAfterFB: "));
                        ok_ratio.add(Double.parseDouble(str_ok_ratio));
                        String str_win_fb = line.substring(line.lastIndexOf("WinAfterFB: ")+11,line.lastIndexOf("%"));
                        win_after_fb.add(Double.parseDouble(str_win_fb));
                    }
                }
                catch (StringIndexOutOfBoundsException e){
                    continue;
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
        for (int i = 0; i <players_matrix.size() ; i++) {
            double a1 = 0;
            for (int j = 0; j <players_matrix.get(i).size() ; j++) {
                a1+=players_matrix.get(i).get(j);
            }
            double sum_a = a1/players_matrix.get(i).size();
            String sum_rad2 = String.format("%.3f", sum_a).replace(",", ".");
            output.append(sum_rad2+", ");
        }
        return output.toString();
    }

    public String calculateAdvantage(String team_param_1 , String team_param_2) throws IOException {



        ArrayList<Double> sum_team_1 = new ArrayList<>();
        ArrayList<Double> sum_team_2 = new ArrayList<>();

        try {
            int val = 0;
            for (String parametr : team_param_1.split(",")) {
                val++;
                if (val==11){
                    break;
                }
                sum_team_1.add(Double.parseDouble(parametr));
            }
            int val1 = 0;
            for (String parametr2 : team_param_2.split(",")) {
                val1++;
                if(val1==11){
                    break;
                }
                sum_team_2.add(Double.parseDouble(parametr2));
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }

        ArrayList<Double> advantage = new ArrayList<>();


        for (int i = 0; i <sum_team_1.size() ; i++) {
            advantage.add(sum_team_1.get(i)-sum_team_2.get(i));
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <advantage.size() ; i++) {
            String sum = String.format("%.3f", advantage.get(i)).replace(",", ".");
            builder.append(sum+", ");
        }
            return builder.toString();
    }


}