package hltv.matches.upcoming;

import Abstraction.Match;
import general.General;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import resultMatches.Months;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpcomingMatch extends Match {
    public static ArrayList<String> linksAlreadyUsed = new ArrayList<>();
    public static ArrayList<String> toLoad = new ArrayList<>();

    public UpcomingMatch(String matchLink) throws Exception {
        super(matchLink);
    }

    public static String getTimeNow() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);

    }

    //before 00:00
    public static Map<String, String> linkAndTime() throws Exception {
        Map<String, String> link_key = new LinkedHashMap<>();
        Document matches = General.getHtml("https://www.hltv.org/matches");
        Elements block = matches.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.newMatches > div.standardPageGrid > div > div.upcomingMatchesWrapper > div > div:nth-child(1) > div:nth-child(1)");
        Elements matchesFromBlock = block.select("div.upcomingMatch");
        for (Element matchBlock : matchesFromBlock) {
            String time = matchBlock.select("a.match.a-reset > div.matchInfo > div.matchTime").text();
            String link = "https://www.hltv.org" + matchBlock.child(0).attr("href");
            link_key.put(link, time);
        }
        return link_key;
    }

    @Override
    public ArrayList<String> mapPick() throws Exception {
        ArrayList<String> maps = new ArrayList<>();
        maps.add("dust2");
        maps.add("inferno");
        maps.add("train");
        maps.add("vertigo");
        maps.add("mirage");
        maps.add("nuke");
        maps.add("overpass");
        return maps;
    }

    @Override
    public int mapPicker(int mapNumber) {
        Elements mapholder = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(" + mapNumber + ")");

        if (mapholder.select("div.results.played > div.results-left").hasClass("pick")) {
            return 1;
        }
        if (mapholder.select("div.results.played > span.results-right").hasClass("pick")) {
            return 2;
        } else return 0;
    }

    @Override
    public String dateOfMatch() {
        String date = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.date").text();
        String year = date.substring(date.length() - 4);
        String day = "";
        String regex = "^\\d{1,2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            day = matcher.group(0);
        }
        if (Integer.parseInt(day) < 10) {
            StringBuilder builder = new StringBuilder();
            day = builder.append("0").append(day).toString();
        }
        String month = "";
        for (Months monthS : Months.values()) {
            if (date.contains(monthS.getName())) {
                month = monthS.getTitle();
                break;
            }
        }
        return year + "-" + month + "-" + day;
    }
}

