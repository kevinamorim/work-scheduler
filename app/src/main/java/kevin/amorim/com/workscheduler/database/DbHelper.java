package kevin.amorim.com.workscheduler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
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
        Map<String, String> workersValues = new HashMap<>();
        workersValues.put(Worker.COLUMN_ID, "INTEGER PRIMARY KEY");
        workersValues.put(Worker.COLUMN_NAME, "TEXT");
        workersValues.put(Worker.COLUMN_WORK_HOURS, "INTEGER");

        createTable(db, Worker.TABLE_NAME, workersValues);
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS workers");
        onCreate(db);
    }

    public void insertWorker(String name, int workHours) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Worker.COLUMN_NAME, name);
        contentValues.put(Worker.COLUMN_WORK_HOURS, workHours);

        db.insert(Worker.TABLE_NAME, null, contentValues);
    }

    public ArrayList<Worker> getAllWorkers() {
        ArrayList<Worker> list = new ArrayList<Worker>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM workers", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            Worker worker = new Worker();

            worker.setId(res.getInt(res.getColumnIndex(Worker.COLUMN_ID)));
            worker.setName(res.getString(res.getColumnIndex(Worker.COLUMN_NAME)));
            worker.setWorkHours(res.getInt(res.getColumnIndex(Worker.COLUMN_WORK_HOURS)));

            list.add(worker);
            res.moveToNext();
        }

        return list;
    }

}
