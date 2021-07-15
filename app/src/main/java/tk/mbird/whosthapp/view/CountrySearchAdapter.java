package tk.mbird.whosthapp.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

import ir.mirrajabi.searchdialog.StringsHelper;
import tk.mbird.whosthapp.R;
import tk.mbird.whosthapp.model.CountryModel;
import tk.mbird.whosthapp.searchdialog.SearchAdapter;
import tk.mbird.whosthapp.searchdialog.SearchAdapterViewBinder;
import tk.mbird.whosthapp.searchdialog.SearchViewHolder;

public class CountrySearchAdapter extends SearchAdapter<CountryModel> {

    public CountrySearchAdapter(Context context, int layout, @Nullable SearchAdapterViewBinder viewBinder, List items) {
        super(context, layout, viewBinder, items);
    }

    public CountrySearchAdapter(Context context, int layout, List items) {
        super(context, layout, items);
    }

    public CountrySearchAdapter(Context context, SearchAdapterViewBinder viewBinder, int layout, List items) {
        super(context, viewBinder, layout, items);
    }



    @Override
    protected void initializeViews(final CountryModel object, final SearchViewHolder holder, final int position) {
        ImageView imageView = holder.getViewById(R.id.countrysearch_item_icon);
        imageView.setImageResource(object.getImage());

        TextView textView = holder.getViewById(R.id.countrysearch_item_label);
        textView.setText(this.getSearchTag() != null && this.getHightlightPartsInCommon()
                ? StringsHelper.highlightLCS(object.getTitle(), this.getSearchTag(), Color.parseColor(this.getHighlightColor()))
                : object.getTitle());
    }
}
