package hltv.matches;

import general.General;
import interfaces.IMatch;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Match implements IMatch {

    private Document matchDoc;
    private final String matchLink;

    public Match(String matchLink) throws Exception {
        this.matchLink = matchLink;
        this.matchDoc = General.getHtml(matchLink);
    }

    public Document getMatchDoc() {
        return matchDoc;
    }

    /**
     * mapPick FOR LIFE MATCHES ONlY
     * mapPick FOR LIFE MATCHES ONlY
     * mapPick FOR LIFE MATCHES ONlY
     * mapPick FOR LIFE MATCHES ONlY
     */
    @Override
    public ArrayList<String> mapPick() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        Elements column = matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small");
        if (column.text().contains("Best of 3")) {
            /* bo3 */
            for (Element mapholder : column.select("div.flexbox-column > div.mapholder")) {
                    list.add(mapholder.child(0).text());
            }
        } else if (column.text().contains("Best of 1")) {
            /* bo1 */
            for (Element mapholder : column.select("div.flexbox-column > div.mapholder")) {
                    list.add(mapholder.child(0).text());
            }
        } else if (column.text().contains("Best of 5")) {
            /* bo5 */
            for (Element mapholder : column.select("div.flexbox-column > div.mapholder")) {
                    list.add(mapholder.child(0).text());
            }
        } else {
            throw new Exception("Error founding bo");
        }
        return list;
    }

    public Match updatedDoc() throws Exception {
        return new Match(matchLink);
    }
    public String getMatchLink() {
        return matchLink;
    }

}
