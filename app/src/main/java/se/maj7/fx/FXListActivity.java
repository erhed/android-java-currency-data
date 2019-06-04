package se.maj7.fx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class FXListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FXListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fx_list);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //newList();
            }
        });*/

        // Reload-button
        /*ImageView reload = (ImageView) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.notifyDataSetChanged();
            }
        });*/

        // Check if data retrieved and update list
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 100);
                Log.i("TIMER", "EXECUTE list");
                if (FXDatabase.shared.getItemsLoaded() == FXDatabase.shared.getListSize()) {
                    Log.i("TIMER", "DONE list");
                    FXDatabase.shared.resetItemsLoaded();
                    mAdapter.notifyDataSetChanged();
                    handler.removeCallbacks(this);
                }
            }
        };

        handler.postDelayed(runnable, 100);

        // RecyclerView
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFXList);

        mAdapter = new FXListAdapter(this);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Add dividing line between items in the list
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(1));

        // Set adapter
        recyclerView.setAdapter(mAdapter);

        // Swipe to delete
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        //itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Go to detail view when list item clicked
    public void showDetailView(int position) {
        FXListItem item = FXDatabase.shared.getItem(position);
        FXDatabase.shared.getDetailViewData(item.getCurrency1(), item.getCurrency2(), this);

        Intent intent = new Intent(this, CurrencyPairInfoActivity.class);
        startActivity(intent);
    }

    // Not in use atm
    public void deleteListItem(int position) {
        FXDatabase.shared.deleteFromList(position);
    }
}
