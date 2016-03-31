package com.example.chandra.coldstone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chandra.coldstone.constants.EasyPayConstants;
import com.example.chandra.coldstone.database.RequestParams;
import com.example.chandra.coldstone.database.RestCall;
import com.example.chandra.coldstone.dto.Bill;
import com.example.chandra.coldstone.utility.ParseUtility;
import com.example.chandra.coldstone.utility.ToppingsUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements RestCall.HomeFunctionCall {

    Toolbar mToolbar;
    TextView weight, noTrans;
    TextView total;
    TextView unitprice;
    ImageView image;
    TextView weightlabel;
    TextView totallabel;
    TextView pricelabel;
    TextView toppings;
    LinearLayout layout;
    LinearLayout buttonsLayout;
    CardView cardView;
    Bill billinfo;
    boolean transaction;
    String username;
    boolean displayTopping;
    String android_id;
    boolean session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(EasyPayConstants.BASE_TITLE + " Home");

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        displayTopping = true;
        initialize();
        username = getIntent().getExtras().getString(EasyPayConstants.PARAMETER_USERNAME);
        session=true;
        android_id=getIntent().getExtras().getString(EasyPayConstants.ANDROID_DEVICE_ID_KEY);
        getBillForUser(username);
    }

    public void getBillForUser(String username) {
        RequestParams requestParams = new RequestParams(EasyPayConstants.baseurl, EasyPayConstants.METHOD_POST);
        requestParams.setUrl(EasyPayConstants.FUNC_GET_BILL);
        requestParams.addParams(EasyPayConstants.PARAMETER_USERNAME, username);
        new RestCall(HomeActivity.this, EasyPayConstants.FUNC_GET_BILL).execute(requestParams);
    }


    public void doStatusUpdate(View v) {
        RequestParams requestParams = new RequestParams(EasyPayConstants.baseurl, EasyPayConstants.METHOD_POST);
        requestParams.setUrl(EasyPayConstants.FUNC_STATUS_UPDATE);
        requestParams.addParams("username", username);
        requestParams.addParams("id", String.valueOf(billinfo.getId()));
        requestParams.addParams("toppings", ToppingsUtility.convertSetToString(billinfo.getSelectedToppings()));
        requestParams.addParams("price", String.valueOf(billinfo.getPrice() + billinfo.getToppingsPrice()));

        Button b = (Button) v;
        if (b.getId() == R.id.accept) {
            requestParams.addParams("status", "A");
        } else {
            requestParams.addParams("status", "C");
        }
        new RestCall(HomeActivity.this, EasyPayConstants.FUNC_STATUS_UPDATE).execute(requestParams);
    }


    @Override
    public void submitBillForUser(String output) {
        billinfo = ParseUtility.parseBill(output);
        displayBill(billinfo);
    }

    @Override
    public void statusUpdate(String output) {
        int count = Integer.valueOf(output);
        if (count != 0) {
            displayBill(null);
        }
    }

    @Override
    public void doLogout(String output) {
        int status = Integer.valueOf(output);
        if(status!=0){
            session=false;
            goBack(session);
        }
    }

    public void goBack(boolean condition){
        Intent intent = new Intent();
        intent.putExtra("Session",condition);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goBack(session);
    }

    @Override
    public void doActionOnHistory(String output) {
        ArrayList<Bill> list = ParseUtility.getHistory(output);
        Intent intent = new Intent(HomeActivity.this,HistoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("History",list);
        intent.putExtra("Bundle",bundle);
        startActivity(intent);
    }

    public void displayBill(Bill bill) {
        billinfo = bill;
        if (bill != null && bill.getSelectedToppings() != null) {
            transaction = true;
            weight.setText(bill.getWeight());
            unitprice.setText("10 cents");
            displayToppingsWithBill(bill);
            Picasso.with(this).load("https://www.coldstonecreamery.com/assets/img/" +
                    "products/signaturecreations/signaturecreations.jpg").into(image);
            if (displayTopping) {
                displayToppingsList();
            }
        } else if (bill == null) {
            transaction = false;
            invalidateOptionsMenu();
            cardView.setVisibility(View.INVISIBLE);
            buttonsLayout.setVisibility(View.INVISIBLE);
            noTrans.setVisibility(View.VISIBLE);
        } else {
            transaction = true;
            displayToppingsList();
        }
    }

    public void displayToppingsWithBill(Bill bill) {
        String toppingsList = "No Toppings Selected";
        double displayPrice = bill.getPrice();
        if (bill.getSelectedToppings() != null) {
            if (!bill.getSelectedToppings().isEmpty()) {
                toppingsList = ToppingsUtility.convertSetToString(bill.getSelectedToppings());
                displayPrice = bill.getPrice() + bill.getToppingsPrice();
            } else {
                LinkedHashSet<String> list = new LinkedHashSet<>();
                list.add(toppingsList);
                billinfo.setSelectedToppings(list);
                billinfo.setPrice(displayPrice);
            }
        }
        toppings.setText(" " + toppingsList);
        total.setText("$" + displayPrice);
    }


    public void displayToppingsList() {
        Intent intent = new Intent(HomeActivity.this, ToppingsActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras() != null) {
                    displayTopping = false;
                    List<String> list = data.getStringArrayListExtra("SelectedToppings");
                    float price = data.getFloatExtra("Price", (float) 0.0);

                    billinfo.setSelectedToppings(new LinkedHashSet<>(list));
                    billinfo.setToppingsPrice(price);
                    displayBill(billinfo);
                }

            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(transaction){
            menu.clear();
            menu.add(0, 4, Menu.NONE, "Edit toppings");
            menu.add(0, R.id.history, Menu.NONE, "History");
            menu.add(0,R.id.logout,Menu.NONE,"Log out");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.history) {
            RequestParams params = new RequestParams(EasyPayConstants.baseurl,EasyPayConstants.METHOD_POST);
            params.setUrl(EasyPayConstants.FUNC_HISTORY);
            params.addParams(EasyPayConstants.PARAMETER_USERNAME, this.username);
            new RestCall(HomeActivity.this,EasyPayConstants.FUNC_HISTORY).execute(params);
            return true;
        }
        if(id==R.id.logout){
            RequestParams params = new RequestParams(EasyPayConstants.baseurl,EasyPayConstants.METHOD_POST);
            params.setUrl(EasyPayConstants.FUNC_LOGOUT);
            params.addParams(EasyPayConstants.PARAMETER_DEVICE, android_id);
            params.addParams(EasyPayConstants.PARAMETER_USERNAME, username);
            new RestCall(HomeActivity.this,EasyPayConstants.FUNC_LOGOUT).execute(params);
            return true;
        }

        if(id==4){
            displayTopping=true;
            displayBill(billinfo);
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialize() {
        cardView = (CardView) findViewById(R.id.card_view);
        weight = (TextView) findViewById(R.id.weight);
        total = (TextView) findViewById(R.id.total);
        unitprice = (TextView) findViewById(R.id.pricepergram);
        image = (ImageView) findViewById(R.id.image);
        weightlabel = (TextView) findViewById(R.id.weightlabel);
        toppings = (TextView) findViewById(R.id.toppingsname);
        totallabel = (TextView) findViewById(R.id.totallabel);
        pricelabel = (TextView) findViewById(R.id.pricelabel);
        layout = (LinearLayout) findViewById(R.id.parentchild);
        noTrans = (TextView) findViewById(R.id.noTrans);
        buttonsLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
    }
}
