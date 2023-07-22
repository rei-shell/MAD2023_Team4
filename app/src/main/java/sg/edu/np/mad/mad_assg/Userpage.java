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

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Userpage extends Fragment {

    private TextView userid;
    private TextView userid2;
   // private MyDBHandler dbHandler;
    private ImageView settings;

    private FirebaseUser user;
    private FirebaseUser username;
    private ImageView profilepic;
    private FirebaseFirestore db;
    private Uri currentImageUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userpage, container, false);

        ConstraintLayout layout = view.findViewById(R.id.flFragment);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();


        // Get the currently logged-in user
        username = FirebaseAuth.getInstance().getCurrentUser();

        if (username != null) {
            // Fetch the user's data from Firestore
            fetchUserData();
        }
        userid = view.findViewById(R.id.userid);
        userid2 = view.findViewById(R.id.userName);
        settings = view.findViewById(R.id.settingbtn);
        profilepic = view.findViewById(R.id.userpic);

        // Load the user's current profile image using Glide
        if (user != null && user.getPhotoUrl() != null) {
            currentImageUri = user.getPhotoUrl();

            Glide.with(this)
                    .load(currentImageUri)
                    .apply(new RequestOptions().circleCrop())
                    .into(profilepic);
        }

       // dbHandler = new MyDBHandler(getContext(), "User.db", null, 1);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Setttings.class);
                startActivity(intent);
                getActivity().finish();
                Log.d(TAG, "CHange page");

            }
        });

        return view;
    }

    /*private void fetchUserData() {
        // Get the reference to the "users" collection in Firestore
        CollectionReference usersCollection = db.collection("users");

        // Get the current user's ID (assuming you use the user's ID as the document ID)
        String userId = username.getUid();

        // Get the user document from Firestore based on the user's ID
        usersCollection.document(userId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Get the user document snapshot
                            DocumentSnapshot document = task.getResult();

                            if (document != null && document.exists()) {
                                // Retrieve the "username" field from the document
                                String username = document.getString("username");
                                // Fetch the profile picture URL from the intent extra
                                String profileImageUrl = document.getString("photoUrl");
                                // Update the TextView in the fragment with the username
                                userid.setText(username);
                                userid2.setText(username);
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
                                // Handle the case when the document does not exist
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            // Handle errors
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }*/

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
                                    //email.setText(emailValue);

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

}

/*


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userpage, container, false);


        //set userid
        String userid = getActivity().getIntent().getExtras().getString("username");
        user = (TextView) user.findViewById(R.id.userid);
        user2 = (TextView) user.findViewById(R.id.userName);
        user.setText(userid);
        user2.setText(userid);

        return view;
    }*/




/*


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userpage, container, false);


        //set userid
        String userid = getActivity().getIntent().getExtras().getString("username");
        user = (TextView) user.findViewById(R.id.userid);
        user2 = (TextView) user.findViewById(R.id.userName);
        user.setText(userid);
        user2.setText(userid);

        return view;
    }*/

