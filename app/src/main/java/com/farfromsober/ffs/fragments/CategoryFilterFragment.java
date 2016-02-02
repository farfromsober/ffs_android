package com.farfromsober.ffs.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryFilterFragment extends Fragment {

    public static final String ARG_FILTER_VALUES = "com.farfromsober.ffs.fragments.CategoryFilterFragment.ARG_FILTER_VALUES";

    public static final String CATEGORY_KEY = "category";
    public static final String DISTANCE_KEY = "distance";
    public static final int NO_ITEM_SELECTED = -1;

    public WeakReference<FiltersFragmentListener> mListener;

    private HashMap<String, Integer> mFilterSelectedItems;

    @Bind(R.id.categoryList)
    ListView categoriesListView;
    @Bind(R.id.distanceList)
    ListView distanceListView;

    public static CategoryFilterFragment newInstance(HashMap<String, Integer> lastFilterSelectedItems) {
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
            mFilterSelectedItems = (HashMap<String, Integer>) getArguments().getSerializable(ARG_FILTER_VALUES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_filter, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filters, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save_filters: {
                if (mListener != null && mListener.get() != null) {
                    mListener.get().onProductFiltersSelected(mFilterSelectedItems);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        setCallbacks(context);
        super.onAttach(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        setCallbacks(activity);
        super.onAttach(activity);
    }

    private void setCallbacks(Context context) {
        try {
            mListener = new WeakReference<>((FiltersFragmentListener) getActivity());
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " must implement FiltersFragmentListener in Activity");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        final String[] categories = getResources().getStringArray(R.array.CategoryList);
        final String[] distances = getResources().getStringArray(R.array.DistanceList);

        categoriesListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, categories));
        distanceListView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, distances));

        ListUtils.setDynamicHeight(categoriesListView);
        ListUtils.setDynamicHeight(distanceListView);

        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {

                if (mFilterSelectedItems.containsKey(CATEGORY_KEY)) {
                    if (mFilterSelectedItems.get(CATEGORY_KEY) == position) {
                        categoriesListView.setItemChecked(position, false);
                        mFilterSelectedItems.remove(CATEGORY_KEY);
                    } else {
                        mFilterSelectedItems.put(CATEGORY_KEY, position);
                    }
                } else {
                    mFilterSelectedItems.put(CATEGORY_KEY, position);
                }
            }
        });

        distanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {

                if (mFilterSelectedItems.containsKey(DISTANCE_KEY)) {
                    if (mFilterSelectedItems.get(DISTANCE_KEY) == Integer.valueOf(distances[position])) {
                        distanceListView.setItemChecked(position, false);
                        mFilterSelectedItems.remove(DISTANCE_KEY);
                    } else {
                        mFilterSelectedItems.put(DISTANCE_KEY, Integer.valueOf(distances[position]));
                    }
                } else {
                    mFilterSelectedItems.put(DISTANCE_KEY, Integer.valueOf(distances[position]));
                }
            }
        });

        int categoryFilterSelectedIndex = NO_ITEM_SELECTED;
        int distanceFilterSelectedIndex = NO_ITEM_SELECTED;
        if (mFilterSelectedItems == null) {
            mFilterSelectedItems = new HashMap<>();
        }
        if (mFilterSelectedItems != null) {
            if (mFilterSelectedItems.containsKey(CATEGORY_KEY)) {
                categoryFilterSelectedIndex = mFilterSelectedItems.get(CATEGORY_KEY);
            }
            if (mFilterSelectedItems.containsKey(DISTANCE_KEY)) {
                distanceFilterSelectedIndex = mFilterSelectedItems.get(DISTANCE_KEY);
            }
        }
        if (categoryFilterSelectedIndex != NO_ITEM_SELECTED) {
            categoriesListView.setItemChecked(categoryFilterSelectedIndex, true);
        }
        if (distanceFilterSelectedIndex != NO_ITEM_SELECTED) {
            int index = Arrays.asList(distances).indexOf(String.valueOf(distanceFilterSelectedIndex));
            distanceListView.setItemChecked(index, true);
        }

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
