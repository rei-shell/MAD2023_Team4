package sg.edu.np.mad.mad_assg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

public class GeneralSetting extends AppCompatActivity {
    String[] language = {"English", "Chinese", "Spanish", "Korean", "Japanese", "Malay", "Tamil"};
    String[] mode = {"Light", "Dark"};
    private Spinner dropdown;
    private Spinner modedropdown;
    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_setting);

        ImageView back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralSetting.this, Setttings.class);
                startActivity(intent);
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, language);
        dropdown = findViewById(R.id.dropdown);
        dropdown.setAdapter(adapter);

        ArrayAdapter<String> theme = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, mode);
        modedropdown = findViewById(R.id.dropdown_mode);
        modedropdown.setAdapter(theme);
        // Read the night mode preference from Firestore
        readNightModePreference();

        modedropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the mode dropdown
                String selectedMode = mode[position];

                // Determine if the selected mode is "Dark"
                boolean isNightModeEnabled = selectedMode.equals("Dark");

                // Apply the night mode immediately
                setAppNightMode(isNightModeEnabled);

                // Update the night mode preference in Firestore
                updateNightModePreference(isNightModeEnabled);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected (optional)
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
    protected void onResume() {
        super.onResume();
        // Read the night mode preference from Firestore again (in case it was updated while the activity was paused)
        readNightModePreference();
    }

    private void readNightModePreference() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Get the user's document from Firestore
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // Read the night mode preference from the document
                                nightMode = documentSnapshot.getBoolean("nightModeEnabled");
                                // Apply the night mode
                                setAppNightMode(nightMode);
                                // Set the selected item in the mode dropdown
                                modedropdown.setSelection(Arrays.asList(mode).indexOf(nightMode ? "Dark" : "Light"));
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to read night mode preference
                        }
                    });
        }
    }

    private void setAppNightMode(boolean isEnabled) {
        AppCompatDelegate.setDefaultNightMode(isEnabled
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
    }
}

    /*@Override
    protected void onResume() {
        super.onResume();
        // Read the night mode preference from SharedPreferences
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        // Set the appropriate night mode based on the preference
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            // If night mode is enabled, set the "Dark" item as selected in the Spinner
            modedropdown.setSelection(Arrays.asList(mode).indexOf("Dark"));
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // If night mode is disabled, set the "Light" item as selected in the Spinner
            modedropdown.setSelection(Arrays.asList(mode).indexOf("Light"));
        }
    }
}*/

/*
        // Read the night mode preference from SharedPreferences
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        // Set the appropriate night mode based on the preference
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            // If night mode is enabled, set the "Dark" item as selected in the Spinner
            modedropdown.setSelection(Arrays.asList(mode).indexOf("Dark"));
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // If night mode is disabled, set the "Light" item as selected in the Spinner
            modedropdown.setSelection(Arrays.asList(mode).indexOf("Light"));
        }

        modedropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the mode dropdown
                String selectedMode = mode[position];

                if (selectedMode.equals("Dark")) {
                    // If "Dark" is selected, enable night mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    // Store the night mode preference
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", true);
                    editor.apply();
                } else {
                    // If "Light" is selected, disable night mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    // Store the night mode preference
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", false);
                    editor.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/