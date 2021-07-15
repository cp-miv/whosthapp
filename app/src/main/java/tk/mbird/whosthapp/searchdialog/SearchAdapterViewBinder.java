package tk.mbird.whosthapp.searchdialog;

public interface SearchAdapterViewBinder<T> {

    void bind(SearchViewHolder holder, T item, int position);
}
