package tk.mbird.whosthapp.model;

public class KeypadModel {
    private String mPrimaryValue;
    private String mSecondaryValue;

    public KeypadModel(String primaryKey, String secondaryKey) {
        this.mPrimaryValue = primaryKey;
        this.mSecondaryValue = secondaryKey;
    }

    public String getPrimaryValue() {
        return mPrimaryValue;
    }

    public String getSecondaryValue() {
        return mSecondaryValue;
    }
}
