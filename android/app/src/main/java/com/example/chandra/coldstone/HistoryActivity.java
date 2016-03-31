package com.example.chandra.coldstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.chandra.coldstone.adapters.ListAdapter;
import com.example.chandra.coldstone.constants.EasyPayConstants;
import com.example.chandra.coldstone.dto.Bill;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        setTitle(EasyPayConstants.BASE_TITLE + "History");

        listView=(ListView)findViewById(R.id.listview);

        Bundle bundle = getIntent().getBundleExtra("Bundle");
        ArrayList<Bill> list = (ArrayList <Bill>)bundle.getSerializable("History");

        displayHistory(list);
    }


    public void displayHistory(ArrayList<Bill> history){
        ListAdapter adapter = new ListAdapter(HistoryActivity.this,R.layout.listrow,history);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
