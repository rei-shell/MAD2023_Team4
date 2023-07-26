package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment{

    public Search() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<CategoryData> categoryData = new ArrayList<>();

        categoryData.add(new CategoryData("Korean"));
        categoryData.add(new CategoryData("Chinese"));
        categoryData.add(new CategoryData("Bakery"));
        categoryData.add(new CategoryData("Western"));
        categoryData.add(new CategoryData("Japanese"));
        categoryData.add(new CategoryData("Indonesian"));
        categoryData.add(new CategoryData("Thai"));
        categoryData.add(new CategoryData("Drinks"));
        categoryData.add(new CategoryData("Kids-Friendly"));
        categoryData.add(new CategoryData("Sides"));

        RecyclerView categoryView = view.findViewById(R.id.category);
        categoryView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categoryData);
        categoryView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item) -> {
            // Retrieve the selected category data and perform the action
            String category = item.getCategory();
         //   String imageUrl = item.getImageUrl();
            // Perform the action, such as starting a new activity
            Intent intent = new Intent(getActivity(), RecipeView.class);
            intent.putExtra("category", category);
           // intent.putExtra("imageUrl", imageUrl);
            startActivity(intent);
        });

        return view;
    }
}