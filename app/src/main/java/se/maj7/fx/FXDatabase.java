package se.maj7.fx;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static se.maj7.fx.FXFetchData.*;

public class FXDatabase {

    public static final FXDatabase shared = new FXDatabase();

    private ArrayList<FXListItem> mFXList = new ArrayList<>();
    private static CurrencyPairInfoData mDetailData = new CurrencyPairInfoData();
    private JSONSerializer mSerializer;

    private int listItemsLoaded = 0;

    private FXDatabase() {
        makeDummyList();
    }

    public void getPrices(Context context) {
        for (final FXListItem item : mFXList) {
            getPrice(item.getCurrency1(), item.getCurrency2(), context, 0, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    item.setPrice(Float.parseFloat(result));
                }
            });
        }
        for (final FXListItem item : mFXList) {
            getPrice(item.getCurrency1(), item.getCurrency2(), context, 1, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    item.setPreviousPrice(Float.parseFloat(result));
                    item.setIsUp();
                    listItemsLoaded++;
                }
            });
        }
    }

    public FXListItem getItem(int index) {
        return mFXList.get(index);
    }

    /*public CurrencyPairInfoData getDetailData() {
        return mDetailData;
    }*/

    public CurrencyPairInfoData getInfoData() {
        return mDetailData;
    }

    public int getItemsLoaded() {
        return listItemsLoaded;
    }

    public void resetItemsLoaded() {
        listItemsLoaded = 0;
    }


    public int getListSize() {
        return mFXList.size();
    }

    private void makeDummyList() {
        FXListItem item = new FXListItem("USD","SEK");
        mFXList.add(item);
        FXListItem item2 = new FXListItem("EUR","SEK");
        mFXList.add(item2);
        FXListItem item3 = new FXListItem("DKK","SEK");
        mFXList.add(item3);
        FXListItem item4 = new FXListItem("NOK","SEK");
        mFXList.add(item4);
        FXListItem item5 = new FXListItem("GBP","SEK");
        mFXList.add(item5);
        FXListItem item6 = new FXListItem("USD","JPY");
        mFXList.add(item6);
        FXListItem item8 = new FXListItem("EUR","JPY");
        mFXList.add(item8);
        FXListItem item9 = new FXListItem("GBP","USD");
        mFXList.add(item9);
    }

    // FX LIST DATA HANDLING

    /*public void saveList() {
        try {
            mSerializer.save(mFXList);
        } catch(Exception e) {
            Log.e("Error saving lists","", e);
        }
    }

    public void loadList() {
        try {
            mFXList = mSerializer.load();
        } catch (Exception e) {
            Log.e("Error loading lists: ", "", e);
        }
    }*/

    public void deleteFromList(int position) {
        mFXList.remove(position);
    }

    // DETAIL VIEW DATA

    public void getDetailViewData(String currency1, String currency2, Context context) {

        mDetailData = new CurrencyPairInfoData();

        mDetailData.setCurrency1(currency1);
        mDetailData.setCurrency2(currency2);

        // CURRENT
        getPrice(currency1, currency2, context, 0, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                mDetailData.setCurrentPrice(Double.parseDouble(result));
                mDetailData.setMonthNumber(Double.parseDouble(result), 0);
                mDetailData.confirmDataPoint();
            }
        });

        // PREVIOUS
        getPrice(currency1, currency2, context, 1, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                mDetailData.setPreviousPrice(Double.parseDouble(result));
                mDetailData.confirmDataPoint();
            }
        });

        // WEEK
        getPrice(currency1, currency2, context, 2, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                mDetailData.setWeekPrice(Double.parseDouble(result));
                mDetailData.confirmDataPoint();
            }
        });

        // MONTH
        getPrice(currency1, currency2, context, 3, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                mDetailData.setMonthPrice(Double.parseDouble(result));
                mDetailData.setMonthNumber(Double.parseDouble(result), 1);
                mDetailData.confirmDataPoint();
            }
        });

        // MONTHS FOR LINE CHART
        for (int i = 1; i<12; i++) {
            final int x = i;
            getPrice(currency1, currency2, context, 3 + i, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    mDetailData.setMonthNumber(Double.parseDouble(result), x);
                    mDetailData.confirmDataPoint();
                }
            });
        }

        // YEAR
        getPrice(currency1, currency2, context, 14, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                mDetailData.setYearPrice(Double.parseDouble(result));
                mDetailData.confirmDataPoint();
            }
        });
    }
}
