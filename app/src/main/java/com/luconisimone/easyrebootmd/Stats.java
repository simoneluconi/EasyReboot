package com.luconisimone.easyrebootmd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;


public class Stats extends AppCompatActivity {

    Drawer result = null;
    int riavvio = 0;
    int riavviorecovery = 0;
    int riavvioveloce = 0;
    int riavviosicuro = 0;
    int riavviobootloader = 0;
    int spegni = 0;
    int accensioni = 0;
    int autoreboot = 0;
    int autopoweroff = 0;
    int riavviodownload = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView riavviotot = (TextView) findViewById(R.id.totalreboot);
        TextView riavviorectot = (TextView) findViewById(R.id.totalrecoveryreboot);
        TextView riavviovelotot = (TextView) findViewById(R.id.totalfastreboot);
        TextView riavviosictot = (TextView) findViewById(R.id.totalsafereboot);
        TextView riavvioboottot = (TextView) findViewById(R.id.totalbootloaderreboot);
        TextView spegnitot = (TextView) findViewById(R.id.totalpoweroff);
        TextView accensionitot = (TextView) findViewById(R.id.totalstart);
        TextView autoreboottv = (TextView) findViewById(R.id.totalautoreboot);
        TextView autopowerofftv = (TextView) findViewById(R.id.totalautopoweroff);
        TextView totaldownload = (TextView) findViewById(R.id.totaldownloadmode);
        LinearLayout downloadlay = (LinearLayout) findViewById(R.id.layoutstatsdownload);

        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        riavvio = sharePref.getInt("riavvio" , 0 );
        riavviorecovery = sharePref.getInt("riavviorecovery" , 0 );
        riavvioveloce = sharePref.getInt("riavvioveloce" , 0 );
        riavviobootloader = sharePref.getInt("riavviobootloader" , 0 );
        spegni = sharePref.getInt("spegni" , 0 );
        riavviosicuro = sharePref.getInt("riavviosicuro" , 0 );
        accensioni = sharePref.getInt("accensioni" , 0 );
        autoreboot = sharePref.getInt("riavvioautom",0);
        autopoweroff = sharePref.getInt("spegniautom",0);
        riavviodownload = sharePref.getInt("riavviodownload", 0);

        riavviotot.setText(Integer.toString(riavvio));
        riavviorectot.setText(Integer.toString(riavviorecovery));
        riavviovelotot.setText(Integer.toString(riavvioveloce));
        riavvioboottot.setText(Integer.toString(riavviobootloader));
        accensionitot.setText(Integer.toString(accensioni));
        spegnitot.setText(Integer.toString(spegni));
        riavviosictot.setText(Integer.toString(riavviosicuro));
        autoreboottv.setText(Integer.toString(autoreboot));
        autopowerofftv.setText(Integer.toString(autopoweroff));
        totaldownload.setText(Integer.toString(riavviodownload));

        if (!android.os.Build.BRAND.toUpperCase().equals("SAMSUNG")) {
            downloadlay.setVisibility(View.GONE);
        }

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.materialbanner)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withSliderBackgroundColorRes((R.color.background))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.home).withIcon(R.drawable.appicongrey).withIdentifier(0),
                        new PrimaryDrawerItem().withName(R.string.stats).withIcon(R.drawable.graph).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.schedulingtitle).withIcon(R.drawable.schedule).withIdentifier(2),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.info).withIcon(R.drawable.info).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.action_settings).withIcon(R.drawable.settings).withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int id, IDrawerItem drawerItem) {
                        if (id ==1) {
                            Intent myIntent = new Intent(Stats.this, MainActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Stats.this.startActivity(myIntent);
                        } else if (id==3) {
                            Intent myIntent = new Intent(Stats.this, Scheduling.class);
                            result.setSelection(1);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Stats.this.startActivity(myIntent);

                        } else if (id==5) {
                            Intent myIntent = new Intent(Stats.this, Extra.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Stats.this.startActivity(myIntent);

                        }else if (id==6) {
                            Intent myIntent = new Intent(Stats.this, Settings.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Stats.this.startActivity(myIntent);
                        }
                        return false;
                    }
                })
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        result.setSelection(1);
    }

    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            Intent myIntent = new Intent(Stats.this, MainActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Stats.this.startActivity(myIntent);
        }

    }

}

