package starter;

import Bot.Bot;
import HltvPath.Hltv;
import HltvPath.Logic;
import HltvPath.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;


public class HltvBuilder {

    public static Document getHtml(String link) throws IOException, InterruptedException {
        try {
            System.out.println("New request... ");
            //String proxy = parser.getRandomProxy();
            //String ip = proxy.substring(0, proxy.lastIndexOf(":"));
            //String port = proxy.substring(proxy.lastIndexOf(":") + 1);
            Thread.sleep(1000);
            return Jsoup.connect(link)
                    .referrer("https://www.hltv.org/")
                    .userAgent(UserParser.userAgent)
                    .ignoreHttpErrors(true)
                    // .proxy(ip, Integer.parseInt(port))
                    .get();
        } catch (java.net.SocketTimeoutException ste) {
            System.out.println("timeout");
            ste.printStackTrace();
        } catch (javax.net.ssl.SSLHandshakeException ssle) {
            System.out.println("ssl error");
            ssle.printStackTrace();
        } catch (java.io.IOException ioe) {
            Thread.sleep(5000);
            ioe.printStackTrace();
            System.out.println("probably bad proxy");
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
        return null;
    }

    public static void start() throws Exception {
        Hltv hltv = new Hltv();
        Logic logic = new Logic();
        Document hltvMainDoc = getHtml("https://www.hltv.org/");
        Map<String, Boolean> matches = new LinkedHashMap<>(hltv.allMatches(hltvMainDoc));
        doAction(matches, hltv, logic);
        hltv.reloadMatches(matches, hltv, logic);
    }

    public static void doAction(Map<String, Boolean> matches, Hltv hltv, Logic logic) throws Exception {
        UserParser.setUserAgent(UserParser.getRandomAgent());
        for (Map.Entry<String, Boolean> matchLink : matches.entrySet()) {
            StringBuilder buildMessage = new StringBuilder();
            System.out.println("\n****************DOWNLOADING****************");
            Document matchDoc = getHtml(matchLink.getKey());
            Document team1Doc = getHtml(hltv.getTeamLink1(matchDoc));
            Document team2Doc = getHtml(hltv.getTeamLink2(matchDoc));

            //teamInit
            for (String playerLink : hltv.PlayersLinks(team1Doc)) {
                System.out.println("New Player initialisation");
                Player player = new Player(hltv.getStatLink(playerLink));
                player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc, matchLink.getValue()));
            }
            for (String playerLink : hltv.PlayersLinks(team2Doc)) {
                System.out.println("New Player initialisation");
                Player player = new Player(hltv.getStatLink(playerLink));
                player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc, matchLink.getValue()));
            }
            //.

            //teamNames
            buildMessage.append(hltv.getMatchTime(matchDoc, matchLink.getValue()));
            buildMessage.append("<b>").append(hltv.getTeam1Name(matchDoc).toUpperCase()).append("</b>").append(" \uD83C\uDD9A ")
                    .append("<b>").append(hltv.getTeam2Name(matchDoc).toUpperCase()).append("</b>").append("\n");
            //.

            for (String map : hltv.mapPick(matchDoc, matchLink.getValue())) {
                try {
                    String advantage = logic.calculateAdvantage(hltv, matchDoc, team1Doc, team2Doc, map, matchLink.getValue(), "src\\main\\java\\players\\");
                    String advantage3m = logic.calculateAdvantage(hltv, matchDoc, team1Doc, team2Doc, map, matchLink.getValue(), "src\\main\\java\\players3month\\");
                    advantage3m = advantage3m.substring(advantage3m.indexOf("("));
                    for (int i = 0; i < 30; i++) {
                        System.out.print("-");
                    }
                    System.out.println("\n" + advantage);
                    buildMessage.append("\n").append(advantage).append("\n").append("\t\t\t\t\t"+advantage3m).append("\n");
                } catch (Exception ignored) {
                }
            }
            for (int i = 0; i < 30; i++) {
                System.out.print("-");
            }
            Bot.setMessage(buildMessage.toString());
            Bot.sendMessage();
        }
    }
}
