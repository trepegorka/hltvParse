package hltv.matches.upcoming;

import hltv.MainLifeBuilder;

public class Loader extends Thread {
    @Override
    public void run() {
        try {
            if (!UpcomingMatch.toLoad.isEmpty()) {
                for (String link : UpcomingMatch.toLoad) {
                    new MainLifeBuilder().loadAllUpcoming(link);
                    UpcomingMatch.toLoad.remove(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
