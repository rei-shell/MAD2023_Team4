package sg.edu.np.mad.mad_assg;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewedHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewedhistory);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        List<ViewHistoryItem> items = new ArrayList<ViewHistoryItem>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewHistoryAdapter(getApplicationContext(),items));
    }
}