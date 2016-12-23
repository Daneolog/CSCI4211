package user.com.csci4211;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class JournalActivity extends AppCompatActivity {
    private Socket socket;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        context = getApplicationContext();

        Button writeData = (Button)findViewById(R.id.writeJournal);

        try { socket = IO.socket("http://10.134.148.217:3000"); }
        catch (URISyntaxException e) {}
        socket.connect();

        writeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText)findViewById(R.id.title);
                EditText content = (EditText)findViewById(R.id.content);

                socket.emit("journal", title.getText().toString() + '|' + content.getText().toString());

                Intent segue = new Intent(context, DashboardActivity.class);
                startActivity(segue);
            }
        });
    }
}
