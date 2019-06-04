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
import android.widget.TextView;

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

        // Get access to data
        CurrencyPairInfoData data = FXDatabase.shared.getInfoData();

        // Set title
        TextView title = (TextView) findViewById(R.id.fxInfoTitleText);
        String titleString = data.getCurrency1() + "/" + data.getCurrency2();
        title.setText(titleString);

        // Setup recyclerView
        setupRecyclerView();

        // Loading screen
        loadingScreen = new Dialog(this);
        loadingScreen.setContentView(R.layout.dialog_loading_screen);
        loadingScreen.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadingScreen.show();

        // Dismiss loading screen when data is loaded, data confirmation is checked with a timer
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 100);
                Log.i("TIMER", "EXECUTE");
                if (FXDatabase.shared.getInfoData().getConfirmedDataPoints() == 16) {
                    Log.i("TIMER", "DONE");
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
        handler.postDelayed(runnable, 100);
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

    // Update screen when data is loaded
    public void isLoaded() {
        //setupRecyclerView();
        mAdapter.notifyDataSetChanged();
        loadingScreen.dismiss();
    }
}
