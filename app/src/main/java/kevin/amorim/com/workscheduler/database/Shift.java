package kevin.amorim.com.workscheduler.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shift {

    public static final String TABLE_NAME = "shifts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAY_OF_THE_WEEK_ID = "dayOfTheWeekId";
    public static final String COLUMN_STARTING_TIME = "startingTime";
    public static final String COLUMN_ENDING_TIME = "endingTime";

    public static final Map<String, String> getValuesMap() {
        Map<String, String> values = new HashMap<>();

        values.put(Shift.COLUMN_ID, "INTEGER PRIMARY KEY");
        values.put(Shift.COLUMN_DAY_OF_THE_WEEK_ID, "INTEGER");
        values.put(Shift.COLUMN_STARTING_TIME, "TEXT");
        values.put(Shift.COLUMN_ENDING_TIME, "TEXT");

        return values;
    }

    private int id;
    private int dayOfTheWeekId;
    private String startingTime;
    private String endingTime;

    public Shift() {

    }

    public Shift(int id, int dayOfTheWeekId, String startingTime, String endingTime) {
        this.id = id;
        this.dayOfTheWeekId = dayOfTheWeekId;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public Shift(int dayOfTheWeekId, String startingTime, String endingTime) {
        this.dayOfTheWeekId = dayOfTheWeekId;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public int getId() {
        return id;
    }

    public int getDayOfTheWeekId() {
        return dayOfTheWeekId;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public static ArrayList<Shift> getAllFromCursor(Cursor c) {
        ArrayList<Shift> result = new ArrayList<>();

        if(c.moveToFirst()) {
            do {

                int id = c.getInt(c.getColumnIndex(Shift.COLUMN_ID));
                int dayOfTheWeekId = c.getInt(c.getColumnIndex(Shift.COLUMN_DAY_OF_THE_WEEK_ID));
                String startingTime = c.getString(c.getColumnIndex(Shift.COLUMN_STARTING_TIME));
                String endingTime = c.getString(c.getColumnIndex(Shift.COLUMN_ENDING_TIME));

                Shift shift = new Shift(id, dayOfTheWeekId, startingTime, endingTime);

                result.add(shift);

            } while(c.moveToNext());
        }

        return result;
    }

}
