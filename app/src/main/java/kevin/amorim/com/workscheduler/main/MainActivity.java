package kevin.amorim.com.workscheduler.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kevin.amorim.com.workscheduler.R;
import kevin.amorim.com.workscheduler.database.DbHelper;
import kevin.amorim.com.workscheduler.database.Worker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToWorkers(View view) {
        Intent intent = new Intent(this, WorkersActivity.class);
        startActivity(intent);
    }

}
