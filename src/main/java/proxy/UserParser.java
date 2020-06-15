package proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class UserParser {
    private Document getProxyListNetHtml() throws IOException {
        return Jsoup.connect("https://free-proxy-list.net")
                .userAgent("OPR/68.0.3618.125")
                .referrer("http://www.google.com")
                .get();
    }

    public String getRandomIp() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Elements ip = getProxyListNetHtml().select("#proxylisttable > tbody > tr:nth-child(" + i + ") > td:nth-child(1)");
            list.add(ip.text());
        }
        Random rand = new Random();
        return ("OPR/" + list.get(rand.nextInt(list.size())));
    }
}
