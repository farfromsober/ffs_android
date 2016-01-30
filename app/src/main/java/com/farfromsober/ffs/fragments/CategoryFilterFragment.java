package com.farfromsober.ffs.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;

import java.util.Arrays;
import java.util.HashMap;

/**
 */
public class CategoryFilterFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_FILTER_VALUES = "com.farfromsober.ffs.fragments.CategoryFilterFragment.ARG_FILTER_VALUES";

    public static final String CATEGORY_KEY = "category";
    public static final String DISTANCE_KEY = "distance";
    public static final int NO_ITEM_SELECTED = -1;

    Button button;
    public FiltersFragmentListener mListener;

    private ListView mListView1, mListView2;

    private HashMap<String, Integer> mFilterSelectedItems;
    private HashMap<String,Integer> mLastFilterSelectedItems;

    public static CategoryFilterFragment newInstance(HashMap<String,Integer> lastFilterSelectedItems) {
        CategoryFilterFragment fragment = new CategoryFilterFragment();

        // 2) Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_FILTER_VALUES, lastFilterSelectedItems);

        // 3) Asignamos los argumentos al fragment y lo devolvemos ya creado
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLastFilterSelectedItems = (HashMap<String,Integer>) getArguments().getSerializable(ARG_FILTER_VALUES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_filter, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findViewsById();

        final String[] catetogories = getResources().getStringArray(R.array.CategoryList);
        final String[] distances = getResources().getStringArray(R.array.DistanceList);

        mListView1 = (ListView) getView().findViewById(R.id.categoryList);
        mListView2 = (ListView) getView().findViewById(R.id.distanceList);

        mListView1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, catetogories));
        mListView2.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, distances));

        ListUtils.setDynamicHeight(mListView1);
        ListUtils.setDynamicHeight(mListView2);

        mFilterSelectedItems = new HashMap<>();
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {

                if (mFilterSelectedItems.containsKey(CATEGORY_KEY)) {
                    if (mFilterSelectedItems.get(CATEGORY_KEY) == position) {
                        mListView1.setItemChecked(position, false);
                        mFilterSelectedItems.remove(CATEGORY_KEY);
                    } else {
                        mFilterSelectedItems.put(CATEGORY_KEY, position);
                    }
                } else {
                    mFilterSelectedItems.put(CATEGORY_KEY, position);
                }
            }
        });


        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {

                if (mFilterSelectedItems.containsKey(DISTANCE_KEY)) {
                    if (mFilterSelectedItems.get(DISTANCE_KEY) == Integer.valueOf(distances[position])) {
                        mListView2.setItemChecked(position, false);
                        mFilterSelectedItems.remove(DISTANCE_KEY);
                    } else {
                        mFilterSelectedItems.put(DISTANCE_KEY, Integer.valueOf(distances[position]));
                    }
                } else {
                    mFilterSelectedItems.put(DISTANCE_KEY, Integer.valueOf(distances[position]));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                mListener.onProductFiltersSelected(mFilterSelectedItems);
            }
        });

        int categoryFilterSelectedIndex = NO_ITEM_SELECTED;
        int distanceFilterSelectedIndex = NO_ITEM_SELECTED;
        if (mLastFilterSelectedItems != null) {
            if (mLastFilterSelectedItems.containsKey(CATEGORY_KEY)) {
                categoryFilterSelectedIndex = mLastFilterSelectedItems.get(CATEGORY_KEY);
            }
            if (mLastFilterSelectedItems.containsKey(DISTANCE_KEY)) {
                distanceFilterSelectedIndex = mLastFilterSelectedItems.get(DISTANCE_KEY);
            }
        }
        if (categoryFilterSelectedIndex != NO_ITEM_SELECTED) {
            mListView1.setItemChecked(categoryFilterSelectedIndex, true);
        }
        if (distanceFilterSelectedIndex != NO_ITEM_SELECTED) {
            int index = Arrays.asList(distances).indexOf(String.valueOf(distanceFilterSelectedIndex));
            mListView2.setItemChecked(index, true);
        }
    }

    private void findViewsById() {

        button = (Button) getActivity().findViewById(R.id.filter1_submit);
    }


    @Override
    public void onClick(View v) {

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
