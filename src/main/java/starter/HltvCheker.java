package starter;

import HltvPath.Hltv;
import HltvPath.Logic;
import HltvPath.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;
import resultMatches.MatchResult;

import java.io.*;
import java.util.ArrayList;

public class HltvCheker {

    public static Document getHtml(String link) throws IOException, InterruptedException {
        try {
            Thread.sleep(1000);
            //String proxy = parser.getRandomProxy();
            //String ip = proxy.substring(0, proxy.lastIndexOf(":"));
            //String port = proxy.substring(proxy.lastIndexOf(":") + 1);

            Thread.sleep(1000);
//            System.out.println(UserParser.userAgent);
            return Jsoup.connect(link)
                    .referrer("https://www.hltv.org/")
                    .userAgent(UserParser.userAgent)
                    .ignoreHttpErrors(true)
                    // .proxy(ip, Integer.parseInt(port))
                    .get();
        } catch (java.net.SocketTimeoutException ste) {
            System.out.println("timeout");
        } catch (javax.net.ssl.SSLHandshakeException ssle) {
            System.out.println("ssl error");
            ssle.printStackTrace();
        } catch (java.io.IOException ioe) {
            Thread.sleep(5000);
            ioe.printStackTrace();
            System.out.println("probably bad proxy");
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return null;
    }


    public static void start() throws Exception {
        Hltv hltv = new Hltv();
        Logic logic = new Logic();
        String main_path = "src/main/java/players/";


        PrintWriter out = new PrintWriter(new FileWriter("Winrate", true));


        UserParser.setUserAgent(UserParser.getRandomAgent());
        Document hltvMainDoc = getHtml("https://www.hltv.org/results");
        for (String matchLink : hltv.resultMatches(hltvMainDoc)) {
            Document matchDoc = getHtml(matchLink);

            System.out.println(hltv.resultMatches(hltvMainDoc).size());


            Document team1Doc = getHtml(hltv.getTeamLink1(matchDoc));

            for (String playerLink : hltv.PlayersLinks(team1Doc)) {


                System.out.println(hltv.PlayersLinks(team1Doc).size());
                String https_link = "";
                String hltv_link = "";
                String id = "";
                String name = "";
                int a = 0;
                for (String parametr : playerLink.split("/")) {
                    a++;
                    if (a == 1) {
                        https_link = parametr;
                    } else if (a == 3) {
                        hltv_link = parametr;
                    } else if (a == 5) {
                        id = parametr;
                    } else if (a == 6) {
                        name = parametr;
                    }
                }
                try {


                    BufferedReader br = new BufferedReader(new FileReader(main_path + name + ".txt"));
                    String line;


                    ArrayList<String> haveMap1 = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        String map = line.substring(0, line.lastIndexOf("Rating:"));
                        haveMap1.add(map);
                    }
                    ArrayList<String> h = hltv.mapPick(matchDoc);

                    haveMap1.sort(String::compareToIgnoreCase);

                    h.sort(String::compareToIgnoreCase);


                    if (haveMap1.equals(h)) {
                        continue;
                    } else {
                        System.out.println("Cant find Player Map in DB");
                        System.out.println("Downloading...");
                        String main_player_stat_link = https_link + "//" + hltv_link + "/" + "stats/" + "players/" + id + "/" + name;
                        Player player = new Player(main_player_stat_link);
                        player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc));
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Cant Find Player In DB");
                    System.out.println("Downloading...");
                    String main_player_stat_link = https_link + "//" + hltv_link + "/" + "stats/" + "players/" + id + "/" + name;
                    Player player = new Player(main_player_stat_link);
                    player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc));

                }

            }
            //TEAM2

            UserParser.setUserAgent(UserParser.getRandomAgent());
            Document team2Doc = getHtml(hltv.getTeamLink2(matchDoc));
            System.out.println("team 2");
            for (String playerLink : hltv.PlayersLinks(team2Doc)) {

                String https_link = "";
                String hltv_link = "";
                String id = "";
                String name = "";
                int a = 0;
                for (String parametr : playerLink.split("/")) {
                    a++;
                    if (a == 1) {
                        https_link = parametr;
                    } else if (a == 3) {
                        hltv_link = parametr;
                    } else if (a == 5) {
                        id = parametr;
                    } else if (a == 6) {
                        name = parametr;
                    }
                }
                try {
                    System.out.println(name);
                    BufferedReader br = new BufferedReader(new FileReader(main_path + name + ".txt"));
                    String line;
                    ArrayList<String> haveMap2 = new ArrayList<>();
                    ArrayList<String> h = hltv.mapPick(matchDoc);

                    while ((line = br.readLine()) != null) {
                        String map = line.substring(0, line.lastIndexOf("Rating:"));
                        haveMap2.add(map);
                    }


                    haveMap2.sort(String::compareToIgnoreCase);

                    h.sort(String::compareToIgnoreCase);


                    if (haveMap2.equals(h)) {
                        continue;
                    } else {
                        System.out.println("not equals");
                        System.out.println("Cant Player Map In DB");
                        System.out.println("Downloading...");
                        String main_player_stat_link = https_link + "//" + hltv_link + "/" + "stats/" + "players/" + id + "/" + name;
                        Player player = new Player(main_player_stat_link);
                        player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc));
                    }

                    br.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Cant Find Player In DB");
                    System.out.println("Downloading...");
                    String main_player_stat_link = https_link + "//" + hltv_link + "/" + "stats/" + "players/" + id + "/" + name;
                    Player player = new Player(main_player_stat_link);
                    player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc));

                }

            }

            System.out.println(hltv.getTeamLink1(matchDoc).substring(hltv.getTeamLink1(matchDoc).lastIndexOf("/") + 1).toUpperCase() + "  " +
                    "vs  " + hltv.getTeamLink2(matchDoc).substring(hltv.getTeamLink2(matchDoc).lastIndexOf("/") + 1).toUpperCase());
            int mapGett = 0;
            for (String map : hltv.mapPick(matchDoc)) {

                for (int i = 0; i < 75; i++) {
                    System.out.print("-");
                }
                String str_map = map.substring(0, map.length() - 1);
                String calc1 = logic.calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(team1Doc)), str_map);

                String calc2 = logic.calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(team2Doc)), str_map);

                System.out.println("\n" + map + " " + calc1);
                System.out.println(map + " " + calc2);
                System.out.println("-_-_-_-_-_-_-_-_-_-_-_-ADVANTAGE-_-_-_-_-_-_-_-_-_-_-_-");
                System.out.println(map + " " + logic.calculateAdvantage(calc1, calc2));
                System.out.println(map + " " + logic.calculateSumAdvantage(logic.calculateAdvantage(calc1, calc2)));
                try {
                    System.out.println(MatchResult.getMapWinners(matchDoc).get(mapGett));
                    out.println(map + " " + logic.calculateSumAdvantage(logic.calculateAdvantage(calc1, calc2))+" "+MatchResult.getMapWinners(matchDoc).get(mapGett));
                    mapGett++;
                    out.close();
                }
                catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                    continue;
                }
            }
            for (int j = 0; j < 100; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
