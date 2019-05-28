package se.maj7.fx;

public class FXListItem {

    private String mCurrency1;
    private String mCurrency2;

    private float mPrice;
    private float mPreviousPrice;

    private boolean mIsUp;

    public FXListItem(String currency1, String currency2) {
        mCurrency1 = currency1;
        mCurrency2 = currency2;
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

    public void setPrice(float price) {
        mPrice = price;
    }

    public void setPreviousPrice(float price) {
        mPreviousPrice = price;
    }

    public void setIsUp() {
        if (mPrice > mPreviousPrice) {
            mIsUp = true;
        } else {
            mIsUp = false;
        }
    }
}
