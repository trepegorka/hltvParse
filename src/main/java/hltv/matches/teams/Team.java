package hltv.matches.teams;

import general.General;
import hltv.matches.teams.players.Player;
import interfaces.ITeam;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import resultMatches.MatchResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Team implements ITeam {

    private final Document teamDoc;
    private final String teamLink;
    private int currentRanking;
    private final String map;
    private final String teamName;
    private final ArrayList<Player> listOfPlayers;

    public Document getTeamDoc() {
        return teamDoc;
    }

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public String getTeamLink() {
        return teamLink;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getCurrentRanking() {
        return currentRanking;
    }

    public Team(String teamLink, String map) throws Exception {
        this.teamLink = teamLink;
        this.map = map;
        teamDoc = General.getHtml(teamLink);
        this.teamName = teamDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.standard-box.profileTopBox.clearfix > div.flex > div.profile-team-container.text-ellipsis > div.profile-team-info > h1").text();
        try {
            currentRanking = Integer.parseInt(teamDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.standard-box.profileTopBox.clearfix > div.profile-team-stats-container > div:nth-child(1) > span > a").text().substring(1));
        } catch (Exception e) {
            System.out.println("No ranking found");
            currentRanking = 0;
        }
        listOfPlayers = generateListOfPlayers();
    }

    private ArrayList<Player> generateListOfPlayers() throws Exception {
        Elements players = teamDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.bodyshot-team-bg > div.bodyshot-team.g-grid");
        List<Element> list = new ArrayList<>(players.select("a"));
        ArrayList<Player> playersList = new ArrayList<>();
        for (Element e : list) {
            String link = "https://www.hltv.org" + e.attr("href");
            String statLink = "https://www.hltv.org/stats/players/" + link.substring(28) + get3mFormat() + "&maps=de_" + map.toLowerCase();
            Document playerDoc = General.getHtml(statLink);
            playersList.add(new Player(playerDoc));
        }
        return playersList;
    }

    private String get3mFormat() throws ParseException { //?startDate=2020-06-02&endDate=2020-09-02 will return like that
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(MatchResult.dateOfMatch()); // for past
//        Date date = sdf.parse(String.valueOf(java.time.LocalDate.now())); // for life
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -90);
        Date m3Ago = calendar.getTime();
        String formatter = sdf.format(m3Ago);
//        return ("?startDate=" + formatter + "&endDate=" + java.time.LocalDate.now()); //for life
        return ("?startDate=" + formatter + "&endDate=" + MatchResult.dateOfMatch()); //for past
    }
}
