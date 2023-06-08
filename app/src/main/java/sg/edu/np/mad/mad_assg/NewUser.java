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

public class NewUser extends AppCompatActivity {

    /* public String GLOBAL_PREFS = "myPrefs";
     public String MY_USERNAME = "MyUserName";
     public String MY_PASSWORD = "MyPassword";
     SharedPreferences sharedPreferences;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        EditText etUsername = findViewById(R.id.usernametxt);
        EditText etEmail = findViewById(R.id.emailtxt);
        EditText etPassword = findViewById(R.id.passwordtxt);
        EditText etreenterPassword = findViewById(R.id.repwdtxt);

        Button signin = findViewById(R.id.signinbtn);

        MyDBHandler dbHandler = new MyDBHandler(this,null,null,1);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MY_USERNAME, etUsername.getText().toString());
                editor.putString(MY_PASSWORD, etPassword.getText().toString());
                editor.commit();

                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);*/

                UserData userData = dbHandler.findUser(etUsername.getText().toString());
                if (userData == null){
                    String dbUsername = etUsername.getText().toString();
                    String dbPassword = etPassword.getText().toString();
                    String dbEmail = etEmail.getText().toString();
                    String dbRePwd = etreenterPassword.getText().toString();
                    if (dbPassword == dbRePwd)
                    {
                        UserData dbUserData = new UserData(dbUsername, dbEmail, dbPassword, dbRePwd);
                        dbHandler.addUser(dbUserData);
                        Intent intent = new Intent(NewUser.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(NewUser.this, "Password not tally", Toast.LENGTH_SHORT).show();
                        }
                }

            }
        });

    }
}