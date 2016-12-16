package user.com.csci4211;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepCountHistoryActivity extends AppCompatActivity {
    long stepCounts[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count_history);

        stepCounts = getIntent().getLongArrayExtra("list");
    }
}
