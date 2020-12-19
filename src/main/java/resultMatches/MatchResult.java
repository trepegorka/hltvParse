package resultMatches;

import Abstraction.Match;
import Abstraction.Team;
import database.HltvDatabaseManager;
import general.General;
import general.logics.AdvantageGenerator;
import hltv.matches.teams.players.Player;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import resultMatches.pastTeams.PastTeam;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchResult extends Match {

    private ArrayList<String> listOfMaps;

    public MatchResult(String matchLink) throws Exception {
        super(matchLink);
    }

    //write to file 'list of links' with past matches
    public void writeResultLinks() throws Exception {
        FileWriter writer = new FileWriter("src/main/java/resultMatches/resultLinks.txt", true);
        ArrayList<String> linksList = new ArrayList<>();
        int offset = 300;
        for (int i = 0; i < 220; i++) {
            Document hltvResults = General.getHtml("https://www.hltv.org/results?offset=" + offset);
            Elements links = hltvResults.select("body > div.bgPadding > div > div.colCon > div.contentCol > div.results > div.results-holder > div.results-all").select("a");
            for (Element link : links) {
                linksList.add(link.attr("abs:href"));
                writer.append(link.attr("abs:href")).append("\n").flush();
            }
            offset += 100;
            System.out.println("size = " + linksList.size() + ", wait");
            Thread.sleep(1000);
        }
        writer.close();
    }


    @Override
    public String dateOfMatch() throws ParseException {
        String date = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.date").text();
        String year = date.substring(date.length() - 4);
        String day = "";
        String regex = "^\\d{1,2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            day = matcher.group(0);
        }
        if (Integer.parseInt(day) < 10) {
            StringBuilder builder = new StringBuilder();
            day = builder.append("0").append(day).toString();
        }
        String month = "";
        for (Months monthS : Months.values()) {
            if (date.contains(monthS.getName())) {
                month = monthS.getTitle();
                break;
            }
        }
        String wellDate = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(wellDate));
        c.add(Calendar.DATE, -1);
        wellDate = sdf.format(c.getTime());
        return wellDate;
    }

    public ArrayList<String> mapPick() {
        ArrayList<String> list = new ArrayList<>();
        Elements column = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small");
        for (Element mapholder : column.select("div.flexbox-column > div.mapholder")) {
            if (mapholder.child(0).hasClass("played")) {
                list.add(mapholder.child(0).text());
            }
        }
        this.listOfMaps = list;
        return list;
    }

    public int mapPicker(int mapNumber) {
        Elements mapholder = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(" + mapNumber + ")");

        if (mapholder.select("div.results.played > div.results-left").hasClass("pick")) {
            return 1;
        }
        if (mapholder.select("div.results.played > span.results-right").hasClass("pick")) {
            return 2;
        } else return 0;
    }

    //if winner = 0 -> left won. left = 1
    public int matchWinner() {
        if (getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div:nth-child(1) > div > div").hasClass("won")) {
            return 0;
        } else return 1;
    }

    public int mapWinner(int numberOfMap) {
        if (getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(" + numberOfMap + ") > div.results.played > div.results-left").hasClass("won")) {
            return 0;
        } else return 1;
    }
}

class MainResults {
    //fill database by team attitudes for all played maps.
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/main/java/resultMatches/resultLinks.txt"));
        int number = 0;
        while (scanner.hasNextLine()) {
            number++;
            System.out.println("Match №" + number);
            System.out.println("Match download...");
            Match matchResult = new MatchResult(scanner.nextLine());
            System.out.println("Match downloaded successfully\n");
            int mapNumber = 1;
            for (String map : matchResult.mapPick()) {
                System.out.println(map);
                if (map.equals("Default")) {
                    mapNumber++;
                    continue;
                }
                try {
                    System.out.println("First team download...");
                    Team leftTeam = new PastTeam(map, matchResult, "left");
                    System.out.println("First team downloaded successfully\n");
                    System.out.println("Second team download...");
                    Team rightTeam = new PastTeam(map, matchResult, "right");
                    System.out.println("Second team downloaded successfully\n");
                    AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);
                    System.out.println("Database filling...");
                    HltvDatabaseManager manager = new HltvDatabaseManager(generator, matchResult);
                    manager.fillLine(mapNumber);
                    mapNumber++;
                    System.out.println("Database filled successful\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.sleep(30000);
                }
            }
            for (int i = 0; i < 35; i++) {
                System.out.print("*");
            }
        }
    }
}





