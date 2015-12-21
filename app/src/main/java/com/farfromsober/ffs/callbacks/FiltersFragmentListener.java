package com.farfromsober.ffs.callbacks;
import android.app.Fragment;

import com.farfromsober.ffs.model.Product;

import java.util.ArrayList;

/**
 * Created by joanbiscarri on 04/12/15.
 */
public interface FiltersFragmentListener {
    public void onProductFilter1Selected(Fragment f,ArrayList<String> selectedItems);
}
