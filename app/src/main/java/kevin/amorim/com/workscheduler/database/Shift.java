package kevin.amorim.com.workscheduler.database;

public class Shift {

    private int id;
    private DayOfTheWeek dayOfTheWeek;
    private String startingHour;
    private String endingHour;

    public Shift() {

    }

    public Shift(int id, DayOfTheWeek dayOfTheWeek, String startingHour, String endingHour) {
        this.id = id;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startingHour = startingHour;
        this.endingHour = endingHour;
    }

    public Shift(DayOfTheWeek dayOfTheWeek, String startingHour, String endingHour) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.startingHour = startingHour;
        this.endingHour = endingHour;
    }

}
