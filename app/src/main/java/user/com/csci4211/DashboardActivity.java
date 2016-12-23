package user.com.csci4211;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = getApplicationContext();

        Button heartRate = (Button)findViewById(R.id.heartRateButton);
        Button stepCount = (Button)findViewById(R.id.stepCountButton);
        Button breathing = (Button)findViewById(R.id.breathingButton);
        Button journal = (Button)findViewById(R.id.journalButton);

        heartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segue = new Intent(context, HeartRateActivity.class);
                startActivity(segue);
            }
        });

        stepCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segue = new Intent(context, StepCountActivity.class);
                startActivity(segue);
            }
        });

        breathing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segue = new Intent(context, BreathingActivity.class);
                startActivity(segue);
            }
        });

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segue = new Intent(context, JournalActivity.class);
                startActivity(segue);
            }
        });
    }
}
