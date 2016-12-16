package user.com.csci4211;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.jtransforms.fft.DoubleFFT_1D;

public class BreathingActivity extends AppCompatActivity {
    private SensorManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);
    }
}
