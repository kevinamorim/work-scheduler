package kevin.amorim.com.workscheduler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Map;

public class DbHelper extends SQLiteOpenHelper {

    private static final String LOG = "DbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WorkSchedule.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, Worker.TABLE_NAME, Worker.getValuesMap());
        createTable(db, DayOfTheWeek.TABLE_NAME, DayOfTheWeek.getValuesMap());
        createTable(db, Shift.TABLE_NAME, Shift.getValuesMap());

        seedDatabase(db);
    }

    private void createTable(SQLiteDatabase db, String tableName, Map<String, String> values) {

        String query = "CREATE TABLE " + tableName + " (";

        boolean first = true;
        for(Map.Entry<String, String> entry : values.entrySet()) {

            if(!first) {
                query += ", ";
            } else {
                first = false;
            }

            String valueName = entry.getKey();
            String valueType = entry.getValue();

            query += valueName + " " + valueType;
        }

        query += ");";

        db.execSQL(query);

    }

    private void seedDatabase(SQLiteDatabase db) {
        seedDaysOfTheWeek(db);
    }

    private void seedDaysOfTheWeek(SQLiteDatabase db) {
        insertDayOfTheWeek(db, "Monday");
        insertDayOfTheWeek(db, "Tuesday");
        insertDayOfTheWeek(db, "Wednesday");
        insertDayOfTheWeek(db, "Thursday");
        insertDayOfTheWeek(db, "Friday");
        insertDayOfTheWeek(db, "Saturday");
        insertDayOfTheWeek(db, "Sunday");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Worker.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DayOfTheWeek.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Shift.TABLE_NAME);
        onCreate(db);
    }

    public void insertWorker(String name, int workHours, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Worker.COLUMN_NAME, name);
        contentValues.put(Worker.COLUMN_WORK_HOURS, workHours);
        contentValues.put(Worker.COLUMN_PHONE_NUMBER, phoneNumber);

        db.insert(Worker.TABLE_NAME, null, contentValues);

        db.close();
    }

    public void insertDayOfTheWeek(SQLiteDatabase db, String name) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DayOfTheWeek.COLUMN_NAME, name);

        db.insert(DayOfTheWeek.TABLE_NAME, null, contentValues);
    }

    public void insertShift(int dayOfTheWeekId, String startingTime, String endingTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Shift.COLUMN_DAY_OF_THE_WEEK_ID, dayOfTheWeekId);
        contentValues.put(Shift.COLUMN_STARTING_TIME, startingTime);
        contentValues.put(Shift.COLUMN_ENDING_TIME, endingTime);

        db.insert(Shift.TABLE_NAME, null, contentValues);

        db.close();
    }

    public ArrayList<Worker> getAllWorkers() {
        ArrayList<Worker> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM workers", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            Worker worker = new Worker();

            worker.setId(res.getInt(res.getColumnIndex(Worker.COLUMN_ID)));
            worker.setName(res.getString(res.getColumnIndex(Worker.COLUMN_NAME)));
            worker.setWorkHours(res.getInt(res.getColumnIndex(Worker.COLUMN_WORK_HOURS)));
            worker.setPhoneNumber(res.getString(res.getColumnIndex(Worker.COLUMN_PHONE_NUMBER)));

            list.add(worker);
            res.moveToNext();
        }

        db.close();
        res.close();

        return list;
    }

    public ArrayList<DayOfTheWeek> getAllDaysOfTheWeek() {
        ArrayList<DayOfTheWeek> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DayOfTheWeek.TABLE_NAME, null);

        if(res.moveToFirst()) {
            do {
                int id = res.getInt(res.getColumnIndex(DayOfTheWeek.COLUMN_ID));
                String name = res.getString(res.getColumnIndex(DayOfTheWeek.COLUMN_NAME));

                DayOfTheWeek dayOfTheWeek = new DayOfTheWeek(id, name);
                list.add(dayOfTheWeek);
            } while(res.moveToNext());
        }

        db.close();
        res.close();

        return list;
    }

    public DayOfTheWeek getDayOfTheWeekByName(String dayOfTheWeekName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DayOfTheWeek.TABLE_NAME + " WHERE " + DayOfTheWeek.COLUMN_NAME + " = '" + dayOfTheWeekName + "'", null);

        ArrayList<DayOfTheWeek> daysOfTheWeek = new ArrayList<>();

        if(res.moveToFirst()) {
            do {
                int id = res.getInt(res.getColumnIndex(DayOfTheWeek.COLUMN_ID));
                String name = res.getString(res.getColumnIndex(DayOfTheWeek.COLUMN_NAME));

                DayOfTheWeek dayOfTheWeek = new DayOfTheWeek(id, name);
                daysOfTheWeek.add(dayOfTheWeek);
            } while(res.moveToNext());
        }

        db.close();
        res.close();

        if(daysOfTheWeek != null && daysOfTheWeek.size() > 0) {
            return daysOfTheWeek.get(0);
        }

        return null;

    }

    public ArrayList<Shift> getAllShifts() {
        ArrayList<Shift> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Shift.TABLE_NAME, null);

        if(res.moveToFirst()) {
            do {
                int id = res.getInt(res.getColumnIndex(Shift.COLUMN_ID));
                int dayOfTheWeekId = res.getInt(res.getColumnIndex(Shift.COLUMN_DAY_OF_THE_WEEK_ID));
                String startingTime = res.getString(res.getColumnIndex(Shift.COLUMN_STARTING_TIME));
                String endingTime = res.getString(res.getColumnIndex(Shift.COLUMN_ENDING_TIME));

                Shift shift = new Shift(id, dayOfTheWeekId, startingTime, endingTime);
                list.add(shift);
            } while(res.moveToNext());
        }

        db.close();

        return list;
    }

}
