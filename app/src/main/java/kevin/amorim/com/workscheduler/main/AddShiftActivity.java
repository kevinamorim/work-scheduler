package kevin.amorim.com.workscheduler.main;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.database.DayOfTheWeek;
import kevin.amorim.com.workscheduler.database.DbHelper;
import kevin.amorim.com.workscheduler.helpers.TimeHelper;

public class AddShiftActivity extends AppCompatActivity {

    Calendar calendar;
    boolean is24HourFormat;

    int startingHour;
    int startingMinute;

    int endingHour;
    int endingMinute;

    DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        calendar = Calendar.getInstance();
        is24HourFormat = true;

        startingHour = 0;
        startingMinute = 0;

        endingHour = 0;
        endingMinute = 0;

        mDbHelper = new DbHelper(this);


        Spinner spDaysOfTheWeek = (Spinner) findViewById(R.id.spDayOfTheWeek);

        ArrayList<DayOfTheWeek> daysOfTheWeeks = mDbHelper.getAllDaysOfTheWeek();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        for(int i = 0; i < daysOfTheWeeks.size(); i++) {
            adapter.add(daysOfTheWeeks.get(i).getName());
        }

        spDaysOfTheWeek.setAdapter(adapter);
    }

    public void changeStartingTime(View view) {
        new TimePickerDialog(this, changeStartingTimeDialogListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourFormat).show();
    }

    public void changeEndingTime(View view) {
        new TimePickerDialog(this, changeEndingTimeDialogListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourFormat).show();
    }

    TimePickerDialog.OnTimeSetListener changeStartingTimeDialogListener = new TimePickerDialog.OnTimeSetListener() {
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
          onStartingTimeChanged(hourOfDay, minute);
      }
    };

    TimePickerDialog.OnTimeSetListener changeEndingTimeDialogListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            onEndingTimeChanged(hourOfDay, minute);
        }
    };

    private void onStartingTimeChanged(int hour, int minute) {
        startingHour = hour;
        startingMinute = minute;

        TextView tvStartingTime = (TextView) findViewById(R.id.tvStartingTime);
        tvStartingTime.setText(TimeHelper.formatTimeString(startingHour, startingMinute));
    }

    private void onEndingTimeChanged(int hour, int minute) {
        endingHour = hour;
        endingMinute = minute;

        TextView tvEndingTime = (TextView) findViewById(R.id.tvEndingTime);
        tvEndingTime.setText(TimeHelper.formatTimeString(endingHour, endingMinute));
    }

    public void save(View view) {

        Spinner spDayOfTheWeek = (Spinner) findViewById(R.id.spDayOfTheWeek);
        String selectedDayOfTheWeekName = spDayOfTheWeek.getSelectedItem().toString();
        DayOfTheWeek selectedDayOfTheWeek = mDbHelper.getDayOfTheWeekByName(selectedDayOfTheWeekName);

        String startingTime = TimeHelper.formatTimeString(startingHour, startingMinute);
        String endingTime = TimeHelper.formatTimeString(endingHour, endingMinute);

        mDbHelper.insertShift(selectedDayOfTheWeek.getId(), startingTime, endingTime);

        Intent intent = new Intent(this, ShiftsActivity.class);
        startActivity(intent);
    }

}
