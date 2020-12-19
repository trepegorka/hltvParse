import general.General;
import hltv.MainLifeBuilder;
import org.jsoup.nodes.Document;

public class AfterMatcher {
    private static String lastLinkToCompare="";
    public static void main(String[] args) throws Exception {
        reload();
    }
    private static void reload() throws Exception {
        Document resultsDoc = General.getHtml("https://www.hltv.org/results");
        String lastMatchLink ="https://www.hltv.org/"+ resultsDoc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.results > div.results-holder > div.results-all > div:nth-child(1) > div:nth-child(2) > a").attr("href");
        if(lastLinkToCompare.equals("")){
            lastLinkToCompare = lastMatchLink;
            new MainLifeBuilder().loadAllLife(lastMatchLink);
        }
        if(lastMatchLink.equals(lastLinkToCompare)){
            Thread.sleep(30000);
        } else{
            lastLinkToCompare = lastMatchLink;
            new MainLifeBuilder().loadAllLife(lastMatchLink);
        }
        reload();

    }
}
