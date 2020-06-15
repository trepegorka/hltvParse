package HltvPath;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Hltv {

    private Document getHltvHtml() throws IOException {
        return Jsoup.connect("https://www.hltv.org/")
                .userAgent("OPR/68.0.3618.125")
                .referrer("http://www.google.com")
                .get();
    }

    private Document getHltvMatchHtml(String matchLink) throws IOException {
        return Jsoup.connect(matchLink)
                .userAgent("OPR/68.0.3618.125")
                .referrer("http://www.google.com")
                .get();
    }

    private Document getHltvTeamHtml(String teamLink) throws IOException {
        return Jsoup.connect(teamLink)
                .userAgent("OPR/68.0.3618.125")
                .referrer("http://www.google.com")
                .get();
    }

    private Document getHltvPlayerHtml(String playerLink) throws IOException {
        return Jsoup.connect(playerLink)
                .userAgent("OPR/68.0.3618.125")
                .referrer("http://www.google.com")
                .get();
    }

    public List<String> liveMatches() throws IOException {
        Elements matches = getHltvHtml().select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
        List<Element> list = new ArrayList<>();
        for (Element element : matches.select("a")) {
            if (element.getElementsByAttribute("filteraslive").equals(element.getElementsByAttributeValueContaining("filteraslive", "true"))) {
                list.add(element);
            }
        }
        List<String> links = new ArrayList<>();
        for (Element e : list) {
            links.add("https://www.hltv.org" + e.attr("href"));
        }
        return links;
    }

    public ArrayList<String> mapPick(String matchLink) throws Exception {
        Elements matches = getHltvMatchHtml(matchLink).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
        StringBuilder builder = new StringBuilder();
        builder.append(matches.select("div").text()).append("\n");
        String pick = builder.toString().substring(builder.toString().lastIndexOf("1"));
        StringBuilder mainPick = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            list.add("de_"+mainPick.append(pick, pick.indexOf(i + "."), pick.indexOf((i + 1) + ".")).toString());
            mainPick.setLength(0);
        }
        list.removeIf(i -> i.contains("removed"));
        for(int i=0; i<list.size(); i++){
             list.set(i, "de_"+list.get(i).substring(list.get(i).indexOf("picked")+6).toLowerCase());
        }

        String leftMap = mainPick.append(pick, pick.indexOf(7 + "."), pick.indexOf("was")).toString();
        leftMap = leftMap.substring(2);
        list.add("de_"+leftMap.toLowerCase());

        return list;
    }

    public String getTeamLink1(String matchLink) throws IOException {
        Elements matches = getHltvMatchHtml(matchLink).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > a");
        return "https://www.hltv.org" + matches.attr("href");
    }

    public String getTeamLink2(String matchLink) throws IOException {
        Elements matches = getHltvMatchHtml(matchLink).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a");
        return "https://www.hltv.org" + matches.attr("href");
    }

    public ArrayList<String> PlayersLinks(String teamLink) throws IOException {
        Elements matches = getHltvTeamHtml(teamLink).select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.bodyshot-team-bg > div.bodyshot-team.g-grid");
        List<Element> list = new ArrayList<>();
        for (Element element : matches.select("a")) {
            if (element.getElementsByAttribute("filteraslive").equals(element.getElementsByAttributeValueContaining("filteraslive", "true"))) {
                list.add(element);
            }
        }
        ArrayList<String> links = new ArrayList<>();
        for (Element e : list) {
            links.add("https://www.hltv.org" + e.attr("href"));
        }
        return links;
    }

    public ArrayList<String> getPlayersNickNames(ArrayList<String> listOfLinksOfPlayers) {
        ArrayList<String> names = new ArrayList<>();
        for (String link : listOfLinksOfPlayers) {
            names.add(link.substring(28));
        }
        return names;
    }

    public String getNickName(String playerLink) {
        return playerLink.substring(playerLink.lastIndexOf("/"));
    }

    public String statLink(String playerLink) throws IOException {
        Elements matches = getHltvPlayerHtml(playerLink).select("#infoBox > div:nth-child(4) > a");
        if (matches.attr("href").equals("")) {
            matches = getHltvPlayerHtml(playerLink).select("#infoBox > div.moreButton-container > a");
        }
        return ("https://www.hltv.org" + matches.attr("href"));
    }
}
