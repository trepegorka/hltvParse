package general;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;

public class General {
    public static Document getHtml(String link) throws Exception {
        try {
            System.out.println("New request... ");
            //sleep
            Thread.sleep(2000);
            return Jsoup.connect(link)
                    .referrer("https://www.hltv.org/")
                    .userAgent(UserParser.userAgent)
                    .ignoreHttpErrors(true)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Retry to connect");
            Thread.sleep(50000);
            getHtml(link);
        }
        return null;
    }
}
