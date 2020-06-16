import HltvPath.Calculator;
import HltvPath.Hltv;
import HltvPath.Player;

public class Runner {

    public static void main(String[] args) throws Exception {
        Hltv hltv = new Hltv();
        Calculator calculator = new Calculator();
        for (String matchLink : hltv.liveMatches()) {
            System.out.println(hltv.getTeamLink1(matchLink).substring(hltv.getTeamLink1(matchLink).lastIndexOf("/") + 1) + "  " +
                    "vs  " + hltv.getTeamLink2(matchLink).substring(hltv.getTeamLink2(matchLink).lastIndexOf("/") + 1));

            System.out.println("\nteam1: " + hltv.getTeamLink1(matchLink).substring(hltv.getTeamLink1(matchLink).lastIndexOf("/") + 1));
            for (String map : hltv.mapPick(matchLink)) {
                System.out.println(map + " " + calculator.calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(hltv.getTeamLink1(matchLink))), map.substring(0, map.length() - 1)));
            }

            System.out.println("\nteam2: " + hltv.getTeamLink2(matchLink).substring(hltv.getTeamLink2(matchLink).lastIndexOf("/") + 1));
            ;
            for (String map : hltv.mapPick(matchLink)) {
                System.out.println(map + " " + calculator.calculateAverageParam(hltv.getPlayersNickNames(hltv.PlayersLinks(hltv.getTeamLink2(matchLink))), map.substring(0, map.length() - 1)));
            }

            for (int j = 0; j < 75; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
