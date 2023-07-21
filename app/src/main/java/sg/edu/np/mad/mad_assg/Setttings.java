package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
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

        //content done
        LinearLayout profile = (LinearLayout) findViewById(R.id.profile_edit);

        //content not done
        LinearLayout history = (LinearLayout) findViewById(R.id.view_history);

        LinearLayout gerenal = findViewById(R.id.stuff);

        LinearLayout feedback = findViewById(R.id.feedback);

        LinearLayout aboutus = findViewById(R.id.about);
        ImageView back = (ImageView) findViewById(R.id.backbtn);
        Button logout = (Button) findViewById(R.id.logoutbtn);
        Switch toggle = findViewById(R.id.toggleswitch);

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

        //toggle notification system
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle the toggle event here
                if (isChecked) {
                    // Switch is turned ON
                    // Perform actions for ON state
                    toggle.setChecked(true);
                } else {
                    // Switch is turned OFF
                    // Perform actions for OFF state
                    toggle.setChecked(false);
                }
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

        gerenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setttings.this, GeneralSetting.class);
                startActivity(intent);
                finish();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setttings.this, FeedBack.class);
                startActivity(intent);
                finish();
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setttings.this, AboutUs.class);
                startActivity(intent);
                finish();
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
