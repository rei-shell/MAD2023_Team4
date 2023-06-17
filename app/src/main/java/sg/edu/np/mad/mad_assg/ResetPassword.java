package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        TextView username = (TextView) findViewById(R.id.username);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        TextInputLayout etpassword = (TextInputLayout) findViewById(R.id.new_password);
        TextInputLayout etconfirmpassword = (TextInputLayout) findViewById(R.id.confirm_new_password);

        Button reset = (Button) findViewById(R.id.confirm);

        MyDBHandler dbhandler = new MyDBHandler(this, "User.db", null, 1);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String password = etpassword.getEditText().getText().toString();
                String confirmpwd = etconfirmpassword.getEditText().getText().toString();

                if(password.equals(confirmpwd)){

                    Boolean passwordupdate = dbhandler.updatepassword(user, password);

                    if(passwordupdate==true){
                        Intent intent = new Intent(ResetPassword.this, Login.class);
                        startActivity(intent);
                        Toast.makeText(ResetPassword.this, "Password Reset!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ResetPassword.this, "Password Not Reset!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ResetPassword.this, "Password Not Match!", Toast.LENGTH_SHORT).show();
                }
        }
    });
}
}
