package sg.edu.np.mad.mad_assg;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHistoryViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView,categoryView;
    public ViewHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        categoryView = itemView.findViewById(R.id.category);
    }
}