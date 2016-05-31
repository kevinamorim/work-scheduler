package kevin.amorim.com.workscheduler.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.database.WorkerShift;

public class WorkerShiftItemAdapter  extends BaseAdapter {

    private Activity activity;
    private ArrayList<WorkerShift> data;
    private static LayoutInflater inflater = null;

    public WorkerShiftItemAdapter(Activity activity, ArrayList<WorkerShift> data) {
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
            vi = inflater.inflate(R.layout.worker_shift_item, null);
        }

        TextView tvWorkerShiftId = (TextView) vi.findViewById(R.id.tvWorkerShiftId);
        TextView tvWorkerId = (TextView) vi.findViewById(R.id.tvWorkerId);

        WorkerShift workerShift = data.get(position);

        tvWorkerShiftId.setText("" + workerShift.getId());
        tvWorkerId.setText("" + workerShift.getWorkerId());

        return vi;
    }
}
