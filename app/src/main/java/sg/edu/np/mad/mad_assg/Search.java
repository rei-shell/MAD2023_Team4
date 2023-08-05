package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment {
    private FirebaseFirestore db;
    private CategoryRecyclerViewAdapter adapter;
    private boolean isSearching = false;
    private SearchRecycleViewAdapter searchAdapter;
    private ArrayList<RecipeList> searchResults;
    RecyclerView categoryRecyclerView;
    RecyclerView searchRecyclerView;
    TextView noResultsTextView;

    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<CategoryData> categoryData = new ArrayList<>();
        categoryData.add(new CategoryData("Korean"));
        categoryData.add(new CategoryData("Chinese"));
        categoryData.add(new CategoryData("Bakery"));
        categoryData.add(new CategoryData("Western"));
        categoryData.add(new CategoryData("Japanese"));
        categoryData.add(new CategoryData("Indonesian"));
        categoryData.add(new CategoryData("Thai"));
        categoryData.add(new CategoryData("Drinks"));
        categoryData.add(new CategoryData("Kids-Friendly"));
        categoryData.add(new CategoryData("Sides"));

        // Initialize RecyclerView for categories
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new CategoryRecyclerViewAdapter(categoryData);
        categoryRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item) -> {
            // Retrieve the selected category data and perform the action
            String category = item.getCategory();
            Intent intent = new Intent(getActivity(), Recipe.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });

        // Initialize RecyclerView for search results
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get the reference to the "No search results found" TextView
        noResultsTextView = view.findViewById(R.id.noResultsTextView);

        // Initialize the list for search results
        searchResults = new ArrayList<>();

// Initialize the SearchRecycleViewAdapter
        searchAdapter = new SearchRecycleViewAdapter(searchResults);
        searchRecyclerView.setAdapter(searchAdapter);

// Set the OnItemClickListener for the searchAdapter
        searchAdapter.setOnItemClickListener(new SearchRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle the item click here
                // For example, you can open a new activity to display the details of the clicked recipe
                RecipeList clickedRecipe = searchResults.get(position);
                String recipeJson = convertRecipeToJsonString(clickedRecipe);
                Intent intent = new Intent(getActivity(), RecipeView.class);
                intent.putExtra("recipeJson", recipeJson);
                startActivity(intent);
            }
        });


        // Get the reference to the SearchView from your layout
        final SearchView searchView = view.findViewById(R.id.searchview);

        // Set the OnQueryTextListener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchItem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchItem(newText);
                return false;
            }
        });

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        return view;
    }

    private String convertRecipeToJsonString(RecipeList recipe) {
        Gson gson = new Gson();
        return gson.toJson(recipe);
    }

    private void SearchItem(String newText) {
        if (TextUtils.isEmpty(newText)) {
            // If the search query is empty, show the categoryRecyclerView and hide the searchRecyclerView
            isSearching = false;
            categoryRecyclerView.setVisibility(View.VISIBLE);
            noResultsTextView.setVisibility(View.GONE);
            searchRecyclerView.setVisibility(View.GONE);
        } else {
            // If there is a search query, show the searchRecyclerView and hide the categoryRecyclerView
            isSearching = true;
            categoryRecyclerView.setVisibility(View.GONE);
            searchRecyclerView.setVisibility(View.VISIBLE);

            // Perform the search and update the searchResults list
            db.collection("recipes")
                    .orderBy("title")
                    .startAt(newText)
                    .endAt(newText + "\uf8ff")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        searchResults.clear();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            RecipeList recipe = documentSnapshot.toObject(RecipeList.class);
                            searchResults.add(recipe);
                        }
                        // Update the searchAdapter with the new data
                        searchAdapter.notifyDataSetChanged();

                        // If there are no search results, show the "No search results found" TextView
                        noResultsTextView.setVisibility(searchResults.isEmpty() ? View.VISIBLE : View.GONE);

                        Log.d("Search", "Search Query: " + newText);
                        Log.d("Search", "Number of Results: " + searchResults.size());

                    })
                    .addOnFailureListener(e -> {
                        // Handle the failure to fetch search results if needed
                        Log.e("Search", "Error fetching search results: " + e.getMessage());
                    });
        }
    }
}

