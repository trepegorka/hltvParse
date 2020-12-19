package general;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import proxy.UserParser;

public class General {
    synchronized public static Document getHtml(String link) throws Exception {
        try {
            System.out.println("New request... ");
            //sleep
            Thread.sleep(1300);
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
