package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

public class ForgotPassword extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        Button cancel = findViewById(R.id.cancel);
        Button confirm = findViewById(R.id.confirm);

        //TextInputLayout etUsername = findViewById(R.id.forgot_username);
        TextInputLayout etemail = findViewById(R.id.forgot_email);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, StartPage.class);
                startActivity(intent);
            }
        });

        // Initialize Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getEditText().getText().toString();

                // Check if the email exists in Firebase Authentication
                if (TextUtils.isEmpty(email)) {
                    etemail.setError("Please enter your email");
                } else {
                    mAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()) {
                                    SignInMethodQueryResult result = task.getResult();
                                    List<String> signInMethods = result.getSignInMethods();

                                    if (signInMethods != null && !signInMethods.isEmpty()) {
                                        // Password reset email sent successfully
                                        mAuth.sendPasswordResetEmail(email)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "Email sent successfully.");
                                                            // You can show a toast or dialog here to inform the user that the email was sent.
                                                            Toast.makeText(ForgotPassword.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(ForgotPassword.this, Login.class);
                                                            startActivity(intent);
                                                            finish();

                                                        } else {
                                                            Log.e(TAG, "Failed to send email. Error: " + task.getException());
                                                            // Handle the error and show appropriate message to the user.
                                                            etemail.setError("Failed to send password reset email");
                                                        }
                                                    }
                                                });
                                    } else {
                                        // Email does not exist in Firebase Authentication
                                        etemail.setError("Email not registered");
                                    }
                                } else {
                                    // Error occurred while checking the email
                                    Log.e(TAG, "Error checking email: " + task.getException());
                                    Toast.makeText(ForgotPassword.this, "Error checking email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}

