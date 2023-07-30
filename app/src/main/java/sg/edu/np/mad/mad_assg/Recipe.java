package sg.edu.np.mad.mad_assg;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;

public class Recipe extends AppCompatActivity {
    private FirebaseFirestore database;
    private ArrayList<RecipeList> recipes;
    private MainRecipeRecyclerViewAdapter mainRecipeRecyclerViewAdapter;
    private RecipeRecycleViewAdapter recipeRecycleViewAdapter;
    private ImageView image;
    private TextView name;
    private TextView category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodrecipeoptionsview);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        List<ViewHistoryItem> items = new ArrayList<ViewHistoryItem>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewHistoryAdapter(getApplicationContext(), items));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodrecipeoptionsview, container, false);
        database = FirebaseFirestore.getInstance();
        mainRecipeRecyclerViewAdapter = new MainRecipeRecyclerViewAdapter(recipes);
        recipeRecycleViewAdapter = new RecipeRecycleViewAdapter(recipes);
        image = findViewById(R.id.imageview);
        name = findViewById(R.id.name);
        return view;
    }



    //fetching data from firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference recipesRef = db.collection("recipes");


}




