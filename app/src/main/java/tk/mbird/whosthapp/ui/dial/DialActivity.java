package tk.mbird.whosthapp.ui.dial;

import androidx.databinding.library.baseAdapters.BR;

import android.app.ActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnLongClick;
import tk.mbird.whosthapp.R;
import tk.mbird.whosthapp.databinding.ActivityDialBinding;
import tk.mbird.whosthapp.ui.BaseActivity;
import tk.mbird.whosthapp.view.CountrySearchDialogCompat;
import tk.mbird.whosthapp.services.CountryService;
import tk.mbird.whosthapp.model.KeypadModel;
import tk.mbird.whosthapp.services.KeypadService;
import tk.mbird.whosthapp.view.CutCopyPasteEditText;


public class DialActivity extends BaseActivity<DialViewModel, ActivityDialBinding> implements DialNavigator {

    private static final String TAG = "DialActivity";

    @BindView(R.id.txtPhoneNumber)
    CutCopyPasteEditText mTxtPhoneNumber;

    @BindViews({R.id.btnDial00, R.id.btnDial01, R.id.btnDial02, R.id.btnDial03, R.id.btnDial04, R.id.btnDial05, R.id.btnDial06, R.id.btnDial07, R.id.btnDial08, R.id.btnDial09, R.id.btnDial10, R.id.btnDial11})
    List<Button> mBtnDials;

    private KeypadService keypadService;

    public DialActivity() {
        super(R.layout.activity_dial, BR.viewModel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        this.initTxtPhoneNumber();
        this.initKeypads();
    }


    private void initTxtPhoneNumber() {
        this.mTxtPhoneNumber.setRawInputType(InputType.TYPE_CLASS_TEXT);
        this.mTxtPhoneNumber.setShowSoftInputOnFocus(false);
        this.mTxtPhoneNumber.setTextIsSelectable(true);

        this.mTxtPhoneNumber.setOnCutListener(v -> getActivityViewModel().setPhoneNumber(v.getText().toString()));
        this.mTxtPhoneNumber.setOnPasteListener(v -> getActivityViewModel().setPhoneNumber(v.getText().toString()));

        getActivityViewModel().getEditingStart().observe(this, integer -> mTxtPhoneNumber.setSelectionStart(integer));

        getActivityViewModel().getEditingEnd().observe(this, integer -> mTxtPhoneNumber.setSelectionEnd(integer));

        this.mTxtPhoneNumber.setOnSelectionChangeListener(view -> {
            getActivityViewModel().setEditingStart(view.getSelectionStart());
            getActivityViewModel().setEditingEnd(view.getSelectionEnd());
        });

        this.mTxtPhoneNumber.setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() != KeyEvent.ACTION_DOWN) {
                return true;
            }

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                DialActivity.this.finish();
            }
            else if (Arrays.asList(KeyEvent.KEYCODE_DEL).contains(keyCode)) {
                getActivityViewModel().removePhoneNumberPart();
            }
            else {
                String key = String.valueOf((char) event.getUnicodeChar());
                getActivityViewModel().addPhoneNumberPart(key);
            }

            return true;
        });
    }

    private void initKeypads() {

        this.keypadService = new KeypadService();

        List<KeypadModel> keypads = this.keypadService.getKeypads();

        Iterator<Button> btnDialIterator = this.mBtnDials.iterator();
        Iterator<KeypadModel> keypadsIterator = keypads.iterator();

        while (btnDialIterator.hasNext() && keypadsIterator.hasNext()) {
            KeypadModel dialControl = keypadsIterator.next();
            Button btnDial = btnDialIterator.next();
            btnDial.setTag(dialControl);
            btnDial.setText(dialControl.getPrimaryValue());
        }
    }


    @OnClick({R.id.btnDial00, R.id.btnDial01, R.id.btnDial02, R.id.btnDial03, R.id.btnDial04, R.id.btnDial05, R.id.btnDial06, R.id.btnDial07, R.id.btnDial08, R.id.btnDial09, R.id.btnDial10, R.id.btnDial11})
    public void onBtnDialClick(View v) {
        this.getActivityViewModel().addPhoneNumberPart(((KeypadModel) v.getTag()).getPrimaryValue());
    }

    @OnLongClick({R.id.btnDial00, R.id.btnDial01, R.id.btnDial02, R.id.btnDial03, R.id.btnDial04, R.id.btnDial05, R.id.btnDial06, R.id.btnDial07, R.id.btnDial08, R.id.btnDial09, R.id.btnDial10, R.id.btnDial11})
    public void onBtnDialLongClick(View v) {
        this.getActivityViewModel().addPhoneNumberPart(((KeypadModel) v.getTag()).getSecondaryValue());
    }


    @OnClick(R.id.btnBackspace)
    public void onBtnBackspaceClick() {
        this.getActivityViewModel().removePhoneNumberPart();
    }

    @OnLongClick(R.id.btnBackspace)
    public void onBtnBackspaceLongClick() {
        this.getActivityViewModel().clearPhoneNumber();
    }


    @OnClick(R.id.btnCountryPrefix)
    public void onBtnCountryPrefixClick() {
        new CountrySearchDialogCompat(this, "Pays", "Nom ou indicatif téléphonique", null,
                new CountryService().getCountries(),
                (dialog, item, position) -> {
                    this.getActivityViewModel().setSelectedCountry(item);
                    dialog.dismiss();
                }).show();
    }


    @OnClick(R.id.btnCall)
    public void onBtnCallClick() {
        String countryPrefix = this.getActivityViewModel().getSelectedCountry().getValue().getPhonePrefix();
        String phoneNumber = this.getActivityViewModel().getPhoneNumber().getValue();

        String countryPrefixUrl = countryPrefix.replaceFirst("^\\+", "");
        String phoneNumberUrl = phoneNumber.replaceFirst("^(" + Pattern.quote(countryPrefix) + ")?0*", "");

        Uri uri = Uri.parse("whatsapp://send/?phone=" + countryPrefixUrl + phoneNumberUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(this.getPackageManager()) == null) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show();
            return;
        }

        this.startActivity(intent);
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
