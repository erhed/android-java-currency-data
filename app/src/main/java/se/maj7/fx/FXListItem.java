package se.maj7.fx;

public class FXListItem {

    private String mCurrency1;
    private String mCurrency2;

    private float mPrice;

    private boolean mIsUp;

    public FXListItem(String currency1, String currency2, float current, float previous) {
        mCurrency1 = currency1;
        mCurrency2 = currency2;
        mPrice = current;

        if (current > previous) {
            mIsUp = true;
        } else {
            mIsUp = false;
        }
    }

    public String getCurrency1() {
        return mCurrency1;
    }

    public String getCurrency2() {
        return mCurrency2;
    }

    public float getPrice() {
        return mPrice;
    }

    public boolean getIsUp() {
        return mIsUp;
    }
}
