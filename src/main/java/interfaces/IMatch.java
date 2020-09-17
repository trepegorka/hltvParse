package interfaces;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

public interface IMatch {

    Document getMatchDoc();

    ArrayList<String> mapPick() throws Exception;

    default String getFirstTeamLink(){
        return getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > a").attr("abs:href");
    }

    default String getSecondTeamLink(){
        return getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(3) > div > a").attr("abs:href");
    }
}
