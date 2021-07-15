package tk.mbird.whosthapp.searchdialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mirrajabi.searchdialog.core.BaseFilter;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.OnPerformFilterListener;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import tk.mbird.whosthapp.R;

public abstract class SearchDialogCompat<T extends Searchable> extends BaseSearchDialogCompat<T> {

    private String mTitle;
    private String mSearchHint;
    private SearchResultListener<T> mSearchResultListener;

    private TextView mTxtTitle;
    private EditText mSearchBox;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private boolean mCancelOnTouchOutside = true;

    private Handler mHandler;

    public SearchDialogCompat(Context context, String title, String searchHint, @Nullable Filter filter, List<T> items, SearchResultListener<T> searchResultListener) {
        super(context, new ArrayList<T>(items), filter, null, null);
        this.init(title, searchHint, searchResultListener);
    }

    private void init(String title, String searchHint, SearchResultListener<T> searchResultListener) {
        mTitle = title;
        mSearchHint = searchHint;
        mSearchResultListener = searchResultListener;
        setFilterResultListener(new FilterResultListener<T>() {
            @Override
            public void onFilter(ArrayList<T> items) {

                SearchAdapter<T> adapter = (SearchAdapter<T>)getAdapter();
                adapter.setSearchTag(mSearchBox.getText().toString());
                adapter.setItems(items);
            }
        });
        mHandler = new Handler();
    }

    @Override
    protected void getView(View view) {
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        mTxtTitle = view.findViewById(R.id.txt_title);
        mSearchBox = view.findViewById(getSearchBoxId());
        mRecyclerView = view.findViewById(getRecyclerViewId());
        mProgressBar = view.findViewById(R.id.progress);
        mTxtTitle.setText(mTitle);
        mSearchBox.setHint(mSearchHint);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);
        view.findViewById(R.id.dummy_background)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mCancelOnTouchOutside) {
                            dismiss();
                        }
                    }
                });

        final SearchAdapter<T> adapter = this.createAdapter();
        adapter.setSearchResultListener(this.mSearchResultListener);
        adapter.setSearchDialog(this);
        this.setAdapter(adapter);
        this.mSearchBox.requestFocus();

        ((BaseFilter<T>) getFilter()).setOnPerformFilterListener(new OnPerformFilterListener() {
            @Override
            public void doBeforeFiltering() {
                setLoading(true);
            }

            @Override
            public void doAfterFiltering() {
                setLoading(false);
            }
        });
    }

    protected abstract SearchAdapter createAdapter();

    public SearchDialogCompat setSearchHint(String searchHint) {
        mSearchHint = searchHint;
        if (mSearchBox != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSearchBox.setHint(mSearchHint);
                }
            });
        }
        return this;
    }

    public void setLoading(final boolean isLoading) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mProgressBar != null) {
                    mRecyclerView.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
                }
                if (mRecyclerView != null) {
                    mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                }
            }
        });
    }

    public SearchDialogCompat setSearchResultListener(
            SearchResultListener<T> searchResultListener
    ) {
        mSearchResultListener = searchResultListener;
        return this;
    }

    @LayoutRes
    @Override
    protected int getLayoutResId() {
        return  ir.mirrajabi.searchdialog.R.layout.search_dialog_compat;
    }

    @IdRes
    @Override
    protected int getSearchBoxId() {
        return R.id.txt_search;
    }

    @IdRes
    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_items;
    }

    public EditText getSearchBox() {
        return mSearchBox;
    }

    public String getTitle() {
        return mTitle;
    }

    public SearchDialogCompat setTitle(String title) {
        mTitle = title;
        if (mTxtTitle != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTxtTitle.setText(mTitle);
                }
            });
        }
        return this;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public TextView getTitleTextView() {
        return mTxtTitle;
    }

    public boolean willCancelOnTouchOutside() {
        return mCancelOnTouchOutside;
    }

    public void setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
        mCancelOnTouchOutside = cancelOnTouchOutside;
    }
}
