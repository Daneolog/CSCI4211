package user.com.csci4211;

import android.content.Context;
import android.content.Intent;
import android.hardware.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.*;

import java.net.URISyntaxException;

public class StepCountActivity extends AppCompatActivity implements SensorEventListener {
    private Socket socket;
    private SensorManager manager;
    private EditText count;
    boolean activityRunning;
    Context context;

    long stepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);
        count = (EditText) findViewById(R.id.stepCount);
        Button writeData = (Button)findViewById(R.id.writeStepCount);
        Button history = (Button)findViewById(R.id.stepCountHistory);
        context = getApplicationContext();

        try { socket = IO.socket("http://10.134.148.217:3000"); }
        catch (URISyntaxException e) {}
        socket.connect();

        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        activityRunning = true;

        if (sensor == null) Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();

        writeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("stepCount", stepCount);

                Intent segue = new Intent(context, DashboardActivity.class);
                startActivity(segue);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segue = new Intent(context, StepCountHistoryActivity.class);
                startActivity(segue);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        manager.unregisterListener(this);
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[2] >= 2) {
            stepCount++;
            count.setText(Long.toString(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
