package general;

import Abstraction.Match;
import hltv.MainLifeBuilder;


public class ThreadMatch extends Thread {
    private Match match;

    public ThreadMatch(Match match) {
        this.match = match;
    }

    @Override
    public void run() {
        try {
            while (true) {
                match = match.updatedDoc();
                if (match.mapPick().get(0).equals("TBA")) {
                    Thread.sleep(10000);
                } else {
                    break;
                }
            }
            new MainLifeBuilder().loadAllLife(match.getMatchLink());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

