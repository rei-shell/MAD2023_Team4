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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Upload_Recipe extends AppCompatActivity {
    // Member variables
    private TextInputLayout recipeNameEditText;
    private ImageView photoImageView;
    private TextInputLayout descriptionEditText;
    private Spinner categorySpinner;
    private Spinner numberOfPersonsSpinner;
    private TextInputLayout ingredientsEditText;
    private TextInputLayout recipeStepsEditText;
    private TextInputLayout preparationTimeEditText;
    private TextInputLayout cookingTimeEditText;
    private Button saveButton;

    private Uri imageUri;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_recipe);

        // Initialize member variables and views
        recipeNameEditText = findViewById(R.id.rexipename);
        photoImageView = findViewById(R.id.addphotoicon);
        descriptionEditText = findViewById(R.id.recipedescription);
        categorySpinner = findViewById(R.id.category);
        numberOfPersonsSpinner = findViewById(R.id.numberOfPersons);
        ingredientsEditText = findViewById(R.id.ingredients);
        recipeStepsEditText = findViewById(R.id.recipeSteps);
        preparationTimeEditText = findViewById(R.id.preparationTime);
        cookingTimeEditText = findViewById(R.id.cookingTime);
        saveButton = findViewById(R.id.savebtn);

        // Initialize Firebase instances
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Get the current user
        user = mAuth.getCurrentUser();

        // Set up the category Spinner using the ArrayAdapter
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.category, android.R.layout.simple_spinner_item
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Create a list to hold the numbers from 1 to 99
        List<String> numberList = new ArrayList<>();
        for (int i = 1; i <= 99; i++) {
            numberList.add(String.valueOf(i));
        }
        ArrayAdapter<String> numberAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, numberList
        );
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfPersonsSpinner.setAdapter(numberAdapter);

        // Set an OnClickListener on the photoImageView to trigger image selection
        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        // Load the user's current profile image using Glide
        if (user != null && user.getPhotoUrl() != null) {
            imageUri = user.getPhotoUrl();
            Glide.with(this)
                    .load(imageUri)
                    .apply(new RequestOptions())
                    .into(photoImageView);
        }

        // Set a click listener on the saveButton to save the recipe to Firebase
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipeToFirebase();
            }
        });
    }

    private void saveRecipeToFirebase() {
        if (user != null) {
            // Get the user input data
            String recipeName = recipeNameEditText.getEditText().getText().toString();
            String description = descriptionEditText.getEditText().getText().toString();
            String category = categorySpinner.getSelectedItem().toString();
            String personsText = numberOfPersonsSpinner.getSelectedItem().toString();
            String ingredients = ingredientsEditText.getEditText().getText().toString();
            String recipeSteps = recipeStepsEditText.getEditText().getText().toString();
            String preparationTimeText = preparationTimeEditText.getEditText().getText().toString();
            String cookingTimeText = cookingTimeEditText.getEditText().getText().toString();

            // Validate input before parsing to integers
            if (TextUtils.isEmpty(personsText) || TextUtils.isEmpty(recipeName)
                    || TextUtils.isEmpty(description) || TextUtils.isEmpty(category)
                    || TextUtils.isEmpty(ingredients) || TextUtils.isEmpty(recipeSteps)
                    || TextUtils.isEmpty(preparationTimeText) || TextUtils.isEmpty(cookingTimeText)) {
                // Display an error message or toast to inform the user that required fields are missing
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int numberOfPersons = Integer.parseInt(personsText);
            int preparationTime = Integer.parseInt(preparationTimeText);
            int cookingTime = Integer.parseInt(cookingTimeText);
            int totalTime = preparationTime + cookingTime;

            // Get the reference to the Firebase Firestore
            CollectionReference recipesRef = db.collection("recipes");

            // Create a unique key for the new recipe entry
            String recipeId = recipesRef.document().getId();

            // Create a Recipe object with the provided data and image URL (if available)
            RecipeList recipe = new RecipeList(
                    recipeName, description, imageUri.toString(), category, numberOfPersons,
                    ingredients, recipeSteps, preparationTime, cookingTime, totalTime
            );

            // Save the recipe object using the unique key
            recipesRef.document(recipeId).set(recipe)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Recipe saved successfully, show a success message if needed
                            uploadImage(recipeId); // Pass the recipeId to the uploadImage() method
                            Toast.makeText(Upload_Recipe.this, "Recipe saved successfully!", Toast.LENGTH_SHORT).show();
                            // You can also navigate to the user's page or perform any other action here
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to save the recipe, show an error message if needed
                            Toast.makeText(Upload_Recipe.this, "Failed to save recipe: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void uploadImage(String recipeId) {
        if (imageUri != null) {
            // Generate a random image name
            String imageName = UUID.randomUUID().toString() + ".jpg";
            StorageReference imageRef = storageRef.child("recipe_images/" + imageName);

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
                                saveUserDataToFirestore(downloadUri.toString(), recipeId); // Pass the recipeId to the method
                                Intent intent = new Intent(Upload_Recipe.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Upload_Recipe.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload_Recipe.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // If imageUri is null, proceed with saving the recipe data without uploading an image
            saveUserDataToFirestore(null, recipeId);
            Intent intent = new Intent(Upload_Recipe.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void saveUserDataToFirestore(String photoUrl, String recipeId) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Get the reference to the "users" collection in Firestore
            CollectionReference usersCollection = db.collection("users");

            // Get the current user's UID
            String userId = currentUser.getUid();

            // Update the user document in Firestore with the new display name and profile picture URL
            Map<String, Object> data = new HashMap<>();
            if (photoUrl != null) {
                data.put("photoUrl", photoUrl);
            }

            // Add the recipeId to the user's document in Firestore
            data.put("recipeId", recipeId);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Display the selected image using Glide
            Glide.with(this)
                    .load(imageUri)
                    .apply(new RequestOptions())
                    .into(photoImageView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the profile image using the stored URI
        if (imageUri != null) {
            Glide.with(this)
                    .load(imageUri)
                    .apply(new RequestOptions())
                    .into(photoImageView);
        }
    }
}