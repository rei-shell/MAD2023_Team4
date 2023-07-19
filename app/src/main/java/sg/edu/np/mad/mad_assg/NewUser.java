package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NewUser extends AppCompatActivity {

    /*public String GLOBAL_PREFS = "myPrefs";
    public String MY_USERNAME = "MyUserName";
    public String MY_PASSWORD = "MyPassword";
    public String RENTER_PASSWORD = "RenterPassword";
    public String MY_EMAIL = "MyEmail";
    SharedPreferences sharedPreferences;*/
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    CollectionReference usersCollection;

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        ConstraintLayout layout = findViewById(R.id.constraint);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        TextInputLayout etUsername = (TextInputLayout) findViewById(R.id.username);
        TextInputLayout etEmail = (TextInputLayout) findViewById(R.id.email);
        TextInputLayout etPassword = (TextInputLayout) findViewById(R.id.password);
        TextInputLayout etreenterPassword = (TextInputLayout) findViewById(R.id.confirm_password);

        Button signin = findViewById(R.id.signinbtn);

        ImageView backbtn = findViewById(R.id.backbtn1);

        // Initialize Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();
        // Get reference to the "users" collection in Firestore
        usersCollection = db.collection("users");

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
                String dbUserName = etUsername.getEditText().getText().toString().trim();
                String dbPassword = etPassword.getEditText().getText().toString();
                String dbConfirmPwd = etreenterPassword.getEditText().getText().toString();
                String dbEmail = etEmail.getEditText().getText().toString();

                boolean isValidEmailFormat = isValidEmailType(dbEmail);
                //boolean isEmailAvailable = isValidEmail(dbEmail);
                boolean isValidPasswordFormat = isValidPassword(dbPassword);
                boolean isPasswordConfirmed = dbPassword.equals(dbConfirmPwd);
                //boolean isUsernameUnique = checkUniqueUsername(dbUserName);
                //boolean isUserNameAvailable = isValidUserName(dbUserName);

                // check if input are empty
                if (TextUtils.isEmpty(dbUserName)) {
                    Toast.makeText(NewUser.this, "Please enter a unique username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(dbEmail)) {
                    Toast.makeText(NewUser.this, "Please enter a unique email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(dbPassword)) {
                    Toast.makeText(NewUser.this, "Please enter a Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(dbConfirmPwd)) {
                    Toast.makeText(NewUser.this, "Please confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // valid email format
                if (!isValidEmailFormat) {
                    etEmail.setError("Invalid email format");
                    return;
                }

                //valid password format
                if (!isValidPasswordFormat) {
                    etPassword.setError("1 number, 1 lowercase, 1 uppercase, up to 8-10 length");
                    Toast.makeText(NewUser.this, "Invalid password format", Toast.LENGTH_SHORT).show();
                    return;
                }

                //valid password match
                if (!isPasswordConfirmed) {
                    etPassword.setError("Passwords do not match");
                    etreenterPassword.setError("Passwords do not match");
                    return;
                }

                /*
                   // Proceed with creating the user only if the username is unique
                if (!isUsernameUnique) {
                    etUsername.setError("Username is already taken");
                    return;
                }

                valid unique username
                if (!isUserNameAvailable) {
                    etUsername.setError("Username is already taken");
                    return;
                }*/

                else {
                    mAuth.createUserWithEmailAndPassword(dbEmail, dbPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");

                                        // Save the user's data, including the username, in Firestore
                                        saveUserDataToFirestore(mAuth.getCurrentUser().getUid(), dbUserName, dbEmail);

                                        // Proceed to the main activity or any other desired activity
                                        Intent intent = new Intent(NewUser.this, Login.class);
                                        startActivity(intent);
                                        finish();

                                        Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        etEmail.setError("Email exist");
                                    }
                                }
                            });
                }
            }



            private void saveUserDataToFirestore(String userId, String username, String email) {
                Map<String, Object> userData = new HashMap<>();
                userData.put("UID", userId);
                userData.put("email", email);
                userData.put("username", username);

                usersCollection.document(userId).set(userData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data saved successfully
                                Log.d(TAG, "User data saved to Firestore");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save data
                                Log.e(TAG, "Failed to save user data to Firestore", e);
                            }
                        });
            }
        });
    }


    public boolean isValidPassword (String password){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,10}$";

        /* Password validation meaning
        ^: Start of the string.
        (?=.*[0-9]): Positive lookahead for at least one digit (0-9).
        (?=.*[a-z]): Positive lookahead for at least one lowercase letter (a-z).
        (?=.*[A-Z]): Positive lookahead for at least one uppercase letter (A-Z).
        (?=\S+$): Positive lookahead for no whitespace allowed until the end of the string.
        .{8,10}: Match any character (except newline) between 8 and 10 times (password length requirement).
        $: End of the string.*/

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}

              /*  boolean isValidEmailFormat = isValidEmailType(dbEmail);
                boolean isEmailAvailable = isValidEmail(dbEmail);
                boolean isValidPasswordFormat = isValidPassword(dbPassword);
                boolean isPasswordConfirmed = dbPassword.equals(dbConfirmPwd);
                boolean isUserNameAvailable = isValidUserName(dbUserName);

                if (!isValidEmailFormat) {
                    // valid email format
                    etEmail.setError("Invalid email format");
                } else if (!isEmailAvailable) {
                    // valid unique email
                    etEmail.setError("This email is already registered");
                } else if (!isValidPasswordFormat) {
                    //valid password format
                    etPassword.setError("Invalid password format");
                    Toast.makeText(NewUser.this, "at least 1 number, 1 lowercase, 1 uppercase", Toast.LENGTH_SHORT).show();
                } else if (!isPasswordConfirmed) {
                    //valid password match
                    etPassword.setError("Passwords do not match");
                    etreenterPassword.setError("Passwords do not match");
                } else if (!isUserNameAvailable) {
                    //valid unique username
                    etUsername.setError("Username is already taken");
                } else {
                    UserData dbUserData = new UserData(dbUserName, dbPassword, dbEmail);
                    dbHandler.addUser(dbUserData);
                    Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewUser.this, Login.class);
                    startActivity(intent);
                }*/



        /*signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dbUserName = etUsername.getEditText().getText().toString();
                String dbPassword = etPassword.getEditText().getText().toString();
                String dbConfirmPwd = etreenterPassword.getEditText().getText().toString();
                String dbEmail = etEmail.getEditText().getText().toString();
                UserData dbUserData = new UserData(dbUserName, dbPassword, dbEmail);

                if (isValidEmailType(dbEmail)) {
                    if (isValidEmail(dbEmail)) {
                        if (isValidPassword(dbPassword)) {
                            if (isValidPassword(dbConfirmPwd)) {
                                if (dbPassword.equals(dbConfirmPwd)) {
                                    if (isValidUserName(dbUserName)) {
                                        if (isValidEmail(dbEmail)) {
                                            dbHandler.addUser(dbUserData);
                                            Intent intent = new Intent(NewUser.this, Login.class);
                                            Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        } else {
                                            etEmail.setError("This email is already registered!");
                                        }
                                    } else {
                                        etUsername.setError("Username is already taken!");
                                    }
                                } else {
                                    etPassword.setError("Password does not match!");
                                    etreenterPassword.setError("Password does not match!");
                                }
                            } else {
                                etreenterPassword.setError("Must contain 1 letter, 1 uppercase, 1 lowercase, 1 special character, at least 4 characters long!");
                            }
                        } else {
                            etPassword.setError("Must contain 1 letter, 1 uppercase, 1 lowercase, 1 special character, at least 4 characters long!");
                        }
                    } else {
                        etEmail.setError("This email is already registered!");
                    }
                } else {
                    etEmail.setError("Invalid email format");
                }
            }
        });
        }*/

