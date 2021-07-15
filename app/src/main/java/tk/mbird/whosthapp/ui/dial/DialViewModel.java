package tk.mbird.whosthapp.ui.dial;

import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import tk.mbird.whosthapp.model.CountryModel;
import tk.mbird.whosthapp.services.CountryService;
import tk.mbird.whosthapp.ui.BaseViewModel;

public class DialViewModel extends BaseViewModel<DialNavigator> {

    private static final String TAG = DialViewModel.class.getName();

    private MutableLiveData<List<CountryModel>> mCountries;
    private MutableLiveData<CountryModel> mSelectedCountry;
    private MutableLiveData<String> mPhoneNumber;

    private MutableLiveData<Integer> mEditingStart;
    private MutableLiveData<Integer> mEditingEnd;


    @Inject
    public DialViewModel() {
        this.mCountries = new MutableLiveData<>(new CountryService().getCountries());
        this.mSelectedCountry = new MutableLiveData<>(this.mCountries.getValue().get(0));

        this.mPhoneNumber = new MutableLiveData<>("");

        this.mEditingStart = new MutableLiveData<>(0);
        this.mEditingEnd = new MutableLiveData<>(0);
    }


    public LiveData<List<CountryModel>> getCountries() {
        return this.mCountries;
    }

    public LiveData<CountryModel> getSelectedCountry() {
        return this.mSelectedCountry;
    }

    public void setSelectedCountry(CountryModel country) {
        this.mSelectedCountry.setValue(country);
    }


    public LiveData<String> getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber.setValue(phoneNumber);
    }

    public void clearPhoneNumber() {
        this.setPhoneNumber("");
    }

    public void addPhoneNumberPart(String value) {

        if (TextUtils.isEmpty(value))
            return;

        final String phoneNumber = this.getPhoneNumber().getValue();

        int start = this.mEditingStart.getValue();
        int end = this.mEditingEnd.getValue();

        final String prefix = phoneNumber.substring(0, start);
        final String suffix = phoneNumber.substring(end);
        final String newPhoneNumber = prefix + value + suffix;

        this.setPhoneNumber(newPhoneNumber);
    }

    public void removePhoneNumberPart() {

        final String phoneNumber = this.getPhoneNumber().getValue();

        int start = this.mEditingStart.getValue();
        int end = this.mEditingEnd.getValue();

        if (start == end)
            start--;

        start = Math.max(start, 0);
        end = Math.min(end, phoneNumber.length());

        if (start == end)
            return;

        final String prefix = phoneNumber.substring(0, start);
        final String suffix = phoneNumber.substring(end);
        final String newPhoneNumber = prefix + suffix;

        this.setPhoneNumber(newPhoneNumber);
    }


    public LiveData<Integer> getEditingStart() {
        return mEditingStart;
    }

    public void setEditingStart(int editingStart) {
        mEditingStart.setValue(editingStart);
    }

    public LiveData<Integer> getEditingEnd() {
        return mEditingEnd;
    }

    public void setEditingEnd(int editingEnd) {
        mEditingEnd.setValue(editingEnd);
    }
}
