package hltv.matches.upcoming;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Updater extends Thread {

    private static String timer(int minutes) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(UpcomingMatch.getTimeNow());
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, minutes);
        return df.format(cal.getTime());
    }

    @Override
    public void run() {
        while (true) {
            try {

                for (Map.Entry<String, String> entry : UpcomingMatch.linkAndTime().entrySet()) {
                    if (entry.getValue().equals(timer(5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15))) {
                        //if link was not load earlier
                        if (!UpcomingMatch.linksAlreadyUsed.contains(entry.getKey())) {
                            UpcomingMatch.toLoad.add(entry.getKey());
                            UpcomingMatch.linksAlreadyUsed.add(entry.getKey());
                        }
                    }
                }
                if(!UpcomingMatch.toLoad.isEmpty()) {
                    Loader loader = new Loader();
                    loader.start();
                    loader.join();
                } else {
                    Thread.sleep(30000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