/*checkUniqueUsername(dbUserName, new UniqueUsernameCallback() {
                @Override
                public void onUsernameChecked(boolean isUnique) {
                    if (isUnique) {
                        // Proceed with creating the user only if the username is unique
                        mAuth.createUserWithEmailAndPassword(etEmail, etPassword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");

                                            // Save the user's data, including the username, in Firestore
                                            saveUserDataToFirestore(mAuth.getCurrentUser().getUid(), etUsername, etEmail);

                                            // Proceed to the main activity or any other desired activity
                                            Intent intent = new Intent(NewUser.this, Login.class);
                                            startActivity(intent);
                                            finish();

                                            Toast.makeText(NewUser.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            etEmail.setError("Email exist");
                                        }
                                    }
                                });
                    } else {
                        etUsername.setError("Username already exists");
                    }
                }
            });


            private void checkUniqueUsername(String username, UniqueUsernameCallback callback) {
                usersCollection.whereEqualTo("username", username).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                boolean isUnique = queryDocumentSnapshots.isEmpty();
                                callback.onUsernameChecked(isUnique);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle the database error if needed
                                Toast.makeText(NewUser.this, "Database Error", Toast.LENGTH_SHORT).show();
                                callback.onUsernameChecked(false);
                            }
                        });
            }

        // Define the UniqueUsernameCallback interface
        interface UniqueUsernameCallback {
            void onUsernameChecked(boolean isUnique);
        }*/

    //MyDBHandler dbHandler = new MyDBHandler(this, "User.db", null, 1);
    /*public boolean isValidPassword (String password){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,10}$";

        /* Password validation meaning
        ^: Start of the string.
        (?=.*[0-9]): Positive lookahead for at least one digit (0-9).
        (?=.*[a-z]): Positive lookahead for at least one lowercase letter (a-z).
        (?=.*[A-Z]): Positive lookahead for at least one uppercase letter (A-Z).
        (?=\S+$): Positive lookahead for no whitespace allowed until the end of the string.
        .{8,10}: Match any character (except newline) between 8 and 10 times (password length requirement).
        $: End of the string.*/

        /*pattern = Pattern.compile(PASSWORD_PATTERN);
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
}*/
    /*private boolean isValidUserName(String username) {
        return dbHandler.user_IsUsernameFree(username);
    }

    private boolean isValidEmail(String email) {
        return dbHandler.user_IsEmailFree(email);
    }*/


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