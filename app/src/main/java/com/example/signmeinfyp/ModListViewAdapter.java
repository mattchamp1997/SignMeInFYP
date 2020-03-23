package com.example.signmeinfyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModListViewAdapter extends ArrayAdapter
{
    List list = new ArrayList();

    public ModListViewAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        ModDetailsHolder modDetailsHolder;

        if(row==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent,false);
            modDetailsHolder = new ModDetailsHolder();
            modDetailsHolder.modID = (TextView)row.findViewById(R.id.tx_moduleID);
            modDetailsHolder.lecID = (TextView)row.findViewById(R.id.tx_lecID);
            modDetailsHolder.modName = (TextView)row.findViewById(R.id.tx_moduleName);
            modDetailsHolder.classList = (TextView)row.findViewById(R.id.tx_classList);

            row.setTag(modDetailsHolder);
        }
        else
        {
            modDetailsHolder = (ModDetailsHolder)row.getTag();
        }

        ModListViewDetails modListViewDetails = (ModListViewDetails)this.getItem(position);
        modDetailsHolder.modID.setText(modListViewDetails.getModuleID());
        modDetailsHolder.lecID.setText(modListViewDetails.getLecturerID());
        modDetailsHolder.modName.setText(modListViewDetails.getModuleName());
        modDetailsHolder.classList.setText(modListViewDetails.getClassList());

        return row;
    }

    static class ModDetailsHolder
    {
        TextView modID, lecID, modName, classList;
    }
}
