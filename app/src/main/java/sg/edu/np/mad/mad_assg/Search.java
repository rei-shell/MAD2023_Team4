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

      /*  LinearLayout layout = view.findViewById(R.id.linearlayout);
        //With the help of AnimatedDrawable class, we can set
        //the duration to our background and then call the
        //function start at the end.
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();*/

        ArrayList<CategoryData> categoryData = new ArrayList<>();
        /*categoryData.add(new CategoryData("Korean", "https://th.bing.com/th/id/OIP.4XNdsS_4d498NGWtsFUEMgHaCv?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Chinese", "https://th.bing.com/th/id/OIP.0DIaCtOaFN2N9amGQaw3BwHaHa?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Bakery", "https://th.bing.com/th/id/OIP.jimuK0kF5msXZ884lzkvzQAAAA?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Western", "https://purepng.com/public/uploads/large/purepng.com-american-flagflagscountrylandflag-831523995311m0uxm.png"));
        categoryData.add(new CategoryData("Japanese", "https://www.downloadclipart.net/large/70566-flag-of-japan-clipart.png"));
        categoryData.add(new CategoryData("Indonesian", "https://i.pinimg.com/originals/cb/b2/0c/cbb20c310b64e3016ef059f6c49a3224.png"));
        categoryData.add(new CategoryData("Thai", "https://th.bing.com/th/id/R.357a65f8e8f3715fa59ef13173675606?rik=E6clXS1ynPUi%2fw&riu=http%3a%2f%2ficons.iconarchive.com%2ficons%2fwikipedia%2fflags%2f1024%2fTH-Thailand-Flag-icon.png&ehk=qDuYxKWVKPwWTHPQ1sU32uL3q8DrOdMrrioR86xy76g%3d&risl=&pid=ImgRaw&r=0"));
        categoryData.add(new CategoryData("Drinks", "https://th.bing.com/th/id/OIP.1zwixZBF8cEJlU8CsSgwhgHaHa?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Kids-Friendly", "https://th.bing.com/th/id/OIP.jUW7iZ2qHh-zIZXSVQfUIQHaE6?pid=ImgDet&rs=1"));
        categoryData.add(new CategoryData("Sides", "https://th.bing.com/th/id/OIP.AtyT8Vt-wec3X18MUTj-9AAAAA?w=136&h=146&c=7&r=0&o=5&pid=1.7"));*/

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

