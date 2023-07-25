package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class EditUser extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private FirebaseUser user;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private Uri imageUri;
    private ImageView profileImageView;
    private TextInputLayout usernameLayout;
    private String newDisplayName;

    private Uri currentImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        profileImageView = findViewById(R.id.profileImageView);
        usernameLayout = findViewById(R.id.usernameLayout);
        Button saveButton = findViewById(R.id.edit_button);
        ImageView backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUser.this, EditUserProfile.class);
                startActivity(intent);
                finish();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDisplayName = usernameLayout.getEditText().getText().toString().trim();
                if (imageUri != null) {
                    uploadImage();
                    Log.d(TAG, "New photo URL: " + profileImageView);
                    Toast.makeText(EditUser.this, "User profile picture updated.", Toast.LENGTH_SHORT).show();
                } //else {
                //           saveUserDataToFirestore(null); // Passing null for photoUrl
                //   }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Reload the profile image using the stored URI
        if (currentImageUri != null) {
            Glide.with(this)
                    .load(currentImageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(profileImageView);
        }
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setItems(new CharSequence[]{"Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        selectImageFromGallery();
                        break;
                    case 1:
                        // Implement captureImageWithCamera() if needed
                        break;
                }
            }
        });
        builder.show();
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (imageUri != null) {
            // Generate a random image name
            String imageName = UUID.randomUUID().toString() + ".jpg";
            StorageReference imageRef = storageRef.child("profile_images/" + imageName);

            try {
                // Convert the image to a byte array
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] imageData = baos.toByteArray();

                UploadTask uploadTask = imageRef.putBytes(imageData);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Inside the `uploadImage` method, after successfully uploading the image:
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                saveUserDataToFirestore(downloadUri.toString());
                                Intent intent = new Intent(EditUser.this, EditUserProfile.class);
                                intent.putExtra("newPhotoUrl", downloadUri.toString()); // Pass the new photo URL as an intent extra
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditUser.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUser.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void saveUserDataToFirestore(String photoUrl) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Get the reference to the "users" collection in Firestore
            CollectionReference usersCollection = db.collection("users");

            // Get the current user's UID
            String userId = currentUser.getUid();

            // Update the user document in Firestore with the new display name and profile picture URL
            Map<String, Object> data = new HashMap<>();
            data.put("username", newDisplayName);
            if (photoUrl != null) {
                data.put("photoUrl", photoUrl);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}


    /*private void loadProfileImage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;
            if (photoUrl != null && !photoUrl.isEmpty()) {
                ImageView profileImageView = findViewById(R.id.profileImageView);
                Picasso.get()
                        .load(photoUrl)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.outline_person_24)
                        .error(R.drawable.baseline_error_24)
                        .into(profileImageView);
            }
        }
    }
    private void loadProfileImage() {
        // Load the user's profile image using Glide or any other image loading library
        if (user.getPhotoUrl() != null) {
            Log.d(TAG, "Load Photo: ");
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .apply(new RequestOptions().circleCrop())
                    .into(profileImageView);
        }
    }

    */

