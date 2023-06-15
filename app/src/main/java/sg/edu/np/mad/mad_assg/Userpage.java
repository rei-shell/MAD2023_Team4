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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userpage, container, false);

    }
/*
    private TextView user;
    private TextView user2;

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


}