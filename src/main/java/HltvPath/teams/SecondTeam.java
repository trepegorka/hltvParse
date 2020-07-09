package HltvPath.teams;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import starter.HltvBuilder;


public class SecondTeam extends Team {

    public SecondTeam(Document matchDoc) throws Exception {
        super(matchDoc);
    }

    @Override
    public String getTeamLink(Document documentMatchLink) {
        Elements matches = documentMatchLink.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a");
        return "https://www.hltv.org" + matches.attr("href");
    }
}
