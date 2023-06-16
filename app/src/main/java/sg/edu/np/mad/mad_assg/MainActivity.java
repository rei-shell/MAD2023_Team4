package sg.edu.np.mad.mad_assg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomePage homeFragment = new HomePage();
    Search searchFragment = new Search();
    Userpage userFragment = new Userpage();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment;
                int id = item.getItemId();

                if (id == R.id.home) {
                    fragment = new HomePage();
                } else if (id == R.id.search) {
                    fragment = new Search();
                } else if (id == R.id.person) {
                    fragment = userFragment;
                } else {
                    return false;
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commit();

                return true;
            }
        });

        // Retrieve the username from the SQL database
        String username = getUserNameFromDatabase();

        // Set the username text in the Userpage fragment
        userFragment.setUsernameText(username);
    }


    @SuppressLint("Range")
    private String getUserNameFromDatabase() {
        MyDBHandler dbHandler = new MyDBHandler(this, "User.db", null, 1);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String username = null;

        // Perform the SQL query to retrieve the username from the database
        Cursor cursor = db.query("User", new String[]{"UserName"}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex("UserName"));
            cursor.close();
        }
        db.close();

        return username;
    }
}



/*

    switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, homeFragment).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, searchFragment).commit();
                        return true;
                    case R.id.person:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, userFragment).commit();
                        return true;
                }
                return false;
    DrawerLayout drawer;
    NavigationView navigationView;


    ArrayList<String> myList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.homepage);

        };
    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first

        navigationView = (NavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        HomePage homeFragment = new HomePage();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.home)
        {
            //connect to home page
            HomePage homeFragment = new HomePage();
            manager.beginTransaction().replace(R.id.content, homeFragment, homeFragment.getTag()).addToBackStack(null).commit();

        }
        else if (id == R.id.search)
        {
            //connect to search page
            Search search = new Search();
            manager.beginTransaction().replace(R.id.content, search, search.getTag()).addToBackStack(null).commit();

        }
        else if (id == R.id.person)
        {
            //connect to user page
            Userpage user = new Userpage();
            manager.beginTransaction().replace(R.id.content, user, user.getTag()).addToBackStack(null).commit();

        }
       /* else if (id == R.id.nav_search)
        {
            //connect to search recipes page
            SearchFragment searchFragment = new SearchFragment();
            manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.getTag()).addToBackStack(null).commit();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

        myList.add("");
    }






/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        ft = getSupportFragmentManager().beginTransaction();
        currentFragment = new HomePage();
        ft.replace(R.id.content, currentFragment);
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    Fragment currentFragment = null;
    FragmentTransaction ft;
    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.search:
                    currentFragment = new Search();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, currentFragment);
                    ft.commit();
                    return true;
                case R.id.home:
                    currentFragment = new HomePage();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, currentFragment);
                    ft.commit();
                    return true;
                case R.id.person:
                    currentFragment = new Userpage();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, currentFragment);
                    ft.commit();
                    return true;
            }

            return false;
        }

    };
Logout();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /*public void switchToFragment1() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new HomePage()).commit();
    }

    public void switchToFragment2() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new Search()).commit();
    }

    public void switchToFragment3() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new Userpage()).commit();
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                switchToFragment1();
                break;

            case R.id.search:
                switchToFragment2();
                break;

            case R.id.person:
                switchToFragment3();
                break;
        }
        return true;

    }
*/





