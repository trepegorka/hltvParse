import hltv.Hltv;
import hltv.MainLifeBuilder;
import hltv.matches.upcoming.Loader;
import hltv.matches.upcoming.Updater;

import java.io.File;
import java.util.*;

class Main {
    private static List<String> lifeMatchesLinks;

    static {
        try {
            lifeMatchesLinks = Hltv.getHltv().getLiveMatchesLinks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        Updater updater = new Updater();
//        updater.start();
//        for (String lifeMatchLink : lifeMatchesLinks) {
//            new MainLifeBuilder().loadAllLife(lifeMatchLink);
//        }
//        reloadList();
//
        Scanner scanner = new Scanner(new File("src/main/java/result13.txt"));
        while (scanner.hasNextLine()){
            new MainLifeBuilder().loadPast(scanner.nextLine());
        }
    }

    //new list for comparison
    private static void reloadList() throws Exception {
        System.out.println("\nReloadMatches...");
        List<String> nextlifeMatchesLinks = Hltv.getHltv().getLiveMatchesLinks();
        nextlifeMatchesLinks.removeAll(lifeMatchesLinks);
        if (nextlifeMatchesLinks.isEmpty()) {
            System.out.println("\nNo one new match. Sleep...");
            Thread.sleep(5000);
            reloadList();
        } else {
            lifeMatchesLinks.addAll(nextlifeMatchesLinks);
            for (String lifeMatchLink : nextlifeMatchesLinks) {
                new MainLifeBuilder().loadAllLife(lifeMatchLink);
            }
            reloadList();
        }
    }
}