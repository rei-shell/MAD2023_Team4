package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Setttings extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        LinearLayout profile = (LinearLayout) findViewById(R.id.profile_edit);

        //content not done
        LinearLayout history = (LinearLayout) findViewById(R.id.view_history);

        ImageView back = (ImageView) findViewById(R.id.backbtn);
        Button logout = (Button) findViewById(R.id.logoutbtn);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), StartPage.class);
            startActivity(intent);
            finish();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Userpage");
                Intent intent = new Intent(Setttings.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Inside the onClick listener for the "Edit Profile" button in the Settings activity
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the user's profile picture URL
                String profileImageUrl = ""; // Replace this with the actual profile image URL from Firebase or any other source
                // Create an intent to start the EditUserProfile activity
                Intent intent = new Intent(Setttings.this, EditUserProfile.class);
                // Put the profile picture URL as an extra in the intent
                intent.putExtra("profileImageUrl", profileImageUrl);
                startActivity(intent);
                finish();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setttings.this, HistoryViewHolder.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Setttings.this, StartPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }
}
