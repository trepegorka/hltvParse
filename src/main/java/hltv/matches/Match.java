package hltv.matches;

import general.General;
import interfaces.IMatch;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class Match implements IMatch {

    private final Document matchDoc;
    private final String matchLink;

    public Match(String matchLink) throws Exception {
        this.matchLink = matchLink;
        this.matchDoc = General.getHtml(matchLink);
    }

    public Document getMatchDoc() {
        return matchDoc;
    }

    /**
     * mapPick
     */
    @Override
    public ArrayList<String> mapPick() throws Exception {
        return null;
    }

    public String getMatchLink() {
        return matchLink;
    }

}
