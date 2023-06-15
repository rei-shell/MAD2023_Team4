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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


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

        TextInputLayout etUsername = (TextInputLayout) findViewById(R.id.username);
        TextInputLayout etPassword = (TextInputLayout) findViewById(R.id.password);

        ImageView backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, StartPage.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getEditText().getText().toString();
                String password = etPassword.getEditText().getText().toString();

                if ((userUsername(username)) && (userPassword(password))) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(Login.this, "Invaild UserName/Password!", Toast.LENGTH_SHORT).show();
                    }
                }



        });
    };

    private boolean userUsername (String username){
        return dbHandler.user_IsUsernameFree(username);
    }

    private boolean userPassword (String password){
        return dbHandler.user_checkPassword(password);
    }

/*
public boolean login(String username, String password){

        if (dbHandler.user_Login(username, password)){
            return true;
        } return false;
    }
    public boolean isValidCredential(String username, String password){
        /*sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
        String sharedUsername = sharedPreferences.getString(MY_USERNAME, "");
        String sharedPassword = sharedPreferences.getString(MY_PASSWORD,"");

        if (sharedUsername.equals(username) && sharedPassword.equals(password)){
            return true;
        }
        return false;*/

        /*UserData dbData = (UserData) dbHandler.getUser(username);
        return dbData.getUsername().equals(username) && dbData.getPassword().equals(password);

    }*/
/*
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
