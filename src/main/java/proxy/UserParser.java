package proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserParser{
    public static void main(String[] args) throws IOException {
//        deleteDuplicatesMapsFromDirectory();
    deleteDuplicatesMap("Winrate.txt");
    }

    public static String userAgent = "";


    static {
        try {
            userAgent = getRandomAgent();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Document getProxyListNetHtml() throws IOException {
        return Jsoup.connect("https://free-proxy-list.net")
                .userAgent(userAgent)
                .referrer("http://www.google.com")
                .get();
    }

    public String getRandomProxy() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Document doc = getProxyListNetHtml();
        for (int i = 1; i < 70; i++) {

            Elements ipAndPort = doc.select("#proxylisttable > tbody > tr:nth-child(" + i + ")");

            if(ipAndPort.select("tr:nth-child("+i+") >td.hx").text().equals("no")){
               list.add(ipAndPort.select("td:nth-child(1)").text() + ":" + ipAndPort.select("td:nth-child(2)").text());
           }
        }
        Random random = new Random();
        int a = random.nextInt((list.size() - 1));
        return list.get(a);
    }


    public static void setUserAgent(String userAgent) {
        UserParser.userAgent = userAgent;
    }

    public static String getRandomAgent() throws FileNotFoundException {
        File f = new File("src/main/java/proxy/UserAgents");
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

    public static void stripDuplicatesFromFile(String filename) throws IOException {
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

    public static void deleteDuplicatesMap(String filename) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> write = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String map = line.substring(0,line.lastIndexOf(" Rating:"));
            if (lines.contains(map)){

            } else {
                write.add(line);
                lines.add(map);
            }
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String unique : write) {
            writer.write(unique);
            writer.newLine();
        }
        writer.close();
    }

    public static void deleteDuplicatesMapsFromDirectory() throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get("src\\main\\java\\players"))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());

            for(String i : result){
                deleteDuplicatesMap(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
