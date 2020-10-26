package resultMatches;

import general.General;
import interfaces.IMatch;
import interfaces.IResults;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchResult implements IResults, IMatch {

    private static Document matchDoc;

    public Document getMatchDoc() {
        return matchDoc;
    }

    public MatchResult(String matchLink) throws Exception {
        matchDoc = General.getHtml(matchLink);
    }
    public MatchResult(){};

    public static String dateOfMatch() {
        String date = matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.date").text();
        String year = date.substring(date.length() - 4);
        //day
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
        //month
        String month = "";
        for (Months monthS : Months.values()) {
            if (date.contains(monthS.getName())) {
                month = monthS.getTitle();
                break;
            }
        }
        return (year + "-" + month + "-" + day);
    }

    //write to file 'list of links' with past matches
    public void writeResultLinks() throws Exception {
        FileWriter writer = new FileWriter("src/main/java/resultMatches/resultLinks.txt", true);
        ArrayList<String> linksList = new ArrayList<>();
        int offset = 100;
        for (int i = 0; i < 220; i++) {
            Document hltvResults = General.getHtml("https://www.hltv.org/results?offset=" + offset);
            Elements links = hltvResults.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.results > div.results-holder > div.results-all").select("a");
            for (Element link : links) {
                linksList.add(link.attr("abs:href"));
                writer.append(link.attr("abs:href")).append("\n").flush();
            }
            offset += 100;
            System.out.println("size = " + linksList.size() + ", wait");
            Thread.sleep(1000);
        }
        writer.close();
    }

    //list Of Played Maps
    @Override
    public final ArrayList<String> mapPick() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        Elements column = matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small");
        if (column.text().contains("Best of 3")) {
            /* bo3 */
            for (Element mapholder : column.select("div.flexbox-column > div.mapholder")) {
                if (mapholder.child(0).hasClass("played")) {
                    list.add(mapholder.child(0).text());
                }
            }
        } else if (column.text().contains("Best of 1")) {
            /* bo1 */
            for (Element mapholder : column.select("div.flexbox-column > div.mapholder")) {
                if (mapholder.child(0).hasClass("played")) {
                    list.add(mapholder.child(0).text());
                }
            }
        } else if (column.text().contains("Best of 5")) {
            /* bo5 */
            for (Element mapholder : column.select("div.flexbox-column > div.mapholder")) {
                if (mapholder.child(0).hasClass("played")) {
                    list.add(mapholder.child(0).text());
                }
            }
        } else {
            throw new Exception("Error founding bo");
        }

        return list;
    }

    //if winner = 1 -> right won. left = 0
    public int winner() {
        if (matchDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > div").hasClass("won")) {
            return 0;
        } else return 1;
    }

    public static void main(String[] args) throws Exception {
        new MatchResult().writeResultLinks();
    }
}



