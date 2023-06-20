package sg.edu.np.mad.mad_assg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment {

    public HomePage() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);




        MyDBHandler dbHelper = new MyDBHandler(getContext(), "Recipe.db", null, 1);
        ArrayList<RecipeList> recipes = new ArrayList<>();

        // Insert a ChocolateCake
        RecipeList chocolatecake = new RecipeList();
        chocolatecake.setRecipeName("Chocolate Cake");
        chocolatecake.setCategory("Bakery");
        chocolatecake.setUsername("Rachel Manley");
        chocolatecake.setDescription("Our really easy chocolate cake recipe is perfect for birthdays. It’s so moist and fudgy and will keep well for 4–5 days. For buttercream quantities, instead of ganache, use our cake calculator. Each serving provides 477 kcal, 6.5g protein, 56g carbohydrates (of which 40g sugars), 25g fat (of which 10.5g saturates), 2.5g fibre and 0.6g salt. Serves up to 12 people.\n");
        chocolatecake.setIngredients("For the cake\n" +
                "225g/8oz plain flour\n" +
                "350g/12½oz caster sugar\n" +
                "85g/3oz cocoa powder\n" +
                "1½ tsp baking powder\n" +
                "1½ tsp bicarbonate of soda\n" +
                "2 free-range eggs\n" +
                "250ml/9fl oz milk\n" +
                "125ml/4½fl oz vegetable oil\n" +
                "2 tsp vanilla extract\n" +
                "250ml/9fl oz boiling water\n" +
                "\n" +
                "For the chocolate ganache\n" +
                "200g/7oz plain chocolate\n" +
                "200ml/7fl oz double cream");
        chocolatecake.setSteps("1. Preheat the oven to 180C/160C Fan/Gas 4. Grease and line two 20cm/8in sandwich tins.\n" +
                "\n" +
                "2. For the cake, place all of the cake ingredients, except the boiling water, into a large mixing bowl. Using a wooden spoon, or electric whisk, beat the mixture until smooth and well combined.\n" +
                "\n" +
                "3. Add the boiling water to the mixture, a little at a time, until smooth. (The cake mixture will now be very liquid.)\n" +
                "\n" +
                "4. Divide the cake batter between the sandwich tins and bake in the oven for 25–35 minutes, or until the top is firm to the touch and a skewer inserted into the centre of the cake comes out clean.\n" +
                "\n" +
                "5. Remove the cakes from the oven and allow to cool completely, still in their tins, before icing.\n" +
                "\n" +
                "6. For the chocolate icing, heat the chocolate and cream in a saucepan over a low heat until the chocolate melts. Remove the pan from the heat and whisk the mixture until smooth, glossy and thickened. Set aside to cool for 1–2 hours, or until thick enough to spread over the cake.\n" +
                "\n" +
                "7. To assemble the cake, run a round-bladed knife around the inside of the cake tins to loosen the cakes. Carefully remove the cakes from the tins.\n" +
                "\n" +
                "8. Spread a little chocolate icing over the top of one of the chocolate cakes, then carefully top with the other cake.\n" +
                "\n" +
                "9. Transfer the cake to a serving plate and ice the cake all over with the chocolate icing, using a palette knife.");
        chocolatecake.setImageUrl("https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/easy_chocolate_cake_31070_16x9.jpg");
        recipes.add(chocolatecake);

        // Insert a PastaCarbonara
        RecipeList pastacarbonara = new RecipeList();
        pastacarbonara.setRecipeName("Pasta Carbonara");
        pastacarbonara.setCategory("Western");
        pastacarbonara.setUsername("ELISE BAUER");
        pastacarbonara.setDescription("Pasta carbonara is an indulgent yet surprisingly simple recipe. Made with pancetta (or bacon) and plenty of Parmesan, this recipe takes only 30 minutes to prepare from start to finish! serve up to 4 to 6 servings\n");
        pastacarbonara.setIngredients("1 tablespoon extra virgin olive oil or unsalted butter\n" +
                "1/2 pound pancetta or thick cut bacon, diced\n" +
                "1 to 2 garlic cloves, minced, about 1 teaspoon (optional)\n" +
                "3 to 4 whole eggs\n" +
                "1 cup grated Parmesan or pecorino cheese\n" +
                "1 pound spaghetti (or bucatini or fettuccine)\n" +
                "Kosher salt and freshly ground black pepper to taste");
        pastacarbonara.setSteps("1. Put a large pot of salted water on to boil (1 tablespoon salt for every 2 quarts of water.)\n" +
                "\n" +
                "2. While the water is coming to a boil, heat the olive oil or butter in a large sauté pan over medium heat. Add the bacon or pancetta and cook slowly until crispy. Add the garlic (if using) and cook another minute, then turn off the heat and put the pancetta and garlic into a large bowl.\n" +
                "\n" +
                "3. In a small bowl, beat the eggs and mix in about half of the cheese.\n" +
                "\n" +
                "4. Once the water has reached a rolling boil, add the dry pasta, and cook, uncovered, at a rolling boil.\n" +
                "\n" +
                "5. When the pasta is al dente (still a little firm, not mushy), use tongs to move it to the bowl with the bacon and garlic. Let it be dripping wet. Reserve some of the pasta water.\n" +
                "\n" +
                "6. Move the pasta from the pot to the bowl quickly, as you want the pasta to be hot. It's the heat of the pasta that will heat the eggs sufficiently to create a creamy sauce.\n" +
                "\n" +
                "7. Toss everything to combine, allowing the pasta to cool just enough so that it doesn't make the eggs curdle when you mix them in. (That's the tricky part.)\n" +
                "\n" +
                "8. Add the beaten eggs with cheese and toss quickly to combine once more. Add salt to taste. Add some pasta water back to the pasta to keep it from drying out.");
        pastacarbonara.setImageUrl("https://www.simplyrecipes.com/thmb/9DSEOemXX-gGJQBJqsY-qDzRjDw=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/Simply-Recipes-Spaghetti-Carbonara-LEAD-6-b3880a6eb49f4158be6f13885c797ded.jpg");
        recipes.add(pastacarbonara);

        // Insert a GimBap
        RecipeList gimbap = new RecipeList();
        gimbap.setRecipeName("GimBap");
        gimbap.setCategory("Korean");
        gimbap.setUsername("Maangchi");
        gimbap.setDescription("Basically, gimbap is a seaweed rice roll made of gim (a sheet of dried seaweed) and bap (rice). So as long as you can roll some rice in gim, you can say: “Check out the gimbap that I made!” serves up to 2-3 for 5 rolls");
        gimbap.setIngredients("5 sheets of gim (seaweed paper), roasted slightly (sometimes called “nori” from Japanese)\n" +
                "4 cups cooked rice (the recipe is here, but make with 2 cups of short grain rice instead of 1 cup)\n" +
                "½ pound beef skirt steak (or tenderloin, or ground beef)\n" +
                "1 large carrot, cut into matchsticks (about 1½ cup)\n" +
                "5 strips of yellow pickled radish (use pre-cut danmuji or cut into 8 inch long strips)\n" +
                "8  to 10 ounces spinach (1 small bunch), blanched, rinsed in cold water, and strained\n" +
                "3 eggs\n" +
                "3 garlic cloves\n" +
                "2 teaspoons soy sauce\n" +
                "1 tablespoon plus 1 teaspoon brown (or white) sugar\n" +
                "1½ teaspoon kosher salt\n" +
                "2½ tablespoons toasted sesame oil\n" +
                "vegetable oil");
        gimbap.setSteps("Rice:\n" +
                "1. Place freshly made rice in a large, shallow bowl. Gently mix in ½ teaspoon kosher salt and 2 teaspoons toasted sesame oil over top with a rice scoop or a wooden spoon.\n" +
                "\n" +
                "2. Let it cool down enough so it’s no longer steaming. Cover and set aside.\n" +
                "\n" +
                "Spinach:\n" +
                "3. Combine the blanched spinach, 2 minced garlic cloves, ½ teaspoon kosher salt, and 2 teaspoons toasted sesame oil in a bowl.\n" +
                "\n" +
                "4. Mix well by hand and put it on a large platter with the sliced yellow pickled radish.\n" +
                "\n" +
                "Carrots:\n" +
                "5. Combine the carrot matchsticks with ¼ teaspoon kosher salt. Mix well and let it sweat for 5 to 10 minutes. Heat a pan and add a few drops vegetable oil.\n" +
                "\n" +
                "6. Squeeze out excess water from the carrot, then saute for about 1 minute. Put it on the platter next to the spinach.\n" +
                "\n" +
                "Steaks:\n" +
                "7. Trim the fat from the skirt steaks and slice into ¼ inch wide, 3 to 5 inch strips. Put the strips into a bowl. Add 2 teaspoons soy sauce, 1 minced garlic clove, ¼ teaspoon ground black pepper,1 tablespoon plus 1 teaspoon brown (or white) sugar, and 2 teaspoons toasted sesame oil.\n" +
                "\n" +
                "8. Mix well by hand.\n" +
                "\n" +
                "9. Set aside, and let them marinate while we do the egg strips.\n" +
                "\n" +
                "Eggs:\n" +
                "10. Crack 3 eggs in a bowl and add ¼ teaspoon kosher salt. Beat it with fork and remove the stringy chalaza.\n" +
                "\n" +
                "11. Drizzle a few drops of oil on a heated 10 to 12 inch non-stick pan. Wipe off the excess with a paper towel so only a thin sheen of oil remains. Turn down the heat to low and pour the egg mixture into the pan. Spread it into a large circle so it fills the pan.\n" +
                "\n" +
                "12. When the bottom of the egg is cooked, flip it over with a spatula. Remove from the heat and let it cook slowly in the hot pan for about 5 minutes, with the ultimate goal of keeping the egg as yellow as possible, and not brown.\n" +
                "\n" +
                "13. Cut it into ½ inch wide strips. Put it next to the spinach on the platter.\n" +
                "\n" +
                "Finish steaks:\n" +
                "14. Heat up a pan over medium high heat and cook the marinated beef, stirring it with a wooden spoon until well cooked.\n" +
                "\n" +
                "15. Heat up a pan over medium high heat and cook the marinated beef, stirring it with a wooden spoon until well cooked.\n" +
                "\n" +
                "Let’s roll gimbap!\n" +
                "\n" +
                "16. Place a sheet of gim on a bamboo mat with the shiny side down. Evenly spread about ¾ cup of cooked rice over top of it, leaving about 2 inches uncovered on one side of the gim.\n" +
                "\n" +
                "17. Place beef, carrot, yellow pickled radish strip, a few egg strips, and spinach in the center of the rice.\n" +
                "\n" +
                "18. Let’s roll gimbap!\n" +
                "Place a sheet of gim on a bamboo mat with the shiny side down. Evenly spread about ¾ cup of cooked rice over top of it, leaving about 2 inches uncovered on one side of the gim.\n" +
                "\n" +
                "19. Place beef, carrot, yellow pickled radish strip, a few egg strips, and spinach in the center of the rice.\n" +
                "\n" +
                "20. Remove the roll from the mat at the end and set the finished roll aside with the seam down, to seal it nicely.\n" +
                "\n" +
                "21. Repeat 4 more times with the remaining ingredients.\n" +
                "\n" +
                "\n" +
                "22. Put some toasted sesame oil on the finshed rolls and sprinkle some sesame seeds over top. Cut each roll into ¼ inch bite size pieces with a sharp knife, occasionally wiping it with a wet paper towel or cloth to clean the starch off and to ease cutting.\n" +
                "\n" +
                "23. Put it on a plate and serve immediately or pack it in a lunchbox.");
        gimbap.setImageUrl("https://www.maangchi.com/wp-content/uploads/2007/08/gimbap_blog-590x351.jpg");
        recipes.add(gimbap);

        // Insert a MapoTofu
        RecipeList mapotofu = new RecipeList();
        mapotofu.setRecipeName("MaPo ToFu");
        mapotofu.setCategory("Chinese");
        mapotofu.setUsername("Nami");
        mapotofu.setDescription("Japanese-style Mapo Tofu (Mabo Dofu) is incredibly flavorful but less spicy than the Sichuan version. It's a delicious meal ready in 30 minutes that even children can enjoy! Servings for 4");
        mapotofu.setIngredients("2 cloves garlic\n" +
                "1 knob ginger (1 inch, 2.5 cm)\n" +
                "2 green onions/scallions\n" +
                "14 oz soft/silken tofu (kinugoshi dofu)\n" +
                "1 Tbsp neutral-flavored oil\n" +
                "½ lb ground pork (you can also use other meat and veggies of your choice)\n" +
                "\n" +
                "For the Seasonings\n" +
                "2½ Tbsp doubanjiang (spicy chili bean paste) (or a mixture of 1½ Tbsp doubanjiang (non-spicy) and 1 Tbsp la doubanjiang (spicy); click here to see the package of the non-spicy version; you can get this gluten-free doubanjiang)\n" +
                "2 Tbsp mirin\n" +
                "1 Tbsp miso\n" +
                "1 Tbsp oyster sauce\n" +
                "½ Tbsp soy sauce\n" +
                "1 tsp roasted sesame oil\n" +
                "1 tsp potato starch or cornstarch\n" +
                "4 Tbsp water");
        mapotofu.setSteps("1. her all the ingredients.\n" +
                "\n" +
                "2. Combine all the ingredients for the seasonings (2½ Tbsp doubanjiang (spicy chili bean paste), 2 Tbsp mirin, 1 Tbsp miso, 1 Tbsp oyster sauce, ½ Tbsp soy sauce, 1 tsp roasted sesame oil, 1 tsp potato starch or cornstarch, and 4 Tbsp water) in a bowl and mix well together.\n" +
                "\n" +
                "3. Mince 2 cloves garlic and 1 knob ginger finely.\n" +
                "\n" +
                "4. Cut 2 green onions/scallions into small pieces. Drain 14 oz soft/silken tofu (kinugoshi dofu) and cut into 1-inch (2.5 cm) cubes.\n" +
                "\n" +
                "To Cook the Mapo Tofu:\n" +
                "5. Heat a large frying pan on medium heat. When it‘s hot, add 1 Tbsp neutral oil. Add the garlic and ginger to the pan and sauté in the oil (make sure they don‘t burn). Once they are fragrant, add ½ lb ground pork and break it up with a spatula or wooden spoon.\n" +
                "\n" +
                "6. When the meat is no longer pink, add the seasonings mixture and stir thoroughly. Bring the sauce to a boil.\n" +
                "\n" +
                "7. Once the sauce is boiling, add the tofu and gently coat it with the sauce. Stir frequently, without mashing the tofu, until it is heated through. Add the green onions and stir to incorporate just before taking the pan off the heat. Serve immediately.\n" +
                "\n" +
                "To Store\n" +
                "8. You can keep the leftovers in an airtight container and store in the refrigerator for up to 3 days or in the freezer for a month.");
        mapotofu.setImageUrl("https://www.justonecookbook.com/wp-content/uploads/2020/03/Mapo-Tofu-5079-I.jpg");
        recipes.add(mapotofu);

