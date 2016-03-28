package com.example.chandra.coldstone;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.LinkedHashSet;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToppingsFragment extends Fragment {

    GridView gridView;
    ToppingsInterface toppingsInterface;
    Button checkOut;
    GridAdapter adapter;

    public ToppingsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toppings, container, false);
        gridView = (GridView) view.findViewById(R.id.gridviewid);
        checkOut = (Button) view.findViewById(R.id.checkOut);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toppingsInterface.doCheckout(adapter.getToppingsRate(), adapter.getSelectedToppingList());
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            toppingsInterface = (ToppingsInterface) activity;
            activity.setTitle("EasyPay - Select Toppings");
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }

    public void displayToppingsList(List<Toppings> list) {
        adapter = new GridAdapter(getActivity(), R.layout.gridrow, list);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public interface ToppingsInterface {
        public void getToppings();
        public void doCheckout(float price,LinkedHashSet<String> toppingsList);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toppingsInterface.getToppings();
    }


}
