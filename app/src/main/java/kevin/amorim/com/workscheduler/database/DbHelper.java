package kevin.amorim.com.workscheduler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
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
        createTable(db, WorkerShift.TABLE_NAME, WorkerShift.getValuesMap());

        seedDatabase(db);
    }

    private void createTable(SQLiteDatabase db, String tableName, Map<String, String> values) {
        db.execSQL(SqlQueryCreator.createTable(tableName, values));
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

    /*
        INSERT
     */
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

    public void insertWorkerShift(WorkerShift workerShift) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(WorkerShift.TABLE_NAME, null, workerShift.getContentValues());

        db.close();
    }

    /*
        GET
     */
    public ArrayList<Worker> getAllWorkers() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SqlQueryCreator.selectAllFrom(Worker.TABLE_NAME), null);

        ArrayList<Worker> result = Worker.getAllFromCursor(c);

        c.close();
        db.close();

        return result;
    }

    public Worker getWorkerById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(SqlQueryCreator.selectAllFromById(Worker.TABLE_NAME, Worker.COLUMN_ID, id), null);

        ArrayList<Worker> result = Worker.getAllFromCursor(c);

        c.close();
        db.close();

        return getSingleFromArrayList(result);
    }

    public Worker getWorkerByName(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(SqlQueryCreator.selectAllFromByStringParameter(Worker.TABLE_NAME, Worker.COLUMN_NAME, name), null);

        ArrayList<Worker> result = Worker.getAllFromCursor(c);

        c.close();
        db.close();

        return (result != null && result.size() > 0) ? result.get(0) : null;
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

    public DayOfTheWeek getDayOfTheWeekById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SqlQueryCreator.selectAllFromById(DayOfTheWeek.TABLE_NAME, DayOfTheWeek.COLUMN_ID, id), null);

        ArrayList<DayOfTheWeek> result = DayOfTheWeek.getAllFromCursor(c);

        c.close();
        db.close();

        return (result != null && result.size() > 0) ? result.get(0) : null;
    }

    public ArrayList<Shift> getAllShifts() {
        ArrayList<Shift> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Shift.TABLE_NAME, null);

        if(res.moveToFirst()) {
            do {
                int id = res.getInt(res.getColumnIndex(Shift.COLUMN_ID));
                int dayOfTheWeekId = res.getInt(res.getColumnIndex(Shift.COLUMN_DAY_OF_THE_WEEK_ID));
                String startingTime = res.getString(res.getColumnIndex(Shift.COLUMN_STARTING_TIME));
                String endingTime = res.getString(res.getColumnIndex(Shift.COLUMN_ENDING_TIME));

                Shift shift = new Shift(id, dayOfTheWeekId, startingTime, endingTime);
                result.add(shift);
            } while(res.moveToNext());
        }

        for(int i = 0; i < result.size(); i++) {
            result.get(i).setDayOfTheWeek(getDayOfTheWeekById(result.get(i).getDayOfTheWeekId()));
        }

        db.close();

        return Shift.reorder(result);
    }

    public Shift getShiftById(int shiftId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SqlQueryCreator.selectAllFromById(Shift.TABLE_NAME, Shift.COLUMN_ID, shiftId), null);

        ArrayList<Shift> result = Shift.getAllFromCursor(c);

        for(int i = 0; i < result.size(); i++) {
            result.get(i).setDayOfTheWeek(getDayOfTheWeekById(result.get(i).getDayOfTheWeekId()));
        }

        c.close();
        db.close();

        return (result != null && result.size() > 0) ? result.get(0) : null;
    }

    public ArrayList<Shift> getShiftsByWorkerId(int workerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<WorkerShift> workerShifts = getWorkerShiftsByWorkerId(workerId);
        ArrayList<Shift> shifts = new ArrayList<>();

        for(int i = 0; i < workerShifts.size(); i++) {
            Shift shift = getShiftById(workerShifts.get(i).getShiftId());
            shift.setDayOfTheWeek(getDayOfTheWeekById(shift.getDayOfTheWeekId()));
            shifts.add(shift);
        }

        db.close();

        return Shift.reorder(shifts);
    }

    public ArrayList<WorkerShift> getWorkerShiftsByShiftId(int shiftId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(SqlQueryCreator.selectAllFromById(WorkerShift.TABLE_NAME, WorkerShift.COLUMN_SHIFT_ID, shiftId), null);

        ArrayList<WorkerShift> result = WorkerShift.getAllFromCursor(c);

        for(int i = 0; i < result.size(); i++) {
            result.get(i).setWorker(getWorkerById(result.get(i).getWorkerId()));
        }

        c.close();
        db.close();

        return result;
    }

    public ArrayList<WorkerShift> getWorkerShiftsByWorkerId(int workerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(SqlQueryCreator.selectAllFromById(WorkerShift.TABLE_NAME, WorkerShift.COLUMN_WORKER_ID, workerId), null);

        ArrayList<WorkerShift> result = WorkerShift.getAllFromCursor(c);

        for(int i = 0; i < result.size(); i++) {
            result.get(i).setWorker(getWorkerById(result.get(i).getWorkerId()));
        }

        c.close();
        db.close();

        return result;
    }

    /*
        Helpers
     */
    private <T> T getSingleFromArrayList(ArrayList<T> arrayList) {
        return (arrayList != null && arrayList.size() > 0) ? arrayList.get(0) : null;
    }

}
