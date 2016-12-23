package user.com.csci4211;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SignInActivity extends AppCompatActivity {
    private Socket socket;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        context = getApplicationContext();

        Button signIn = (Button)findViewById(R.id.signInButton);
        Button signUp = (Button)findViewById(R.id.gotoSignUp);

        try { socket = IO.socket("http://10.134.148.217:3000"); }
        catch (URISyntaxException e) {}
        socket.connect();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.signInUsername);
                EditText password = (EditText)findViewById(R.id.signInPassword);

                socket.emit("signin", username.getText().toString() + '|' + password.getText().toString());
                socket.on("signInResult", listener);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segue = new Intent(context, SignUpActivity.class);
                startActivity(segue);
            }
        });
    }

    private Emitter.Listener listener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject result = (JSONObject)args[0];

                    try {
                        if (result.getString("signInResult") == "true") {
                            Intent segue = new Intent(context, DashboardActivity.class);
                            startActivity(segue);
                        } else {
                            Toast.makeText(context, "Hmm... something's wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e) {}
                }
            });
        }
    };
}
