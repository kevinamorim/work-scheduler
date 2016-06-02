package kevin.amorim.com.workscheduler.database;

import android.database.Cursor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kevin.amorim.com.workscheduler.helpers.TimeHelper;

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

    private DayOfTheWeek dayOfTheWeek;

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

    /*
        Getters
     */
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

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek != null ? dayOfTheWeek : new DayOfTheWeek("Error");
    }

    /*
        Setters
     */
    public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
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

    @Override
    public String toString() {
        String result = "";

        if(dayOfTheWeek != null) {
            result += dayOfTheWeek.getName();
        } else {
            result += "Error";
        }

        result += ": ";

        result += startingTime + " - " + endingTime;

        return result;
    }

    public static ArrayList<Shift> reorder(ArrayList<Shift> shifts) {
        ArrayList<Shift> result = new ArrayList<>();
        ArrayList<Integer> daysDone = new ArrayList<>();

        int firstDay = 1;
        int totalDays = 7;

        for(int currentWeekDay = firstDay; currentWeekDay < (totalDays + firstDay); currentWeekDay++) {

            // Build array with days of the current day
            ArrayList<Shift> tempArray = new ArrayList<>();

            for(int i = 0; i < shifts.size(); i++) {
                if(shifts.get(i).getDayOfTheWeekId() == currentWeekDay) {
                    tempArray.add(shifts.get(i));
                }
            }

            int count = 0;

            while(count != tempArray.size()) {
                for(int i = 0; i < shifts.size(); i++) {

                    if(daysDone.contains(i)) {
                        continue;
                    }

                    if(shifts.get(i).getDayOfTheWeekId() == currentWeekDay) {
                        int temp = i;
                        for(int j = 0; j < shifts.size(); j++) {

                            if(daysDone.contains(j) || j == i) {
                                continue;
                            }

                            if(shifts.get(j).getDayOfTheWeekId() == currentWeekDay) {
                                if(TimeHelper.isTimeLessThan(shifts.get(j).getStartingTime(), shifts.get(temp).getStartingTime())) {
                                    temp = j;
                                }
                            }
                        }

                        result.add(shifts.get(temp));
                        daysDone.add(temp);
                        count++;
                    }
                }
            }
        }


        return result;
    }

}
