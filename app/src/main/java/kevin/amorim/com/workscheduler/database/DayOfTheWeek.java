package kevin.amorim.com.workscheduler.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DayOfTheWeek {

    public static final String TABLE_NAME = "daysOfTheWeek";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    public static final Map<String, String> getValuesMap() {
        Map<String, String> values = new HashMap<>();

        values.put(Worker.COLUMN_ID, "INTEGER PRIMARY KEY");
        values.put(Worker.COLUMN_NAME, "TEXT");

        return values;
    }

    private int id;
    private String name;

    public DayOfTheWeek() {

    }

    public DayOfTheWeek(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DayOfTheWeek(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<DayOfTheWeek> getAllFromCursor(Cursor c) {
        ArrayList<DayOfTheWeek> result = new ArrayList<>();

        if(c.moveToFirst()) {
            do {

                int id = c.getInt(c.getColumnIndex(DayOfTheWeek.COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DayOfTheWeek.COLUMN_NAME));

                DayOfTheWeek dayOfTheWeek = new DayOfTheWeek(id, name);

                result.add(dayOfTheWeek);

            } while(c.moveToNext());
        }

        return result;
    }

}
