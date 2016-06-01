package kevin.amorim.com.workscheduler.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.adapters.WorkerItemAdapter;
import kevin.amorim.com.workscheduler.database.DbHelper;
import kevin.amorim.com.workscheduler.database.Worker;

public class WorkersActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);

        mDbHelper = new DbHelper(this);

        ListView lvWorkersList = (ListView) findViewById(R.id.lvWorkersList);

        ArrayList<Worker> workers = mDbHelper.getAllWorkers();

        WorkerItemAdapter adapter = new WorkerItemAdapter(this, workers);
        lvWorkersList.setAdapter(adapter);

        setWorkersListListeners();

    }

    private void setWorkersListListeners() {
        ListView lvWorkersList = (ListView) findViewById(R.id.lvWorkersList);
        lvWorkersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WorkerDetailsActivity.class);
                intent.putExtra(WorkerDetailsActivity.WORKER_ID_EXTRA_KEY, (int)id);
                startActivity(intent);
            }
        });
    }

    public void goToAddWorker(View view) {
        Intent intent = new Intent(this, AddWorkerActivity.class);
        startActivity(intent);
    }


}
