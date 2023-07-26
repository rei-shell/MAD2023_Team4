package sg.edu.np.mad.mad_assg;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecycleViewAdapter extends RecyclerView.Adapter<RecipeRecycleViewAdapter.ViewHolder> {
    private List<RecipeList> recipeList;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public RecipeRecycleViewAdapter(List<RecipeList> recipeList) {
        this.recipeList = recipeList;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.recipe_image_id);
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userpage_recipe_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeList recipe = recipeList.get(position);

        // Load the recipe image using Glide or any other image loading library
        Glide.with(holder.imageView.getContext())
                .load(recipe.getPhotoUrl())
                .into(holder.imageView);
    }


    // getItemCount
    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // Set the OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // OnItemClickListener interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}