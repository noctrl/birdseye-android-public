package com.noctrl.birdeye.release.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.noctrl.birdeye.release.R;

import java.util.List;

public class LotsAdapter extends ArrayAdapter<Lot> {

    public static final String LOG_TAG = "LotsAdapter";

    private Context context;
    private int layoutResourceId;
//    private Lot data[] = null;
    private List<Lot> data;


//    public LotsAdapter(Context context, int layoutResourceId, Lot data[]) {
    public LotsAdapter(Context context, int layoutResourceId, List<Lot> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LotsHolder holder;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(this.layoutResourceId, parent, false);

            holder = new LotsHolder();
            holder.nameTextView = (TextView)row.findViewById(R.id.lotNameTextView);
            holder.spacesAvailableTextView = (TextView)row.findViewById(R.id.spacesRemainingTextView);
            holder.spacesTotalTextView = (TextView)row.findViewById(R.id.totalSpacesTextView);

            row.setTag(holder);
        } else {
            holder = (LotsHolder)row.getTag();
        }

//        Lot lot = data[position];
        Lot lot = data.get(position);
        holder.nameTextView.setText(lot.name);
        holder.spacesAvailableTextView.setText(""+lot.spacesAvailable);
        holder.spacesTotalTextView.setText(""+lot.spacesTotal);

        return row;
    }

    static class LotsHolder {
        TextView nameTextView;
        TextView spacesAvailableTextView;
        TextView spacesTotalTextView;
    }
}

