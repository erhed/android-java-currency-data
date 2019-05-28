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
        for (final FXListItem item : mFXList) {
            getPrice(item.getCurrency1(),item.getCurrency2(), context, 0, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    item.setPrice(Float.parseFloat(result));
                }
            });
        }
        for (final FXListItem item : mFXList) {
            getPrice(item.getCurrency1(),item.getCurrency2(), context, 1, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    item.setPreviousPrice(Float.parseFloat(result));
                    item.setIsUp();
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
        FXListItem item = new FXListItem("USD","SEK");
        mFXList.add(item);
        FXListItem item2 = new FXListItem("EUR","USD");
        mFXList.add(item2);
        FXListItem item3 = new FXListItem("GBP","USD");
        mFXList.add(item3);
        FXListItem item4 = new FXListItem("EUR","CHF");
        mFXList.add(item4);
    }

}
