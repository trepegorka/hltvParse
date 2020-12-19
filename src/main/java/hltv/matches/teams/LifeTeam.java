package hltv.matches.teams;

import Abstraction.Match;
import Abstraction.Team;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LifeTeam extends Team {


    public LifeTeam(String map, Match match, String leftOrRight) throws Exception {
        super(map, match, leftOrRight);
    }

    @Override
    public String get3mFormat() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(String.valueOf(java.time.LocalDate.now()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -91);
        Date m3Ago = calendar.getTime();
        String formatter = sdf.format(m3Ago);
        return ("?startDate=" + formatter + "&endDate=" + java.time.LocalDate.now());
    }

}
