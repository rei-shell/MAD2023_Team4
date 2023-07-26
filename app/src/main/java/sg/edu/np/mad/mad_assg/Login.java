package sg.edu.np.mad.mad_assg;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Objects;


public class Login extends AppCompatActivity {
    /*
        public String GLOBAL_PREFS = "myPrefs";
        public String MY_USERNAME = "MyUserName";
        public String MY_PASSWORD = "MyPassword";
        SharedPreferences sharedPreferences;*/
    //MyDBHandler dbHandler = new MyDBHandler(this,"User.db",null,1);
    FirebaseAuth mAuth;

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ConstraintLayout layout = findViewById(R.id.constraint);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        Button loginButton = findViewById(R.id.loginbtn);

        TextInputLayout etEmail = (TextInputLayout) findViewById(R.id.username);
        TextInputLayout etPassword = (TextInputLayout) findViewById(R.id.password);

        ImageView backbtn = findViewById(R.id.backbtn);

        TextView forgot = (TextView) findViewById(R.id.forgotpassword);

// Initialize Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
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
                String email = etEmail.getEditText().getText().toString().trim();
                String password = etPassword.getEditText().getText().toString();

                // check if input are empty
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Please enter your username");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Please enter your password");
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                        Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        // Get the specific exception from the task
                                        Exception exception = task.getException();

                                        if (exception instanceof FirebaseAuthInvalidUserException) {
                                            // If the user does not exist in Firebase Authentication
                                            etEmail.setError("User not found");
                                        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                            // If the provided credentials are invalid
                                            String errorCode = ((FirebaseAuthInvalidCredentialsException) exception).getErrorCode();

                                            if (errorCode.equals("ERROR_INVALID_EMAIL")) {
                                                // Invalid email format
                                                etEmail.setError("Invalid email");
                                            } else if (errorCode.equals("ERROR_WRONG_PASSWORD")) {
                                                // Invalid password
                                                etPassword.setError("Invalid password");
                                            } else {
                                                // Other exceptions or general login failure
                                                Toast.makeText(Login.this, "Login Failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            //Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                            //updateUI(null);
                                        }
                                    }

                                }

                                ;
                            });
                }
            }

            ;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}
                /*if (isValidCredentials(username, password)) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid Username/Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
   /* private boolean isValidCredentials(String username, String password) {
        return dbHandler.user_checkUsername(username) && dbHandler.user_checkPassword(username, password);
    }
}*/

/*
private boolean userUsername (String username){
        return dbHandler.user_IsUsernameFree(username);
    }

    private boolean userPassword (String password){
        return dbHandler.user_checkPassword(password);
    }
        ConstraintLayout constraintLayout = findViewById(R.id.bckgrd_color);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();


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

