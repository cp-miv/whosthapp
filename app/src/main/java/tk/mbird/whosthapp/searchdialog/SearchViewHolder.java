package tk.mbird.whosthapp.searchdialog;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    private View mBaseView;

    public SearchViewHolder(View view) {
        super(view);

        this.mBaseView = view;
    }

    public View getBaseView() {
        return this.mBaseView;
    }

    public <T> T getViewById(@IdRes int id) {
        return (T) this.mBaseView.findViewById(id);
    }

    public void clearAnimation(@IdRes int id) {
        this.mBaseView.findViewById(id).clearAnimation();
    }
}
