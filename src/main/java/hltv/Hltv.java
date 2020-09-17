package hltv;

import general.General;
import interfaces.IHltv;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


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

    public Document getDocumentHltvLink() {
        return documentHltvLink;
    }

    public void reloadMatches(Map<String, Boolean> lastListOfMatches) throws Exception {
        Elements matches = documentHltvLink.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
        ArrayList<Element> listEle = new ArrayList<>(matches.select("a"));
        Map<String, Boolean> copyLastMatches = new LinkedHashMap<>(lastListOfMatches);
        Map<String, Boolean> newMatches = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            newMatches.put(("https://www.hltv.org" + listEle.get(i).attr("href")), listEle.get(i).getElementsByAttribute("filteraslive").equals(listEle.get(i).getElementsByAttributeValueContaining("filteraslive", "true")));
        }
        for (Map.Entry<String, Boolean> newMatch : newMatches.entrySet()) {
            if (!copyLastMatches.containsKey(newMatch.getKey())) {
                copyLastMatches.put(newMatch.getKey(), newMatch.getValue());
            }
        }

        Iterator<Map.Entry<String, Boolean>> fit = copyLastMatches.entrySet().iterator();
        while (fit.hasNext()) {
            Map.Entry<String, Boolean> match = fit.next();
            String keyMatch = match.getKey();
            boolean valueMatch = match.getValue();

            if (newMatches.containsKey(keyMatch)) {
                if (newMatches.get(keyMatch) && valueMatch) {
                    fit.remove();
                } else if (newMatches.get(keyMatch) && !valueMatch) {
                    copyLastMatches.replace(keyMatch, false, true);
                } else if (!newMatches.get(keyMatch) && !valueMatch) {
                    fit.remove();
                }
            } else if (!newMatches.containsKey(keyMatch)) {
                fit.remove();
            }

        }
        if (copyLastMatches.isEmpty()) {
            System.out.println("No changes. Wait...");
        } else {
            lastListOfMatches = getAllMatches();

           /**
            * General.doAction(copyLastMatches);
            **/
        }
        Thread.sleep(600000);
        reloadMatches(lastListOfMatches);
    }
}
