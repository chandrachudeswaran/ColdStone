package com.example.chandra.coldstone;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    ListView listview;
    HistoryInterface historyInterface;
    public HistoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_history, container, false);
        listview =(ListView)view.findViewById(R.id.listview);
        historyInterface.getHistoryBillList();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            historyInterface = (HistoryInterface) activity;
            activity.setTitle("EasyPay - History");
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }

    public interface HistoryInterface{
        public void getHistoryBillList();
    }

    public void displayHistory(ArrayList<Bill> list){
        ListAdapter adapter = new ListAdapter(getActivity(),R.layout.listrow,list);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
