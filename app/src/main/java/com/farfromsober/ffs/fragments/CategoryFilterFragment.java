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

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.callbacks.FiltersFragmentListener;
import com.farfromsober.ffs.callbacks.ProductsFragmentListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
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
                        android.R.layout.simple_list_item_multiple_choice, catetogories);
                setListAdapter(adapter);
                getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        }

        private void findViewsById() {
                //listView = (ListView) findViewById(R.id.list);
                button = (Button) getActivity().findViewById(R.id.filter1_submit);
        }

        /*@Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        }*/
        @Override
        public void onClick(View v) {
                long[] checked = getListView().getCheckedItemIds();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.length; i++) {
                        selectedItems.add(String.valueOf(checked[i]));
                }

                mListener.onProductFilter1Selected(selectedItems);
                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        }

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p/>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
        public interface OnFragmentInteractionListener {
                // TODO: Update argument type and name
                void onFragmentInteraction(Uri uri);
        }
}
