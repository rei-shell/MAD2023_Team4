package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Userpage extends Fragment {

    private TextView user;
    private TextView user2;
    private MyDBHandler dbHandler;

    private ImageView settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userpage, container, false);

        ConstraintLayout layout = view.findViewById(R.id.flFragment);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        user = view.findViewById(R.id.userid);
        user2 = view.findViewById(R.id.userName);
        settings = view.findViewById(R.id.settingbtn);

        dbHandler = new MyDBHandler(getContext(), "User.db", null, 1);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingpage();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            String username = bundle.getString("username");
            if (username != null) {
                user.setText(username);
                user2.setText(username);
            }
        }

        return view;
    }
/*
    public void setUsernameText(Intent username) {
        if (user != null) {
            user.setText((CharSequence) username);
        }
        if (user2 != null) {
            user2.setText((CharSequence) username);
        }
    }*/
    public void settingpage() {
        Intent intent = new Intent(getActivity(), Setttings.class);
        startActivity(intent);
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

