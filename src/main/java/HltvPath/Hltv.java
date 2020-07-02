package HltvPath;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import starter.HltvBuilder;

import javax.print.Doc;
import java.io.IOException;
import java.util.*;


public class Hltv {

    public Hltv() throws IOException {
    }

    public Map<String, Boolean> allMatches(Document documentHltvLink) {
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
        ArrayList<Element> listEle = new ArrayList<>(matches.select("a"));
        Map<String, Boolean> links = new LinkedHashMap<>();
        Map<String, Boolean> linksTrue = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            links.put(("https://www.hltv.org" + listEle.get(i).attr("href")), listEle.get(i).getElementsByAttribute("filteraslive").equals(listEle.get(i).getElementsByAttributeValueContaining("filteraslive", "true")));
        }
        for(Map.Entry<String, Boolean> link: links.entrySet()){
            if(link.getValue()){
                linksTrue.put(link.getKey(), true);
            }
        }
        return links;
    }

    public String getMatchTime(Document matchDoc, boolean isLive) {
        if (!isLive) {
            String time = matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.time").text();
            String messageTime = ("<b>" + time + "</b>\n\n");
            int hours = Integer.parseInt(time.substring(0, time.indexOf(":")));
            switch (hours) {
                case 1:
                case 13:
                    return "\uD83D\uDD50" + messageTime;
                case 2:
                case 14:
                    return "\uD83D\uDD51" + messageTime;
                case 3:
                case 15:
                    return "\uD83D\uDD52" + messageTime;
                case 4:
                case 16:
                    return "\uD83D\uDD53" + messageTime;
                case 5:
                case 17:
                    return "\uD83D\uDD54" + messageTime;
                case 6:
                case 18:
                    return "\uD83D\uDD55" + messageTime;
                case 7:
                case 19:
                    return "\uD83D\uDD56" + messageTime;
                case 8:
                case 20:
                    return "\uD83D\uDD57" + messageTime;
                case 9:
                case 21:
                    return "\uD83D\uDD58" + messageTime;
                case 10:
                case 22:
                    return "\uD83D\uDD59" + messageTime;
                case 11:
                case 23:
                    return "\uD83D\uDD5A" + messageTime;
                case 12:
                case 0:
                    return "\uD83D\uDD5B" + messageTime;
            }
        }
        return "\u2757<b>LIVE</b>\n\n";
    }

    public List<String> resultMatches(Document documentHltvLink) throws IOException {
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.results > div.results-holder > div.results-all");
        List<Element> list = new ArrayList<Element>();
        for (Element element : matches.select("a")) {
            list.add(element);
        }
        List<String> links = new ArrayList<String>();
        for (Element e : list) {
            links.add("https://www.hltv.org" + e.attr("href"));
        }
//        links.subList(0, 25).clear();
        return links;
    }


    public ArrayList<String> mapPick(Document documentMatchLink, boolean isLive) throws Exception {
        if (isLive) {
            try {
//                if(documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(1) > div > div > div").text().equals("TBA")){
//                    System.out.println("No maps yet");
//                    Thread.sleep(300000);
//                }
                Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
                StringBuilder builder = new StringBuilder();
                builder.append(matches.select("div").text()).append("\n");
                String pick = "";
                pick = builder.toString().substring(builder.toString().lastIndexOf("1"));
                StringBuilder mainPick = new StringBuilder();
                ArrayList<String> list = new ArrayList<String>();
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
                } catch (java.lang.IndexOutOfBoundsException ignored) {
                }
                return list;
            } catch (Exception e) {
                Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
                ArrayList<String> list = new ArrayList<String>();
                list.add(matches.select("div").text());
                return list;
            }
        } else return listOf7Maps();
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

    public ArrayList<String> getPlayersNickNames(ArrayList<String> listOfLinksOfPlayers) {
        ArrayList<String> names = new ArrayList<String>();
        for (String link : listOfLinksOfPlayers) {
            names.add(link.substring(28).substring(link.substring(28).lastIndexOf("/") + 1));
        }
        return names;
    }

    public String getNickName(String playerLink) {
        return playerLink.substring(playerLink.lastIndexOf("/"));
    }

    public String getSimpleNickName(String playerLink) {
        String simpleNickname = playerLink.substring(playerLink.lastIndexOf("/"));
        return simpleNickname.substring(simpleNickname.lastIndexOf("/") + 1);
    }

    public String statLink(Document documentPlayerLink) throws IOException {
        Elements matches = documentPlayerLink.select("#infoBox > div:nth-child(4) > a");
        if (matches.attr("href").equals("")) {
            matches = documentPlayerLink.select("#infoBox > div.moreButton-container > a");
        }
        return ("https://www.hltv.org" + matches.attr("href"));
    }

    public String getStatLink(String playerLink) {

        String https_link = "";
        String hltv_link = "";
        String id = "";
        String name = "";
        int a = 0;
        for (String parametr : playerLink.split("/")) {
            a++;
            if (a == 1) {
                https_link = parametr;
            } else if (a == 3) {
                hltv_link = parametr;
            } else if (a == 5) {
                id = parametr;
            } else if (a == 6) {
                name = parametr;
            }
        }

        return https_link + "//" + hltv_link + "/" + "stats/" + "players/" + id + "/" + name;
    }

    public String getTeam1Name(Document matchDoc) throws IOException {
        return getTeamLink1(matchDoc).substring(getTeamLink1(matchDoc).lastIndexOf("/") + 1);
    }

    public String getTeam2Name(Document matchDoc) throws IOException {
        return getTeamLink2(matchDoc).substring(getTeamLink2(matchDoc).lastIndexOf("/") + 1);
    }

    public static ArrayList<String> listOf7Maps() {
        ArrayList<String> maps = new ArrayList<>();
        maps.add("dust2 ");
        maps.add("inferno ");
        maps.add("train ");
        maps.add("vertigo ");
        maps.add("mirage ");
        maps.add("nuke ");
        maps.add("overpass ");
        return maps;
    }

    public void reloadMatches(Map<String, Boolean> lastMatches, Hltv hltv, Logic logic) throws Exception {
        Document documentHltvLink = HltvBuilder.getHtml("https://www.hltv.org/");
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
        ArrayList<Element> listEle = new ArrayList<>(matches.select("a"));
        Map<String, Boolean> copyLastMatches = new LinkedHashMap<>(lastMatches);
        Map<String, Boolean> newMatches = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            newMatches.put(("https://www.hltv.org" + listEle.get(i).attr("href")), listEle.get(i).getElementsByAttribute("filteraslive").equals(listEle.get(i).getElementsByAttributeValueContaining("filteraslive", "true")));
        }
        for (Map.Entry<String, Boolean> newMatch : newMatches.entrySet()) {
            if (!copyLastMatches.containsKey(newMatch.getKey())) {
                copyLastMatches.put(newMatch.getKey(), newMatch.getValue());
            }
        }

        Iterator<Map.Entry<String, Boolean>> fit = copyLastMatches.entrySet().iterator();
        while (fit.hasNext()) {
            Map.Entry<String, Boolean> match = fit.next();
            String keyMatch = match.getKey();
            boolean valueMatch = match.getValue();

            if (newMatches.containsKey(keyMatch)) {
                if (newMatches.get(keyMatch) && valueMatch) {
                    fit.remove();
                } else if (newMatches.get(keyMatch) && !valueMatch) {
                    copyLastMatches.replace(keyMatch, false, true);
                } else if (!newMatches.get(keyMatch) && !valueMatch) {
                    fit.remove();
                }
            } else if (!newMatches.containsKey(keyMatch)) {
                fit.remove();
            }

        }
        if (copyLastMatches.isEmpty()) {
            System.out.println("No changes. Wait...");
            Thread.sleep(600000);
            reloadMatches(lastMatches, hltv, logic);
        } else {
            lastMatches = allMatches(documentHltvLink);
            HltvBuilder.doAction(copyLastMatches, hltv, logic);
            Thread.sleep(600000);
            reloadMatches(lastMatches, hltv, logic);
        }
    }
}
