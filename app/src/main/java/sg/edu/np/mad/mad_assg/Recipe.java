package sg.edu.np.mad.mad_assg;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class Recipe extends AppCompatActivity {

    private ArrayList<RecipeList> searchResults;
    private RecipeAdapter recipeAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodrecipeoptionsview);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        ImageView bckbtn = findViewById(R.id.backbutton);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recipe.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();

        searchResults = new ArrayList<>();

        // Initialize the RecipeAdapter
        recipeAdapter = new RecipeAdapter(searchResults);
        recyclerView.setAdapter(recipeAdapter);

        // Set the onItemClickListener for the adapter
        recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                RecipeList clickedRecipe = searchResults.get(position);
                // Perform any action you want based on the clicked recipe.
                // For example, you can show a dialog, navigate to another fragment, etc.
                Toast.makeText(Recipe.this, "Clicked Recipe: " + clickedRecipe.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Recipe.this, RecipeView.class);
                intent.putExtra("title", clickedRecipe.getTitle());
                startActivity(intent);
            }
        });

        // Fetch user recipes from Firestore
        fetchRecipes();


    }

    private void fetchRecipes() {
        Log.d("RecipeDebug", "Fetching recipes from Firestore...");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get the reference to the "recipes" collection in Firestore
            CollectionReference recipesRef = db.collection("recipes");

            // Fetch the recipes
            recipesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        searchResults.clear(); // Clear the list first
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a RecipeList object
                            RecipeList recipe = document.toObject(RecipeList.class);
                            searchResults.add(recipe);
                            Log.d("RecipeDebug", "Recipe added: " + recipe.getTitle());
                        }

                        // Notify the adapter that data has changed
                        recipeAdapter.notifyDataSetChanged();
                        Log.d("RecipeDebug", "Recipes fetched and adapter updated successfully.");
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }
}

//private FirebaseFirestore database;
// private ArrayList<RecipeList> recipes;
//private MainRecipeRecyclerViewAdapter mainRecipeRecyclerViewAdapter;
// private RecipeRecycleViewAdapter recipeRecycleViewAdapter;
// private ImageView image;
// private TextView name;
//   private TextView category;


    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodrecipeoptionsview, container, false);
        database = FirebaseFirestore.getInstance();
        mainRecipeRecyclerViewAdapter = new MainRecipeRecyclerViewAdapter(recipes);
        recipeRecycleViewAdapter = new RecipeRecycleViewAdapter(recipes);
        image = findViewById(R.id.imageview);
        name = findViewById(R.id.name);
        return view;
    }*/


//List<ViewHistoryItem> items = new ArrayList<ViewHistoryItem>();
//recyclerView.setLayoutManager(new LinearLayoutManager(this));
//recyclerView.setAdapter(new ViewHistoryAdapter(getApplicationContext(), items));