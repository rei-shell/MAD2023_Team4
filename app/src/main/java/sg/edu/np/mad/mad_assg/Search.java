package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Locale;


public class Search extends Fragment {

    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        MyDBHandler dbHelper = new MyDBHandler(getContext(), "Recipe.db", null, 1);
        //ArrayList<CategoryData> categoryData = new ArrayList<>();


        CategoryData.setCategory("Korean");
        CategoryData.setCategory("Chinese");
        CategoryData.setCategory("Bakery");
        CategoryData.setCategory("Western");
        CategoryData.setCategory("Japanese");
        CategoryData.setCategory("Indonesian");
        CategoryData.setCategory("Thai");
        CategoryData.setCategory("Easy-Made");
        CategoryData.setCategory("Drinks");
        CategoryData.setCategory("Kids-Friendly");
        CategoryData.setCategory("Sides");

        // Insert each recipe into the database


        return view;
    }
}