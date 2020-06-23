package starter;

import HltvPath.Hltv;
import HltvPath.Logic;
import HltvPath.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;
import resultMatches.MatchResult;

import java.io.*;


public class HltvCheker {

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
        Document hltvMainDoc = getHtml("https://www.hltv.org/results?offset=100");
        for (String matchLink : hltv.resultMatches(hltvMainDoc)) {
            System.out.println("\n****************DOWNLOADING****************\n");
            Document matchDoc = getHtml(matchLink);
            Document team1Doc = getHtml(hltv.getTeamLink1(matchDoc));
            Document team2Doc = getHtml(hltv.getTeamLink2(matchDoc));

            teamInit(hltv, matchDoc, team1Doc);
            teamInit(hltv, matchDoc, team2Doc);

            showResults(hltv, logic, matchDoc, team1Doc, team2Doc);
        }
    }

    private static void teamInit(Hltv hltv, Document matchDoc, Document teamDoc) throws Exception {
        for (String playerLink : hltv.PlayersLinks(teamDoc)) {
            Player player = new Player(hltv.getStatLink(playerLink));
            player.loadPlayerMapsStatsToFile(hltv.mapPick(matchDoc));
        }
    }

    private static void showResults(Hltv hltv, Logic logic, Document matchDoc, Document team1Doc, Document team2Doc) throws Exception {
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
//            System.out.println(map + " " + logic.calculateAdvantage(calc1, calc2));
//            System.out.println(map + " " + logic.calculateSumAdvantage(logic.calculateAdvantage(calc1, calc2)));
            try {
                PrintWriter out = new PrintWriter(new FileWriter("Winrate", true));
                System.out.println(MatchResult.getMapWinners(matchDoc).get(mapGett));
//                out.println(logic.calculateAdvantage(calc1, calc2) + " " + MatchResult.getMapWinners(matchDoc).get(mapGett));
                mapGett++;
                out.close();
                UserParser.stripDuplicatesFromFile("Winrate");
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }
        for (int j = 0; j < 100; j++) {
            System.out.print("*");
        }
        System.out.println();
    }


}

