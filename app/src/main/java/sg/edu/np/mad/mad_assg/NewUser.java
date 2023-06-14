package sg.edu.np.mad.mad_assg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.AccessController;
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
        TextView confirmpwdwarning = findViewById(R.id.confirmpwdwarning);

        Button signin = findViewById(R.id.signinbtn);

        ImageView backbtn = findViewById(R.id.backbtn1);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUser.this, StartPage.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String dbUserName = etUsername.getText().toString();
                String dbPassword = etPassword.getText().toString();
                String dbConfirmPwd = etreenterPassword.getText().toString();
                String dbEmail = etEmail.getText().toString();
                UserData dbUserData = new UserData(dbUserName, dbPassword, dbEmail);
                if (isValidEmailType(dbEmail)) {                        //check email format
                    if (isValidEmail(dbEmail)) {                        //check if email have been registered
                        if (isValidPassword(dbPassword)) {              //check password format
                            if (isValidPassword(dbConfirmPwd)) {        //check password format
                                if (dbPassword.equals(dbConfirmPwd)) {  //check password and confirm password is same
                                    if (isValidUserName(dbUserName)) {  //check if username have been registered
                                        dbHandler.addUser(dbUserData);
                                        Intent intent = new Intent(NewUser.this, Login.class);
                                        Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(NewUser.this, "This username is already exist!", Toast.LENGTH_SHORT).show();//change after textview created
                                    }
                                } else {
                                    pwdwarnihng.setText("Password mismatch!");
                                    confirmpwdwarning.setText("Password mismatch!");
                                }
                            } else {
                                confirmpwdwarning.setText("Must contain 1 letter, 1 uppercase, 1 lowercase, 1 special character, at least 4 character long!");
                            }
                        } else {
                            pwdwarnihng.setText("Must contain 1 letter, 1 uppercase, 1 lowercase, 1 special character, at least 4 character long!");
                        }
                    } else {
                        emailwarning.setText("This email is already exist!");
                    }
                } else {
                    emailwarning.setText("Invaild Email format");
                }
            }
        });
    }
    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        public boolean isValidPassword (String password){

            Pattern pattern;
            Matcher matcher;

            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);


            return matcher.matches();

        }

        private boolean isValidEmailType (String email){

            return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
        }

        private boolean isValidUserName (String username){
            return dbHandler.user_IsUsernameFree(username);
        }

        private boolean isValidEmail (String email){
            return dbHandler.user_IsEmailFree(email);
        }
        /*
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
/*

                /*sharedPreferences = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE);
SharedPreferences.Editor editor = sharedPreferences.edit();

editor.putString(MY_USERNAME, etUsername.getText().toString());
editor.putString(MY_PASSWORD, etPassword.getText().toString());
editor.putString(MY_EMAIL, etEmail.getText().toString());
editor.putString(RENTER_PASSWORD, etreenterPassword.getText().toString());
String sharedemail = sharedPreferences.getString(MY_EMAIL, "");
editor.apply();*/
        /*if (MY_PASSWORD == RENTER_PASSWORD) {

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

                /*UserData userData = dbHandler.findUser(etUsername.getText().toString());

                if (userData == null){
                    String dbUsername = etUsername.getText().toString();
                    String dbPassword = etPassword.getText().toString();
                    String dbEmail = etEmail.getText().toString();
                    String dbConfirmpwd = etreenterPassword.getText().toString();
                    UserData dbUserData = new UserData(dbUsername, dbPassword, dbEmail);
                    dbHandler.addUser(dbUserData);
*/
                    /*if (etreenterPassword.equals(etPassword)){
                        dbHandler.addUser(dbUserData);
                        Intent intent = new Intent(NewUser.this, MainActivity.class);
                        Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else {
                        pwdwarnihng.setText("Password not tally!");
                    }*/
                    /*if (isValidEmailId(etEmail.getText().toString().trim())){
                        if (isValidPassword(etPassword.getText().toString().trim()) ) {

                            Intent intent = new Intent(NewUser.this, MainActivity.class);
                            Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else if (!dbConfirmpwd.equals(dbPassword)){
                            pwdwarnihng.setText("Password not tally!");
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
               /* }
                else if (userData.equals(etUsername)){
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

        });*/