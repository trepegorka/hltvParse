package HltvPath;

import java.io.*;
import java.util.ArrayList;

public class Calculator {
    public String calculateAverageParam(ArrayList<String> player_list,String current_map) throws IOException, IOException {
        String main_path = "src\\main\\java\\players\\";
        ArrayList<ArrayList<Double>> players_matrix  = new ArrayList<>();
        ArrayList<Double> rating = new ArrayList<>();
        ArrayList<Double> dpr = new ArrayList<>();
        ArrayList<Double> kast = new ArrayList<>();
        ArrayList<Double> impact = new ArrayList<>();
        ArrayList<Double> adr = new ArrayList<>();
        ArrayList<Double> kpr = new ArrayList<>();
        ArrayList<Double> hs = new ArrayList<>();
        ArrayList<Double> kd_ratio = new ArrayList<>();
        for (String i: player_list){
            try{
                BufferedReader br = new BufferedReader(new FileReader(main_path+i+".txt"));
                br.readLine();
            }
            catch (FileNotFoundException e){
               e.printStackTrace();
            }
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
                        String str_kd_ratio = line.substring(line.lastIndexOf("K/D Ratio: ")+11);
                        kd_ratio.add(Double.parseDouble(str_kd_ratio));
                    }
                }
                catch (StringIndexOutOfBoundsException e){
                    continue;
                }
            }
        }
        players_matrix.add(rating);
        players_matrix.add(dpr);
        players_matrix.add(kast);
        players_matrix.add(impact);
        players_matrix.add(adr);
        players_matrix.add(kpr);
        players_matrix.add(hs);
        players_matrix.add(kd_ratio);
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
}
