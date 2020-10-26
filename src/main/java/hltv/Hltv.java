package hltv;

import general.General;
import general.logics.AdvantageGenerator;
import hltv.matches.Match;
import hltv.matches.teams.Team;
import interfaces.IHltv;
import org.apache.commons.math3.util.Precision;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.util.*;


public class Hltv implements IHltv {

    private Document documentHltvLink;

    //Singleton realisation
    private static Hltv hltv;

    private Hltv() {
        try {
            this.documentHltvLink = General.getHtml("https://www.hltv.org/");
        } catch (Exception e) {
            System.out.println(":::: " + e.toString() + ", class " + Hltv.class.getName() + ", line " + e.getStackTrace()[0].getLineNumber());
        }
    }

    public static Hltv getHltv() { //singleton
        if (hltv == null) {
            hltv = new Hltv();
        }
        return hltv;
    }


    //life and future
    public Map<String, Boolean> getAllMatches() {
        assert documentHltvLink != null;
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
        ArrayList<Element> listEle = new ArrayList<>(matches.select("a"));
        Map<String, Boolean> links = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            links.put(("https://www.hltv.org" + listEle.get(i).attr("href")), listEle.get(i).getElementsByAttribute("filteraslive").equals(listEle.get(i).getElementsByAttributeValueContaining("filteraslive", "true")));
        }
        return links;
    }

    //life
    public List<String> getLiveMatchesLinks() throws Exception {
        documentHltvLink = General.getHtml("https://www.hltv.org/");
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide > a > div[filteraslive=\"true\"]");
        ArrayList<String> links = new ArrayList<>();
        for (Element life : matches) {
            links.add("https://www.hltv.org" + life.parent().attr("href"));
        }
        return links;
    }

    public Document getDocumentHltvLink() throws Exception {
        documentHltvLink = General.getHtml("https://www.hltv.org/");
        return documentHltvLink;
    }

}
