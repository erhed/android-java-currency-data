package se.maj7.fx;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;

public class CurrencyPairInfoData {

    private String mCurrency1;
    private String mCurrency2;

    private double mCurrentPrice;
    private double mPreviousPrice;
    private double mWeekPrice;
    private double mMonthPrice;
    private double mYearPrice;

    private double[] mMonths = new double[12];

    private int dataPointsFetched;

    public CurrencyPairInfoData() {
        dataPointsFetched = 0;
    }

    // GET

    public String getCurrency1() {
        return mCurrency1;
    }

    public String getCurrency2() {
        return mCurrency2;
    }

    public double getCurrentPrice() {
        return mCurrentPrice;
    }

    public double[] getPricesForBars() {
        double[] prices = {
                mCurrentPrice,
                mPreviousPrice,
                mWeekPrice,
                mMonths[1],
                mYearPrice
        };
        return prices;
    }

    public double[] getPricesForLineChart() {
        return mMonths;
    }

    public String[] getPercentagesForBars() {
        double percentDay = (mCurrentPrice > mPreviousPrice) ? ((mCurrentPrice / mPreviousPrice) - 1) * 100 : (1 - (mCurrentPrice / mPreviousPrice)) * -100;
        double percentWeek = (mCurrentPrice > mWeekPrice) ? ((mCurrentPrice / mWeekPrice) - 1) * 100 : (1 - (mCurrentPrice / mWeekPrice)) * -100;
        double percentMonth = (mCurrentPrice > mMonths[1]) ? ((mCurrentPrice / mMonths[1]) - 1) * 100 : (1 - (mCurrentPrice / mMonths[1])) * -100;
        double percentYear = (mCurrentPrice > mYearPrice) ? ((mCurrentPrice / mYearPrice) - 1) * 100 : (1 - (mCurrentPrice / mYearPrice)) * -100;

        String[] percentages = {
                String.format("%.1f", percentDay),
                String.format("%.1f", percentWeek),
                String.format("%.1f", percentMonth),
                String.format("%.1f", percentYear)
        };

        return percentages;
    }

    public int getConfirmedDataPoints() {
        return dataPointsFetched;
    }

    // SET

    public void setCurrency1(String currency) {
        mCurrency1 = currency;
    }

    public void setCurrency2(String currency) {
        mCurrency2 = currency;
    }

    public void setCurrentPrice(double price) {
        mCurrentPrice = price;
    }

    public void setPreviousPrice(double price) {
        mPreviousPrice = price;
    }

    public void setWeekPrice(double price) {
        mWeekPrice = price;
    }

    public void setMonthPrice(double price) {
        mMonthPrice = price;
    }

    public void setYearPrice(double price) {
        mYearPrice = price;
    }

    public void setMonthNumber(double price, int month) {
        mMonths[month] = price;
    }

    public void confirmDataPoint() {
        dataPointsFetched++;
        Log.i("CONFIRMED", String.valueOf(dataPointsFetched));
    }

    public void resetConfirmedDataPoints() {
        dataPointsFetched = 0;
    }

    public void resetPrices() {

    }

}
