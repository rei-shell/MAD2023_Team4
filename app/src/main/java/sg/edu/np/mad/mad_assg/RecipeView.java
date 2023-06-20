package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class RecipeView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        // Retrieve recipe data from intent extras
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipename");
        String imageUrl = intent.getStringExtra("image");
        String username = intent.getStringExtra("username");
        String description = intent.getStringExtra("description");
        String ingredients = intent.getStringExtra("ingredients");
        String steps = intent.getStringExtra("steps");

        // Connect with view
        TextView recipeNameTextView = findViewById(R.id.recipename);
        TextView authorTextView = findViewById(R.id.author);
        TextView ingredientsTextView = findViewById(R.id.ingredients);
        TextView descriptionTextView = findViewById(R.id.description);
        TextView howtoTextView = findViewById(R.id.steps);
        ImageView mainImg = findViewById(R.id.recipeimg);

        // Set recipe data to views
        recipeNameTextView.setText(recipeName);
        authorTextView.setText(username);
        ingredientsTextView.setText(ingredients);
        descriptionTextView.setText(description);
        howtoTextView.setText(steps);
        Picasso.get().load(imageUrl).into(mainImg);
    }
}
