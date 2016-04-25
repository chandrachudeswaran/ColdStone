package com.example.chandra.coldstone;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chandra.coldstone.adapters.ListAdapter;
import com.example.chandra.coldstone.constants.EasyPayConstants;
import com.example.chandra.coldstone.dto.Bill;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ListView listView;
    LinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ice_cream_icon);
        setTitle(EasyPayConstants.BASE_TITLE + "History");
        parent=(LinearLayout)findViewById(R.id.parent);
        listView=(ListView)findViewById(R.id.listview);

        Bundle bundle = getIntent().getBundleExtra("Bundle");
        ArrayList<Bill> list = (ArrayList <Bill>)bundle.getSerializable("History");

        displayHistory(list);
    }


    public void displayHistory(ArrayList<Bill> history){
        if(history.isEmpty()){
            parent.removeView(listView);
            TextView textView = new TextView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(layoutParams);
            textView.setText("No History purchases");
            textView.setTextAppearance(this, android.R.style.TextAppearance_Large);
            textView.setTextColor(Color.GREEN);
            textView.setGravity(Gravity.CENTER);
            parent.addView(textView);
        }else {
            ListAdapter adapter = new ListAdapter(HistoryActivity.this, R.layout.listrow, history);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
