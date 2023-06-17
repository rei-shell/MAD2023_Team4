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


public class Search extends Fragment {

    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        /*GridView gridView = (GridView) view.findViewById(R.id.gridView_category);

        MyDBHandler dbHelper = new MyDBHandler(getContext(), "Recipe.db", null, 1);
        //final ArrayList<CategoryData> categotyList = dbHelper.recipes_SelectCategory();

        //gridView.setAdapter(new category_adapter(this.getContext(), categotyList, R.layout.fragment_search));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  CategoryData selectCategory = categotyList.get(position);
              //  Intent intent = new Intent(getActivity(), RecipeList.class);
              //  intent.putExtra("category", selectCategory.get_category());
              //  startActivity(intent);
            }
        });*/

        return view;
    }
}