package kevin.amorim.com.workscheduler.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Worker {

    public static final String TABLE_NAME = "workers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_WORK_HOURS = "workHours";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";

    private int id;
    private String name;
    private int workHours;
    private String phoneNumber;

    public static final Map<String, String> getValuesMap() {
        Map<String, String> values = new HashMap<>();

        values.put(Worker.COLUMN_ID, "INTEGER PRIMARY KEY");
        values.put(Worker.COLUMN_NAME, "TEXT");
        values.put(Worker.COLUMN_WORK_HOURS, "INTEGER");
        values.put(Worker.COLUMN_PHONE_NUMBER, "TEXT");

        return values;
    }

    public Worker() {

    }

    public Worker(String name, int workHours, String phoneNumber) {
        this.name = name;
        this.workHours = workHours;
        this.phoneNumber = phoneNumber;
    }

    public Worker(int id, String name, int workHours, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.workHours = workHours;
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWorkHours() {
        return workHours;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public static ArrayList<Worker> getAllFromCursor(Cursor c) {
        ArrayList<Worker> result = new ArrayList<>();

        if(c.moveToFirst()) {
            do {

                int id = c.getInt(c.getColumnIndex(Worker.COLUMN_ID));
                String name = c.getString(c.getColumnIndex(Worker.COLUMN_NAME));
                int workHours = c.getInt(c.getColumnIndex(Worker.COLUMN_WORK_HOURS));
                String phoneNumber = c.getString(c.getColumnIndex(Worker.COLUMN_PHONE_NUMBER));

                Worker worker = new Worker(id, name, workHours, phoneNumber);

                result.add(worker);

            } while(c.moveToNext());
        }

        return result;
    }
}
