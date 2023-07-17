package sg.edu.np.mad.mad_assg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class StartPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);

        ConstraintLayout layout = findViewById(R.id.constraint);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        Button newUser = (Button) findViewById(R.id.SignUp);
        newUser.setOnClickListener(view -> {
            Intent signin = new Intent(this, NewUser.class);
            startActivity(signin);
        });


        Button oldUser = (Button) findViewById(R.id.Login);
        oldUser.setOnClickListener(view -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }

}