/*// Initialize the CategoryRecyclerViewAdapter
adapter = new CategoryRecyclerViewAdapter(categoryData);
categoryView.setAdapter(adapter);

// Set the OnItemClickListener for the adapter
adapter.setOnItemClickListener(new CategoryRecyclerViewAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(int position, CategoryData categoryData) {
        // Handle the item click here
        // For example, you can show a Toast message with the category name
        String categoryName = categoryData.getCategory();
        Toast.makeText(getActivity(), "Clicked on category: " + categoryName, Toast.LENGTH_SHORT).show();
    }
});
*/

/*// Initialize the SearchRecycleViewAdapter
searchAdapter = new SearchRecycleViewAdapter(searchResults);
searchRecyclerView.setAdapter(searchAdapter);

// Set the OnItemClickListener for the searchAdapter
searchAdapter.setOnItemClickListener(new SearchRecycleViewAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(int position) {
        // Handle the item click here
        // For example, you can open a new activity to display the details of the clicked recipe
        RecipeList clickedRecipe = searchResults.get(position);
        Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
        intent.putExtra("recipeId", clickedRecipe.getId());
        startActivity(intent);
    }
});
*/
    /*private void SearchItem(String newText) {
        FirebaseRecyclerOptions<RecipeList> options =
                new FirebaseRecyclerOptions.Builder<RecipeList>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("recipe")
                                .orderByChild("title")
                                .startAt(newText)
                                .endAt(newText + "\uf8ff"), RecipeList.class)
                        .build();

        // Update the searchResults list with the new data
        searchResults.clear();
        searchResults.addAll(options.getSnapshots());

        // Notify the adapter about the data change
        searchAdapter.notifyDataSetChanged();
    }*/



    /*private void SearchItem(String newText) {
        // Perform the search with the new text (convert to lowercase)
        String searchString = newText.toLowerCase();

        CollectionReference recipesRef = db.collection("recipes");
        recipesRef
                .whereEqualTo("title", searchString)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0) {
                            // Iterate through the QuerySnapshot to access the documents
                            ArrayList<RecipeList> searchResults = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Assuming your RecipeList class has a constructor to deserialize the snapshot properly
                                RecipeList recipe = document.toObject(RecipeList.class);
                                searchResults.add(recipe);
                            }
                            // Display the search results in the RecyclerView
                            displaySearchResults(searchResults);
                        } else {
                            // No results found
                            // Clear the RecyclerView adapter's data when no results are found
                            adapter.clearData();
                        }
                    }
                });
    }*/
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Get the reference to the SearchView from your layout
        final SearchView searchView = view.findViewById(R.id.searchview);

        // Set the OnQueryTextListener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchItem(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchItem(newText);
                return true;
            }
        });

        // Initialize RecyclerView and its adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainRecipeRecyclerViewAdapter( Pass an empty ArrayList or null here );
        recyclerView.setAdapter(adapter);

        // Set up the FirebaseRecyclerOptions and adapter
        FirebaseRecyclerOptions<RecipeList> options =
                new FirebaseRecyclerOptions.Builder<RecipeList>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("recipe")
                                .orderByChild("title"), RecipeList.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RecipeList, MainRecipeViewHolder>(options) {
            @NonNull
            @Override
            public MainRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Inflate your ViewHolder layout and return a new ViewHolder instance
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipe, parent, false);
                return new MainRecipeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MainRecipeViewHolder holder, int position, @NonNull RecipeList model) {
                // Bind your data to the ViewHolder layout using the MainRecipeViewHolder
                holder.SetDetails( Pass the necessary data from the model );
            }
        };

        // Set the adapter to your RecyclerView
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        // Start listening for changes in the Firebase Realtime Database
        firebaseRecyclerAdapter.startListening();

        return view;
    }*/

