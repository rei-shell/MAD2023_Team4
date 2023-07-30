package sg.edu.np.mad.mad_assg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchRecycleViewAdapter extends RecyclerView.Adapter<SearchRecycleViewAdapter.ViewHolder> {

    private List<RecipeList> recipeList;
    private OnItemClickListener onItemClickListener; // Define the interface for item click handling

    // Constructor
    public SearchRecycleViewAdapter(List<RecipeList> recipeList) {
        this.recipeList = recipeList;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView recipeName, categoryName;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageview);
            recipeName = view.findViewById(R.id.name);
            categoryName = view.findViewById(R.id.category);
            view.setOnClickListener(this); // Set the click listener to the view
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position); // Call the item click callback
                }
            }
        }
    }

    @NonNull
    @Override
    public SearchRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecycleViewAdapter.ViewHolder holder, int position) {
        RecipeList recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getTitle());
        holder.categoryName.setText(recipe.getCategory());
        Glide.with(holder.imageView.getContext())
                .load(recipe.getPhotoUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Method to set the OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Interface for item click handling
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
