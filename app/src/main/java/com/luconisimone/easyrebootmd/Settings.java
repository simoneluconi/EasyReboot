package com.luconisimone.easyrebootmd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class Settings extends AppCompatActivity {

    Drawer result = null;
    int batterylowalert = 0;
    int alertdialogyesno = 0;
    int mostraeasyrebootv = 0;
    int mostrariavviovelocev = 0;
    int mostrariavviov = 0;
    int mostrariavviorecoveryv=0;
    int mostrariavviobootloaderv=0;
    int mostrariavviodownloadv = 0;
    int mostraspegniv = 0;
    int englishlocale = 0;
    int movecard = 0;
    int mostrasystemui = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        final TextView BatteryAlertText = (TextView) findViewById(R.id.textViewBatteryAlert);
        final DiscreteSeekBar batteryalertbar = (DiscreteSeekBar) findViewById(R.id.seekBarBatteryAlert);
        final CheckBox checkdialog = (CheckBox) findViewById(R.id.checkBoxDialog);
        final CheckBox moveinfocard = (CheckBox) findViewById(R.id.checkBoxMoveCard);
        final CheckBox mostraeasyreboot = (CheckBox) findViewById(R.id.switchwidget11Title);
        final CheckBox mostrariavvioveloce = (CheckBox) findViewById(R.id.switchwidget11FastReboot);
        final CheckBox mostrariavvio = (CheckBox) findViewById(R.id.switchwidget11NormalReboot);
        final CheckBox mostrarecovery = (CheckBox) findViewById(R.id.switchwidget11Recovery);
        final CheckBox mostrabootloader = (CheckBox) findViewById(R.id.switchwidget11Bootloader);
        final CheckBox mostraspegni = (CheckBox) findViewById(R.id.switchwidget11Off);
        final CheckBox mostrarebootui = (CheckBox) findViewById(R.id.switchwidget11Systemui);
        final TextView batteryalertdisable = (TextView) findViewById(R.id.batteryalertdisable);
        final CheckBox mostrariavviodownload = (CheckBox) findViewById(R.id.switchwidget11download);

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
                        if (id ==2) {
                            savedata();
                            Intent myIntent = new Intent(Settings.this, Stats.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Settings.this.startActivity(myIntent);
                        } else if (id==5) {
                            savedata();
                            Intent myIntent = new Intent(Settings.this, Extra.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Settings.this.startActivity(myIntent);

                        }   else if (id==3) {
                            savedata();
                            Intent myIntent = new Intent(Settings.this, Scheduling.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Settings.this.startActivity(myIntent);

                        }else if (id==1) {
                            savedata();
                            Intent i = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        return false;
                    }
                })
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        result.setSelection(5);



        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        batterylowalert = sharePref.getInt("batterylowalert", 15);
        alertdialogyesno = sharePref.getInt("alertdialog", 1);
        mostraeasyrebootv = sharePref.getInt("mostraeasyrebootv", 1);
        mostrariavviovelocev = sharePref.getInt("mostrariavviovelocev" ,1);
        mostrariavviov = sharePref.getInt("mostrariavviov",1);
        mostrariavviorecoveryv = sharePref.getInt("mostrariavviorecoveryv",1);
        mostrariavviobootloaderv = sharePref.getInt("mostrariavviobootloaderv",1);
        mostraspegniv = sharePref.getInt("mostraspegniv",1);
        mostrasystemui = sharePref.getInt("mostrasystemui",1);
        mostrariavviodownloadv = sharePref.getInt("mostradownload",1);
        englishlocale = sharePref.getInt("englishlocale" ,0);
        movecard = sharePref.getInt("movecard" , 0);

        if (!android.os.Build.BRAND.toUpperCase().equals("SAMSUNG")) {
            mostrariavviodownloadv = 0;
            mostrariavviodownload.setVisibility(View.GONE);
        }

        batteryalertbar.setProgress(batterylowalert);
        String battx = batterylowalert + "%";
        BatteryAlertText.setText(battx);
        String badtx = "0 = " + getString(R.string.disablebatteryalter);
        batteryalertdisable.setText(badtx);

        if (alertdialogyesno==0) {
            checkdialog.setChecked(false);
        } else {
            checkdialog.setChecked(true);
        }

        if (mostraeasyrebootv==1) {
            mostraeasyreboot.setChecked(true);
        } else {
            mostraeasyreboot.setChecked(false);
        }

        if (mostrariavviovelocev==1) {
            mostrariavvioveloce.setChecked(true);
        } else {
            mostrariavvioveloce.setChecked(false);
        }

        if (mostrariavviov==1) {
            mostrariavvio.setChecked(true);
        } else {
            mostrariavvio.setChecked(false);
        }

        if (mostrariavviorecoveryv==1){
            mostrarecovery.setChecked(true);
        } else {
            mostrarecovery.setChecked(false);
        }

        if (mostrariavviobootloaderv==1){
            mostrabootloader.setChecked(true);
        } else {
            mostrabootloader.setChecked(false);
        }

        if (mostrariavviodownloadv==1) {
            mostrariavviodownload.setChecked(true);
        } else {
            mostrariavviodownload.setChecked(false);
        }

        if (mostraspegniv==1) {
            mostraspegni.setChecked(true);
        } else {
            mostraspegni.setChecked(false);
        }

        if (mostrasystemui==1) {
            mostrarebootui.setChecked(true);
        } else {
            mostrarebootui.setChecked(false);
        }

        if (movecard ==1) {
            moveinfocard.setChecked(true);
        } else {
            moveinfocard.setChecked(false);
        }

        moveinfocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moveinfocard.isChecked()) {
                    movecard=1;
                } else {
                    movecard=0;
                }
            }
        });

        mostrarebootui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mostrarebootui.isChecked()) {
                    mostrasystemui=1;
                } else {
                    mostrasystemui=0;
                }
            }
        });




            mostraeasyreboot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mostraeasyreboot.isChecked()){
                        mostraeasyrebootv = 1;
                    } else {
                        mostraeasyrebootv = 0;
                    }
                }
            });


        mostrariavvioveloce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostrariavvioveloce.isChecked()){
                    mostrariavviovelocev = 1;
                } else {
                    mostrariavviovelocev = 0;
                }
            }
        });

        mostrariavvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostrariavvio.isChecked()) {
                    mostrariavviov =1;
                } else {
                    mostrariavviov = 0;
                }
            }
        });

        mostrarecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostrarecovery.isChecked()) {
                    mostrariavviorecoveryv=1;
                } else {
                    mostrariavviorecoveryv=0;
                }
            }
        });

        mostrabootloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostrabootloader.isChecked()) {
                    mostrariavviobootloaderv=1;
                } else {
                    mostrariavviobootloaderv=0;
                }
            }
        });

        mostrariavviodownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mostrariavviodownload.isChecked()) {
                    mostrariavviodownloadv = 1;
                } else {
                    mostrariavviodownloadv = 0;
                }

            }
        });

        mostraspegni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostraspegni.isChecked()) {
                    mostraspegniv = 1;
                } else {
                    mostraspegniv = 0;
                }
            }
        });

        batteryalertbar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                batterylowalert = value;
                String battx2 = batterylowalert + "%";
                BatteryAlertText.setText(battx2);
                if (batterylowalert == 100) {
                    Toast.makeText(getApplicationContext(), "Really?", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });


        checkdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkdialog.isChecked()) {
                    alertdialogyesno = 1;
                } else {
                    alertdialogyesno = 0;
                }
            }
        });
    }

    public  void savedata() {
        SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("batterylowalert", batterylowalert);
        editor.apply();
        editor.putInt("alertdialog", alertdialogyesno);
        editor.apply();
        editor.putInt("mostraeasyrebootv", mostraeasyrebootv);
        editor.apply();
        editor.putInt("mostrariavviovelocev", mostrariavviovelocev);
        editor.apply();
        editor.putInt("mostrariavviov", mostrariavviov);
        editor.apply();
        editor.putInt("mostrariavviorecoveryv", mostrariavviorecoveryv);
        editor.apply();
        editor.putInt("mostrariavviobootloaderv", mostrariavviobootloaderv);
        editor.apply();
        editor.putInt("mostraspegniv", mostraspegniv);
        editor.apply();
        editor.putInt("movecard", movecard);
        editor.apply();
        editor.putInt("mostrasystemui", mostrasystemui);
        editor.apply();
        editor.putInt("mostradownload", mostrariavviodownloadv);
        editor.apply();
            }


    @Override
    public void onBackPressed() {

        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {

            savedata();

            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

}

