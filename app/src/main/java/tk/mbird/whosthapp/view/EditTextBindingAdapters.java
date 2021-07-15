package tk.mbird.whosthapp.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;

import org.w3c.dom.Text;

public class EditTextBindingAdapters {

    @SuppressLint("RestrictedApi")
    @BindingAdapter("android:text")
    public static void setText(EditText view, String oldText, String newText) {

        String currentText = view.getText().toString();

        if (TextUtils.equals(currentText, newText))
            return;

        int oldTextLength = oldText != null ? oldText.length() : 0;
        int newTextLength = newText != null ? newText.length() : 0;

        int deltaLength = newTextLength - oldTextLength;

        int end = view.getSelectionEnd();
        int newStart = end + deltaLength;
        newStart = Math.max(0, newStart);
        newStart = Math.min(newStart, newTextLength);

        TextViewBindingAdapter.setText(view, newText);
        view.setSelection(newStart);
    }
}
