package kevin.amorim.com.workscheduler.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.adapters.WorkerShiftItemAdapter;
import kevin.amorim.com.workscheduler.database.DbHelper;
import kevin.amorim.com.workscheduler.database.Worker;
import kevin.amorim.com.workscheduler.database.WorkerShift;

public class AddWorkerShiftActivity extends AppCompatActivity {

    public static final String SHIFT_ID_EXTRA_KEY = "ShiftId";

    private DbHelper mDbHelper;
    private int shiftId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker_shift);

        mDbHelper = new DbHelper(this);

        getCurrentShiftId();
        populateWorkersSpinner();
        updateShiftWorkersList();
    }

    private void getCurrentShiftId() {
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            shiftId = extras.getInt(SHIFT_ID_EXTRA_KEY);
        } else {
            shiftId = -1;
        }
    }

    private void populateWorkersSpinner() {
        Spinner spWorkers = (Spinner) findViewById(R.id.spWorkers);

        ArrayList<Worker> workers = mDbHelper.getAllWorkers();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        for(int i = 0; i < workers.size(); i++) {
            adapter.add(workers.get(i).getName());
        }

        spWorkers.setAdapter(adapter);
    }

    private void updateShiftWorkersList() {

        ArrayList<WorkerShift> workerShifts = mDbHelper.getWorkerShiftsByShiftId(shiftId);

        ListView lvShiftWorkers = (ListView) findViewById(R.id.lvShiftWorkers);

        WorkerShiftItemAdapter adapter = new WorkerShiftItemAdapter(this, workerShifts);
        lvShiftWorkers.setAdapter(adapter);

    }

    public void addWorkerToShift(View view) {

        Spinner spWorkers = (Spinner) findViewById(R.id.spWorkers);

        String name = spWorkers.getSelectedItem().toString();

        Worker selectedWorker = mDbHelper.getWorkerByName(name);

        WorkerShift workerShift = new WorkerShift(selectedWorker.getId(), shiftId);

        mDbHelper.insertWorkerShift(workerShift);

        updateShiftWorkersList();

    }
}
