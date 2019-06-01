package se.maj7.fx;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Timer;

import javax.security.auth.callback.Callback;

public class CurrencyPairInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CurrencyPairInfoAdapter mAdapter;
    Dialog loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_pair_info);

        setupRecyclerView();

        loadingScreen = new Dialog(this);
        loadingScreen.setContentView(R.layout.dialog_loading_screen);

        loadingScreen.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadingScreen.show();

        // Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (FXDatabase.shared.getInfoData().getConfirmedDataPoints() == 16) {
                    FXDatabase.shared.getInfoData().resetConfirmedDataPoints();
                    isLoaded();
                }
            }
        };

        loadingScreen.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 500);
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

    public void isLoaded() {
        //setupRecyclerView();
        mAdapter.notifyDataSetChanged();
        loadingScreen.dismiss();
    }
}
