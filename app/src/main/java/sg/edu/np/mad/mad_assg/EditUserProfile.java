package sg.edu.np.mad.mad_assg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class EditUserProfile extends AppCompatActivity {

    private static final String TAG = "EditUserProfile";

    private FirebaseUser user;
    private FirebaseFirestore db;
    private TextView username;
    private TextView email;
    private ImageView profileImageView;

    private static final int EDIT_USER_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();
        // Get the currently logged-in user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Find views
        //ImageView userpic = findViewById(R.id.userpic);
        username = findViewById(R.id.userName);
        email = findViewById(R.id.Email);
        profileImageView = findViewById(R.id.userpic);

        // Fetch the user's data from Firestore
        fetchUserData();

        ImageView backbtn = findViewById(R.id.backbtn);
        Button delete = findViewById(R.id.deleteacct);
        Button edit = findViewById(R.id.editacct);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserProfile.this, Userpage.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserProfile.this, EditUser.class);
                editUserLauncher.launch(intent);
            }
        });
    }

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

            builder.setCancelable(false);
            AlertDialog alert = builder.create();
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
                        // If there is a new photo URL, load and display the image using Picasso
                        if (!TextUtils.isEmpty(newPhotoUrl)) {
                            Log.d(TAG, "New photo URL: " + newPhotoUrl);
                            Picasso.get()
                                    .load(newPhotoUrl)
                                    .fit()
                                    .centerCrop()
                                    .placeholder(R.drawable.outline_person_24)
                                    .error(R.drawable.baseline_error_24)
                                    .into(profileImageView);
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
                                String photoUrl = document.getString("updatedPhotoUrl"); // Use the correct key

                                username.setText(userName);
                                email.setText(emailValue);

                                if (!TextUtils.isEmpty(photoUrl)) {
                                    Picasso.get()
                                            .load(photoUrl)
                                            .fit()
                                            .centerCrop()
                                            .placeholder(R.drawable.outline_person_24)
                                            .error(R.drawable.baseline_error_24)
                                            .into(profileImageView);
                                } else {
                                    // Set a default placeholder image if no profile picture is available
                                    profileImageView.setImageDrawable(ContextCompat.getDrawable(EditUserProfile.this, R.drawable.outline_person_24));
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
                String newPhotoUrl = data.getStringExtra("photoUrl");

                // Update the TextView with the new display name
                username.setText(newDisplayName);

                // If there is a new photo URL, update the profile picture
                if (!TextUtils.isEmpty(newPhotoUrl)) {
                    updateProfilePicture(newPhotoUrl);
                }
            }
        }
    }

    private void updateProfilePicture(String newPhotoUrl) {
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
                                // Update the profile picture in the ImageView using Picasso
                                Picasso.get()
                                        .load(newPhotoUrl)
                                        .fit()
                                        .centerCrop()
                                        .placeholder(R.drawable.outline_person_24)
                                        .error(R.drawable.baseline_error_24)
                                        .into(profileImageView);
                            } else {
                                Log.e(TAG, "Error updating user profile picture: " + task.getException());
                                // Handle the error if the update fails
                                Toast.makeText(EditUserProfile.this, "Failed to update profile picture", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}

/*private void saveUserDataToFirestore(String newDisplayName, String newPhotoUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DocumentReference userRef = db.collection("users").document(user.getUid());

            // Create a map to update the user's data
            Map<String, Object> updates = new HashMap<>();

            // Update the display name if available
            if (newDisplayName != null && !newDisplayName.isEmpty()) {
                updates.put("username", newDisplayName);
            }

            // Update the photo URL if available
            if (newPhotoUrl != null && !newPhotoUrl.isEmpty()) {
                updates.put("updatedPhotoUrl", newPhotoUrl); // Use the correct key for the photo URL
            }

            // Perform the update operation
            userRef.update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "User data updated successfully.");
                            // Show a success message if the update is successful
                            Toast.makeText(EditUserProfile.this, "User data updated successfully.", Toast.LENGTH_SHORT).show();

                            // If needed, you can update the local user object with the new data
                            if (newDisplayName != null) {
                                user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(newDisplayName).build());
                            }
                            if (newPhotoUrl != null) {
                                user.updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(newPhotoUrl)).build());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Failed to update user data: " + e.getMessage());
                            // Show an error message if the update fails
                            Toast.makeText(EditUserProfile.this, "Failed to update user data.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }*/