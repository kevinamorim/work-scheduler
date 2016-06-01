package kevin.amorim.com.workscheduler.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WorkerShift {

    /*
        Database constants
     */
    public static final String TABLE_NAME = "workerShifts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORKER_ID = "workerId";
    public static final String COLUMN_SHIFT_ID = "shiftId";

    /*
        Fields
     */
    private int id;
    private int workerId;
    private int shiftId;

    public WorkerShift(int id, int workerId, int shiftId) {
        this.id = id;
        this.workerId = workerId;
        this.shiftId = shiftId;
    }

    public WorkerShift(int workerId, int shiftId) {
        this.workerId = workerId;
        this.shiftId = shiftId;
    }

    /*
        Getters
     */
    public int getId() { return id; }

    public int getWorkerId() { return workerId; }

    public int getShiftId() { return shiftId; }

    /*
        Setters
     */

    /*
        Database helpers
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(WorkerShift.COLUMN_WORKER_ID, workerId);
        values.put(WorkerShift.COLUMN_SHIFT_ID, shiftId);

        return values;
    }

    public static final Map<String, String> getValuesMap() {
        Map<String, String> values = new LinkedHashMap<>();

        values.put(WorkerShift.COLUMN_ID, SqlDataTypes.PRIMARY_KEY);
        values.put(WorkerShift.COLUMN_WORKER_ID, SqlDataTypes.INTEGER);
        values.put(WorkerShift.COLUMN_SHIFT_ID, SqlDataTypes.INTEGER);

        return values;
    }

    public static ArrayList<WorkerShift> getAllFromCursor(Cursor c) {
        ArrayList<WorkerShift> result = new ArrayList<>();

        if(c.moveToFirst()) {
            do {

                int id = c.getInt(c.getColumnIndex(WorkerShift.COLUMN_ID));
                int workerId = c.getInt(c.getColumnIndex(WorkerShift.COLUMN_WORKER_ID));
                int shiftId = c.getInt(c.getColumnIndex(WorkerShift.COLUMN_SHIFT_ID));

                WorkerShift workerShift = new WorkerShift(id, workerId, shiftId);

                result.add(workerShift);

            } while(c.moveToNext());
        }

        return result;
    }
}
