package sg.edu.np.mad.mad_assg;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
/*
    public String GLOBAL_PREFS = "myPrefs";
    public String MY_USERNAME = "MyUserName";
    public String MY_PASSWORD = "MyPassword";
    SharedPreferences sharedPreferences;*/
    MyDBHandler dbHandler = new MyDBHandler(this,null,null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button loginButton = findViewById(R.id.loginbtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUsername = findViewById(R.id.usernametxt);
                EditText etPassword = findViewById(R.id.passwordtxt);
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (dbHandler.user_Login(username, password)) {
                    Intent intent = new Intent(Login.this, HomePage.class);
                    startActivity(intent);
                    SharedPreferences pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("userID", username);
                    editor.commit();
                    // short delay before departing to main page (1.5s)
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);
                }
                else {
                    Toast.makeText(Login.this, "Invaild Username/Password!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    };

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
