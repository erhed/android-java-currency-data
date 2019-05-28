package se.maj7.fx;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static se.maj7.fx.FXFetchData.*;

public class FXDatabase {

    public static final FXDatabase shared = new FXDatabase();

    private ArrayList<FXListItem> mFXList = new ArrayList<>();

    private FXDatabase() {
        makeDummyList();
    }

    public void getPrices(Context context) {
        for (FXListItem item : mFXList) {
            getPrice(item.getCurrency1(),item.getCurrency2(), context, 0, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    Log.e("MAINACTIVITY", result);
                }
            });
        }

    }

    public FXListItem getItem(int index) {
        return mFXList.get(index);
    }

    public int getListSize() {
        return mFXList.size();
    }

    private void makeDummyList() {
        FXListItem item = new FXListItem("EUR","USD", 1.1198f, 1.1138f);
        mFXList.add(item);
        mFXList.add(item);
    }

}
