package sg.edu.np.mad.mad_assg;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    TextView txt;
    public HistoryViewHolder(View itemView){
        super(itemView);
        txt = itemView.findViewById(R.id.textView);

    }
}
