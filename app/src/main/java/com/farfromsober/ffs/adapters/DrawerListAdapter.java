package com.farfromsober.ffs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.farfromsober.ffs.R;
import com.farfromsober.ffs.model.DrawerMenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerListAdapter extends ArrayAdapter {

    Context mContext;

    public DrawerListAdapter(Context context, List objects) {
        super(context, 0, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerListHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_navigation_drawer_menu_list, null);

            holder = new DrawerListHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (DrawerListHolder) convertView.getTag();
        }

        DrawerMenuItem item = (DrawerMenuItem) getItem(position);
        holder.title.setText(item.getTitle());

        ListView drawerMenuListView = (ListView) parent;
        if (drawerMenuListView.isItemChecked(position)) {
            holder.icon.setImageResource(item.getIconOnId());
        } else {
            holder.icon.setImageResource(item.getIconOffId());
        }

        return convertView;
    }

    static class DrawerListHolder{
        @Bind(R.id.menu_item_title) TextView title;
        @Bind(R.id.menu_item_icon) ImageView icon;

        public DrawerListHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
