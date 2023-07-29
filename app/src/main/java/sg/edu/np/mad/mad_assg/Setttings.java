package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ThemeUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Setttings extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    String[] mode = {"Light", "Dark"};
    private Spinner themeSpinner;
    boolean nightMode;
    private static final String NIGHT_MODE_PREF_KEY = "nightModePref";
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //content done
        LinearLayout profile = (LinearLayout) findViewById(R.id.profile_edit);

        //content not done
        LinearLayout history = (LinearLayout) findViewById(R.id.view_history);

        //LinearLayout gerenal = findViewById(R.id.stuff);

        LinearLayout feedback = findViewById(R.id.feedback);

        LinearLayout aboutus = findViewById(R.id.about);
        ImageView back = (ImageView) findViewById(R.id.backbtn);
        Button logout = (Button) findViewById(R.id.logoutbtn);
        Switch toggle = findViewById(R.id.toggleswitch);
        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Read the night mode preference from SharedPreferences and apply it
        nightMode = sharedPreferences.getBoolean(NIGHT_MODE_PREF_KEY, false);
        setAppNightMode(nightMode);


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

        // Initialize the theme Spinner
        themeSpinner = findViewById(R.id.dropdown_mode);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mode);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(themeAdapter);

        // Set an item selection listener for the theme Spinner
        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMode = mode[position];
                boolean isNightModeEnabled = selectedMode.equals("Dark");

                // Apply the night mode immediately
                setAppNightMode(isNightModeEnabled);

                // Update the night mode preference in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(NIGHT_MODE_PREF_KEY, isNightModeEnabled);
                editor.apply();

                // Update the night mode preference in Firestore
                updateNightModePreference(isNightModeEnabled);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected (optional)
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
                Intent intent = new Intent(Setttings.this, ViewedHistory.class);
                startActivity(intent);
                finish();
            }
        });

        /*gerenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setttings.this, GeneralSetting.class);
                startActivity(intent);
                finish();
            }
        });*/

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
    private void updateNightModePreference(boolean isEnabled) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Create a map to update the night mode preference
            Map<String, Object> nightModePreference = new HashMap<>();
            nightModePreference.put("nightModeEnabled", isEnabled);

            // Update the preference in Firestore
            db.collection("users").document(userId)
                    .set(nightModePreference, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Night mode preference updated successfully
                            // Now save the preference locally using SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(NIGHT_MODE_PREF_KEY, isEnabled);
                            editor.apply();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to update night mode preference
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }

    public class ThemeUtils {
        public static void setAppNightMode(Context context, boolean isEnabled) {
            AppCompatDelegate.setDefaultNightMode(isEnabled
                    ? AppCompatDelegate.MODE_NIGHT_YES
                    : AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Apply the saved night mode preference again (in case it was updated while the activity was paused)
        readAndApplyNightModePreference();
    }

    private void readAndApplyNightModePreference() {
        // Retrieve the saved night mode preference from SharedPreferences
        boolean savedNightMode = sharedPreferences.getBoolean(NIGHT_MODE_PREF_KEY, false);

        // Apply the saved night mode
        setAppNightMode(savedNightMode);

        // Set the selected item in the mode dropdown (Spinner) based on the saved preference
        themeSpinner.setSelection(Arrays.asList(mode).indexOf(savedNightMode ? "Dark" : "Light"));
    }

    private void setAppNightMode(boolean isEnabled) {
        AppCompatDelegate.setDefaultNightMode(isEnabled
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
