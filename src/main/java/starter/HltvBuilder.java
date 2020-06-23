package starter;

import HltvPath.Hltv;
import HltvPath.Logic;
import HltvPath.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;

import java.io.*;
import java.util.ArrayList;


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

    public static ArrayList<String> start() throws Exception {
        Hltv hltv = new Hltv();
        Logic logic = new Logic();
        UserParser.setUserAgent(UserParser.getRandomAgent());
        Document hltvMainDoc = getHtml("https://www.hltv.org/");
        ArrayList<String> list = new ArrayList<String>();
        for (String matchLink : hltv.liveMatches(hltvMainDoc)) {
            System.out.println("\n****************DOWNLOADING****************");
            Document matchDoc = getHtml(matchLink);
            Document team1Doc = getHtml(hltv.getTeamLink1(matchDoc));
            Document team2Doc = getHtml(hltv.getTeamLink2(matchDoc));


            teamInit(hltv, matchDoc, team1Doc);
            teamInit(hltv, matchDoc, team2Doc);
            list.add(hltv.getTeam1Name(matchDoc)+" vs "+hltv.getTeam2Name(matchDoc));
            for (String map : hltv.mapPick(matchDoc)) {
                try {
                    for (int i = 0; i < 30; i++) {
                        System.out.print("-");
                    }
                    System.out.println("\n" + logic.calculateAdvantage(hltv, logic, matchDoc, team1Doc, team2Doc, map));
                    list.add(logic.calculateAdvantage(hltv, logic, matchDoc, team1Doc, team2Doc, map));
                } catch (Exception ignored) {
                }
            }
            for(String i: list){
                System.out.println(i);
            }
            for (int i = 0; i < 30; i++) {
                System.out.print("-");
            }
        }

        return list;
    }

    private static void teamInit(Hltv hltv, Document matchDoc, Document teamDoc) throws Exception {
        for (String playerLink : hltv.PlayersLinks(teamDoc)) {
            Player player = new Player(hltv.getStatLink(playerLink));
            player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc));
        }
    }

}
