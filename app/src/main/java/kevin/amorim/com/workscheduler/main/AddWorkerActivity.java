package kevin.amorim.com.workscheduler.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.database.DayOfTheWeek;
import kevin.amorim.com.workscheduler.database.DbHelper;

public class AddWorkerActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);

        mDbHelper = new DbHelper(this);

        Spinner spDaysOfTheWeek = (Spinner) findViewById(R.id.spDaysOfTheWeek);

        ArrayList<DayOfTheWeek> daysOfTheWeeks = mDbHelper.getAllDaysOfTheWeek();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        for(int i = 0; i < daysOfTheWeeks.size(); i++) {
            adapter.add(daysOfTheWeeks.get(i).getName());
        }

        spDaysOfTheWeek.setAdapter(adapter);

    }

    public void addWorker(View view) {

        EditText etWorkerName = (EditText) findViewById(R.id.etWorkerName);
        EditText etWorkerWorkHours = (EditText) findViewById(R.id.etWorkerWorkHours);

        String workerName = etWorkerName.getText().toString();
        int workerWorkHours = Integer.parseInt(etWorkerWorkHours.getText().toString());

        mDbHelper.insertWorker(workerName, workerWorkHours);

        Intent intent = new Intent(this, WorkersActivity.class);
        startActivity(intent);
    };


}
