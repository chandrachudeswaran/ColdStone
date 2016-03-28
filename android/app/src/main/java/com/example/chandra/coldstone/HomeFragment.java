package com.example.chandra.coldstone;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.LinkedHashSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView weight,noTrans;
    TextView total;
    TextView unitprice;
    ImageView image;
    TextView weightlabel;
    TextView totallabel;
    TextView pricelabel;
    TextView toppings;
    LinearLayout layout;
    Button accept;
    Button reject;
    LinearLayout buttonsLayout;
    CardView cardView;
    boolean displayTopping;
    boolean transaction;

    Bill billinfo;
    CheckBillInterface checkBillInterface;

    public HomeFragment() {
        displayTopping=true;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(transaction){
            menu.clear();
            menu.add(0, 4, Menu.NONE, "Re-Select toppings");
            menu.add(0, R.id.history, Menu.NONE, "History");
            menu.add(0,R.id.logout,Menu.NONE,"Log out");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(id==R.id.history){
            checkBillInterface.showHistoryFragment();
        }
        else if(id==R.id.logout){
                checkBillInterface.doLogout();
        }else if(id==4){
            displayTopping=true;
            displayBill(billinfo);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        cardView = (CardView) view.findViewById(R.id.card_view);
        weight =(TextView) view.findViewById(R.id.weight);
        total = (TextView) view.findViewById(R.id.total);
        unitprice = (TextView ) view.findViewById(R.id.pricepergram);
        image = (ImageView) view.findViewById(R.id.image);
        weightlabel = (TextView)view.findViewById(R.id.weightlabel);
        toppings = (TextView) view.findViewById(R.id.toppingsname);
        totallabel = (TextView)view.findViewById(R.id.totallabel);
        pricelabel = (TextView)view.findViewById(R.id.pricelabel);
        layout=(LinearLayout)view.findViewById(R.id.parentchild);

        accept = (Button)view.findViewById(R.id.accept);
        reject = (Button) view.findViewById(R.id.reject);
        noTrans = (TextView) view.findViewById(R.id.noTrans);
        buttonsLayout = (LinearLayout) view.findViewById(R.id.buttonsLayout);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBillInterface.doUserActionOnBill(true);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBillInterface.doUserActionOnBill(false);
            }
        });
        return view;


    }

    public interface CheckBillInterface{
        public void getBillForUser();
        public void doUserActionOnBill(boolean check);
        public void showHistoryFragment();
        public void doLogout();
        public void showToppingsFragment();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            checkBillInterface = (CheckBillInterface) activity;
            activity.setTitle("EasyPay - Home");
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkBillInterface.getBillForUser();
    }



    public void displayBill(Bill bill) {
        billinfo=bill;
        if (bill != null && bill.getSelectedToppings()!=null) {
            transaction=true;
            weight.setText(bill.getWeight());
            total.setText("$" + Double.toString(bill.getPrice()+ bill.getToppingsPrice()));
            unitprice.setText("10 cents");
            toppings.setText(" " + ToppingsUtility.convertSetToString(bill.getSelectedToppings()));
            Picasso.with(this.getContext()).load("https://www.coldstonecreamery.com/assets/img/" +
                            "products/signaturecreations/signaturecreations.jpg").into(image);
            if(displayTopping){
                displayTopping=displayToppings();
            }
        }else if(bill==null){
            transaction=false;
            getActivity().invalidateOptionsMenu();
            cardView.setVisibility(View.INVISIBLE);
            buttonsLayout.setVisibility(View.INVISIBLE);
            noTrans.setVisibility(View.VISIBLE);
        }else{
            transaction=true;
            displayToppings();


        }
    }


    public boolean displayToppings(){
        checkBillInterface.showToppingsFragment();
        return false;
    }





}
