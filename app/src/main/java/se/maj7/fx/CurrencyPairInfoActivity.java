package se.maj7.fx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CurrencyPairInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CurrencyPairInfoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_pair_info);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewInfo);

        mAdapter = new CurrencyPairInfoAdapter(this);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Add dividing line between items in the list
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Set adapter
        recyclerView.setAdapter(mAdapter);

        // Swipe to delete
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(mAdapter, this));
        //itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
