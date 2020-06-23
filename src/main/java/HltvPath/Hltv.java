package HltvPath;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Hltv {




    public Hltv() throws IOException {
    }



    public static void requestTelegram(ArrayList<String> list,String chat_id) throws IOException, InterruptedException {

        String api = "1278410888:AAHaO2JkgP4NjGrVJSSS91t4fNJt0aCc2js";

        String bank="%F0%9F%92%B0";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append("%0A"+bank+list.get(i)+bank);
        }

        URL url1 = new URL("https://api.telegram.org/bot"+api+"/sendMessage?chat_id="+chat_id+"&text= "+stringBuilder.toString());
        HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();

        con1.setRequestMethod("GET");
        con1.connect();
        con1.getContent();


    }
    public List<String> liveMatches(Document documentHltvLink) throws IOException {
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
        List<Element> list = new ArrayList<Element>();
        for (Element element : matches.select("a")) {
            if (element.getElementsByAttribute("filteraslive").equals(element.getElementsByAttributeValueContaining("filteraslive", "true"))) {
                list.add(element);
            }
        }
        List<String> links = new ArrayList<String>();
        for (Element e : list) {
            links.add("https://www.hltv.org" + e.attr("href"));
        }
        return links;
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


    public ArrayList<String> mapPick(Document documentMatchLink) throws Exception {
        try {
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
            } catch (java.lang.IndexOutOfBoundsException e) {

            }
            return list;
        } catch (Exception e){
            Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div:nth-child(3) > div");
            ArrayList<String> list = new ArrayList<String>();
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

    public ArrayList<String> playerNames(ArrayList<String> playerLinks){
        ArrayList<String> names = new ArrayList<String>();
        String name;
        for(String i : playerLinks){
            name = i.substring(i.lastIndexOf("/")+1);
            names.add(name);
        }
        return names;
    }

    public ArrayList<String> getPlayersNickNames(ArrayList<String> listOfLinksOfPlayers) {
        ArrayList<String> names = new ArrayList<String>();
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

    public String getStatLink(String playerLink){

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
}
