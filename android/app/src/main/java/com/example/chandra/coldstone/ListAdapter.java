package com.example.chandra.coldstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Raghuveer on 2/10/2016.
 */
public class ListAdapter extends ArrayAdapter<Bill> {

    ArrayList<Bill> list;
    Context context;
    int resource;


    public ListAdapter(Context context, int resource, ArrayList<Bill> objects) {
        super(context, resource, objects);

        this.list = objects;
        this.resource = resource;
        this.context = context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        TextView weight =(TextView)convertView.findViewById(R.id.weight);
        weight.setText("Weight of the item: " +list.get(position).getWeight());
        TextView cost =(TextView)convertView.findViewById(R.id.cost);
        cost.setText("Cost of the item: "+ list.get(position).getPrice()+"");
        TextView date =(TextView)convertView.findViewById(R.id.date);
        date.setText("Date Purchase: " +list.get(position).getDate());

        return convertView;

    }
}
