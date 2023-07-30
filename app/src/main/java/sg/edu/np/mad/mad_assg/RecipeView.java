package sg.edu.np.mad.mad_assg;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import com.squareup.picasso.Picasso;
import android.graphics.drawable.AnimationDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeView extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<RecipeList> recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        ImageView bckbtn = findViewById(R.id.backarrow);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeView.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Retrieve the recipe list from the intent
        recipes = new ArrayList<>();
        // Retrieve the recipe JSON string from the intent
        String recipeJson = getIntent().getStringExtra("recipeJson");
        // Convert the JSON string back to a RecipeList object
        RecipeList selectedRecipe = convertJsonToRecipe(recipeJson);

        // Retrieve the selected recipe title from the intent
       // String title = getIntent().getStringExtra("title");

        // Find the selected recipe by its title
       // RecipeList selectedRecipe = getRecipeByTitle(title, recipes);

        if (selectedRecipe != null) {
            String recipeName = selectedRecipe.getTitle();
            String imageUrl = selectedRecipe.getPhotoUrl();
            String username = selectedRecipe.getUserid();
            String description = selectedRecipe.getDescription();
            String ingredients = selectedRecipe.getIngredients();
            String steps = selectedRecipe.getRecipeSteps();
            int prep = selectedRecipe.getPreparationTime();
            int cook = selectedRecipe.getCookingTime();
            int total = selectedRecipe.getTotalTime();

            TextView recipeNameTextView = findViewById(R.id.recipename);
            TextView authorTextView = findViewById(R.id.author);
            TextView ingredientsTextView = findViewById(R.id.ingredientsTxt);
            TextView descriptionTextView = findViewById(R.id.descriptionText);
            TextView howtoTextView = findViewById(R.id.stepsTxt);
            ImageView mainImg = findViewById(R.id.recipeimg);
            TextView preping = findViewById(R.id.mins);
            TextView cooking = findViewById(R.id.mins2);
            TextView totaltime = findViewById(R.id.mins3);

            // Set recipe data to views
            recipeNameTextView.setText(recipeName);
            authorTextView.setText(username);
            ingredientsTextView.setText(ingredients);
            descriptionTextView.setText(description);
            howtoTextView.setText(steps);
            Picasso.get().load(imageUrl).into(mainImg);
            // Convert integer time values to strings
            String preparationTimeString = String.valueOf(prep);
            String cookingTimeString = String.valueOf(cook);
            String totalTimeString = String.valueOf(total);

            preping.setText(preparationTimeString);
            cooking.setText(cookingTimeString);
            totaltime.setText(totalTimeString);

            Button feedback = findViewById(R.id.feedback);

            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecipeView.this, FeedBack_Result.class);
                    intent.putExtra("mainimg", imageUrl);
                    startActivity(intent);
                }
            });
        } else {
            // Handle the case when the selected recipe is not found
            // For example, display an error message or return to the previous activity.
        }
    }

    private RecipeList getRecipeByTitle(String title, ArrayList<RecipeList> recipes) {
        for (RecipeList recipe : recipes) {
            if (recipe.getTitle().equals(title)) {
                return recipe;
            }
        }
        return null;
    }

    private RecipeList convertJsonToRecipe(String recipeJson) {
        try {
            JSONObject jsonObject = new JSONObject(recipeJson);
            String userid = jsonObject.getString("userid");
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            String photoUrl = jsonObject.getString("photoUrl");
            String category = jsonObject.getString("category");
            int numberOfPersons = jsonObject.getInt("numberOfPersons");
            String ingredients = jsonObject.getString("ingredients");
            String recipeSteps = jsonObject.getString("recipeSteps");
            int preparationTime = jsonObject.getInt("preparationTime");
            int cookingTime = jsonObject.getInt("cookingTime");
            int totalTime = jsonObject.getInt("totalTime");

            return new RecipeList(userid, title, description, photoUrl, category, numberOfPersons,
                    ingredients, recipeSteps, preparationTime, cookingTime, totalTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}



/*public class RecipeView extends AppCompatActivity {

    //private ArrayList<RecipeList> recipes;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        ScrollView layout = findViewById(R.id.scrollview);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        ImageView bckbtn = findViewById(R.id.backarrow);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeView.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

     //   Intent intent = getIntent();
       // String title = intent.getStringExtra("title");
        RecipeList recipeList = new RecipeList();

        String recipeName = recipeList.getTitle();
        String imageUrl = recipeList.getPhotoUrl();
        String username = recipeList.getUserid();
        String description = recipeList.getDescription();
        String ingredients = recipeList.getIngredients();
        String steps = recipeList.getRecipeSteps();

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

        Button feedback = findViewById(R.id.feedback);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeView.this, FeedBack_Result.class);
                intent.putExtra("mainimg", imageUrl);
                startActivity(intent);
            }
        });
    }
}*/

    // Add this function to retrieve the RecipeList object by title
    /*private RecipeList getRecipeByTitle(String title, ArrayList<RecipeList> recipes) {
        for (RecipeList recipe : recipes) {
            if (recipe.getTitle().equals(title)) {
                return recipe;
            }
        }
        return null;
    }


    // This is a sample function to return a list of RecipeList objects (replace it with your actual data)
    private void fetchRecipes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get the reference to the "recipes" collection in Firestore
            CollectionReference recipesRef = db.collection("recipes");

            // Query the recipes collection
            recipesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<RecipeList> recipes = new ArrayList<>(); // Create a new list to hold recipes
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a RecipeList object
                            RecipeList recipe = document.toObject(RecipeList.class);
                            recipes.add(recipe); // Add the recipe to the list
                            Log.d("RecipeDebug", "Recipe added: " + recipe.getTitle());
                        }
                        // Pass the list of recipes to the RecipeView activity
                        Intent intent = new Intent(RecipeView.this, RecipeView.class);
                        startActivity(intent);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }
}*/
    /*@SuppressLint("ResourceAsColor")
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.recipe_view);
        Intent intent = getIntent();
        intent.getStringExtra("title");

        RecipeList recipeList = new RecipeList();

        String recipeName = recipeList.getTitle();
        String imageUrl = recipeList.getPhotoUrl();
        String username = recipeList.getUserid();
        String description = recipeList.getDescription();
        String ingredients = recipeList.getIngredients();
        String steps = recipeList.getRecipeSteps();


        // Retrieve recipe data from intent extras

        //String recipeName = intent.getStringExtra("recipename");
        //String imageUrl = intent.getStringExtra("image");
        //String username = intent.getStringExtra("username");
        //String description = intent.getStringExtra("description");
        //String ingredients = intent.getStringExtra("ingredients");
        //String steps = intent.getStringExtra("steps");

        // Connect with view
        TextView recipeNameTextView = findViewById(R.id.recipename);
        TextView authorTextView = findViewById(R.id.author);
        TextView ingredientsTextView = findViewById(R.id.ingredients);
        TextView descriptionTextView = findViewById(R.id.description);
        TextView howtoTextView = findViewById(R.id.steps);
        ImageView mainImg = findViewById(R.id.recipeimg);

        Button feedback = findViewById(R.id.feedback);

        // Set recipe data to views
        recipeNameTextView.setText(recipeName);
        authorTextView.setText(username);
        ingredientsTextView.setText(ingredients);
        descriptionTextView.setText(description);
        howtoTextView.setText(steps);
        Picasso.get().load(imageUrl).into(mainImg);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeView.this, FeedBack_Result.class);
                intent.putExtra("mainimg", imageUrl);
                startActivity(intent);
            }
        });

        Intent ratingreview = getIntent();
        RatingBar ratingBar = findViewById(R.id.ratingstar);
        TextView review = findViewById(R.id.review);
        String userreview = ratingreview.getStringExtra("tvFeedback");
        review.setText(userreview);
        review.setTextColor(R.color.black);
        Bundle bundle = ratingreview.getExtras();
        float totalRating = bundle.getFloat("rbStars");
        ratingBar.setRating(totalRating);
    }
}*/

/*
        // Add your RecipeList objects to the list here
        // For example:
        recipes.add(new RecipeList("Rachel Manley", "Chocolate Cake", "Our really easy chocolate cake recipe is perfect for birthdays. It’s so moist and fudgy and will keep well for 4–5 days. For buttercream quantities, instead of ganache, use our cake calculator. Each serving provides 477 kcal, 6.5g protein, 56g carbohydrates (of which 40g sugars), 25g fat (of which 10.5g saturates), 2.5g fibre and 0.6g salt.", "https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/easy_chocolate_cake_31070_16x9.jpg", "Bakery", 12, "For the cake\\n\" +\n" +
                "                \"225g/8oz plain flour\\n\" +\n" +
                "                \"350g/12½oz caster sugar\\n\" +\n" +
                "                \"85g/3oz cocoa powder\\n\" +\n" +
                "                \"1½ tsp baking powder\\n\" +\n" +
                "                \"1½ tsp bicarbonate of soda\\n\" +\n" +
                "                \"2 free-range eggs\\n\" +\n" +
                "                \"250ml/9fl oz milk\\n\" +\n" +
                "                \"125ml/4½fl oz vegetable oil\\n\" +\n" +
                "                \"2 tsp vanilla extract\\n\" +\n" +
                "                \"250ml/9fl oz boiling water\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"For the chocolate ganache\\n\" +\n" +
                "                \"200g/7oz plain chocolate\\n\" +\n" +
                "                \"200ml/7fl oz double cream", "1. Preheat the oven to 180C/160C Fan/Gas 4. Grease and line two 20cm/8in sandwich tins.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"2. For the cake, place all of the cake ingredients, except the boiling water, into a large mixing bowl. Using a wooden spoon, or electric whisk, beat the mixture until smooth and well combined.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"3. Add the boiling water to the mixture, a little at a time, until smooth. (The cake mixture will now be very liquid.)\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"4. Divide the cake batter between the sandwich tins and bake in the oven for 25–35 minutes, or until the top is firm to the touch and a skewer inserted into the centre of the cake comes out clean.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"5. Remove the cakes from the oven and allow to cool completely, still in their tins, before icing.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"6. For the chocolate icing, heat the chocolate and cream in a saucepan over a low heat until the chocolate melts. Remove the pan from the heat and whisk the mixture until smooth, glossy and thickened. Set aside to cool for 1–2 hours, or until thick enough to spread over the cake.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"7. To assemble the cake, run a round-bladed knife around the inside of the cake tins to loosen the cakes. Carefully remove the cakes from the tins.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"8. Spread a little chocolate icing over the top of one of the chocolate cakes, then carefully top with the other cake.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"9. Transfer the cake to a serving plate and ice the cake all over with the chocolate icing, using a palette knife.", 30, 60, 90));
        recipes.add(new RecipeList("ELISE BAUER", "Pasta Carbonara", "Pasta carbonara is an indulgent yet surprisingly simple recipe. Made with pancetta (or bacon) and plenty of Parmesan, this recipe takes only 30 minutes to prepare from start to finish!", "https://www.simplyrecipes.com/thmb/9DSEOemXX-gGJQBJqsY-qDzRjDw=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/Simply-Recipes-Spaghetti-Carbonara-LEAD-6-b3880a6eb49f4158be6f13885c797ded.jpg", "Western", 5, "1 tablespoon extra virgin olive oil or unsalted butter\\n\" +\n" +
                "                \"1/2 pound pancetta or thick cut bacon, diced\\n\" +\n" +
                "                \"1 to 2 garlic cloves, minced, about 1 teaspoon (optional)\\n\" +\n" +
                "                \"3 to 4 whole eggs\\n\" +\n" +
                "                \"1 cup grated Parmesan or pecorino cheese\\n\" +\n" +
                "                \"1 pound spaghetti (or bucatini or fettuccine)\\n\" +\n" +
                "                \"Kosher salt and freshly ground black pepper to taste", "1. Put a large pot of salted water on to boil (1 tablespoon salt for every 2 quarts of water.)\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"2. While the water is coming to a boil, heat the olive oil or butter in a large sauté pan over medium heat. Add the bacon or pancetta and cook slowly until crispy. Add the garlic (if using) and cook another minute, then turn off the heat and put the pancetta and garlic into a large bowl.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"3. In a small bowl, beat the eggs and mix in about half of the cheese.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"4. Once the water has reached a rolling boil, add the dry pasta, and cook, uncovered, at a rolling boil.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"5. When the pasta is al dente (still a little firm, not mushy), use tongs to move it to the bowl with the bacon and garlic. Let it be dripping wet. Reserve some of the pasta water.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"6. Move the pasta from the pot to the bowl quickly, as you want the pasta to be hot. It's the heat of the pasta that will heat the eggs sufficiently to create a creamy sauce.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"7. Toss everything to combine, allowing the pasta to cool just enough so that it doesn't make the eggs curdle when you mix them in. (That's the tricky part.)\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"8. Add the beaten eggs with cheese and tos quickly to combine once more. Add salt to taste. Add some pasta water back to the pasta to keep it from drying out.", 10, 20, 30));*/