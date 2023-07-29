package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

public class Recipe extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<RecipeList> recipes;
    private MainRecipeRecyclerViewAdapter adapter;
    private RecipeRecycleViewAdapter adapter1;


//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.recipe);

//        myrecipeList.add("Carbonara");
//        myrecipeList.add("Briyani");
//        myrecipeList.add("Nasi Lemak");
//        myrecipeList.add("Chicken Rice");
//        myrecipeList.add("Nutella Tarts");
//
//        RecyclerView recyclerView = findViewById(R.id.recipe_recyclerview);
//        RecipeAdapter adapter = new RecipeAdapter(myrecipeList);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//
//
//
//        if (CategoryData == "Korean"){
//
//        }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodrecipeoptionsview);

        db = FirebaseFirestore.getInstance();
        recipes = new ArrayList<>();
        adapter = new MainRecipeRecyclerViewAdapter(recipes);
        adapter1 = new RecipeRecycleViewAdapter(recipes);
}






    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        db = FirebaseFirestore.getInstance();
        recipes = new ArrayList<>();
        adapter = new MainRecipeRecyclerViewAdapter(recipes);
        adapter1 = new RecipeRecycleViewAdapter(recipes);

//        RecyclerView updateview = view.findViewById(R.id.updateview);
//        updateview.setAdapter(adapter);
//        RecyclerView recoview = view.findViewById(R.id.recoview);
//        recoview.setAdapter(adapter);
//        RecyclerView exploreview = view.findViewById(R.id.exploreview);
//        exploreview.setAdapter(adapter1);

        // Set the onItemClickListener for the adapter
//        adapter.setOnItemClickListener(new MainRecipeRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // Handle item click here
//                RecipeList clickedRecipe = recipes.get(position);
//                // Perform any action you want based on the clicked recipe.
//                // For example, you can show a dialog, navigate to another fragment, etc.
//                Toast.makeText(requireContext(), "Clicked Recipe: " + clickedRecipe.getTitle(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(requireContext(), RecipeView.class);
//                intent.putExtra("title", clickedRecipe.getTitle());
//                startActivity(intent);
//            }
//        });

        // Fetch user recipes from Firestore
        fetchUserRecipes();

        return view;
    }

    private void fetchUserRecipes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get the current user's UID
            //String userId = currentUser.getUid();

            // Get the reference to the "recipes" collection in Firestore
            CollectionReference recipesRef = db.collection("recipes");

            // Query for recipes uploaded by the current user
            //    Query query = recipesRef.whereEqualTo("userid", userId); // Use the correct field name "userid" instead of "userId"

            // Fetch the recipes
            recipesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        recipes.clear(); // Clear the list first
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a RecipeList object
                            RecipeList recipe = document.toObject(RecipeList.class);
                            recipes.add(recipe);
                            Log.d("RecipeDebug", "Recipe added: " + recipe.getTitle());
                        }
                        // Notify the adapter that data has changed
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }

//    if(CategoryData == "")
}