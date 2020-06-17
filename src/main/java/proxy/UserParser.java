package proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

public class UserParser {

    public static String userAgent = "";

    private Document getProxyListNetHtml() throws IOException {
        return Jsoup.connect("https://free-proxy-list.net")
                .referrer("http://www.google.com")
                .get();
    }

    public ArrayList<String> getRandomIpAndPort() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Elements ipAndPort = getProxyListNetHtml().select("#proxylisttable > tbody > tr:nth-child(" + i + ")");
            list.add(ipAndPort.select("td:nth-child(1)").text());
            list.add(ipAndPort.select("td:nth-child(2)").text());
        }
        Random random = new Random();
        int a = random.nextInt((list.size() - 1));
        if (a % 2 != 0) {
            a++;
        }
        ArrayList<String> retList = new ArrayList<>();
        retList.add(list.get(a));
        retList.add(list.get(a + 1));
        return retList;
    }

    public static String getRandomAgent() throws FileNotFoundException {
        File f = new File("src\\main\\java\\proxy\\UserAgents");
        String result = null;
        Random rand = new Random();
        int n = 0;
        for (Scanner sc = new Scanner(f); sc.hasNext(); ) {
            ++n;
            String line = sc.nextLine();
            if (rand.nextInt(n) == 0)
                result = line;
        }
        return result;
    }

    public static void SetRandomAgent(String agent) {
        userAgent = agent;
    }

    public void stripDuplicatesFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Set<String> lines = new HashSet<String>(10000); // maybe should be bigger
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String unique : lines) {
            writer.write(unique);
            writer.newLine();
        }
        writer.close();
    }
}