// Insert each recipe into the database
        for (RecipeList recipe : recipes) {
            dbHelper.insertRecipe(recipe);
        }

        RecyclerView update = view.findViewById(R.id.updateview);
        update.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        MainRecipeRecyclerViewAdapter adapter = new MainRecipeRecyclerViewAdapter(recipes);
        update.setAdapter(adapter);

        adapter.setOnItemClickListener(new MainRecipeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RecipeList selectRecipe = recipes.get(position);
                Intent intent = new Intent(getActivity(), RecipeView.class);
                intent.putExtra("recipename", selectRecipe.getRecipeName());
                intent.putExtra("image", selectRecipe.getImageUrl());
                intent.putExtra("username", selectRecipe.getUsername());
                intent.putExtra("description", selectRecipe.getDescription());
                intent.putExtra("ingredients", selectRecipe.getIngredients());
                intent.putExtra("steps", selectRecipe.getSteps());
                startActivity(intent);
            }
        });

        RecyclerView recommendation = view.findViewById(R.id.recoview);
        recommendation.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendation.setAdapter(adapter);
        adapter.setOnItemClickListener(new MainRecipeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RecipeList selectRecipe = recipes.get(position);
                Intent intent = new Intent(getActivity(), RecipeView.class);
                intent.putExtra("recipename", selectRecipe.getRecipeName());
                intent.putExtra("image", selectRecipe.getImageUrl());
                intent.putExtra("username", selectRecipe.getUsername());
                intent.putExtra("description", selectRecipe.getDescription());
                intent.putExtra("ingredients", selectRecipe.getIngredients());
                intent.putExtra("steps", selectRecipe.getSteps());
                startActivity(intent);
            }
        });

        RecyclerView explore = view.findViewById(R.id.exploreview);
        explore.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        explore.setAdapter(adapter);


        adapter.setOnItemClickListener(new MainRecipeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RecipeList selectRecipe = recipes.get(position);
                Intent intent = new Intent(getActivity(), RecipeView.class);
                intent.putExtra("recipename", selectRecipe.getRecipeName());
                intent.putExtra("image", selectRecipe.getImageUrl());
                intent.putExtra("username", selectRecipe.getUsername());
                intent.putExtra("description", selectRecipe.getDescription());
                intent.putExtra("ingredients", selectRecipe.getIngredients());
                intent.putExtra("steps", selectRecipe.getSteps());
                startActivity(intent);
            }
        });



        /*GridView recoGridView = (GridView)view.findViewById(R.id.GridView_reco);
        recoGridView.setAdapter(new MainRecipeRecyclerViewAdapter(this.getContext(), recipes, R.layout.cardview_recipe));

        recoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                RecipeList selectRecipe = recipes.get(position);
                Intent intent = new Intent(getActivity(), RecipeView.class);
                intent.putExtra("recipename", selectRecipe.getRecipeName());
                intent.putExtra("image", selectRecipe.getImageUrl());
                intent.putExtra("username", selectRecipe.getUsername());
                intent.putExtra("description", selectRecipe.getDescription());
                intent.putExtra("ingredients", selectRecipe.getIngredients());
                intent.putExtra("steps", selectRecipe.getSteps());
                startActivity(intent);
                //Toast.makeText(view.getContext(),selectRecipe.get_recipName(),Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;
    }
/*
    private List<ItemRecipe> setupRecipe(){
        itemList = new ArrayList<>();
        String recipe[] = {"Mapo Tofu", "Chcoloate Cake", "Pasta Carbonara", "Gimbap"};
        String img[] = {"https://www.justonecookbook.com/wp-content/uploads/2020/03/Mapo-Tofu-5079-I.jpg", "https://ichef.bbci.co.uk/food/ic/food_16x9_1600/recipes/easy_chocolate_cake_31070_16x9.jpg", "https://www.simplyrecipes.com/thmb/9DSEOemXX-gGJQBJqsY-qDzRjDw=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/Simply-Recipes-Spaghetti-Carbonara-LEAD-6-b3880a6eb49f4158be6f13885c797ded.jpg","https://www.maangchi.com/wp-content/uploads/2007/08/gimbap_blog-590x351.jpg"};
        //String time[] = {"1h 5'", "30m", "1h 10'", "50m", "20m", "1h 20'", "20m", "1h 20'"};
        float rating[] = {3, 4, 4, 5};

        for (int i = 0; i<recipe.length; i++){
            ItemRecipe item = new ItemRecipe();
            item.setRecipe(recipe[i]);
            item.setRating(rating[i]);
            item.setImg(img[i]);
            itemList.add(item);
        }
        return itemList;
    }*/
}


