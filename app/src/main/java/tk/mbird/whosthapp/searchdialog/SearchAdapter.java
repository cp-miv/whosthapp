package tk.mbird.whosthapp.searchdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public abstract class SearchAdapter<T extends Searchable> extends RecyclerView.Adapter<SearchViewHolder> {

    protected Context mContext;
    private int mLayout;
    private SearchAdapterViewBinder<T> mViewBinder;
    private List<T> mItems;

    private LayoutInflater mLayoutInflater;
    private SearchDialogCompat mSearchDialog;
    private SearchResultListener mSearchResultListener;

    private String mSearchTag;
    private String mHighlightColor;
    private boolean mHighlightPartsInCommon;


    public SearchAdapter(Context context, @LayoutRes int layout, @Nullable SearchAdapterViewBinder<T> viewBinder, List<T> items) {
        this.mContext = context;
        this.mLayout = layout;
        this.mViewBinder = viewBinder;
        this.mItems = items;

        this.mLayoutInflater = LayoutInflater.from(context);

        this.mHighlightPartsInCommon = true;
        this.mHighlightColor = "#FFED2E47";
    }

    public SearchAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context, layout, null, items);
    }

    public SearchAdapter(Context context, SearchAdapterViewBinder<T> viewBinder, @LayoutRes int layout, List<T> items) {
        this(context, layout, viewBinder, items);
    }



    public T getItem(int position) { return this.mItems.get(position); }

    @Override
    public int getItemCount() { return this.mItems.size(); }

    @Override
    public long getItemId(int position) { return position; }



    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = this.mLayoutInflater.inflate(mLayout, parent, false);
        SearchViewHolder viewHolder = new SearchViewHolder(convertView);
        convertView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {

        final T object = this.getItem(position);

        if (this.mViewBinder != null) {
            this.mViewBinder.bind(holder, object, position);
        }

        this.initializeViews(object, holder, position);

        final SearchResultListener searchResultListener = this.getSearchResultListener();

        if (searchResultListener != null) {
            final SearchDialogCompat searchDialog = this.getSearchDialog();

            holder.getBaseView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchResultListener.onSelected(getSearchDialog(), object, position);
                }
            });
        }
    }

    protected abstract void initializeViews(final T object, final SearchViewHolder holder, final int position);


    public SearchResultListener getSearchResultListener() { return this.mSearchResultListener; }
    public void setSearchResultListener(SearchResultListener searchResultListener) { this.mSearchResultListener = searchResultListener; }

    public List<T> getItems() { return this.mItems; }
    public void setItems(List<T> objects) { this.mItems = objects; this.notifyDataSetChanged(); }

    public String getSearchTag() { return this.mSearchTag; }
    public void setSearchTag(String searchTag) { this.mSearchTag = searchTag; }

    public SearchAdapterViewBinder<T> getViewBinder() { return this.mViewBinder; }
    public void setViewBinder(SearchAdapterViewBinder<T> viewBinder) { this.mViewBinder = viewBinder; }

    public boolean getHightlightPartsInCommon() { return this.mHighlightPartsInCommon; }
    public void setHighlightPartsInCommon(boolean highlightPartsInCommon) { this.mHighlightPartsInCommon = highlightPartsInCommon; }

    public String getHighlightColor() { return this.mHighlightColor; }
    public void setHighlightColor(String highlightColor) { this.mHighlightColor = highlightColor; }

    public SearchDialogCompat getSearchDialog() { return this.mSearchDialog; }
    public void setSearchDialog(SearchDialogCompat searchDialog) { this.mSearchDialog = searchDialog; }
}

