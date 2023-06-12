package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends MainActivity{
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this,StartPage.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }

}
