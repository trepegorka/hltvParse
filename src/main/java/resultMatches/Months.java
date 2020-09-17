package resultMatches;

public enum Months {

    January("January", "01"),
    February("February", "02"),
    March("March", "03"),
    April("April", "04"),
    May("May", "05"),
    June("June", "06"),
    July("July", "07"),
    August("August", "08"),
    September("September", "09"),
    October("October", "10"),
    November("November", "11"),
    December("December", "12");

    private final String title;
    private final String name;

    Months(String name, String title) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

}
