package kevin.amorim.com.workscheduler.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.adapters.WorkerShiftItemAdapter;
import kevin.amorim.com.workscheduler.database.DayOfTheWeek;
import kevin.amorim.com.workscheduler.database.DbHelper;
import kevin.amorim.com.workscheduler.database.Shift;
import kevin.amorim.com.workscheduler.database.Worker;
import kevin.amorim.com.workscheduler.database.WorkerShift;

public class AddWorkerShiftActivity extends AppCompatActivity {

    public static final String SHIFT_ID_EXTRA_KEY = "ShiftId";

    private DbHelper mDbHelper;
    private Shift shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker_shift);

        mDbHelper = new DbHelper(this);

        getCurrentShift();
        updateTitle();
        populateWorkersSpinner();
        updateShiftWorkersList();
    }

    private void getCurrentShift() {
        Bundle extras = getIntent().getExtras();

        int shiftId = -1;

        if(extras != null) {
            shiftId = extras.getInt(SHIFT_ID_EXTRA_KEY);
        }

        if(shiftId > -1) {
            shift = mDbHelper.getShiftById(shiftId);
        }
    }

    private void updateTitle() {
        DayOfTheWeek dayOfTheWeek = mDbHelper.getDayOfTheWeekById(shift.getDayOfTheWeekId());

        String title = dayOfTheWeek.getName() + " | " + shift.getStartingTime() + " - " + shift.getEndingTime();

        TextView tvShiftTitle = (TextView) findViewById(R.id.tvShiftTitle);
        tvShiftTitle.setText(title);
    }

    private void populateWorkersSpinner() {
        Spinner spWorkers = (Spinner) findViewById(R.id.spWorkers);

        ArrayList<Worker> workers = mDbHelper.getAllWorkers();
        ArrayList<WorkerShift> workerShifts = mDbHelper.getWorkerShiftsByShiftId(shift.getId());

        ArrayList<Integer> workersToHide = new ArrayList<>();
        for(int i = 0; i < workers.size(); i++) {
            for(int j = 0; j < workerShifts.size(); j++) {
                if(workerShifts.get(j).getWorkerId() == workers.get(i).getId()) {
                    workersToHide.add(i);
                    break;
                }
            }
        }

        Log.e("AddWorkerShiftActivity", "WorkersToHide: " + workersToHide.size());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        for(int i = 0; i < workers.size(); i++) {

            if(workersToHide.contains(i)) {
                continue;
            }

            adapter.add(workers.get(i).getName());
        }

        spWorkers.setAdapter(adapter);
    }

    private void updateShiftWorkersList() {

        ArrayList<WorkerShift> workerShifts = mDbHelper.getWorkerShiftsByShiftId(shift.getId());

        ListView lvShiftWorkers = (ListView) findViewById(R.id.lvShiftWorkers);

        WorkerShiftItemAdapter adapter = new WorkerShiftItemAdapter(this, workerShifts);
        lvShiftWorkers.setAdapter(adapter);

    }

    public void addWorkerToShift(View view) {

        Spinner spWorkers = (Spinner) findViewById(R.id.spWorkers);

        String name = spWorkers.getSelectedItem().toString();

        Worker selectedWorker = mDbHelper.getWorkerByName(name);

        WorkerShift workerShift = new WorkerShift(selectedWorker.getId(), shift.getId());

        mDbHelper.insertWorkerShift(workerShift);

        updateShiftWorkersList();

    }
}
