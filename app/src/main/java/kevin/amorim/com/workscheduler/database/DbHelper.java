package kevin.amorim.com.workscheduler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String LOG = "DbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WorkSchedule.db";

    private static final String TABLE_WORKERS = "workers";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_WORKHOURS = "workHours";

    private static final String CREATE_WORKERS_TABLE = "CREATE TABLE " + TABLE_WORKERS + " (" + COL_ID + " INTEGER PRIMARY KEY, " + COL_NAME + " TEXT, " + COL_WORKHOURS + " INTEGER)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORKERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS workers");
        onCreate(db);
    }

    public void insertWorker(String name, int workHours) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("workHours", workHours);

        db.insert("workers", null, contentValues);
    }

    public ArrayList<Worker> getAllWorkers() {
        ArrayList<Worker> list = new ArrayList<Worker>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM workers", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            Worker worker = new Worker();

            worker.setId(res.getInt(res.getColumnIndex(COL_ID)));
            worker.setName(res.getString(res.getColumnIndex(COL_NAME)));
            worker.setWorkHours(res.getInt(res.getColumnIndex(COL_WORKHOURS)));

            list.add(worker);
            res.moveToNext();
        }

        return list;
    }

}
