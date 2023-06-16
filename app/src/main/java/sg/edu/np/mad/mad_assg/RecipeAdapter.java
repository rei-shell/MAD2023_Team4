package sg.edu.np.mad.mad_assg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    ArrayList<String>data;

    public RecipeAdapter(ArrayList<String> input) {
        data = input;
    }


    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(item);
    }

    public void onBindViewHolder(RecipeViewHolder holder, int position){
        String s = data.get(position);
        holder.txt.setText(s);
    }

    public int getItemCount(){
        return data.size();
    }



}
