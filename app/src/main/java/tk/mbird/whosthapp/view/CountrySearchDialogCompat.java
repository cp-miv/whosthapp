package tk.mbird.whosthapp.view;

import android.content.Context;
import android.widget.Filter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.searchdialog.core.SearchResultListener;
import tk.mbird.whosthapp.R;
import tk.mbird.whosthapp.model.CountryModel;
import tk.mbird.whosthapp.searchdialog.SearchAdapter;
import tk.mbird.whosthapp.searchdialog.SearchDialogCompat;

public class CountrySearchDialogCompat extends SearchDialogCompat<CountryModel> {

    public CountrySearchDialogCompat(Context context, String title, String searchHint, @Nullable Filter filter, List<CountryModel> items, SearchResultListener<CountryModel> searchResultListener) {
        super(context, title, searchHint, filter, items, searchResultListener);
    }

    @Override
    protected SearchAdapter createAdapter() {
        return new CountrySearchAdapter(this.getContext(), R.layout.countrysearch_item_icon_label, this.getItems());
    }
}
