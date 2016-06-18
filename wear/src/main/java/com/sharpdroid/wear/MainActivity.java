package com.sharpdroid.wear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements WearableListView.ClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] prova = {"ciao", "come", "va"};
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                final WearableListView listView = (WearableListView) stub.findViewById(R.id.wearable_list);
                if (listView == null) {
                    Log.d("DevicesListActivity", "ListView is null");
                } else {
                    listView.setAdapter(new MyAdapter(getApplicationContext(), prova));
                    listView.setClickListener(MainActivity.this);
                }

            }
        });


    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Integer tag = (Integer) viewHolder.itemView.getTag();
        Log.v("Elemento", String.valueOf(tag));
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
