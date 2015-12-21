package com.farfromsober.ffs.fragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;

import java.util.ArrayList;

/**
 */
public class CategoryFilterFragment extends ListFragment implements View.OnClickListener {

        Button button;
        ArrayAdapter adapter;
        public FiltersFragmentListener mListener;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_category_filter, container, false);
                return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);

                findViewsById();

                button.setOnClickListener(this);

                String[] catetogories = getResources().getStringArray(R.array.CategoryList);
                adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_checked, catetogories);
                setListAdapter(adapter);
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        }

        private void findViewsById() {

                button = (Button) getActivity().findViewById(R.id.filter1_submit);
        }

        /*@Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        }*/
        @Override
        public void onClick(View v) {
            SparseBooleanArray checked = getListView().getCheckedItemPositions();
            ArrayList<String> selectedItems = new ArrayList<String>();
            for (int i = 0; i < checked.size(); i++) {
                int position = checked.keyAt(i);
                // Add if it is checked i.e.) == TRUE!
                if (checked.valueAt(i))
                    selectedItems.add(String.valueOf(position));
            }
            //Toast.makeText(getActivity(), "Selected: " + selectedItems.toString(), Toast.LENGTH_SHORT).show();
            mListener.onProductFilter1Selected(this,selectedItems);

        }


}