/*public class Search extends Fragment {
    private FirebaseFirestore db;
    private CategoryRecyclerViewAdapter adapter;

    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<CategoryData> categoryData = new ArrayList<>();
        categoryData.add(new CategoryData("Korean"));
        categoryData.add(new CategoryData("Chinese"));
        categoryData.add(new CategoryData("Bakery"));
        categoryData.add(new CategoryData("Western"));
        categoryData.add(new CategoryData("Japanese"));
        categoryData.add(new CategoryData("Indonesian"));
        categoryData.add(new CategoryData("Thai"));
        categoryData.add(new CategoryData("Drinks"));
        categoryData.add(new CategoryData("Kids-Friendly"));
        categoryData.add(new CategoryData("Sides"));

        RecyclerView categoryView = view.findViewById(R.id.category);
        categoryView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new CategoryRecyclerViewAdapter(categoryData);
        categoryView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item) -> {
            // Retrieve the selected category data and perform the action
            String category = item.getCategory();
            Intent intent = new Intent(getActivity(), Recipe.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });

        // Get the reference to the SearchView from your layout
        final SearchView searchView = view.findViewById(R.id.searchview);

        // Set the OnQueryTextListener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchItem(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchItem(newText);
                return true;
            }
        });
        return view;
    }

    private void SearchItem(String newText) {
        // Perform the search with the new text (convert to lowercase)
        String searchString = newText.toLowerCase();

        CollectionReference recipesRef = db.collection("recipes");
        recipesRef
                .whereArrayContains("title", searchString.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().getDocuments().size() > 0) {
                            // Iterate through the QuerySnapshot to access the documents
                            ArrayList<RecipeList> searchResults = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Assuming your RecipeList class has a constructor to deserialize the snapshot properly
                                RecipeList recipe = document.toObject(RecipeList.class);
                                searchResults.add(recipe);
                            }
                            // Display the search results in the RecyclerView
                            displaySearchResults(searchResults);
                        }
                    }
                });
    }

    private void displaySearchResults(ArrayList<RecipeList> searchResults) {
        ArrayList<Object> combinedDataList = new ArrayList<>(searchResults);

        if (combinedDataList.isEmpty()) {
            // No results found
            Log.d(TAG, "No results found");

            // Clear the RecyclerView adapter's data when no results are found
            adapter.clearData();
        } else {
            // Update the RecyclerView with the combined data
            adapter.setData(combinedDataList);
        }
    }
}*/

/*public class Search extends Fragment{
    private FirebaseFirestore db;
    public Search() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<CategoryData> categoryData = new ArrayList<>();

        categoryData.add(new CategoryData("Korean"));
        categoryData.add(new CategoryData("Chinese"));
        categoryData.add(new CategoryData("Bakery"));
        categoryData.add(new CategoryData("Western"));
        categoryData.add(new CategoryData("Japanese"));
        categoryData.add(new CategoryData("Indonesian"));
        categoryData.add(new CategoryData("Thai"));
        categoryData.add(new CategoryData("Drinks"));
        categoryData.add(new CategoryData("Kids-Friendly"));
        categoryData.add(new CategoryData("Sides"));

        RecyclerView categoryView = view.findViewById(R.id.category);
        categoryView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categoryData);
        categoryView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item) -> {
            // Retrieve the selected category data and perform the action
            String category = item.getCategory();
            //   String imageUrl = item.getImageUrl();
            // Perform the action, such as starting a new activity
            Intent intent = new Intent(getActivity(), Recipe.class);
            intent.putExtra("category", category);
            // intent.putExtra("imageUrl", imageUrl);
            startActivity(intent);
        });

            FirebaseRecyclerAdapter<data, RecyclerView.ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<data, RecyclerView.ViewHolder>(data.class, R.layout.cardviewrows, ViewHolder.class, databaseReference) {
                @Override
                protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, data model, int position) {
                    viewHolder.Setdetails(context, model.getTitle(), model.getImage(), model.getDescription());
                }
            };
            recyclerView.setAdapter(firebaseRecyclerAdapter);

            // Get the reference to the SearchView from your layout
            final SearchView searchView = findViewById(R.id.searchview);

            // Set the OnQueryTextListener for the SearchView
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    SearchItem(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    SearchItem(newText);
                    return false;
                }
            });
        }

        private void SearchItem(String newText) {
            // Perform the search with the new text (convert to lowercase)
            String searchString = newText.toLowerCase();

            CollectionReference recipesRef = db.collection("recipes");
            recipesRef
                    .whereArrayContains("title", searchString.toLowerCase())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult().getDocuments().size() > 0) {
                                // Iterate through the QuerySnapshot to access the documents
                                ArrayList<RecipeList> searchResults = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Assuming your RecipeList class has a constructor to deserialize the snapshot properly
                                    RecipeList recipe = document.toObject(RecipeList.class);
                                    searchResults.add(recipe);
                                }
                                // Display the search results in the RecyclerView
                                displaySearchResults(searchResults);
                            } else {
                                // No results found
                            }
                        }
                    });
        }

        private void displaySearchResults(ArrayList<RecipeList> searchResults) {
            // Combine the category and recipe data into a single list
            // combinedDataList.addAll(categoryData);
            ArrayList<Object> combinedDataList = new ArrayList<>(searchResults);

            // Check if the search results are empty
            if (combinedDataList.isEmpty()) {
                // No results found
                Log.d(TAG, "No results found");
            } else {
                // Update the RecyclerView with the combined data
                adapter.setData(combinedDataList);
            }
        }
        return view;
    }
}*/

        /*if (task.isSuccessful()) {
                                    // Iterate through the QuerySnapshot to access the documents
                                    ArrayList<RecipeList> searchResults = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Assuming your RecipeList class has a constructor to deserialize the snapshot properly
                                        RecipeList recipe = document.toObject(RecipeList.class);
                                        searchResults.add(recipe);
                                    }
                                    // Display the search results in the RecyclerView
                                    displaySearchResults(searchResults);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }*/

           /* private void displaySearchResults(ArrayList<RecipeList> searchResults) {
                // Combine the category and recipe data into a single list
                // combinedDataList.addAll(categoryData);
                ArrayList<Object> combinedDataList = new ArrayList<>(searchResults);

                // Update the RecyclerView with the combined data
                adapter.setData(combinedDataList);
            }*/

