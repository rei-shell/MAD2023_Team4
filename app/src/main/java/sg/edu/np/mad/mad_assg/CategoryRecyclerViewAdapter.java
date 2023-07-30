package sg.edu.np.mad.mad_assg;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private ArrayList<CategoryData> categoryDataList;
    private OnItemClickListener onItemClickListener; // Define the interface for item click handling

    public CategoryRecyclerViewAdapter(ArrayList<CategoryData> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.categoryitem_text);
            view.setOnClickListener(this); // Set the click listener to the view
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position, categoryDataList.get(position)); // Call the item click callback
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryData categoryData = categoryDataList.get(position);
        holder.name.setText(categoryData.getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    // Method to set the OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Interface for item click handling
    public interface OnItemClickListener {
        void onItemClick(int position, CategoryData categoryData);
    }
}



/*public class CategoryRecyclerViewAdapter<T> extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder> {

    private final ArrayList<CategoryData> categoryDataList;
    private OnItemClickListener onItemClickListener;

    public CategoryRecyclerViewAdapter(ArrayList<CategoryData> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // ImageView imageView;
        TextView name;

        public CategoryViewHolder(View view) {
            super(view);
            // imageView = view.findViewById(R.id.categoryitem_img);
            name = view.findViewById(R.id.categoryitem_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position, categoryDataList.get(position));
                }
            }
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryData categoryData = categoryDataList.get(position);
        // Picasso.get().load(categoryData.getImageUrl()).into(holder.imageView);
        holder.name.setText(categoryData.getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(ArrayList<CategoryData> newData) {
        categoryDataList.clear();
        categoryDataList.addAll(newData);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, CategoryData categoryData);
    }
}*/
/*public class CategoryRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<CategoryData> categoryDataList;
    private OnItemClickListener onItemClickListener;

    public CategoryRecyclerViewAdapter(ArrayList<CategoryData> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //     ImageView imageView;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            // imageView = view.findViewById(R.id.categoryitem_img);
            name = view.findViewById(R.id.categoryitem_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position, categoryDataList.get(position));
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new ViewHolder(view);
    }

   @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryData categoryData = categoryDataList.get(position);
        holder.name.setText(categoryData.getCategory());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            CategoryData categoryData = categoryDataList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            // Set the data to the views in the ViewHolder
            viewHolder.name.setText(categoryData.getCategory());
        }
    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //     ImageView imageView;
        TextView name;

        public CategoryViewHolder(View view) {
            super(view);
            // imageView = view.findViewById(R.id.categoryitem_img);
            name = view.findViewById(R.id.categoryitem_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(position, categoryDataList.get(position));
                }
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryViewHolder) {
            CategoryData categoryData = categoryDataList.get(position);
            CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
            // Set the data to the views in the ViewHolder
            viewHolder.name.setText(categoryData.getCategory());
        }
    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(ArrayList<CategoryData> newData) {
        categoryDataList.clear();
        categoryDataList.addAll(newData);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(int position, CategoryData categoryData);
    }
}*/
