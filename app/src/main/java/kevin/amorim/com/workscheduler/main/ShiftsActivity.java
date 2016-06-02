package kevin.amorim.com.workscheduler.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    private int shiftToDeleteId = -1;

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

        lvShifts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                shiftToDeleteId = (int) id;

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setMessage(view.getResources().getString(R.string.confirmDelete));
                alertDialogBuilder.setPositiveButton(view.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteShift();
                    }
                });
                alertDialogBuilder.setNegativeButton(view.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });
    }

    private void deleteShift() {
        mDbHelper.deleteShift(shiftToDeleteId);
        updateShiftsList();
    }

    public void goToAddShiftActivity(View view) {
        Intent intent = new Intent(this, AddShiftActivity.class);
        startActivity(intent);
    }
};
