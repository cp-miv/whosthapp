package tk.mbird.whosthapp.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tk.mbird.whosthapp.R;
import tk.mbird.whosthapp.model.CountryModel;

public class CountryService {

    private List<CountryModel> mCountries;

    public CountryService() {
    }

    public List<CountryModel> getCountries() {

        if (this.mCountries == null) {
            this.mCountries = new ArrayList<>();
            this.mCountries.addAll(Arrays.asList(
                    new CountryModel("Suisse", "CH", "CHE", "+41", R.drawable.flag_che),
                    new CountryModel("Schweiz", "CH", "CHE", "+41", R.drawable.flag_che),
                    new CountryModel("Svizzera", "CH", "CHE", "+41", R.drawable.flag_che),
                    new CountryModel("Switzerland", "CH", "CHE", "+41", R.drawable.flag_che),
                    new CountryModel("France", "FR", "FRA", "+33", R.drawable.flag_fra),
                    new CountryModel("Frankreich", "FR", "FRA", "+33", R.drawable.flag_fra),
                    new CountryModel("Francia", "FR", "FRA", "+33", R.drawable.flag_fra),
                    /* Same as French */ //new CountryModel("France", "FR", "FRA", "+33", R.drawable.flag_fra),
                    new CountryModel("Allemagne", "DE", "DEU", "+49", R.drawable.flag_deu),
                    new CountryModel("Deutschland", "DE", "DEU", "+49", R.drawable.flag_deu),
                    new CountryModel("Germania", "DE", "DEU", "+49", R.drawable.flag_deu),
                    new CountryModel("Germany", "DE", "DEU", "+49", R.drawable.flag_deu),
                    new CountryModel("Italie", "IT", "ITA", "+39", R.drawable.flag_ita),
                    new CountryModel("Italien", "IT", "ITA", "+39", R.drawable.flag_ita),
                    new CountryModel("Italia", "IT", "ITA", "+39", R.drawable.flag_ita),
                    new CountryModel("Italy", "IT", "ITA", "+39", R.drawable.flag_ita)
            ));
        }

        return this.mCountries;
    }
}
