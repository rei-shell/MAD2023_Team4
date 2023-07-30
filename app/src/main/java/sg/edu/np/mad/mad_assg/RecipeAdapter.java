package sg.edu.np.mad.mad_assg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    //creating variable for list and context
    Context context;
    List<ViewHistoryItem> items;

    //constructor for adapter class
    public RecipeAdapter(Context context, List<ViewHistoryItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout file to display item
        return new RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.viewhistoryitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.imageView.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        //returning the size of the list
        return items.size();
    }




}




