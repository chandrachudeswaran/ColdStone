package com.example.chandra.coldstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Raghuveer on 3/24/2016.
 */
public class GridAdapter extends BaseAdapter {


    Context context;
    int resource;
    List<Toppings> list;
    float price;
    LinkedHashSet<String> toppingsList;

    public GridAdapter(Context context, int resource, List<Toppings> list) {

        this.context = context;
        this.resource = resource;
        this.list = list;
        price = (float) 0.0;
        toppingsList = new LinkedHashSet<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        final ImageView select = (ImageView) convertView.findViewById(R.id.select);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.parent);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select.getVisibility() == View.VISIBLE) {
                    toppingsList.remove(list.get(position).getName());
                    price -= list.get(position).getPrice();
                    select.setVisibility(View.INVISIBLE);
                } else {
                    select.setVisibility(View.VISIBLE);
                    toppingsList.add(list.get(position).getName());
                    price += list.get(position).getPrice();
                }
            }
        });

        ImageView imageView = (ImageView) convertView.findViewById(R.id.toppingimageid);
        TextView toppingName = (TextView) convertView.findViewById(R.id.toppingnameid);
        TextView toppingRate = (TextView) convertView.findViewById(R.id.toppingrateid);

        Picasso.with(this.context).load(list.get(position).getUrl()).into(imageView);
        toppingName.setText(" " + list.get(position).getName());
        toppingRate.setText(" $ " + String.valueOf(list.get(position).getPrice()));

        return convertView;

    }


    public float getToppingsRate() {
        return price;
    }

    public LinkedHashSet<String> getSelectedToppingList() {
        return toppingsList;
    }
}
