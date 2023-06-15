package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class StartPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);

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

}
