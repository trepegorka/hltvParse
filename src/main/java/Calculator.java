import general.General;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Calculator {
    static double bank = 300;

    public static void main(String[] args) throws Exception {

        ArrayList<Double> coefs1D = new ArrayList<>();
        ArrayList<Double> coefs2D = new ArrayList<>();
        ArrayList<Double> coefs3D = new ArrayList<>();
        ArrayList<Double> coefs4D = new ArrayList<>();


//        System.out.println(returnBankAfter());

        int a[] = new int[]
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1,
                        1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0,
                        1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1,
                        0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1,
                        0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1,
                        1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0,
                        0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0,
                        1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0,
                        1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1,
                        0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1
                };
        System.out.println(a.length);
        int value1 = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 1) {
                value1++;
            }
        }
        System.out.println(value1);
        System.out.println((double) value1 / a.length);
//        writeLinks(6, "https://www.hltv.org/results?offset=200");
//        writeLinks(5, "https://www.hltv.org/results?offset=200");
//        writeLinks(4, "https://www.hltv.org/results?offset=200");
//        writeLinks(3, "https://www.hltv.org/results?offset=200");
//        writeLinks(2, "https://www.hltv.org/results?offset=200");
//        writeLinks(1, "https://www.hltv.org/results?offset=200");
//        writeLinks(7, "https://www.hltv.org/results?offset=100");
//

    }

    private static Double returnBankAfter(ArrayList<Double> list) {
//        double bet = bank * 0.05;
        double bet = 15;
        for (double coeff : list) {
            bank = bank - bet;
            if (coeff != 0.0) {
                bank = bank + (bet * coeff);
            }
        }
        return bank;
    }

    private static void writeLinks(int a, String writeFrom) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        Elements links = General.getHtml(writeFrom).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.results > div.results-holder > div.results-all > div:nth-child(" + a + ")").select("a");
        for (Element el : links) {
            list.add(el.attr("abs:href"));
        }
        FileWriter writer = new FileWriter("src/main/java/result13.txt", true);
        for (String link : list) {
            writer.append(link).append("\n").flush();
        }
        writer.close();
    }
}



