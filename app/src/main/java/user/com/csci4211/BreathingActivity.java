package user.com.csci4211;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BreathingActivity extends AppCompatActivity implements SensorEventListener {
    private Socket socket;
    private SensorManager manager;
    EditText breathingRate;
    Button writeData;
    double first, second;
    Context context;

    List<Float> buffer = new ArrayList<>();
    long start = System.currentTimeMillis();

    public float average(List<Float> list) {
        float sum = 0;

        for (Float d:list) sum += d;
        return sum/list.size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);
        breathingRate = (EditText)findViewById(R.id.breathingRate);
        writeData = (Button)findViewById(R.id.writeBreathingRate);
        context = getApplicationContext();

        try { socket = IO.socket("http://10.134.148.217:3000"); }
        catch (URISyntaxException e) {}
        socket.connect();

        manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        writeData.setVisibility(View.INVISIBLE);

        writeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("breathingRate", breathingRate.getText());

                Intent segue = new Intent(context, DashboardActivity.class);
                startActivity(segue);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long timestamp = System.currentTimeMillis() - start;
        double result;
        buffer.add(event.values[2]);
        if (buffer.size() > 10) buffer.remove(0);

        if (event.values[2] - average(buffer) >= 0.2) {
            if (first == 0) first = timestamp;
            else if (second == 0) {
                if (timestamp - first >= 1000 && timestamp - first <= 2500) second = timestamp;
                else if (timestamp - first > 2500) second = 2500;

                result = 60000 / (second-first);

                writeData.setVisibility(View.VISIBLE);
                breathingRate.setText(String.format("%.1f", result) + " breaths per minute");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
