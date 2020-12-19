package Abstraction;

import general.General;
import hltv.matches.teams.players.Player;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class Team {

    private int currentRanking;
    private final String map;
    private String teamName;
    private ArrayList<Player> listOfPlayers;
    private final Match match;

    public int getCurrentRanking() {
        return currentRanking;
    }

    public String getMap() {
        return map;
    }

    public String getTeamName() {
        return teamName;
    }

    public Match getMatch() {
        return match;
    }

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public abstract String get3mFormat() throws ParseException;

    private ArrayList<Player> generateListOfPlayers(String leftOrRight) throws Exception {
        Elements players = null;
        if (leftOrRight.equals("left")) {
            players = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(1) > div.players > table > tbody > tr:nth-child(1)");
        } else if (leftOrRight.equals("right")){
            players = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(3) > div.players > table > tbody > tr:nth-child(1)");
        }
        List<Element> list = new ArrayList<>(players.select("a"));
        ArrayList<Player> playersList = new ArrayList<>();
        for (Element e : list) {
            String link = "https://www.hltv.org" + e.attr("href");
            String statLink = "https://www.hltv.org/stats/players/" + link.substring(28) + get3mFormat() + "&maps=de_" + map.toLowerCase();
            Document playerDoc = General.getHtml(statLink);
            Player player = new Player(playerDoc);
            playersList.add(player);
            if (player.getKDRatio() == 0.0) {
                System.out.println("NO ONE PLAYED MAP BEFORE");
                break;
            }
        } 
        if (playersList.isEmpty()){
            if (leftOrRight.equals("left")) {
                players = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(1) > div.players > table > tbody > tr:nth-child(1)");
            } else if (leftOrRight.equals("right")){
                players = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(3) > div.players > table > tbody > tr:nth-child(2)");
            }
            for(Element player : players){
                int playerId = Integer.parseInt(player.select("div").attr("data-player-id"));
                String playerName = player.select("div > div").text().toLowerCase();
                String link = "https://www.hltv.org/" + playerId + "/"+playerName;
                String statLink = "https://www.hltv.org/stats/players/" + link.substring(28) + get3mFormat() + "&maps=de_" + map.toLowerCase();
                Document playerDoc = General.getHtml(statLink);
                Player playerCl = new Player(playerDoc);
                playersList.add(playerCl);
                if (playerCl.getKDRatio() == 0.0) {
                    System.out.println("NO ONE PLAYED MAP BEFORE");
                    break;
                }
            }
        }
        return playersList;
    }


    public Team(String map, Match match, String leftOrRight) throws Exception {
        this.match = match;
        this.map = map;
        if (leftOrRight.equals("left")) {
            this.teamName = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(1) > div.box-headline.flex-align-center > div.flex-align-center > a").text();
            try {
                this.currentRanking = Integer.parseInt(match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(1) > div.box-headline.flex-align-center > div.teamRanking > a").text().substring(13));
            } catch (Exception e){
                String teamLink = "https://www.hltv.org" + match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(1) > div.box-headline.flex-align-center > div.flex-align-center > a").attr("href");
                try {
                    this.currentRanking = Integer.parseInt(General.getHtml(teamLink).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.standard-box.profileTopBox.clearfix > div.profile-team-stats-container > div:nth-child(1) > span > a").text().substring(1));
                } catch (Exception exception){
                    this.currentRanking = 150;
                }
            }
        } else if (leftOrRight.equals("right")) {
            try {
                this.currentRanking = Integer.parseInt(match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(3) > div.box-headline.flex-align-center > div.teamRanking > a").text().substring(13));
            } catch (Exception e){
                String teamLink = "https://www.hltv.org" + match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(3) > div.box-headline.flex-align-center > div.flex-align-center > a").attr("href");
                try {
                    this.currentRanking = Integer.parseInt(General.getHtml(teamLink).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.standard-box.profileTopBox.clearfix > div.profile-team-stats-container > div:nth-child(1) > span > a").text().substring(1));
                } catch (Exception exception){
                    this.currentRanking = 150;
                }
            }
            this.teamName = match.getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.lineups > div > div:nth-child(3) > div.box-headline.flex-align-center > div.flex-align-center > a").text();
        }
        listOfPlayers = generateListOfPlayers(leftOrRight);
    }
}
