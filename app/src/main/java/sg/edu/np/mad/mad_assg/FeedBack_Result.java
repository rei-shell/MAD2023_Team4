package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import sg.edu.np.mad.mad_assg.R;
import sg.edu.np.mad.mad_assg.RecipeView;

public class FeedBack_Result extends AppCompatActivity {


    private boolean isLiked = false;
    private ImageButton btnLike;

    TextView tvFeedback;
    RatingBar rbStars;

    // Poll Overlay Dialog
    private AlertDialog pollOverlayDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_result);

        btnLike = findViewById(R.id.btnLike);
        ColorStateList colorStateList = getResources().getColorStateList(R.color.btn_like_colors);
        btnLike.setImageTintList(colorStateList);
        btnLike.setImageTintList(null);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLiked = !isLiked;
                btnLike.setImageTintList(isLiked ? colorStateList : null);
            }
        });

        tvFeedback = findViewById(R.id.tvFeedback);
        rbStars = findViewById(R.id.rbStars);

        rbStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0) {
                    tvFeedback.setText("Very Dissatisfied");
                } else if (rating <= 1) {
                    tvFeedback.setText("Dissatisfied");
                } else if (rating <= 2 || rating == 3) {
                    tvFeedback.setText("OK");
                } else if (rating <= 4) {
                    tvFeedback.setText("Satisfied");
                } else if (rating <= 5) {
                    tvFeedback.setText("Very Satisfied");
                } else {

                }
            }
        });

        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText feedbackEditText = findViewById(R.id.EditText);
                RatingBar ratingbar = findViewById(R.id.rbStars);
                Float ratingNumber = ratingbar.getRating();
                String feedback = feedbackEditText.getText().toString().trim();

                if (feedback.isEmpty()) {
                    // Display message for empty feedback
                    Toast.makeText(getApplicationContext(), "Please enter your feedback", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle button click event with non-empty feedback
                    // Your code for sending feedback or further processing
                    Intent intent = new Intent(FeedBack_Result.this, RecipeView.class);
                    intent.putExtra("rbStars", ratingNumber);
                    intent.putExtra("tvFeedback", feedback);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Feedback sent!", Toast.LENGTH_SHORT).show();
                    feedbackEditText.setText(""); // Clear the text in the EditText
                }
            }
        });

        ImageView ivGoBack = findViewById(R.id.ivGoBack);
        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedBack_Result.this, RecipeView.class);
                startActivity(intent);
                finish(); // Go back to the previous activity
            }
        });

        // Enable the back button
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button shareButton = findViewById(R.id.btnSend1);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRecipe();
            }
        });

        // Find the floating poll button
        Button btnPollFloating = findViewById(R.id.btnPollFloating);
        btnPollFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPollOverlay();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void shareRecipe() {

        // Assuming you have obtained the recipe ID dynamically based on the selected recipe
        String recipeId = getRecipeId(); // Replace getRecipeId() with the method or logic to retrieve the recipe ID

        String deepLinkUrl = "http://recipeapp.com/recipes/" + recipeId;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool recipe");
        intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
        startActivity(Intent.createChooser(intent, "Share via"));



    }


    private String getRecipeId() {
        return getPackageName();
    }

    // Method to show the poll overlay
    private void showPollOverlay() {
        // Inflate the poll overlay layout
        View pollOverlayView = getLayoutInflater().inflate(R.layout.layout_poll_overlay, null);

        // Find poll elements in the overlay layout
        TextView tvPollQuestionOverlay = pollOverlayView.findViewById(R.id.tvPollQuestionOverlay);
        RadioGroup radioGroupPollOverlay = pollOverlayView.findViewById(R.id.radioGroupPollOverlay);
        Button btnSubmitPollOverlay = pollOverlayView.findViewById(R.id.btnSubmitPollOverlay);
        Button btnClosePollOverlay = pollOverlayView.findViewById(R.id.btnClosePollOverlay);

        // Handle poll submit button click
        btnSubmitPollOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPollId = radioGroupPollOverlay.getCheckedRadioButtonId();
                if (selectedPollId != -1) {
                    RadioButton selectedPollOption = pollOverlayView.findViewById(selectedPollId);
                    String pollResponse = selectedPollOption.getText().toString();
                    // Save or process the poll response as needed
                    // For this example, we'll just display a toast
                    Toast.makeText(FeedBack_Result.this, "Thank you for your poll response: " + pollResponse, Toast.LENGTH_SHORT).show();
                }
                // Dismiss the poll overlay after the user submits the poll
                pollOverlayDialog.dismiss();
            }
        });

        // Handle poll close button click
        btnClosePollOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the poll overlay if the user closes it without submitting
                pollOverlayDialog.dismiss();
            }
        });

        // Create and show the poll overlay dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(pollOverlayView);
        builder.setCancelable(false); // Prevent the user from closing the dialog by clicking outside
        pollOverlayDialog = builder.create();
        pollOverlayDialog.show();
    }
}