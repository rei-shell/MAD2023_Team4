package sg.edu.np.mad.mad_assg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class NewUser extends AppCompatActivity {

     public String GLOBAL_PREFS = "myPrefs";
     public String MY_USERNAME = "MyUserName";
     public String MY_PASSWORD = "MyPassword";
     public String RENTER_PASSWORD = "RenterPassword";
     public String MY_EMAIL = "MyEmail";
     SharedPreferences sharedPreferences;
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

        /*MyDBHandler dbHandler = new MyDBHandler(this,null,null,1);*/
        signin.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
                                          SharedPreferences.Editor editor = sharedPreferences.edit();

                                          editor.putString(MY_USERNAME, etUsername.getText().toString());
                                          editor.putString(MY_PASSWORD, etPassword.getText().toString());
                                          editor.putString(MY_EMAIL, etEmail.getText().toString());
                                          editor.putString(RENTER_PASSWORD, etreenterPassword.getText().toString());
                                          String sharedemail = sharedPreferences.getString(MY_EMAIL, "");
                                          editor.apply();


                                          if (Objects.equals(MY_PASSWORD, RENTER_PASSWORD) && MY_PASSWORD.length() <= 10) {
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
                                          }
                                      }
                                  });



                /*UserData userData = dbHandler.findUser(etUsername.getText().toString());
                if (userData == null){
                    String dbUsername = etUsername.getText().toString();
                    String dbPassword = etPassword.getText().toString();
                    String dbEmail = etEmail.getText().toString();
                    String dbRePwd = etreenterPassword.getText().toString();
                    if (dbPassword == dbRePwd)
                    {
                        UserData dbUserData = new UserData(dbUsername, dbEmail, dbPassword);
                        dbHandler.addUser(dbUserData);
                        Intent intent = new Intent(NewUser.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(NewUser.this, "Password not tally", Toast.LENGTH_SHORT).show();
                        }*/




    }
}