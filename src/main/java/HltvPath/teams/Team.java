package HltvPath.teams;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import starter.HltvBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Team {

    public static Document matchDoc;

    public Team(Document matchDoc) {
        Team.matchDoc = matchDoc;
    }

    public abstract String getTeamLink(Document documentMatchLink) throws IOException;

    public Document getTeamDoc() throws Exception {
        return HltvBuilder.getHtml(getTeamLink(matchDoc));
    }

    public String getTeamName(Document matchDoc) throws IOException {
        return getTeamLink(matchDoc).substring(getTeamLink(matchDoc).lastIndexOf("/") + 1);
    }

    public ArrayList<String> getPlayersLinks() throws Exception {
        Elements matches = getTeamDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.teamProfile > div.bodyshot-team-bg > div.bodyshot-team.g-grid");
        List<Element> list = new ArrayList<Element>();
        for (Element element : matches.select("a")) {
            if (element.getElementsByAttribute("filteraslive").equals(element.getElementsByAttributeValueContaining("filteraslive", "true"))) {
                list.add(element);
            }
        }
        ArrayList<String> links = new ArrayList<String>();
        for (Element e : list) {
            links.add("https://www.hltv.org" + e.attr("href"));
        }
        return links;
    }

    public ArrayList<String> playerNames(ArrayList<String> playerLinks) {
        ArrayList<String> names = new ArrayList<String>();
        String name;
        for (String i : playerLinks) {
            name = i.substring(i.lastIndexOf("/") + 1);
            names.add(name);
        }
        return names;
    }

    public ArrayList<String> getPlayersNickNames() throws Exception {
        ArrayList<String> names = new ArrayList<String>();
        for (String link : getPlayersLinks()) {
            names.add(link.substring(28).substring(link.substring(28).lastIndexOf("/") + 1));
        }
        return names;
    }

}
