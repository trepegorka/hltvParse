package starter;

import HltvPath.Hltv;
import HltvPath.Logic;
import HltvPath.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;
import resultMatches.MatchResult;

import javax.print.Doc;
import java.io.*;


public class HltvBuilder {

    public static Document getHtml(String link) throws IOException, InterruptedException {
        try {
            Thread.sleep(1000);
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
        UserParser.setUserAgent(UserParser.getRandomAgent());
        Document hltvMainDoc = getHtml("https://www.hltv.org/");
        for (String matchLink : hltv.liveMatches(hltvMainDoc)) {
            System.out.println("\n****************DOWNLOADING****************\n");
            Document matchDoc = getHtml(matchLink);
            Document team1Doc = getHtml(hltv.getTeamLink1(matchDoc));
            Document team2Doc = getHtml(hltv.getTeamLink2(matchDoc));

            teamInit(hltv, matchDoc, team1Doc);
            teamInit(hltv, matchDoc, team2Doc);
            showStats(hltv, logic, matchDoc, team1Doc, team2Doc);
        }
    }

    private static void teamInit(Hltv hltv, Document matchDoc, Document teamDoc) throws Exception {
        for (String playerLink : hltv.PlayersLinks(teamDoc)) {
            Player player = new Player(hltv.getStatLink(playerLink));
            player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc));
        }
    }

    private static void showStats(Hltv hltv, Logic logic, Document matchDoc, Document team1Doc, Document team2Doc) throws Exception {
        System.out.println(hltv.getTeam1Name(matchDoc) + "  " +
                "vs  " + hltv.getTeam2Name(matchDoc));
        for (String map : hltv.mapPick(matchDoc)) {
            for (int i = 0; i < 30; i++) {
                System.out.print("-");
            }
            System.out.println("\n" + logic.calculateAdvantage(hltv, logic, matchDoc, team1Doc, team2Doc, map) + " " + map);
        }
    }
}
