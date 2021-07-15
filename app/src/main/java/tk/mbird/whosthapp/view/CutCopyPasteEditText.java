package tk.mbird.whosthapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.lifecycle.LiveData;

public class CutCopyPasteEditText extends androidx.appcompat.widget.AppCompatEditText {

    public CutCopyPasteEditText(Context context) {
        super(context);
    }

    public CutCopyPasteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CutCopyPasteEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {

        boolean consumed = super.onTextContextMenuItem(id);

        switch (id) {
            case android.R.id.cut:
                onCut();
                break;
            case android.R.id.copy:
                onCopy();
                break;
            case android.R.id.paste:
                onPaste();
        }

        return consumed;
    }

    public void setSelectionStart(int start) {
        this.setSelection(start, this.getSelectionEnd());
    }

    public void setSelectionEnd(int stop) {
        this.setSelection(this.getSelectionStart(), stop);
    }

    public interface OnSelectionChangeListener {
        void onSelectionChanged(EditText view);
    }

    private OnSelectionChangeListener mOnSelectionChangeListener;

    public void setOnSelectionChangeListener(OnSelectionChangeListener listener) {
        this.mOnSelectionChangeListener = listener;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if (this.mOnSelectionChangeListener != null)
            this.mOnSelectionChangeListener.onSelectionChanged(this);
    }


    public interface OnCutListener {
        void onCut(EditText view);
    }

    private OnCutListener mOnCutListener;

    public void setOnCutListener(OnCutListener listener) {
        mOnCutListener = listener;
    }

    public void onCut() {
        if (mOnCutListener != null)
            mOnCutListener.onCut(this);
    }


    public interface OnCopyListener {
        void onCopy(EditText view);
    }

    private OnCopyListener mOnCopyListener;

    public void setOnCopyListener(OnCopyListener listener) {
        mOnCopyListener = listener;
    }

    public void onCopy() {
        if (mOnCopyListener != null)
            mOnCopyListener.onCopy(this);
    }


    public interface OnPasteListener {
        void onPaste(EditText view);
    }

    private OnPasteListener mOnPasteListener;

    public void setOnPasteListener(OnPasteListener listener) {
        mOnPasteListener = listener;
    }

    public void onPaste() {
        if (mOnPasteListener != null)
            mOnPasteListener.onPaste(this);
    }
}
