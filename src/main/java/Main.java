import HltvPath.Hltv;
import HltvPath.Player;

public class Main {
    public static void main(String[] args) throws Exception {
        Hltv hltv = new Hltv();
        for (String matchLink : hltv.liveMatches()) {
            System.out.println("\n" + matchLink);
            System.out.println(hltv.getTeamLink1(matchLink) + "  vs  " + hltv.getTeamLink2(matchLink));
            System.out.println("\nteam1: ");
            for (String playerLink : hltv.PlayersLinks(hltv.getTeamLink1(matchLink))) {
                Player player = new Player(hltv.statLink(playerLink));
                player.loadPlayerMapsStatsToFile();
                System.out.println(playerLink);
                System.out.println(hltv.statLink(playerLink));
            }
            for (String playerNickName : hltv.getPlayersNickNames(hltv.PlayersLinks(hltv.getTeamLink1(matchLink)))) {
                System.out.println(playerNickName);
            }

            System.out.println("\nteam2: ");
            for (String playerLink : hltv.PlayersLinks(hltv.getTeamLink2(matchLink))) {
                Player player = new Player(hltv.statLink(playerLink));
                player.loadPlayerMapsStatsToFile();
                System.out.println(playerLink);
                System.out.println(hltv.statLink(playerLink));
            }
            for (String playerNickName : hltv.getPlayersNickNames(hltv.PlayersLinks(hltv.getTeamLink2(matchLink)))) {
                System.out.println(playerNickName);
            }

            System.out.println("\n" + hltv.mapPick(matchLink));
            for (int j = 0; j < 75; j++) {
                System.out.print("*");
            }
            System.out.println();

        }
    }
}
