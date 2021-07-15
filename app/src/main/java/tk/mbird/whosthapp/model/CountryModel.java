package tk.mbird.whosthapp.model;

import ir.mirrajabi.searchdialog.core.Searchable;

public class CountryModel implements Searchable {

    protected String mName;
    protected String mISO2Code;
    protected String mISO3Code;
    protected String mPhonePrefix;
    protected int mImage;

    public CountryModel(String name, String ISO2Code, String ISO3Code, String phonePrefix, int image) {
        this.mName = name;
        this.mISO2Code = ISO2Code;
        this.mISO3Code = ISO3Code;
        this.mPhonePrefix = phonePrefix;
        this.mImage = image;
    }

    public String getName() {
        return mName;
    }

    public String getISO2Code() {
        return mISO2Code;
    }

    public String getISO3Code() {
        return mISO3Code;
    }

    public String getPhonePrefix() {
        return mPhonePrefix;
    }

    public int getImage() {
        return mImage;
    }

    @Override
    public String getTitle() {
        return this.mName + " (" + this.mPhonePrefix + ")"  ;
    }
}
