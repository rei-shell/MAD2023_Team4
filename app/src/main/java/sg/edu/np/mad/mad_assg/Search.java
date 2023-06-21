package sg.edu.np.mad.mad_assg;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {

    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<CategoryData> categoryData = new ArrayList<>();
        categoryData.add(new CategoryData("Korean", "https://th.bing.com/th/id/OIP.4XNdsS_4d498NGWtsFUEMgHaCv?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Chinese", "https://th.bing.com/th/id/OIP.0DIaCtOaFN2N9amGQaw3BwHaHa?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Bakery", "https://th.bing.com/th/id/OIP.jimuK0kF5msXZ884lzkvzQAAAA?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Western", "https://purepng.com/public/uploads/large/purepng.com-american-flagflagscountrylandflag-831523995311m0uxm.png"));
        categoryData.add(new CategoryData("Japanese", "https://www.downloadclipart.net/large/70566-flag-of-japan-clipart.png"));
        categoryData.add(new CategoryData("Indonesian", "https://i.pinimg.com/originals/cb/b2/0c/cbb20c310b64e3016ef059f6c49a3224.png"));
        categoryData.add(new CategoryData("Thai", "https://th.bing.com/th/id/R.357a65f8e8f3715fa59ef13173675606?rik=E6clXS1ynPUi%2fw&riu=http%3a%2f%2ficons.iconarchive.com%2ficons%2fwikipedia%2fflags%2f1024%2fTH-Thailand-Flag-icon.png&ehk=qDuYxKWVKPwWTHPQ1sU32uL3q8DrOdMrrioR86xy76g%3d&risl=&pid=ImgRaw&r=0"));
        categoryData.add(new CategoryData("Drinks", "https://th.bing.com/th/id/OIP.1zwixZBF8cEJlU8CsSgwhgHaHa?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Kids-Friendly", "https://th.bing.com/th/id/OIP.jUW7iZ2qHh-zIZXSVQfUIQHaE6?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Sides", "https://th.bing.com/th/id/OIP.AtyT8Vt-wec3X18MUTj-9AAAAA?w=136&h=146&c=7&r=0&o=5&pid=1.7"));


        RecyclerView categoryView = view.findViewById(R.id.category);
        categoryView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categoryData);
        categoryView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, item) -> {
            // Retrieve the selected category data and perform the action
            String category = item.getCategory();
            String imageUrl = item.getImageUrl();
            // Perform the action, such as starting a new activity
            Intent intent = new Intent(getActivity(), Recipe.class);
            intent.putExtra("category", category);
            intent.putExtra("imageUrl", imageUrl);
            startActivity(intent);
        });


        /*MyDBHandler dbHandler = new MyDBHandler(getContext(), "Recipe.db", null, 1);
        List<String> recipeNames = dbHandler.getAllRecipeNames();

        ArrayList<RecipeList> recipeList = new ArrayList<>();
        for (String recipeName : recipeNames) {
            // Assuming you have a method to retrieve RecipeList objects based on the recipe name
            RecipeList recipe = dbHandler.getRecipeByName(recipeName);
            recipeList.add(recipe);
        }


        RecyclerView recipeRecyclerView = view.findViewById(R.id.recipename);
        MainRecipeRecyclerViewAdapter adapter1 = new MainRecipeRecyclerViewAdapter(recipeList);
        recipeRecyclerView.setAdapter(adapter1);

        adapter1.setOnItemClickListener(new MainRecipeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RecipeList selectedRecipe = recipeList.get(position);
                Intent intent = new Intent(getActivity(), RecipeView.class);
                intent.putExtra("recipe", selectedRecipe);
                startActivity(intent);
            }
        });*/


        return view;
    }
}
