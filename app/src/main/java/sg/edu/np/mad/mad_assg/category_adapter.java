package sg.edu.np.mad.mad_assg;


import static java.security.AccessController.getContext;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.collection.CircularArray;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.security.AccessController;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        CategoryData categoryItem = categoryList.get(position);
        ImageView icon = convertView.findViewById(R.id.categoryitem_img);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Uri imageUrl = Uri.parse(categoryItem.getImageUrl());
            Picasso.get().load(imageUrl.toString()).into(icon);
        }

        // Set the color of the ImageView
        icon.setColorFilter(ContextCompat.getColor(convertView.getContext(), R.color.blue));

        TextView name = convertView.findViewById(R.id.categoryitem_text);
        name.setText(categoryItem.get_category());
        return convertView;
    }



}
