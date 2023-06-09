package sg.edu.np.mad.mad_assg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUser extends AppCompatActivity {

     /*public String GLOBAL_PREFS = "myPrefs";
     public String MY_USERNAME = "MyUserName";
     public String MY_PASSWORD = "MyPassword";
     public String RENTER_PASSWORD = "RenterPassword";
     public String MY_EMAIL = "MyEmail";
     SharedPreferences sharedPreferences;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        EditText etUsername = findViewById(R.id.usernametxt);
        EditText etEmail = findViewById(R.id.emailtxt);
        EditText etPassword = findViewById(R.id.passwordtxt);
        EditText etreenterPassword = findViewById(R.id.repwdtxt);

        TextView pwdwarnihng = findViewById(R.id.passwordwarning);
        TextView emailwarning = findViewById(R.id.emailwarning);

        Button signin = findViewById(R.id.signinbtn);

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /*sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                  SharedPreferences.Editor editor = sharedPreferences.edit();

                  editor.putString(MY_USERNAME, etUsername.getText().toString());
                  editor.putString(MY_PASSWORD, etPassword.getText().toString());
                  editor.putString(MY_EMAIL, etEmail.getText().toString());
                  editor.putString(RENTER_PASSWORD, etreenterPassword.getText().toString());
                  String sharedemail = sharedPreferences.getString(MY_EMAIL, "");
                  editor.apply();


                  if (MY_PASSWORD == RENTER_PASSWORD) {
                      Intent intent = new Intent(NewUser.this, MainActivity.class);
                      Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                      startActivity(intent);
                  } else {
                      pwdwarnihng.setText("Please enter a Password!");
                  }
                  if (Objects.equals(MY_EMAIL, sharedemail)) {
                      emailwarning.setText("Please enter a non-used email!");
                  } else {
                      emailwarning.setText("Please enter a vaild email!");
                  }*/


                UserData userData = dbHandler.findUser(etUsername.getText().toString());
                if (userData == null){
                    String dbUsername = etUsername.getText().toString();
                    String dbPassword = etPassword.getText().toString();
                    String dbEmail = etEmail.getText().toString();
                    UserData dbUserData = new UserData(dbUsername, dbPassword, dbEmail);
                    dbHandler.addUser(dbUserData);

                    /*if (etreenterPassword.equals(etPassword)){
                        dbHandler.addUser(dbUserData);
                        Intent intent = new Intent(NewUser.this, MainActivity.class);
                        Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else {
                        pwdwarnihng.setText("Password not tally!");
                    }*/
                    if (isValidEmailId(etEmail.getText().toString().trim())){
                        if (isValidPassword(etPassword.getText().toString().trim()) ) {

                            Intent intent = new Intent(NewUser.this, MainActivity.class);
                            Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else {
                            pwdwarnihng.setText("Password invalid!");

                        }
                    }
                    else{
                        emailwarning.setText("Email Invalid!");
                    }


                    /*if (etEmail.equals(dbEmail)){
                        emailwarning.setText("Email has been registered before!");
                    }*/
                }
                else{
                    String dbPassword = etPassword.getText().toString();
                    String dbEmail = etEmail.getText().toString();
                    String dbConfirmpwd = etreenterPassword.getText().toString();
                    UserData userdata = dbHandler.findUser(etEmail.getText().toString());

                    if (!dbConfirmpwd.equals(dbPassword)){
                        pwdwarnihng.setText("Password not tally!");
                    }
                    else if (userdata.equals(dbEmail)){
                        emailwarning.setText("Email has been registered before!");
                    }

                }
                }

        });


    }
    public boolean isValidPassword(String password){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);


        return matcher.matches();

    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}