// Perform the search query in Firestore
                /*CollectionReference recipesRef = db.collection("recipes");
                recipesRef
                        .orderBy("title")
                        .startAt(searchString)
                        .endAt(searchString + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && task.getResult().getDocuments().size() > 0) {
                                    // Iterate through the QuerySnapshot to access the documents
                                    ArrayList<RecipeList> searchResults = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Assuming your RecipeList class has a constructor to deserialize the snapshot properly
                                        RecipeList recipe = document.toObject(RecipeList.class);
                                        searchResults.add(recipe);
                                    }
                                    // Display the search results in the RecyclerView
                                    displaySearchResults(searchResults);
                                } else {
                                    // No results found
                                }
                            }
                        });*/

/* SearchView searchView = view.findViewById(R.id.searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform the search with the new text (convert to lowercase)
                String searchString = newText.toLowerCase();

                CollectionReference recipesRef = db.collection("recipes");
                recipesRef
                        .whereArrayContains("title", searchString.toLowerCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && task.getResult().getDocuments().size() > 0) {
                                    // Iterate through the QuerySnapshot to access the documents
                                    ArrayList<RecipeList> searchResults = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // Assuming your RecipeList class has a constructor to deserialize the snapshot properly
                                        RecipeList recipe = document.toObject(RecipeList.class);
                                        searchResults.add(recipe);
                                    }
                                    // Display the search results in the RecyclerView
                                    displaySearchResults(searchResults);
                                } else {
                                    // No results found
                                }
                            }
                        });
                return true;
            }

            private void displaySearchResults(ArrayList<RecipeList> searchResults) {
                // Combine the category and recipe data into a single list
                // combinedDataList.addAll(categoryData);
                ArrayList<Object> combinedDataList = new ArrayList<>(searchResults);

                // Check if the search results are empty
                if (combinedDataList.isEmpty()) {
                    // No results found
                    Log.d(TAG, "No results found");
                } else {
                    // Update the RecyclerView with the combined data
                    adapter.setData(combinedDataList);
                }
            }
        });*/

 /*private void displaySearchResults(ArrayList<RecipeList> searchResults) {
        ArrayList<Object> combinedDataList = new ArrayList<>(searchResults);

        // Check if the search results are empty
        if (combinedDataList.isEmpty()) {
            // No results found
            Log.d(TAG, "No results found");
        } else {
            // Update the RecyclerView with the combined data
            adapter.setData(combinedDataList);
        }
    }*/

 /*private void SearchItem(String newText) {
        FirebaseRecyclerOptions<RecipeList> options =
                new FirebaseRecyclerOptions.Builder<RecipeList>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("recipe")
                                .orderByChild("title")
                                .startAt(newText)
                                .endAt(newText, "\uf8ff"), RecipeList.class)
                        .build();
        MainRecipeRecyclerViewAdapter = new MainRecipeRecyclerViewAdapter(options.getSnapshots());
        recyclerView.setAdapter(MainRecipeRecyclerViewAdapter);
    }*/

        /*// Get the reference to the SearchView from your layout
            final SearchView searchView = view.findViewById(R.id.searchview);

            // Set the OnQueryTextListener for the SearchView
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    SearchItem(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    SearchItem(newText);
                    return false;
                }
            });

            // Initialize Firestore instance
            db = FirebaseFirestore.getInstance();

            return view;
        }*/