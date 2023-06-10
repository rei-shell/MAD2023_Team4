package sg.edu.np.mad.mad_assg;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Userpage extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView user;

    MyDBHandler dbHandler = new MyDBHandler(this,
            null,
            null,
            1);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_userpage, container, false);
        //set userid
        SharedPreferences pref = view.getContext().getSharedPreferences("Login", Activity.MODE_PRIVATE);
        String userID = pref.getString("userID","");
        user = (TextView) view.findViewById(R.id.rexipe);
        user.setText(userID);

        return view;
    }
}