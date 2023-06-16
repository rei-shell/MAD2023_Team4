package sg.edu.np.mad.mad_assg;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Userpage extends Fragment {

    private TextView user;
    private TextView user2;
    private MyDBHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userpage, container, false);
        user = view.findViewById(R.id.userid);
        user2 = view.findViewById(R.id.userName);
        dbHandler = new MyDBHandler(getContext(), "User.db", null, 1);

        String username = dbHandler.getUsername();
        if (username != null) {
            user.setText(username);
            user2.setText(username);
        }

        return view;
    }

    public void setUsernameText(String username) {
        if (user != null) {
            user.setText(username);
        }
        if (user2 != null) {
            user2.setText(username);
        }
    }
}

/*


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userpage, container, false);


        //set userid
        String userid = getActivity().getIntent().getExtras().getString("username");
        user = (TextView) user.findViewById(R.id.userid);
        user2 = (TextView) user.findViewById(R.id.userName);
        user.setText(userid);
        user2.setText(userid);

        return view;
    }*/

