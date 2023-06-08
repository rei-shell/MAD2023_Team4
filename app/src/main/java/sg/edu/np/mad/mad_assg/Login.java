package sg.edu.np.mad.mad_assg;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {


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

                if (isValidCredential(etUsername.getText().toString(), etPassword.getText().toString())) {
                    Intent intent = new Intent(Login.this, HomePage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Invaild Username/Password!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    };
    MyDBHandler dbHandler = new MyDBHandler(this,null,null,1);
    public boolean isValidCredential(String username, String password){
        /*sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
        String sharedUsername = sharedPreferences.getString(MY_USERNAME, "");
        String sharedPassword = sharedPreferences.getString(MY_PASSWORD,"");

        if (sharedUsername.equals(username) && sharedPassword.equals(password)){
            return true;
        }
        return false;*/


        UserData dbData = dbHandler.findUser(username);
        if (dbData.getUsername().equals(username) && dbData.getPassword().equals(password)){
            return true;
        }
        return false;

    }
}

