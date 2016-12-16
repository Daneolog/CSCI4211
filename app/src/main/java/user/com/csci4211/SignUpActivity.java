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

public class SignUpActivity extends AppCompatActivity {
    private Socket socket;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = getApplicationContext();

        Button signUp = (Button)findViewById(R.id.signUpButton);
        Button signIn = (Button)findViewById(R.id.gotoSignIn);

        try { socket = IO.socket("http://10.134.148.217:3000"); }
        catch (URISyntaxException e) {}
        socket.connect();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.signUpUsername);
                EditText email = (EditText)findViewById(R.id.signUpEmail);
                EditText password = (EditText)findViewById(R.id.signUpPassword);
                EditText confirm = (EditText)findViewById(R.id.signUpConfirm);



                    socket.emit("signup", username.getText().toString() + '|' + password.getText().toString());
                    socket.on("signUpResult", listener);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent segue = new Intent(context, SignInActivity.class);
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
                        if (result.getString("signUpResult") == "true")
                            Toast.makeText(context, "Great!  Your account has been created.  You can now login with your new account.", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(context, "Hmm... looks like that already exists.", Toast.LENGTH_SHORT).show();
                    }
                    catch (JSONException e) {}
                }
            });
        }
    };
}
