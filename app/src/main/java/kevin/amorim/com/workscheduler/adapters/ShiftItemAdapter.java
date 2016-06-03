package kevin.amorim.com.workscheduler.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.database.Shift;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.helpers.TimeHelper;

public class ShiftItemAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Shift> data;
    private static LayoutInflater inflater = null;

    public ShiftItemAdapter(Activity activity, ArrayList<Shift> data) {
        this.activity = activity;
        this.data = data;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if(convertView == null) {
            vi = inflater.inflate(R.layout.shift_item, null);
        }

        TextView tvDayOfTheWeekName = (TextView) vi.findViewById(R.id.tvDayOfTheWeekName);
        TextView tvTime = (TextView) vi.findViewById(R.id.tvTime);

        TextView tvTotalTime = (TextView) vi.findViewById(R.id.tvTotalTime);

        Shift shift = data.get(position);

        tvDayOfTheWeekName.setText(shift.getDayOfTheWeek().getName());
        tvTime.setText(shift.getStartingTime() + " - " + shift.getEndingTime());
        tvTotalTime.setText("(" + TimeHelper.countHoursBetween(shift.getStartingTime(), shift.getEndingTime()) + ")");

        return vi;
    }
}
