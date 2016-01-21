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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class CategoryFilterFragment extends Fragment implements View.OnClickListener {

        Button button;
        public FiltersFragmentListener mListener;

        private ListView mListView1, mListView2;
        private Fragment myFragment;

        private HashMap<String,Integer> itemSelected;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_category_filter, container, false);
                return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
            myFragment = this;

                findViewsById();

            String[] catetogories = getResources().getStringArray(R.array.CategoryList);
            String[] distances = getResources().getStringArray(R.array.DistanceList);

            mListView1 = (ListView)getView().findViewById(R.id.categoryList);
            mListView2 = (ListView)getView().findViewById(R.id.distanceList);

            mListView1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, catetogories));
            mListView2.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, distances));

            ListUtils.setDynamicHeight(mListView1);
            ListUtils.setDynamicHeight(mListView2);

            itemSelected = new HashMap<String,Integer>();
            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long id) {

                    ArrayList<String> selectedItems = new ArrayList<String>();

                    //ListView v = mListView1;
                    //v.setItemChecked(position, true);

                    //CheckedTextView check = (CheckedTextView) arg1;
                    //check.setChecked(!check.isChecked());

                    selectedItems.add(String.valueOf(position));

                    //Toast.makeText(getActivity(), "Selected: " + selectedItems.toString(), Toast.LENGTH_SHORT).show();
                    //mListener.onProductFilter∫1Selected(myFragment, selectedItems);
                    itemSelected.put("category",position);
                }
            });


            mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long id) {
                    ArrayList<String> selectedItems = new ArrayList<String>();

                    selectedItems.add(String.valueOf(position));
                    //Toast.makeText(getActivity(), "Selected: " + selectedItems.toString(), Toast.LENGTH_SHORT).show();
                    //mListener.onProductFilter1Selected(myFragment, selectedItems);
                    itemSelected.put("distance", (Integer) arg0.getItemAtPosition(position));
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    mListener.onProductFilter1Selected(myFragment, itemSelected);
                }
            });

            //ListUtils.setDynamicHeight(mListView1);
            //ListUtils.setDynamicHeight(mListView2);

            /*

                button.setOnClickListener(this);

                String[] catetogories = getResources().getStringArray(R.array.CategoryList);
                String[] distances = getResources().getStringArray(R.array.DistanceList);

                adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_checked, catetogories);
                setListAdapter(adapter);
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                adapterDistances = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_checked, distances);
                setListAdapter(adapterDistances);
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
 */
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
/*
            SparseBooleanArray checkedCategory = mListView1.getCheckedItemPositions();
            SparseBooleanArray checkedDistance = mListView2.getCheckedItemPositions();
            ArrayList<String> selectedItems = new ArrayList<String>();
            for (int i = 0; i < checkedCategory.size(); i++) {
                int position = checkedCategory.keyAt(i);
                // Add if it is checked i.e.) == TRUE!
                if (checkedCategory.valueAt(i))
                    selectedItems.add(String.valueOf(position));
            }
            //Toast.makeText(getActivity(), "Selected: " + selectedItems.toString(), Toast.LENGTH_SHORT).show();
            mListener.onProductFilter1Selected(this,selectedItems);
*/
        }


}

class ListUtils {
    public static void setDynamicHeight(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            // when adapter is null
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }
}
