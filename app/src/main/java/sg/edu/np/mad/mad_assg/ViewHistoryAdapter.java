package sg.edu.np.mad.mad_assg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ViewHistoryAdapter extends RecyclerView.Adapter<ViewHistoryViewHolder> {

    Context context;
    List<ViewHistoryItem> items;

    public ViewHistoryAdapter(Context context, List<ViewHistoryItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.viewhistoryitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHistoryViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.categoryView.setText(items.get(position).getCategory());
        holder.imageView.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


