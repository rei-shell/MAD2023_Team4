package sg.edu.np.mad.mad_assg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Upload_Recipe extends AppCompatActivity {

    private TextInputEditText recipeNameEditText;
    private TextInputEditText photoEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText categoryEditText;
    private TextInputEditText numberOfPersonsEditText;
    private TextInputEditText ingredientsEditText;
    private TextInputEditText recipeStepsEditText;
    private TextInputEditText preparationTimeEditText;
    private TextInputEditText cookingTimeEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_recipe);

        // Initialize member variables
        recipeNameEditText = findViewById(R.id.rexipename);
        photoEditText = findViewById(R.id.addphoto);
        descriptionEditText = findViewById(R.id.recipedescription);
        categoryEditText = findViewById(R.id.category);
        numberOfPersonsEditText = findViewById(R.id.numberOfPersons);
        ingredientsEditText = findViewById(R.id.ingredients);
        recipeStepsEditText = findViewById(R.id.recipeSteps);
        preparationTimeEditText = findViewById(R.id.preparationTime);
        cookingTimeEditText = findViewById(R.id.cookingTime);
        saveButton = findViewById(R.id.savebtn);

        // Set up saveButton click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipeToFirebase();
            }
        });
    }

    private void saveRecipeToFirebase() {
        // Get the values entered by the user
        String recipeName = recipeNameEditText.getText().toString();
        String photoUrl = photoEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String personsText = numberOfPersonsEditText.getText().toString();
        String ingredients = ingredientsEditText.getText().toString();
        String recipeSteps = recipeStepsEditText.getText().toString();
        String preparationTimeText = preparationTimeEditText.getText().toString();
        String cookingTimeText = cookingTimeEditText.getText().toString();

        // Validate input before parsing to integers
        if (TextUtils.isEmpty(personsText) || TextUtils.isEmpty(preparationTimeText) || TextUtils.isEmpty(cookingTimeText)) {
            // Display an error message or toast to inform the user that required fields are missing
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int numberOfPersons = Integer.parseInt(personsText);
        int preparationTime = Integer.parseInt(preparationTimeText);
        int cookingTime = Integer.parseInt(cookingTimeText);
        int totalTime = preparationTime + cookingTime;

        // Get the reference to the Firebase Database
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference().child("recipes");

        // Create a unique key for the new recipe entry
        String recipeId = recipesRef.push().getKey();

        // Create a Recipe object
        RecipeList recipe = new RecipeList(
                recipeName, description, photoUrl, category, numberOfPersons, ingredients,
                recipeSteps, preparationTime, cookingTime, totalTime
        );

        // Save the recipe object using the unique key
        recipesRef.child(recipeId).setValue(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Recipe saved successfully, show a success message if needed
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
