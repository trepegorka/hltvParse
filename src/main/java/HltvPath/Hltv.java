package HltvPath;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Hltv {




    public Hltv() throws IOException {
    }


    public List<String> liveMatches(Document documentHltvLink) throws IOException {
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
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

    public List<String> resultMatches(Document documentHltvLink) throws IOException {
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.results > div.results-holder > div.results-all");
        List<Element> list = new ArrayList<>();
        for (Element element : matches.select("a")) {
                list.add(element);
        }
        List<String> links = new ArrayList<>();
        for (Element e : list) {
            links.add("https://www.hltv.org" + e.attr("href"));
        }
        return links;
    }


    public ArrayList<String> mapPick(Document documentMatchLink) throws Exception {
        try {
            Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
            StringBuilder builder = new StringBuilder();
            builder.append(matches.select("div").text()).append("\n");
            String pick = "";
            pick = builder.toString().substring(builder.toString().lastIndexOf("1"));
            StringBuilder mainPick = new StringBuilder();
            ArrayList<String> list = new ArrayList<>();
            for (int i = 1; i < 7; i++) {
                try {
                    list.add(mainPick.append(pick, pick.indexOf(i + "."), pick.indexOf((i + 1) + ".")).toString());
                    mainPick.setLength(0);
                } catch (Exception ignored) {
                }
            }
            list.removeIf(i -> i.contains("removed"));
            for (int i = 0; i < list.size(); i++) {
                list.set(i, (list.get(i).substring(list.get(i).indexOf("picked") + 6).toLowerCase()).substring(1));
            }

            try {
                String leftMap = mainPick.append(pick, pick.indexOf(7 + "."), pick.indexOf("was")).toString();
                leftMap = leftMap.substring(3);
                list.add(leftMap.toLowerCase());
                return list;
            } catch (java.lang.IndexOutOfBoundsException e) {

            }
            return list;
        } catch (Exception e){
            Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
            ArrayList<String> list = new ArrayList<>();
            list.add(matches.select("div").text());
            return list;
        }
    }

    public String getTeamLink1(Document documentMatchLink) throws IOException {
        Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > a");
        return "https://www.hltv.org" + matches.attr("href");
    }

    public String getTeamLink2(Document documentMatchLink) throws IOException {
        Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a");
        return "https://www.hltv.org" + matches.attr("href");
    }

    public ArrayList<String> PlayersLinks(Document documentTeamLink) throws IOException {
        Elements matches = documentTeamLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.bodyshot-team-bg > div.bodyshot-team.g-grid");
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

    public ArrayList<String> playerNames(ArrayList<String> playerLinks){
        ArrayList<String> names = new ArrayList<>();
        String name;
        for(String i : playerLinks){
            name = i.substring(i.lastIndexOf("/")+1);
            names.add(name);
        }
        return names;
    }

    public ArrayList<String> getPlayersNickNames(ArrayList<String> listOfLinksOfPlayers) {
        ArrayList<String> names = new ArrayList<>();
        for (String link : listOfLinksOfPlayers) {
            names.add(link.substring(28).substring(link.substring(28).lastIndexOf("/")+1));
        }
        return names;
    }

    public String getNickName(String playerLink) {
        return playerLink.substring(playerLink.lastIndexOf("/"));
    }

    public String getSimpleNickName(String playerLink) {
        String simpleNickname = playerLink.substring(playerLink.lastIndexOf("/"));
        return simpleNickname.substring(simpleNickname.lastIndexOf("/")+1);
    }

    public String statLink(Document documentPlayerLink) throws IOException {
        Elements matches = documentPlayerLink.select("#infoBox > div:nth-child(4) > a");
        if (matches.attr("href").equals("")) {
            matches = documentPlayerLink.select("#infoBox > div.moreButton-container > a");
        }
        return ("https://www.hltv.org" + matches.attr("href"));
    }

}
