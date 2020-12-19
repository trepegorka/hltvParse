package resultMatches.pastTeams;

import Abstraction.Match;
import Abstraction.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// "left" / "right"
public class PastTeam extends Team {

    public PastTeam(String map, Match match, String leftOrRight) throws Exception {
        super(map, match, leftOrRight);
    }

    @Override
    public String get3mFormat() throws ParseException { //?startDate=2020-06-02&endDate=2020-09-02 will return like that
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(getMatch().dateOfMatch());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -91);
        Date m3Ago = calendar.getTime();
        String formatter = sdf.format(m3Ago);
        String aaaa  = "?startDate=" + formatter + "&endDate=" + getMatch().dateOfMatch();
        return aaaa;
    }
}
