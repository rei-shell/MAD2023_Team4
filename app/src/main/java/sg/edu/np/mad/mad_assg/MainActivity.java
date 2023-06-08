package sg.edu.np.mad.mad_assg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage);

        Button newUser = (Button) findViewById(R.id.SignUp);
        newUser.setOnClickListener(view -> {
            Intent signin = new Intent(MainActivity.this, NewUser.class);
            startActivity(signin);
        });


        Button oldUser = (Button) findViewById(R.id.Login);
        oldUser.setOnClickListener(view -> {
            Intent login = new Intent(MainActivity.this, Login.class);
            startActivity(login);
        });

    }


}