/*  // Initialize your recipe list with data from a database or API
        recipeList = new ArrayList<>();

        recipeList.add(new RecipeList("https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/easy_chocolate_cake_31070_16x9.jpg",
                "Chocolate Cake", "Bakery", "Rachel Manley", "Our really easy chocolate cake recipe is perfect for birthdays. It’s so moist and fudgy and will keep well for 4–5 days. For buttercream quantities, instead of ganache, use our cake calculator. Each serving provides 477 kcal, 6.5g protein, 56g carbohydrates (of which 40g sugars), 25g fat (of which 10.5g saturates), 2.5g fibre and 0.6g salt. Serves up to 12 people.\n",
                "For the cake\\n\" +\n" +
                "                \"225g/8oz plain flour\\n\" +\n" +
                "                \"350g/12½oz caster sugar\\n\" +\n" +
                "                \"85g/3oz cocoa powder\\n\" +\n" +
                "                \"1½ tsp baking powder\\n\" +\n" +
                "                \"1½ tsp bicarbonate of soda\\n\" +\n" +
                "                \"2 free-range eggs\\n\" +\n" +
                "                \"250ml/9fl oz milk\\n\" +\n" +
                "                \"125ml/4½fl oz vegetable oil\\n\" +\n" +
                "                \"2 tsp vanilla extract\\n\" +\n" +
                "                \"250ml/9fl oz boiling water\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"For the chocolate ganache\\n\" +\n" +
                "                \"200g/7oz plain chocolate\\n\" +\n" +
                "                \"200ml/7fl oz double cream",
                "1. Preheat the oven to 180C/160C Fan/Gas 4. Grease and line two 20cm/8in sandwich tins.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"2. For the cake, place all of the cake ingredients, except the boiling water, into a large mixing bowl. Using a wooden spoon, or electric whisk, beat the mixture until smooth and well combined.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"3. Add the boiling water to the mixture, a little at a time, until smooth. (The cake mixture will now be very liquid.)\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"4. Divide the cake batter between the sandwich tins and bake in the oven for 25–35 minutes, or until the top is firm to the touch and a skewer inserted into the centre of the cake comes out clean.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"5. Remove the cakes from the oven and allow to cool completely, still in their tins, before icing.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"6. For the chocolate icing, heat the chocolate and cream in a saucepan over a low heat until the chocolate melts. Remove the pan from the heat and whisk the mixture until smooth, glossy and thickened. Set aside to cool for 1–2 hours, or until thick enough to spread over the cake.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"7. To assemble the cake, run a round-bladed knife around the inside of the cake tins to loosen the cakes. Carefully remove the cakes from the tins.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"8. Spread a little chocolate icing over the top of one of the chocolate cakes, then carefully top with the other cake.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"9. Transfer the cake to a serving plate and ice the cake all over with the chocolate icing, using a palette knife."));

        recipeList.add(new RecipeList("https://www.simplyrecipes.com/thmb/9DSEOemXX-gGJQBJqsY-qDzRjDw=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/Simply-Recipes-Spaghetti-Carbonara-LEAD-6-b3880a6eb49f4158be6f13885c797ded.jpg",
                "Pasta Carbonara", "Western", "ELISE BAUER", "Pasta carbonara is an indulgent yet surprisingly simple recipe. Made with pancetta (or bacon) and plenty of Parmesan, this recipe takes only 30 minutes to prepare from start to finish! serve up to 4 to 6 servings\n",
                "1 tablespoon extra virgin olive oil or unsalted butter\\n\" +\n" +
                        "                \"1/2 pound pancetta or thick cut bacon, diced\\n\" +\n" +
                        "                \"1 to 2 garlic cloves, minced, about 1 teaspoon (optional)\\n\" +\n" +
                        "                \"3 to 4 whole eggs\\n\" +\n" +
                        "                \"1 cup grated Parmesan or pecorino cheese\\n\" +\n" +
                        "                \"1 pound spaghetti (or bucatini or fettuccine)\\n\" +\n" +
                        "                \"Kosher salt and freshly ground black pepper to taste",
                "1. Put a large pot of salted water on to boil (1 tablespoon salt for every 2 quarts of water.)\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"2. While the water is coming to a boil, heat the olive oil or butter in a large sauté pan over medium heat. Add the bacon or pancetta and cook slowly until crispy. Add the garlic (if using) and cook another minute, then turn off the heat and put the pancetta and garlic into a large bowl.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"3. In a small bowl, beat the eggs and mix in about half of the cheese.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"4. Once the water has reached a rolling boil, add the dry pasta, and cook, uncovered, at a rolling boil.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"5. When the pasta is al dente (still a little firm, not mushy), use tongs to move it to the bowl with the bacon and garlic. Let it be dripping wet. Reserve some of the pasta water.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"6. Move the pasta from the pot to the bowl quickly, as you want the pasta to be hot. It's the heat of the pasta that will heat the eggs sufficiently to create a creamy sauce.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"7. Toss everything to combine, allowing the pasta to cool just enough so that it doesn't make the eggs curdle when you mix them in. (That's the tricky part.)\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"8. Add the beaten eggs with cheese and toss quickly to combine once more. Add salt to taste. Add some pasta water back to the pasta to keep it from drying out."));

        recipeList.add(new RecipeList("https://www.maangchi.com/wp-content/uploads/2007/08/gimbap_blog-590x351.jpg",
                "GimBap", "Korean", "Maangchi", "Basically, gimbap is a seaweed rice roll made of gim (a sheet of dried seaweed) and bap (rice). So as long as you can roll some rice in gim, you can say: “Check out the gimbap that I made!” serves up to 2-3 for 5 rolls",
                "5 sheets of gim (seaweed paper), roasted slightly (sometimes called “nori” from Japanese)\\n\" +\n" +
                        "                \"4 cups cooked rice (the recipe is here, but make with 2 cups of short grain rice instead of 1 cup)\\n\" +\n" +
                        "                \"½ pound beef skirt steak (or tenderloin, or ground beef)\\n\" +\n" +
                        "                \"1 large carrot, cut into matchsticks (about 1½ cup)\\n\" +\n" +
                        "                \"5 strips of yellow pickled radish (use pre-cut danmuji or cut into 8 inch long strips)\\n\" +\n" +
                        "                \"8  to 10 ounces spinach (1 small bunch), blanched, rinsed in cold water, and strained\\n\" +\n" +
                        "                \"3 eggs\\n\" +\n" +
                        "                \"3 garlic cloves\\n\" +\n" +
                        "                \"2 teaspoons soy sauce\\n\" +\n" +
                        "                \"1 tablespoon plus 1 teaspoon brown (or white) sugar\\n\" +\n" +
                        "                \"1½ teaspoon kosher salt\\n\" +\n" +
                        "                \"2½ tablespoons toasted sesame oil\\n\" +\n" +
                        "                \"vegetable oil",
                "Rice:\\n\" +\n" +
                        "                \"1. Place freshly made rice in a large, shallow bowl. Gently mix in ½ teaspoon kosher salt and 2 teaspoons toasted sesame oil over top with a rice scoop or a wooden spoon.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"2. Let it cool down enough so it’s no longer steaming. Cover and set aside.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"Spinach:\\n\" +\n" +
                        "                \"3. Combine the blanched spinach, 2 minced garlic cloves, ½ teaspoon kosher salt, and 2 teaspoons toasted sesame oil in a bowl.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"4. Mix well by hand and put it on a large platter with the sliced yellow pickled radish.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"Carrots:\\n\" +\n" +
                        "                \"5. Combine the carrot matchsticks with ¼ teaspoon kosher salt. Mix well and let it sweat for 5 to 10 minutes. Heat a pan and add a few drops vegetable oil.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"6. Squeeze out excess water from the carrot, then saute for about 1 minute. Put it on the platter next to the spinach.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"Steaks:\\n\" +\n" +
                        "                \"7. Trim the fat from the skirt steaks and slice into ¼ inch wide, 3 to 5 inch strips. Put the strips into a bowl. Add 2 teaspoons soy sauce, 1 minced garlic clove, ¼ teaspoon ground black pepper,1 tablespoon plus 1 teaspoon brown (or white) sugar, and 2 teaspoons toasted sesame oil.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"8. Mix well by hand.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"9. Set aside, and let them marinate while we do the egg strips.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"Eggs:\\n\" +\n" +
                        "                \"10. Crack 3 eggs in a bowl and add ¼ teaspoon kosher salt. Beat it with fork and remove the stringy chalaza.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"11. Drizzle a few drops of oil on a heated 10 to 12 inch non-stick pan. Wipe off the excess with a paper towel so only a thin sheen of oil remains. Turn down the heat to low and pour the egg mixture into the pan. Spread it into a large circle so it fills the pan.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"12. When the bottom of the egg is cooked, flip it over with a spatula. Remove from the heat and let it cook slowly in the hot pan for about 5 minutes, with the ultimate goal of keeping the egg as yellow as possible, and not brown.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"13. Cut it into ½ inch wide strips. Put it next to the spinach on the platter.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"Finish steaks:\\n\" +\n" +
                        "                \"14. Heat up a pan over medium high heat and cook the marinated beef, stirring it with a wooden spoon until well cooked.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"15. Heat up a pan over medium high heat and cook the marinated beef, stirring it with a wooden spoon until well cooked.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"Let’s roll gimbap!\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"16. Place a sheet of gim on a bamboo mat with the shiny side down. Evenly spread about ¾ cup of cooked rice over top of it, leaving about 2 inches uncovered on one side of the gim.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"17. Place beef, carrot, yellow pickled radish strip, a few egg strips, and spinach in the center of the rice.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"18. Let’s roll gimbap!\\n\" +\n" +
                        "                \"Place a sheet of gim on a bamboo mat with the shiny side down. Evenly spread about ¾ cup of cooked rice over top of it, leaving about 2 inches uncovered on one side of the gim.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"19. Place beef, carrot, yellow pickled radish strip, a few egg strips, and spinach in the center of the rice.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"20. Remove the roll from the mat at the end and set the finished roll aside with the seam down, to seal it nicely.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"21. Repeat 4 more times with the remaining ingredients.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"22. Put some toasted sesame oil on the finshed rolls and sprinkle some sesame seeds over top. Cut each roll into ¼ inch bite size pieces with a sharp knife, occasionally wiping it with a wet paper towel or cloth to clean the starch off and to ease cutting.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"23. Put it on a plate and serve immediately or pack it in a lunchbox."));

        recipeList.add(new RecipeList("https://www.justonecookbook.com/wp-content/uploads/2020/03/Mapo-Tofu-5079-I.jpg",
                "MaPo Tofu", "Chinese", "Nami", "Japanese-style Mapo Tofu (Mabo Dofu) is incredibly flavorful but less spicy than the Sichuan version. It's a delicious meal ready in 30 minutes that even children can enjoy! Servings for 4",
                "2 cloves garlic\\n\" +\n" +
                        "                \"1 knob ginger (1 inch, 2.5 cm)\\n\" +\n" +
                        "                \"2 green onions/scallions\\n\" +\n" +
                        "                \"14 oz soft/silken tofu (kinugoshi dofu)\\n\" +\n" +
                        "                \"1 Tbsp neutral-flavored oil\\n\" +\n" +
                        "                \"½ lb ground pork (you can also use other meat and veggies of your choice)\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"For the Seasonings\\n\" +\n" +
                        "                \"2½ Tbsp doubanjiang (spicy chili bean paste) (or a mixture of 1½ Tbsp doubanjiang (non-spicy) and 1 Tbsp la doubanjiang (spicy); click here to see the package of the non-spicy version; you can get this gluten-free doubanjiang)\\n\" +\n" +
                        "                \"2 Tbsp mirin\\n\" +\n" +
                        "                \"1 Tbsp miso\\n\" +\n" +
                        "                \"1 Tbsp oyster sauce\\n\" +\n" +
                        "                \"½ Tbsp soy sauce\\n\" +\n" +
                        "                \"1 tsp roasted sesame oil\\n\" +\n" +
                        "                \"1 tsp potato starch or cornstarch\\n\" +\n" +
                        "                \"4 Tbsp water",
                "1. her all the ingredients.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"2. Combine all the ingredients for the seasonings (2½ Tbsp doubanjiang (spicy chili bean paste), 2 Tbsp mirin, 1 Tbsp miso, 1 Tbsp oyster sauce, ½ Tbsp soy sauce, 1 tsp roasted sesame oil, 1 tsp potato starch or cornstarch, and 4 Tbsp water) in a bowl and mix well together.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"3. Mince 2 cloves garlic and 1 knob ginger finely.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"4. Cut 2 green onions/scallions into small pieces. Drain 14 oz soft/silken tofu (kinugoshi dofu) and cut into 1-inch (2.5 cm) cubes.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"To Cook the Mapo Tofu:\\n\" +\n" +
                        "                \"5. Heat a large frying pan on medium heat. When it‘s hot, add 1 Tbsp neutral oil. Add the garlic and ginger to the pan and sauté in the oil (make sure they don‘t burn). Once they are fragrant, add ½ lb ground pork and break it up with a spatula or wooden spoon.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"6. When the meat is no longer pink, add the seasonings mixture and stir thoroughly. Bring the sauce to a boil.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"7. Once the sauce is boiling, add the tofu and gently coat it with the sauce. Stir frequently, without mashing the tofu, until it is heated through. Add the green onions and stir to incorporate just before taking the pan off the heat. Serve immediately.\\n\" +\n" +
                        "                \"\\n\" +\n" +
                        "                \"To Store\\n\" +\n" +
                        "                \"8. You can keep the leftovers in an airtight container and store in the refrigerator for up to 3 days or in the freezer for a month."));
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