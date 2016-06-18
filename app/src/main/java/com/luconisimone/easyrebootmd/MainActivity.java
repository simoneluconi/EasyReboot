package com.luconisimone.easyrebootmd;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.Switch;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.*;


public class MainActivity extends AppCompatActivity {

    Drawer result = null;
    int root = 0;
    int riavvio = 0;
    int riavviorecovery = 0;
    int riavvioveloce = 0;
    int riavviosicuro = 0;
    int riavviobootloader = 0;
    int spegni = 0;
    int riavviodownload = 0;
    int mostraavviso = 0;
    int donato = 0;
    int batterylevel = 0;
    int batterylowalert = 0;
    int alertdialog = 1;
    int forceenglish = 0;
    int tocchiaccount = 0;
    int movecard = 0;
    int fbvisited = 0;
    int hometype = 1;
    NotificationManager notificationManager;

    // App Icon: http://www.flaticon.com/free-icon/refresh-round-symbol_15517
    // Star Icon: http://www.flaticon.com/free-icon/big-star_78339
    // Graph Icon : http://www.flaticon.com/free-icon/ascending-line-graphic-of-business-stats_60504
    // Custom Command Icon: http://www.flaticon.com/free-icon/command-window_64760
    // Alert Icon: http://www.flaticon.com/free-icon/caution-sign_101

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_barhome);
        setSupportActionBar(toolbar);
        Button reboot = (Button) findViewById(R.id.buttonreboot);
        TextView rootyesno = (TextView) findViewById(R.id.root);
        ImageView immagineroot = (ImageView) findViewById(R.id.rootimage);
        Button rebootrecovery = (Button) findViewById(R.id.buttonrebootrecovery);
        final Button rebootsoft = (Button) findViewById(R.id.buttonrebootsoft);
        final Button rebootbootloader = (Button) findViewById(R.id.buttonrebootbootloader);
        Button rebootdlmd = (Button) findViewById(R.id.buttonrebootdl);
        TextView busyboxinstallato = (TextView) findViewById(R.id.busyboxinstalled);
        TextView deviceinfo = (TextView) findViewById(R.id.deviceinfo);
        TextView rootgiven = (TextView) findViewById(R.id.rootgiven);
        TextView androidversion = (TextView) findViewById(R.id.androidversion);
        rootgiven.setVisibility(View.VISIBLE);
        final Button safereboot = (Button) findViewById(R.id.buttonrebootsafe);
        Button poweroff = (Button) findViewById(R.id.buttonpoweroff);
        TextView installbusybox = (TextView) findViewById(R.id.installbusybox);
        ObservableScrollView listViewMain = (ObservableScrollView) findViewById(R.id.scrollViewmain);
        FloatingActionButton customcommand = (FloatingActionButton) findViewById(R.id.buttoncustomcommand);
        customcommand.attachToScrollView(listViewMain);
        CardView infocard = (CardView) findViewById(R.id.card_view_root);
        CardView downloadmodecard = (CardView) findViewById(R.id.card_view_downloadmode);
        final Switch Switchhome = (Switch) findViewById(R.id.switchhome);
        final FrameLayout home1 = (FrameLayout) findViewById(R.id.home1);
        final FrameLayout home2 = (FrameLayout) findViewById(R.id.home2);
        CardView rebootfasth2 = (CardView) findViewById(R.id.card_h2rebootfast);
        CardView rebooth2 = (CardView) findViewById(R.id.card_h2rebootnormal);
        CardView rebootsafeh2 = (CardView) findViewById(R.id.card_h2rebootsecure);
        CardView rebootrecoveryh2 = (CardView) findViewById(R.id.card_h2rebootrecovery);
        CardView rebootbootloaderh2 = (CardView) findViewById(R.id.card_h2rebootbootloader);
        CardView poweroffh2 = (CardView) findViewById(R.id.card_h2poweroff);
        CardView systemuih2 = (CardView) findViewById(R.id.card_h2rebootsystemui);
        final CardView rebootdownloadmodeh2 = (CardView) findViewById(R.id.card_h2downloadmode);
        Intent batterylevelintent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        try {
            batterylevel = batterylevelintent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No battery data ( " + e.toString() + ")", Toast.LENGTH_LONG).show();

        }
        Button rebootui = (Button) findViewById(R.id.buttonrebootui);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.materialbanner)
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile iProfile) {
                        tocchiaccount = tocchiaccount + 1;
                        if (tocchiaccount == 50) {
                            Toast.makeText(getApplicationContext(), "(☞ﾟヮﾟ)☞", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "☜(ﾟヮﾟ☜) ", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
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
                        if (id == 2) {
                            Intent myIntent = new Intent(MainActivity.this, Stats.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            MainActivity.this.startActivity(myIntent);
                        } else if (id == 3) {
                            Intent myIntent = new Intent(MainActivity.this, Scheduling.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            MainActivity.this.startActivity(myIntent);

                        } else if (id == 5) {
                            Intent myIntent = new Intent(MainActivity.this, Extra.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            MainActivity.this.startActivity(myIntent);

                        } else if (id == 6) {
                            Intent myIntent = new Intent(MainActivity.this, Settings.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            MainActivity.this.startActivity(myIntent);
                        }
                        return false;
                    }
                })
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);


        //Riprendi le statistiche
        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        forceenglish = sharePref.getInt("englishlocale", 0);
        riavvio = sharePref.getInt("riavvio", 0);
        riavviorecovery = sharePref.getInt("riavviorecovery", 0);
        riavvioveloce = sharePref.getInt("riavvioveloce", 0);
        riavviobootloader = sharePref.getInt("riavviobootloader", 0);
        spegni = sharePref.getInt("spegni", 0);
        riavviosicuro = sharePref.getInt("riavviosicuro", 0);
        mostraavviso = sharePref.getInt("avviso", 0);
        riavviodownload = sharePref.getInt("riavviodownload", 0);
        donato = sharePref.getInt("donato", 0);
        batterylowalert = sharePref.getInt("batterylowalert", 15);
        alertdialog = sharePref.getInt("alertdialog", 1);
        forceenglish = sharePref.getInt("englishlocale", 0);
        movecard = sharePref.getInt("movecard", 0);
        fbvisited = sharePref.getInt("fbvisited", 0);
        hometype = sharePref.getInt("hometype", 1);


        SpannableString content = new SpannableString(installbusybox.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        installbusybox.setText(content);

        customcommand.setVisibility(View.GONE);


        if (getString(R.string.riavviosoftazione).length() + getString(R.string.riavvioazione).length() + getString(R.string.riavviosicuroazione).length() > 20) {
            GridLayout gridView = (GridLayout) findViewById(R.id.gridReboot);
            gridView.setColumnCount(2);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        double wi = (double) width / metrics.xdpi;
        double hi = (double) height / metrics.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        screenInches = (double) Math.round(screenInches * 10) / 10;

        if (screenInches < 4.5) {
            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridFastHome);
            gridLayout.setColumnCount(1);
        }


        if (RootTools.isRootAvailable()) {
            root = 1;
            rootyesno.setText(R.string.rootyes);
            rootyesno.setTextColor(getResources().getColor(R.color.greenmaterial));
            immagineroot.setImageResource(R.drawable.happy);
            Log.v("ROOT", "Si");
        } else {
            root = 0;
            rootyesno.setText(R.string.rootno);
            rootyesno.setTextColor(getResources().getColor(R.color.redmaterial));
            Log.v("ROOT", "No");

            new MaterialDialog.Builder(this).title(getString(R.string.noroottx)).iconRes(R.drawable.norooticon)
                    .theme(Theme.LIGHT).content(getString(R.string.notrootmsg)).positiveText(getString(R.string.oknorootmsg)).neutralText(getString(R.string.uninstall))
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(Intent.ACTION_DELETE);
                            intent.setData(Uri.parse("package:com.luconisimone.easyrebootmd"));
                            startActivity(intent);
                        }
                    })
                    .show();

        }


        if (RootTools.isAccessGiven()) {

            rootgiven.setText(R.string.rootgivenyes);
            rootgiven.setTextColor(getResources().getColor(R.color.greenmaterial));
            Log.v("ROOT", "Accesso Concesso");
        } else {

            Log.v("ROOT", "Accesso Negato");

            if (root == 0) {

                rootgiven.setVisibility(View.INVISIBLE);
            } else {

                rootgiven.setText(R.string.rootgivenno);
                rootgiven.setTextColor(getResources().getColor(R.color.orangematerial));
                rootgiven.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
            }
        }

        if (RootTools.isBusyboxAvailable()) {
            busyboxinstallato.setText(R.string.rootyes);
            busyboxinstallato.setTextColor(Color.parseColor("#43A047"));
            installbusybox.setVisibility(View.INVISIBLE);
        } else {
            busyboxinstallato.setText(R.string.rootno);
            busyboxinstallato.setTextColor(Color.parseColor("#F44336"));

            if (root == 0) {
                installbusybox.setVisibility(View.GONE);
                immagineroot.setImageResource(R.drawable.sad);
            } else {
                installbusybox.setVisibility(View.VISIBLE);
                immagineroot.setImageResource(R.drawable.mhh);
                Toast.makeText(getApplicationContext(), R.string.nobusybox, Toast.LENGTH_LONG).show();
            }
        }

        if (batterylevel <= batterylowalert & batterylowalert != 0) {
            if (batterylowalert == 100) {

                new MaterialDialog.Builder(this)
                        .title(R.string.lowbattery)
                        .theme(Theme.LIGHT)
                        .content(getString(R.string.lowbattery1) + " " + batterylevel + "% " + getString(R.string.lowbattery2) + " ಠ_ಠ" + "\n" + getString(R.string.becareful))
                        .positiveText("( ° ͜ ʖ °)")
                        .iconRes(R.drawable.alert)
                        .show();

            } else {

                new MaterialDialog.Builder(this)
                        .title(R.string.lowbattery)
                        .theme(Theme.LIGHT)
                        .content(getString(R.string.lowbattery1) + " " + batterylevel + "% " + getString(R.string.lowbattery2) + "\n" + getString(R.string.becareful))
                        .positiveText(android.R.string.yes)
                        .iconRes(R.drawable.alert)
                        .show();
            }
        }

        //Switch

        if (hometype == 1) {
            Switchhome.setChecked(false);
        } else {
            Switchhome.setChecked(true);
        }

        if (Switchhome.isChecked()) {
            home2.setVisibility(View.VISIBLE);
            home1.setVisibility(View.INVISIBLE);
        } else {
            home2.setVisibility(View.INVISIBLE);
            home1.setVisibility(View.VISIBLE);
        }


        installbusybox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=stericson.busybox"));
                startActivity(browserIntent);
            }
        });

        String deviceinfost = android.os.Build.BRAND.toUpperCase() + " " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";

        deviceinfo.setText(deviceinfost);
        androidversion.setText(android.os.Build.VERSION.RELEASE);

        if (android.os.Build.BRAND.toUpperCase().equals("SAMSUNG")) {
            downloadmodecard.setVisibility(View.VISIBLE);
            rebootdownloadmodeh2.setVisibility(View.VISIBLE);
        } else {
            downloadmodecard.setVisibility(View.GONE);
            rebootdownloadmodeh2.setVisibility(View.GONE);
        }


        if (movecard == 0) {
            infocard.setVisibility(View.VISIBLE);
        } else {
            infocard.setVisibility(View.GONE);
        }

        Switchhome.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (Switchhome.isChecked()) {
                    home2.setVisibility(View.VISIBLE);
                    home1.setVisibility(View.INVISIBLE);
                    hometype = 2;
                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("hometype", hometype);
                    editor.apply();
                } else {
                    home2.setVisibility(View.INVISIBLE);
                    home1.setVisibility(View.VISIBLE);
                    hometype = 1;
                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("hometype", hometype);
                    editor.apply();
                }
            }
        });

        rebootui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rebootuivd();
            }
        });

        rebootdlmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebootdlvd();
            }

        });

        reboot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                rebootvd();
            }
        });

        rebootrecovery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                rebootrecoveryvd();
            }
        });

        rebootsoft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                rebootsoftvd();

            }
        });

        safereboot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                saferebootvd();
            }
        });

        rebootbootloader.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                rebootbootloadervd();
            }
        });

        poweroff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                poweroffvd();

            }
        });

        //Cards

        rebootfasth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebootsoftvd();
            }
        });

        rebooth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebootvd();
            }
        });

        rebootsafeh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saferebootvd();
            }
        });

        rebootrecoveryh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebootrecoveryvd();
            }
        });

        rebootbootloaderh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebootbootloadervd();
            }
        });

        poweroffh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poweroffvd();
            }
        });

        systemuih2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebootuivd();
            }
        });

        rebootdownloadmodeh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebootdlvd();
            }
        });


    }


    //PROCEDURE


    public void rebootvd() {
        if (alertdialog == 0) {

            Command command;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                command = new Command(0, "su", "reboot");
            else
                command = new Command(0, "su", "svc power reboot");


            try {
                riavvio = riavvio + 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavvio", riavvio);
                editor.apply();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException ex) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                riavvio = riavvio - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavvio", riavvio);
                editor.apply();
            } catch (RootDeniedException ex) {
                riavvio = riavvio - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavvio", riavvio);
                editor.apply();

                if (root == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                }
            }


        } else {
            Intent myIntent = new Intent(MainActivity.this, NormalReboot.class);
            MainActivity.this.startActivity(myIntent);
        }
    }

    public void saferebootvd() {
        if (alertdialog == 0) {

            Command command = new Command(0, "su -c setprop persist.sys.safemode 1", "reboot");
            Command command2 = new Command(0, "busybox killall system_server");

            try {
                riavviosicuro = riavviosicuro + 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviosicuro", riavviosicuro);
                editor.apply();

                RootTools.getShell(true).add(command);
                RootTools.getShell(true).add(command2);


            } catch (IOException | TimeoutException ex) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                riavviosicuro = riavviosicuro - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviosicuro", riavviosicuro);
                editor.apply();
            } catch (RootDeniedException ex) {
                riavviosicuro = riavviosicuro - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviosicuro", riavviosicuro);
                editor.apply();
                if (root == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                }
            }

        } else {
            Intent myIntent = new Intent(MainActivity.this, SafeReboot.class);
            MainActivity.this.startActivity(myIntent);
        }
    }

    public void rebootsoftvd() {
        if (alertdialog == 0) {

            Command command = new Command(0, "busybox killall system_server");

            try {
                riavvioveloce = riavvioveloce + 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavvioveloce", riavvioveloce);
                editor.apply();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException ex) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                riavvioveloce = riavvioveloce - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavvioveloce", riavvioveloce);
                editor.apply();
            } catch (RootDeniedException ex) {
                riavvioveloce = riavvioveloce - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavvioveloce", riavvioveloce);
                editor.apply();
                if (root == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                }

            }

        } else {
            Intent myIntent = new Intent(MainActivity.this, SoftReboot.class);
            MainActivity.this.startActivity(myIntent);
        }
    }

    public void rebootrecoveryvd() {
        if (alertdialog == 0) {

            Command command;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                command = new Command(0, "su", "reboot recovery");
            else
                command = new Command(0, "su", "svc power reboot recovery");

            try {
                riavviorecovery = riavviorecovery + 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviorecovery", riavviorecovery);
                editor.apply();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException ex) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();

                riavviorecovery = riavviorecovery - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviorecovery", riavviorecovery);
                editor.apply();
            } catch (RootDeniedException ex) {
                riavviorecovery = riavviorecovery - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviorecovery", riavviorecovery);
                editor.apply();

                if (root == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Intent myIntent = new Intent(MainActivity.this, RecoveryReboot.class);
            MainActivity.this.startActivity(myIntent);
        }
    }

    public void rebootbootloadervd() {

        if (alertdialog == 0) {

            Command command;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                command = new Command(0, "su", "reboot bootloader");
            else
                command = new Command(0, "su", "svc power reboot bootloader");

            try {
                riavviobootloader = riavviobootloader + 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviobootloader", riavviobootloader);
                editor.apply();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException ex) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                riavviobootloader = riavviobootloader - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviobootloader", riavviobootloader);
                editor.apply();

            } catch (RootDeniedException ex) {
                riavviobootloader = riavviobootloader - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviobootloader", riavviobootloader);
                editor.apply();

                if (root == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                }
            }

        } else {
            Intent myIntent = new Intent(MainActivity.this, BootloaderReboot.class);
            MainActivity.this.startActivity(myIntent);
        }
    }


    public void rebootdlvd() {

        if (alertdialog == 0) {

            Command command = new Command(0, "su", "reboot download");

            try {

                riavviodownload = riavviodownload + 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviodownload", riavviodownload);
                editor.apply();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException ex) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                riavviodownload = riavviodownload - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviodownload", riavviodownload);
                editor.apply();
            } catch (RootDeniedException ex) {
                riavviodownload = riavviodownload - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("riavviodownload", riavviodownload);
                editor.apply();

                if (root == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Intent myIntent = new Intent(MainActivity.this, RebootDownload.class);
            MainActivity.this.startActivity(myIntent);
        }
    }

    public void rebootuivd() {
        Command command = new Command(0, "su", "pkill com.android.systemui");
        try {
            RootTools.getShell(true).add(command);


        } catch (IOException | TimeoutException ex) {
            Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
        } catch (RootDeniedException ex) {
            if (root == 0) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void poweroffvd() {
        if (alertdialog == 0) {

            Command command = new Command(0, "su", "reboot -p");

            try {
                spegni = spegni + 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("spegni", spegni);
                editor.apply();
                Toast.makeText(getApplicationContext(), R.string.wait8, Toast.LENGTH_LONG).show();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException ex) {
                Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                spegni = spegni - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("spegni", spegni);
                editor.apply();
            } catch (RootDeniedException ex) {
                spegni = spegni - 1;
                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("spegni", spegni);
                editor.apply();

                if (root == 0) {
                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                }
            }

        } else {
            Intent myIntent = new Intent(MainActivity.this, PowerOff.class);
            MainActivity.this.startActivity(myIntent);
        }
    }

    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            finish();
        }

    }

}