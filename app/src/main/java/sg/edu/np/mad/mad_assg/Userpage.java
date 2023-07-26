package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Userpage extends Fragment {
    private TextView userid;
    private TextView userid2;
    // private MyDBHandler dbHandler;
    private ImageView settings;
    private FirebaseUser user;
    private ImageView profilepic;

    private Uri currentImageUri;
    private FirebaseFirestore db;
    private ArrayList<RecipeList> recipeList;
    private RecipeRecycleViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userpage, container, false);

       /* ConstraintLayout layout = view.findViewById(R.id.flFragment);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();*/

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Get the currently logged-in user
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Fetch the user's data from Firestore
            fetchUserData();
            fetchUserRecipes();
        }

        userid = view.findViewById(R.id.userid);
        userid2 = view.findViewById(R.id.userName);
        settings = view.findViewById(R.id.settingbtn);
        profilepic = view.findViewById(R.id.userpic);

        recipeList = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_id);
        adapter = new RecipeRecycleViewAdapter(recipeList);
        recyclerView.setAdapter(adapter);

        // Set the onItemClickListener for the adapter
        adapter.setOnItemClickListener(new RecipeRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                RecipeList clickedRecipe = recipeList.get(position);
                // Perform any action you want based on the clicked recipe.
                // For example, you can show a dialog, navigate to another fragment, etc.
                Toast.makeText(requireContext(), "Clicked Recipe: " + clickedRecipe.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        // Load the user's current profile image using Glide
        if (user != null && user.getPhotoUrl() != null) {
            currentImageUri = user.getPhotoUrl();

            Glide.with(this)
                    .load(currentImageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(profilepic);
        }

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Setttings.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void fetchUserData() {
        // Get the reference to the "users" collection in Firestore
        CollectionReference usersCollection = db.collection("users");

        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Get the current user's UID
            String userId = currentUser.getUid();

            // Get the user document from Firestore based on the user's UID
            usersCollection.document(userId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    String userName = document.getString("username");
                                    //   String emailValue = document.getString("email");
                                    // Fetch the profile picture URL from the document
                                    String profileImageUrl = document.getString("photoUrl");

                                    // Update the TextViews in the fragment with the user data
                                    userid.setText(userName);
                                    userid2.setText(userName);
                                    // Load the user's current profile image using Glide
                                    if (!TextUtils.isEmpty(profileImageUrl)) {
                                        currentImageUri = Uri.parse(profileImageUrl);
                                        Glide.with(Userpage.this)
                                                .load(currentImageUri)
                                                .apply(new RequestOptions().circleCrop())
                                                .into(profilepic);
                                    } else {
                                        // Set a default placeholder image if no profile picture URL is provided
                                        profilepic.setImageResource(R.drawable.outline_person_24);
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
    }

    private void fetchUserRecipes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Get the current user's UID
            String userId = currentUser.getUid();

            // Get the reference to the "recipes" collection in Firestore
            CollectionReference recipesRef = db.collection("recipes");

            // Query for recipes uploaded by the current user
            Query query = recipesRef.whereEqualTo("userid", userId); // Use the correct field name "userid" instead of "userId"

            // Fetch the recipes
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        recipeList.clear(); // Clear the list first
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convert each document to a RecipeList object
                            RecipeList recipe = document.toObject(RecipeList.class);
                            recipeList.add(recipe);
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
}



