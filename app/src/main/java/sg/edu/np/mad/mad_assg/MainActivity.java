package sg.edu.np.mad.mad_assg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

<<<<<<< HEAD
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
=======
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    NavigationView navigationView;
>>>>>>> origin/main

    ArrayList<String> myList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.homepage);
        drawer = (DrawerLayout) findViewById(R.id.content);

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
        }*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.content);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    }*/
