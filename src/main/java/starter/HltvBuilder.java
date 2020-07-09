package starter;

import HltvPath.teams.FirstTeam;
import HltvPath.teams.SecondTeam;
import HltvPath.teams.Team;
import telegramBot.Bot;
import HltvPath.Hltv;
import HltvPath.Logic;
import HltvPath.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;

import java.util.LinkedHashMap;
import java.util.Map;


public class HltvBuilder {

    public static Document getHtml(String link) throws Exception {
        try {
            System.out.println("New request... ");
            Thread.sleep(1000);
            return Jsoup.connect(link)
                    .referrer("https://www.hltv.org/")
                    .userAgent(UserParser.userAgent)
                    .ignoreHttpErrors(true)
                    .get();
        } catch (Exception e) {
            System.out.println("Retry to connect");
            Thread.sleep(50000);
            getHtml(link);
        }
        return null;
    }

    public static void start() throws Exception {
        Map<String, Boolean> matches = new LinkedHashMap<>(Hltv.allMatches());
        doAction(matches);
    }

    public static void doAction(Map<String, Boolean> matches) throws Exception {
        UserParser.setUserAgent(UserParser.getRandomAgent());
        for (Map.Entry<String, Boolean> matchLink : matches.entrySet()) {
            System.out.println("\n****************DOWNLOADING****************");

            FirstTeam firstTeam = new FirstTeam(getHtml(matchLink.getKey()));
            SecondTeam secondTeam = new SecondTeam(getHtml(matchLink.getKey()));
            StringBuilder buildMessage = new StringBuilder();

            /*teamInit*/
            for (String playerLink : firstTeam.getPlayersLinks()) {
                System.out.println("New Player initialisation");
                Player player = new Player(playerLink);
                player.loadMapsStatsToFile(Hltv.mapPick(matchLink.getValue()));
            }
            for (String playerLink : secondTeam.getPlayersLinks()) {
                System.out.println("New Player initialisation");
                Player player = new Player(playerLink);
                player.loadMapsStatsToFile(Hltv.mapPick(matchLink.getValue()));
            }

            /*teamNames*/
            buildMessage.append(Hltv.getMatchTime(matchLink.getValue()));
            buildMessage.append("<b>").append(firstTeam.getTeamName(Team.matchDoc).toUpperCase()).append("</b>").append(" \uD83C\uDD9A ")
                    .append("<b>").append(secondTeam.getTeamName(Team.matchDoc).toUpperCase()).append("</b>").append("\n");


            for (String map : Hltv.mapPick(matchLink.getValue())) {
                try {
                    String advantage = Logic.calculateAdvantage(firstTeam, secondTeam, map, matchLink.getValue(), "src\\main\\java\\players\\");
                    String advantage3m = Logic.calculateAdvantage(firstTeam, secondTeam, map, matchLink.getValue(), "src\\main\\java\\players3month\\");
                    advantage3m = advantage3m.substring(advantage3m.indexOf("("));
                    for (int i = 0; i < 30; i++) {
                        System.out.print("-");
                    }
                    System.out.println("\n" + advantage);
                    buildMessage.append("\n").append(advantage).append("\n").append("\t\t\t\t\t").append(advantage3m).append("\n");
                } catch (Exception ignored) {
                }
            }
            for (int i = 0; i < 30; i++) {
                System.out.print("-");
            }
            System.out.println("\n");
            Bot.setMessage(buildMessage.toString());
            Bot.sendMessage();
        }
//        Hltv.reloadMatches(matches);
    }
}
