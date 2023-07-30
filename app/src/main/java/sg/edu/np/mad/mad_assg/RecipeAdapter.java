package sg.edu.np.mad.mad_assg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<RecipeList> recipeList;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public RecipeAdapter(List<RecipeList> recipeList) {
        this.recipeList = recipeList;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView recipeName;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageview);
            recipeName = view.findViewById(R.id.name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position);
                }
            }
        }
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodrecipeoptionitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        RecipeList recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getTitle());
        Glide.with(holder.imageView.getContext())
                .load(recipe.getPhotoUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}



/*public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    Context context;
    List<ViewHistoryItem> items;

    public RecipeAdapter(Context context, List<ViewHistoryItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.viewhistoryitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.imageView.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}*/




