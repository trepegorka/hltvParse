package starter;

import HltvPath.Calculator;
import HltvPath.Hltv;
import HltvPath.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;

import java.io.FileNotFoundException;
import java.io.IOException;

public class HltvBuilder {

    private static Document getHtml(String link) throws IOException {
        return Jsoup.connect(link)
                .userAgent(UserParser.userAgent)
                .referrer("http://www.google.com")
//                .proxy("86.123.166.109", 8080)
                .get();
    }

    public static void start() throws Exception {
        Hltv hltv = new Hltv();
        Calculator calculator = new Calculator();
        UserParser.SetRandomAgent(UserParser.getRandomAgent());
        for (String matchLink : hltv.liveMatches(getHtml("https://www.hltv.org"))) {
            System.out.println(hltv.getTeamLink1(getHtml(matchLink)).substring(hltv.getTeamLink1(getHtml(matchLink)).lastIndexOf("/") + 1) + "  " +
                    "vs  " + hltv.getTeamLink2(getHtml(matchLink)).substring(hltv.getTeamLink2(getHtml(matchLink)).lastIndexOf("/") + 1));

            System.out.println("\nteam1: " + hltv.getTeamLink1(getHtml(matchLink)).substring(hltv.getTeamLink1(getHtml(matchLink)).lastIndexOf("/") + 1));
            UserParser.SetRandomAgent(UserParser.getRandomAgent());
            for (String playerLink : hltv.PlayersLinks(getHtml(hltv.getTeamLink1(getHtml(matchLink))))) {
                Player player = new Player(hltv.statLink(getHtml(playerLink)));
                player.loadPlayerMapsStatsToFile(hltv.mapPick(getHtml(matchLink)), getHtml(hltv.statLink(getHtml(playerLink))));
            }
            UserParser.SetRandomAgent(UserParser.getRandomAgent());
            for (String map : hltv.mapPick(getHtml(matchLink))) {
                System.out.println(map + " " + calculator.calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(getHtml(hltv.getTeamLink1(getHtml(matchLink))))), map.substring(0, map.length() - 1)));
            }

            /*NEW TEAM*/

            System.out.println("\nteam2: " + hltv.getTeamLink2(getHtml(matchLink)).substring(hltv.getTeamLink2(getHtml(matchLink)).lastIndexOf("/") + 1));
            for (String playerLink : hltv.PlayersLinks(getHtml(hltv.getTeamLink2(getHtml(matchLink))))) {
                Player player = new Player(hltv.statLink(getHtml(playerLink)));
                player.loadPlayerMapsStatsToFile(hltv.mapPick(getHtml(matchLink)), getHtml(hltv.statLink(getHtml(playerLink))));
            }
            for (String map : hltv.mapPick(getHtml(matchLink))) {
                System.out.println(map + " " + calculator.calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(getHtml(hltv.getTeamLink2(getHtml(matchLink))))), map.substring(0, map.length() - 1)));
            }

            for (int j = 0; j < 75; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    private static void repeatMethod() {
        try {
            UserParser.SetRandomAgent(UserParser.getRandomAgent());
        } catch (FileNotFoundException e) {
            repeatMethod();
        }
    }
}
