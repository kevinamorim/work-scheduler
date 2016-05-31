package kevin.amorim.com.workscheduler.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.adapters.ShiftItemAdapter;
import kevin.amorim.com.workscheduler.database.DbHelper;
import kevin.amorim.com.workscheduler.database.Shift;

public class ShiftsActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shifts);

        mDbHelper = new DbHelper(this);
        updateShiftsList();
        setShiftsListListeners();
    }

    private void updateShiftsList() {
        ListView lvShifts = (ListView) findViewById(R.id.lvShifts);

        ArrayList<Shift> shifts = mDbHelper.getAllShifts();

        ShiftItemAdapter adapter = new ShiftItemAdapter(this, shifts);
        lvShifts.setAdapter(adapter);
    }

    private void setShiftsListListeners() {
        ListView lvShifts = (ListView) findViewById(R.id.lvShifts);

        lvShifts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddWorkerShiftActivity.class);
                intent.putExtra(AddWorkerShiftActivity.SHIFT_ID_EXTRA_KEY, (int)id);
                startActivity(intent);
            }
        });
    }

    public void goToAddShiftActivity(View view) {
        Intent intent = new Intent(this, AddShiftActivity.class);
        startActivity(intent);
    }
};
