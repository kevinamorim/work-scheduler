package kevin.amorim.com.workscheduler.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.adapters.ShiftItemAdapter;
import kevin.amorim.com.workscheduler.database.DbHelper;
import kevin.amorim.com.workscheduler.database.Shift;
import kevin.amorim.com.workscheduler.database.Worker;

public class WorkerDetailsActivity extends AppCompatActivity {

    public static final String WORKER_ID_EXTRA_KEY = "WorkerId";

    private DbHelper mDbHelper;
    private Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details);

        mDbHelper = new DbHelper(this);

        getWorker();
        setWorkerDetails();
        updateScheduleList();
    }

    private void getWorker() {

        Bundle extras = getIntent().getExtras();

        int workerId = -1;

        if(extras != null) {
            workerId = extras.getInt(WORKER_ID_EXTRA_KEY);
        }

        if(workerId > -1) {
            worker = mDbHelper.getWorkerById(workerId);
        }

    }

    private void setWorkerDetails() {
        TextView tvWorkerName = (TextView) findViewById(R.id.tvWorkerName);
        tvWorkerName.setText(worker.getName());

        TextView tvWorkHours = (TextView) findViewById(R.id.tvWorkHours);
        tvWorkHours.setText("" + worker.getWorkHours());

        TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvPhoneNumber.setText(worker.getPhoneNumber());
    }

    private void updateScheduleList() {
        ListView lvSchedule = (ListView) findViewById(R.id.lvSchedule);

        ArrayList<Shift> shifts = mDbHelper.getShiftsByWorkerId(worker.getId());

        ShiftItemAdapter adapter = new ShiftItemAdapter(this, shifts);
        lvSchedule.setAdapter(adapter);
    }

    public void sendSchedule(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + worker.getPhoneNumber()));
        intent.putExtra("sms_body", buildSms());
        startActivity(intent);

    }

    private String buildSms() {

        String message = "Olá, " + worker.getName() + "\n \n";

        ArrayList<Shift> shifts = mDbHelper.getShiftsByWorkerId(worker.getId());

        for(int i = 0; i < shifts.size(); i++) {
            message += shifts.toString();
            message += "\n";
        }

        return message;

    }

}
