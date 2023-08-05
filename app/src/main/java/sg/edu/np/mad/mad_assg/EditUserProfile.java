package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditUserProfile extends AppCompatActivity {
    //private static final String PREFS_NAME = "UserProfilePrefs";
    //private static final String PREF_PROFILE_IMAGE_URL = "profileImageUrl";
    private static final String TAG = "EditUserProfile";
    FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private TextView username;
    private TextView email;
    private ImageView profileImageView;
    private Button verify;
    private Button changepwd;
    private Uri currentImageUri;
    private static final int EDIT_USER_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();
        // Get the currently logged-in user
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Initialize Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        // Find views
        //ImageView userpic = findViewById(R.id.userpic);
        username = findViewById(R.id.userName);
        email = findViewById(R.id.Email);
        profileImageView = findViewById(R.id.userpic);

        // Fetch the user's data from Firestore
        //  fetchUserData();

        // Load the user's current profile image using Glide
        if (user != null && user.getPhotoUrl() != null) {
            currentImageUri = user.getPhotoUrl();

            Glide.with(this)
                    .load(currentImageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(profileImageView);
        }

      /*  // Retrieve the profile picture URL from SharedPreferences
        String profileImageUrl = getProfileImageUrlFromPrefs();
        if (!TextUtils.isEmpty(profileImageUrl)) {
            // Load the profile picture using Picasso or any other image loading library
            Picasso.get()
                    .load(profileImageUrl)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.outline_person_24)
                    .error(R.drawable.baseline_error_24)
                    .into(profileImageView);
        } else {
            // Set a default placeholder image if no profile picture URL is stored
            profileImageView.setImageResource(R.drawable.outline_person_24);
        }*/

     /*   // Retrieve the new photo URL from the intent extra (if available)
        String newPhotoUrl = getIntent().getStringExtra("newPhotoUrl");
        if (newPhotoUrl != null) {
            // Load the new profile picture into the ImageView using Picasso
            Picasso.get()
                    .load(newPhotoUrl)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.outline_person_24)
                    .error(R.drawable.baseline_error_24)
                    .into(profileImageView);
        } else {
            // If newPhotoUrl is not available, load the user's current profile picture from Firebase or Firestore
            fetchUserData();
        }*/

        ImageView backbtn = findViewById(R.id.backbtn);
        Button delete = findViewById(R.id.deleteacct);
        Button edit = findViewById(R.id.editacct);
        verify = findViewById(R.id.verifyemail);
        changepwd = findViewById(R.id.changepassword);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserProfile.this, Setttings.class);
                startActivity(intent);
                finish();
            }
        });
        //done
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        //done
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserProfile.this, EditUser.class);
                editUserLauncher.launch(intent);
                finish();
            }
        });

        //verify if user had verify email
        if (user != null) {
            boolean isEmailVerified = user.isEmailVerified();

            // Set the initial text of the Verify button based on the verification status
            if (isEmailVerified) {
                verify.setText("Verified");
                verify.setClickable(false);
            } else {
                verify.setText("Verify");
            }
        }

        //done
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    if (user.isEmailVerified()) {
                        // User's email is already verified, show a message or handle it as needed
                        Toast.makeText(EditUserProfile.this, "Email already verified.", Toast.LENGTH_SHORT).show();
                    } else {
                        // User's email is not verified, send the verification email
                        sendVerificationEmail();
                    }
                }
            }
        });

        //done
        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepwd();
            }
        });

    }

   /* // Helper method to store the profile picture URL in SharedPreferences
    private void saveProfileImageUrlToPrefs(String imageUrl) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(PREF_PROFILE_IMAGE_URL, imageUrl);
        editor.apply();
    }

    // Helper method to retrieve the profile picture URL from SharedPreferences
    private String getProfileImageUrlFromPrefs() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(PREF_PROFILE_IMAGE_URL, "");
    }*/

    private void deleteUser() {
        if (user != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditUserProfile.this);
            builder.setTitle("Delete Account");
            builder.setMessage("Do you wish to delete your account FOREVER?");

            // Inflate the custom layout for password input
            View view = LayoutInflater.from(EditUserProfile.this).inflate(R.layout.dialog_password_input, null);
            builder.setView(view);

            final EditText passwordEditText = view.findViewById(R.id.passwordEditText);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Get the password entered by the user
                    String userPassword = passwordEditText.getText().toString().trim();

                    // Check if the password is not empty
                    if (!TextUtils.isEmpty(userPassword)) {
                        // Get auth credentials from the user for re-authentication using EmailAuthProvider
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), userPassword);

                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // User re-authenticated, proceed with account deletion
                                            user.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d(TAG, "User account deleted.");
                                                                // Redirect the user to the StartPage activity
                                                                Toast.makeText(EditUserProfile.this, "Account deleted!", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(EditUserProfile.this, StartPage.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                // Handle the error if the delete operation fails
                                                                Log.e(TAG, "Failed to delete user account. Error: " + task.getException());
                                                                Toast.makeText(EditUserProfile.this, "Failed to delete user account", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // Handle the error if re-authentication fails
                                            Log.e(TAG, "Re-authentication failed. Error: " + task.getException());
                                            Toast.makeText(EditUserProfile.this, "Re-authentication failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        // Show a message if the password field is empty
                        Toast.makeText(EditUserProfile.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing, close the dialog
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);

                    positiveButton.setTextAppearance(EditUserProfile.this, R.style.AlertDialogButtonStyle_Yes);
                    negativeButton.setTextAppearance(EditUserProfile.this, R.style.AlertDialogButtonStyle_No);
                }
            });

            builder.setCancelable(false);
            alert.show();
        }
    }

    private void changepwd() {
        // Initialize Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        if (user != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditUserProfile.this);
            builder.setTitle("Change Password");
            builder.setMessage("Do you wish to change your account password? If yes, you will need to re-login into your account.");

            // Inflate the custom layout for password input
            View view = LayoutInflater.from(EditUserProfile.this).inflate(R.layout.dialog_email_input, null);
            builder.setView(view);

            final EditText emailEditText = view.findViewById(R.id.EmailEditText);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String email = emailEditText.getText().toString();

                    // Check if the email exists in Firebase Authentication
                    if (TextUtils.isEmpty(email)) {
                        emailEditText.setError("Please enter your email");
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
                                                                    Toast.makeText(EditUserProfile.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                                                    // after email is sent just logout the user and finish this activity
                                                                    FirebaseAuth.getInstance().signOut();
                                                                    startActivity(new Intent(EditUserProfile.this, Login.class));
                                                                    finish();

                                                                } else {
                                                                    Log.e(TAG, "Failed to send email. Error: " + task.getException());
                                                                    // Handle the error and show appropriate message to the user.
                                                                    emailEditText.setError("Failed to send password reset email");
                                                                }
                                                            }
                                                        });
                                            } else {
                                                // Email does not exist in Firebase Authentication
                                                emailEditText.setError("Email not registered");
                                            }
                                        } else {
                                            // Error occurred while checking the email
                                            Log.e(TAG, "Error checking email: " + task.getException());
                                            Toast.makeText(EditUserProfile.this, "Error checking email", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    dialog.dismiss();
                }

            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing, close the dialog
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);

                    positiveButton.setTextAppearance(EditUserProfile.this, R.style.AlertDialogButtonStyle_Yes);
                    negativeButton.setTextAppearance(EditUserProfile.this, R.style.AlertDialogButtonStyle_No);
                }
            });

            builder.setCancelable(false);
            alert.show();
        }
    }

    private final ActivityResultLauncher<Intent> editUserLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    // Check if the data contains the updated display name and photo URL
                    if (data.hasExtra("displayName") && data.hasExtra("photoUrl")) {
                        String newDisplayName = data.getStringExtra("displayName");
                        String newPhotoUrl = data.getStringExtra("photoUrl");

                        // Update the TextView with the new display name
                        username.setText(newDisplayName);

                        // Load the updated profile picture using Picasso
                        if (!TextUtils.isEmpty(newPhotoUrl)) {
                            Picasso.get()
                                    .load(newPhotoUrl)
                                    .fit()
                                    .centerCrop()
                                    .placeholder(R.drawable.outline_person_24)
                                    .error(R.drawable.baseline_error_24)
                                    .into(profileImageView);
                        } else {
                            // Set a default placeholder image if no profile picture URL is provided
                            profileImageView.setImageResource(R.drawable.outline_person_24);
                        }
                    }
                }
            });

    private void fetchUserData() {
        // Get the reference to the "users" collection in Firestore
        CollectionReference usersCollection = db.collection("users");

        // Get the current user's ID (assuming you use the user's ID as the document ID)
        String userId = user.getUid();

        // Get the user document from Firestore based on the user's ID
        usersCollection.document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String userName = document.getString("username");
                                String emailValue = document.getString("email");
                                // Fetch the profile picture URL from the intent extra
                                String profileImageUrl = document.getString("photoUrl");

                                username.setText(userName);
                                email.setText(emailValue);

                                // Load the user's current profile image using Glide
                                if (!TextUtils.isEmpty(profileImageUrl)) {
                                    currentImageUri = Uri.parse(profileImageUrl);
                                    Glide.with(EditUserProfile.this)
                                            .load(currentImageUri)
                                            .apply(new RequestOptions().circleCrop())
                                            .into(profileImageView);
                                } else {
                                    // Set a default placeholder image if no profile picture URL is provided
                                    profileImageView.setImageResource(R.drawable.outline_person_24);
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_USER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Check if the data contains the updated display name and photo URL
            if (data.hasExtra("displayName") && data.hasExtra("photoUrl")) {
                String newDisplayName = data.getStringExtra("displayName");
                String updatedPhotoUrl = data.getStringExtra("photoUrl"); // Use the correct key for the updated photo URL

                // Update the TextView with the new display name
                username.setText(newDisplayName);
                Log.d(TAG, "Upload photo");
                // If there is a new photo URL, update the profile picture
                if (!TextUtils.isEmpty(updatedPhotoUrl)) {
                    updateProfilePicture(updatedPhotoUrl);
                    Log.d(TAG, "Upload photo");
                }
            }
        }
    }

    /*private void updateProfilePicture(String newPhotoUrl) {
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(newPhotoUrl))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile picture updated.");

                                // Load the new profile picture into the ImageView using Picasso
                                if (!TextUtils.isEmpty(newPhotoUrl)) {
                                    Picasso.get()
                                            .load(newPhotoUrl)
                                            .fit()
                                            .centerCrop()
                                            .placeholder(R.drawable.outline_person_24)
                                            .error(R.drawable.baseline_error_24)
                                            .into(profileImageView);
                                } else {
                                    // If newPhotoUrl is empty, set a default placeholder image
                                    profileImageView.setImageResource(R.drawable.outline_person_24);
                                }
                            } else {
                                Log.e(TAG, "Error updating user profile picture: " + task.getException());
                                // Handle the error if the update fails
                                Toast.makeText(EditUserProfile.this, "Failed to update profile picture", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }*/
    private void updateProfilePicture(String newPhotoUrl) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(newPhotoUrl))
                    .build();

            currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile picture updated.");

                                // Update the user's profile picture URL in the "users" collection
                                updateUserDataInFirestore(newPhotoUrl);

                                // Load the new profile picture into the ImageView using Picasso
                                if (!TextUtils.isEmpty(newPhotoUrl)) {
                                    Picasso.get()
                                            .load(newPhotoUrl)
                                            .fit()
                                            .centerCrop()
                                            .placeholder(R.drawable.outline_person_24)
                                            .error(R.drawable.baseline_error_24)
                                            .into(profileImageView);
                                } else {
                                    // If newPhotoUrl is empty, set a default placeholder image
                                    profileImageView.setImageResource(R.drawable.outline_person_24);
                                }
                            } else {
                                Log.e(TAG, "Error updating user profile picture: " + task.getException());
                                // Handle the error if the update fails
                                Toast.makeText(EditUserProfile.this, "Failed to update profile picture", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void updateUserDataInFirestore(String newPhotoUrl) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Get the reference to the "users" collection in Firestore
            CollectionReference usersCollection = db.collection("users");

            // Get the current user's UID
            String userId = currentUser.getUid();

            // Update the user document in Firestore with the new profile picture URL
            Map<String, Object> data = new HashMap<>();
            if (!TextUtils.isEmpty(newPhotoUrl)) {
                data.put("photoUrl", newPhotoUrl);
            }

            usersCollection.document(userId).update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // Handle the successful update, if needed
                            Log.d(TAG, "User profile data updated in Firestore.");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the failure to update user data in Firestore
                            Log.e(TAG, "Error updating user profile data in Firestore: " + e.getMessage());
                        }
                    });
        }
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // email sent
                                Toast.makeText(EditUserProfile.this, "Verify Email sent.", Toast.LENGTH_SHORT).show();
                                //            verify.setText("Verified");
                                // after email is sent just logout the user and finish this activity
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(EditUserProfile.this, StartPage.class));
                                finish();
                            } else {
                                // email not sent, so display message and restart the activity or do whatever you wish to do

                                //restart this activity
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());

                            }
                        }
                    });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Clear Glide cache when the activity is paused
        Glide.get(this).clearMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(EditUserProfile.this).clearDiskCache();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the user's data from Firestore
        fetchUserData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}