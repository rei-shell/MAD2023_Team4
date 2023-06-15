package sg.edu.np.mad.mad_assg;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView txt;
    public RecipeViewHolder(View itemView){
        super(itemView);
        txt = itemView.findViewById(R.id.textView);
    }
}
