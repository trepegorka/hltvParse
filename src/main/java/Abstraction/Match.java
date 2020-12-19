package Abstraction;

import general.General;
import hltv.matches.LifeMatch;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class Match {
    private final Document matchDoc;
    private final String matchLink;

    public Match(String matchLink) throws Exception {
        this.matchLink = matchLink;
        this.matchDoc = General.getHtml(matchLink);
    }

    public Document getMatchDoc() {
        return matchDoc;
    }

    public String getMatchLink() {
        return matchLink;
    }

    public String getLeftTeamLink() {
        return getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > a").attr("abs:href");
    }

    public String getRightTeamLink() {
        return getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a").attr("abs:href");
    }

    public abstract ArrayList<String> mapPick() throws Exception;

    public abstract int mapPicker(int mapNumber);

    public abstract String dateOfMatch() throws ParseException;

    public LifeMatch updatedDoc() throws Exception {
        return new LifeMatch(getMatchLink());
    }
}
