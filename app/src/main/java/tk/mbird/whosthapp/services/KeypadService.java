package tk.mbird.whosthapp.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tk.mbird.whosthapp.R;
import tk.mbird.whosthapp.model.CountryModel;
import tk.mbird.whosthapp.model.KeypadModel;

public class KeypadService {

    private List<KeypadModel> mKeypads;

    public KeypadService() {
    }

    public List<KeypadModel> getKeypads() {

        if (this.mKeypads == null) {
            this.mKeypads = new ArrayList<>();
            this.mKeypads.addAll(Arrays.asList(
                    new KeypadModel("1", null),
                    new KeypadModel("2", null),
                    new KeypadModel("3", null),
                    new KeypadModel("4", null),
                    new KeypadModel("5", null),
                    new KeypadModel("6", null),
                    new KeypadModel("7", null),
                    new KeypadModel("8", null),
                    new KeypadModel("9", null),
                    new KeypadModel("*", null),
                    new KeypadModel("0", "+"),
                    new KeypadModel("#", null)
            ));
        }

        return this.mKeypads;
    }
}
