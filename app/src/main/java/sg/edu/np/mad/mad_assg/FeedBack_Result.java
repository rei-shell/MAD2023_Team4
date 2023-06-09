package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FeedBack_Result extends AppCompatActivity {
    TextView tvFeedback;
    RatingBar rbStars;

    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_result);

        imgview = findViewById(R.id.imageView);
        tvFeedback = findViewById(R.id.tvFeedback);
        rbStars = findViewById(R.id.rbStars);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("mainimg");
        Picasso.get().load(imageUrl).into(imgview);

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

}
