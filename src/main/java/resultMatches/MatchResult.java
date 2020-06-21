package resultMatches;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MatchResult {
    public static ArrayList<String> getMapWinners(Document documentMatchLink) {
        ArrayList<String> list = new ArrayList<>();

        String map_value_t1 = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > div").text();
        String map_value_t2 = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > div").text();

        int mapval = Integer.parseInt(map_value_t1) + Integer.parseInt(map_value_t2);
        if (mapval > 5) {
            mapval = 1;
        }


        for (int i = 1; i < mapval + 1; i++) {
            String _1mapright = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(" + i + ") > div.results > span > div.results-teamname-container.text-ellipsis > div.results-team-score").text();
            String _1mapleft = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(" + i + ") > div.results > div.results-left > div.results-teamname-container.text-ellipsis > div.results-team-score").text();
            try {
                if (Integer.parseInt(_1mapleft)<Integer.parseInt(_1mapright)) {
                    list.add("2");
                } else {
                    list.add("1");
                }
            } catch (Exception e) {
                list.add("Not played");
            }
        }
        return list;
    }
}
