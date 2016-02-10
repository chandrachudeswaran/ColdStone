package com.example.chandra.coldstone;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView weight;
    TextView total;
    TextView unitprice;
    ImageView image;
    TextView weightlabel;
    TextView totallabel;
    TextView pricelabel;
    LinearLayout layout;
    Button accept;
    Button reject;

    CheckBillInterface checkBillInterface;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
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

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        weight =(TextView) view.findViewById(R.id.weight);
        total = (TextView) view.findViewById(R.id.total);
        unitprice = (TextView ) view.findViewById(R.id.pricepergram);
        image = (ImageView) view.findViewById(R.id.image);
        weightlabel = (TextView)view.findViewById(R.id.weightlabel);
        totallabel = (TextView)view.findViewById(R.id.totallabel);
        pricelabel = (TextView)view.findViewById(R.id.pricelabel);
        layout=(LinearLayout)view.findViewById(R.id.parentchild);
        accept = (Button)view.findViewById(R.id.accept);
        reject = (Button) view.findViewById(R.id.reject);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBillInterface.checkResponse(true);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBillInterface.checkResponse(false);
            }
        });
        return view;


    }






    public interface CheckBillInterface{
        public void doCheckBill();
        public void checkResponse(boolean check);
        public void showHistoryFragment();
        public void doLogout();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            checkBillInterface = (CheckBillInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkBillInterface.doCheckBill();


    }

    public void displayBill(Bill bill) {
        if (bill != null) {
            weight.setText(bill.getWeight());
            total.setText(Double.toString(bill.getPrice()));
            unitprice.setText("10 cents");
            Picasso.with(this.getContext()).load("https://www.coldstonecreamery.com/assets/img/products/signaturecreations/signaturecreations.jpg").into(image);
        }else{
            weight.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
            pricelabel.setVisibility(View.INVISIBLE);
            unitprice.setVisibility(View.INVISIBLE);
            weightlabel.setVisibility(View.INVISIBLE);
            totallabel.setVisibility(View.INVISIBLE);
            accept.setVisibility(View.INVISIBLE);
            reject.setVisibility(View.INVISIBLE);
            image.setVisibility(View.INVISIBLE);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView view = new TextView(getActivity());
            view.setLayoutParams(layoutParams);
            view.setText("No Transactions found!");
            view.setTextSize(20);
            view.setGravity(Gravity.CENTER);
            layout.addView(view);
        }
    }


}
