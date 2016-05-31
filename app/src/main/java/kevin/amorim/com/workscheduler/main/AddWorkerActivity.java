package kevin.amorim.com.workscheduler.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.database.DbHelper;

public class AddWorkerActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);

        mDbHelper = new DbHelper(this);

    }

    public void addWorker(View view) {

        EditText etWorkerName = (EditText) findViewById(R.id.etWorkerName);
        EditText etWorkerWorkHours = (EditText) findViewById(R.id.etWorkerWorkHours);
        EditText etWorkerPhoneNumber = (EditText) findViewById(R.id.etWorkerPhoneNumber);

        String workerName = etWorkerName.getText().toString();
        int workerWorkHours = Integer.parseInt(etWorkerWorkHours.getText().toString());
        String workerPhoneNumber = etWorkerPhoneNumber.getText().toString();

        mDbHelper.insertWorker(workerName, workerWorkHours, workerPhoneNumber);

        Intent intent = new Intent(this, WorkersActivity.class);
        startActivity(intent);

    };


}
