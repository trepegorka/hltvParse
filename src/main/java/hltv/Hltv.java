package HltvPath;

import HltvPath.teams.Team;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import resultMatches.MatchResult;
import starter.HltvBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class Hltv {

    private static Document documentHltvLink;

    static {
        try {
            documentHltvLink = HltvBuilder.getHtml("https://www.hltv.org/");
        } catch (Exception e) {
            System.out.println(":::: "+e.toString()+", class " + Hltv.class.getName() + ", line " + e.getStackTrace()[0].getLineNumber());
        }
    }

    public static Map<String, Boolean> allMatches() {
        assert documentHltvLink != null;
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
        ArrayList<Element> listEle = new ArrayList<>(matches.select("a"));
        Map<String, Boolean> links = new LinkedHashMap<>();
//        Map<String, Boolean> linksTrue = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            links.put(("https://www.hltv.org" + listEle.get(i).attr("href")), listEle.get(i).getElementsByAttribute("filteraslive").equals(listEle.get(i).getElementsByAttributeValueContaining("filteraslive", "true")));
        }
//        for(Map.Entry<String, Boolean> link: links.entrySet()){
//            if(link.getValue()){
//                linksTrue.put(link.getKey(), true);
//            }
//        }
        return links;
    }

    public static String getMatchTime(boolean isLive) {
        if (!isLive) {
            String time = Team.matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.time").text();
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

//    public List<String> resultMatches() {
//        assert documentHltvLink != null;
//        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.results > div.results-holder > div.results-all");
//        List<Element> list = new ArrayList<Element>(matches.select("a"));
//        List<String> links = new ArrayList<String>();
//        for (Element e : list) {
//            links.add("https://www.hltv.org" + e.attr("href"));
//        }
////        links.subList(0, 25).clear();
//        return links;
//    }


    public static ArrayList<String> mapPick(boolean isLive) {
        if (isLive) {
            try {
//                if(documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(1) > div > div > div").text().equals("TBA")){
//                    System.out.println("No maps yet");
//                    Thread.sleep(300000);
//                }
                Elements matches = Team.matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
                StringBuilder builder = new StringBuilder();
                builder.append(matches.select("div").text()).append("\n");
                String pick;
                pick = builder.toString().substring(builder.toString().lastIndexOf("1"));
                StringBuilder mainPick = new StringBuilder();
                ArrayList<String> list = new ArrayList<>();
                for (int i = 1; i < 7; i++) {
                    try {
                        list.add(mainPick.append(pick, pick.indexOf(i + "."), pick.indexOf((i + 1) + ".")).toString());
                        mainPick.setLength(0);
                    } catch (Exception e) {
                        System.out.println(":::: "+e.toString()+", class " + Hltv.class.getName() + ", line " + e.getStackTrace()[0].getLineNumber());
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
                    System.out.println(":::: "+e.toString()+", class " + Hltv.class.getName() + ", line " + e.getStackTrace()[0].getLineNumber());
                }
                return list;
            } catch (Exception e) {
                System.out.println(":::: "+e.toString()+", class " + Hltv.class.getName() + ", line " + e.getStackTrace()[0].getLineNumber());
                Elements matches = Team.matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
                ArrayList<String> list = new ArrayList<>();
                list.add(matches.select("div").text());
                return list;
            }
        } else return listOf7Maps();
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

    public static void reloadMatches(Map<String, Boolean> lastMatches) throws Exception {
        assert documentHltvLink != null;
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
        } else {
            lastMatches = allMatches();
            HltvBuilder.doAction(copyLastMatches);
        }
        Thread.sleep(600000);
        reloadMatches(lastMatches);
    }
}
