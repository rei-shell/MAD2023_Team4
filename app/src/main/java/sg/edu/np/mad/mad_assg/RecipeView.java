package sg.edu.np.mad.mad_assg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class RecipeView extends AppCompatActivity {
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        ScrollView layout = findViewById(R.id.scrollview);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        ImageView bckbtn = findViewById(R.id.backarrow);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeView.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.recipe_view);

        // Retrieve recipe data from intent extras
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipename");
        String imageUrl = intent.getStringExtra("image");
        String username = intent.getStringExtra("username");
        String description = intent.getStringExtra("description");
        String ingredients = intent.getStringExtra("ingredients");
        String steps = intent.getStringExtra("steps");

        // Connect with view
        TextView recipeNameTextView = findViewById(R.id.recipename);
        TextView authorTextView = findViewById(R.id.author);
        TextView ingredientsTextView = findViewById(R.id.ingredients);
        TextView descriptionTextView = findViewById(R.id.description);
        TextView howtoTextView = findViewById(R.id.steps);
        ImageView mainImg = findViewById(R.id.recipeimg);

        Button feedback = findViewById(R.id.feedback);

        // Set recipe data to views
        recipeNameTextView.setText(recipeName);
        authorTextView.setText(username);
        ingredientsTextView.setText(ingredients);
        descriptionTextView.setText(description);
        howtoTextView.setText(steps);
        Picasso.get().load(imageUrl).into(mainImg);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeView.this, FeedBack_Result.class);
                intent.putExtra("mainimg", imageUrl);
                startActivity(intent);
            }
        });

        Intent ratingreview = getIntent();
        RatingBar ratingBar = findViewById(R.id.ratingstar);
        TextView review = findViewById(R.id.review);
        String userreview = ratingreview.getStringExtra("tvFeedback");
        review.setText(userreview);
        review.setTextColor(R.color.black);
        Bundle bundle = ratingreview.getExtras();
        float totalRating = bundle.getFloat("rbStars");
        ratingBar.setRating(totalRating);
    }
}
