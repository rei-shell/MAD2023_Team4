package sg.edu.np.mad.mad_assg;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.collection.CircularArray;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class category_adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<CategoryData> categoryList;
    private int layout;

    public category_adapter(Context context, ArrayList<CategoryData> categoryList, int layout) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categoryList = categoryList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position).get_category();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        RecipeList recipeItem = RecipeList.get(position);
        ImageView icon = (ImageView) convertView.findViewById(R.id.recipe_image_id);
        // Assuming your recipe list is a list of Recipe objects with an image URL property
// Assuming you have a RecipeList object at the desired position
        RecipeList recipe = RecipeList.get(position);

// Load the image using Picasso
        Picasso.get().load(recipe.getImageUrl()).into(icon);

        TextView name = (TextView) convertView.findViewById(R.id.recipe_text);
        name.setText(recipeItem.getRecipeName());
        return convertView;
    }

}
