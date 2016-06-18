package com.luconisimone.easyrebootmd;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.rey.material.app.Dialog;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.Switch;

import java.util.Calendar;


public class Scheduling extends AppCompatActivity {

    String azione = "niente";
    int ora = 0;
    int minuti = 0;
    Drawer result = null;
    TimePickerDialog mTimePicker;
    int temposelezionato = 0;
    RadioButton rebootsched;
    RadioButton poweroffsched;
    Switch switchsched;
    CheckBox notificationonboot;
    CheckBox notificationcount;
    int notification = 1;
    MaterialDialog dialog;
    Intent getAct;
    Dialog.Builder builder = null;
    String oratx = "";
    SnackBar mSnackBar;
    int notcount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView schedtitle = (TextView) findViewById(R.id.textViewSchedTitle);
        String schdtlt = getString(R.string.schedulingtitle) + " BETA";
        schedtitle.setText(schdtlt);
        TextView scheddes = (TextView) findViewById(R.id.textViewSchedDes);
        String schddes = getString(R.string.schedulingdes) + "\n" + (getString(R.string.noticebeta));
        scheddes.setText(schddes);
        switchsched = (Switch) findViewById(R.id.switchScheduling);
        final LinearLayout radiolayout = (LinearLayout) findViewById(R.id.linearlayoutradio);
        final LinearLayout savelayout = (LinearLayout) findViewById(R.id.layoutsavesched);
        Button savesched = (Button) findViewById(R.id.buttonsavesched);
        rebootsched = (RadioButton) findViewById(R.id.radioButtonReboot);
        poweroffsched = (RadioButton) findViewById(R.id.radioButtonPowerOff);
        notificationonboot = (CheckBox) findViewById(R.id.checkBoxNotificationOnBoot);
        notificationcount = (CheckBox) findViewById(R.id.checkBoxNotificationCount);
        final TextView timesetted = (TextView) findViewById(R.id.timesetted);


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
                        if (id == 1) {
                            salva();
                            Intent myIntent = new Intent(Scheduling.this, MainActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Scheduling.this.startActivity(myIntent);
                        } else if (id == 2) {
                            salva();
                            Intent myIntent = new Intent(Scheduling.this, Stats.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Scheduling.this.startActivity(myIntent);

                        } else if (id == 5) {
                            salva();
                            Intent myIntent = new Intent(Scheduling.this, Extra.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Scheduling.this.startActivity(myIntent);

                        } else if (id == 6) {
                            salva();
                            Intent myIntent = new Intent(Scheduling.this, Settings.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Scheduling.this.startActivity(myIntent);
                        }
                        return false;
                    }
                })
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        result.setSelection(2);


        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        azione = sharePref.getString("azionesched", "niente");
        ora = sharePref.getInt("orasched", 0);
        minuti = sharePref.getInt("minsched", 0);
        notification = sharePref.getInt("notificationonboot", 1);
        notcount = sharePref.getInt("notificationcount", 1);

        if (minuti == 0) {
            oratx = "00";
        } else if (minuti == 1) {
            oratx = "01";
        } else if (minuti == 2) {
            oratx = "02";
        } else if (minuti == 3) {
            oratx = "03";
        } else if (minuti == 4) {
            oratx = "04";
        } else if (minuti == 5) {
            oratx = "05";
        } else if (minuti == 6) {
            oratx = "06";
        } else if (minuti == 7) {
            oratx = "07";
        } else if (minuti == 8) {
            oratx = "08";
        } else if (minuti == 9) {
            oratx = "09";
        } else {
            oratx = String.valueOf(minuti);
        }

        timesetted.setText(String.valueOf(ora) + ":" + String.valueOf(oratx));

        if (notification == 1) {
            notificationonboot.setChecked(true);
        } else {
            notificationonboot.setChecked(false);
        }

        if (notcount == 1) {
            notificationcount.setChecked(true);
        } else {
            notificationcount.setChecked(false);
        }

        if (azione.equals("niente")) {
            radiolayout.setVisibility(View.GONE);
            savelayout.setVisibility(View.GONE);
            switchsched.setChecked(false);
        } else if (azione.equals("riavvia")) {
            radiolayout.setVisibility(View.VISIBLE);
            savelayout.setVisibility(View.VISIBLE);
            rebootsched.setChecked(true);
            poweroffsched.setChecked(false);
            switchsched.setChecked(true);
        } else if (azione.equals("spegni")) {
            radiolayout.setVisibility(View.VISIBLE);
            savelayout.setVisibility(View.VISIBLE);
            rebootsched.setChecked(false);
            poweroffsched.setChecked(true);
            switchsched.setChecked(true);
        }

        notificationonboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationonboot.isChecked()) {
                    notification = 1;
                } else {
                    notification = 0;
                }
            }
        });

        switchsched.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (switchsched.isChecked()) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    mTimePicker = new TimePickerDialog(Scheduling.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            ora = selectedHour;
                            minuti = selectedMinute;
                            temposelezionato = 1;
                            if (minuti == 0) {
                                oratx = "00";
                            } else if (minuti == 1) {
                                oratx = "01";
                            } else if (minuti == 2) {
                                oratx = "02";
                            } else if (minuti == 3) {
                                oratx = "03";
                            } else if (minuti == 4) {
                                oratx = "04";
                            } else if (minuti == 5) {
                                oratx = "05";
                            } else if (minuti == 6) {
                                oratx = "06";
                            } else if (minuti == 7) {
                                oratx = "07";
                            } else if (minuti == 8) {
                                oratx = "08";
                            } else if (minuti == 9) {
                                oratx = "09";
                            } else {
                                oratx = String.valueOf(minuti);
                            }


                            timesetted.setText(String.valueOf(ora) + ":" + oratx);

                        }
                    }, hour, minute, true);
                    radiolayout.setVisibility(View.VISIBLE);
                    savelayout.setVisibility(View.VISIBLE);
                    mTimePicker.show();

                    mTimePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if (temposelezionato != 1) {
                                temposelezionato = 0;
                                switchsched.setChecked(false);
                                radiolayout.setVisibility(View.GONE);
                                savelayout.setVisibility(View.GONE);
                                azione = "niente";
                                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("azionesched", azione);
                                editor.apply();
                            } else {
                                azione = "niente";
                                SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("azionesched", azione);
                                editor.apply();
                            }
                        }
                    });


                } else {
                    temposelezionato = 0;
                    azione = "niente";
                    radiolayout.setVisibility(View.GONE);
                    savelayout.setVisibility(View.GONE);
                    cancellarip();
                    poweroffsched.setChecked(false);
                    rebootsched.setChecked(false);
                }
            }
        });


        notificationcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationcount.isChecked()) {
                    notcount = 1;
                } else {
                    notcount = 0;
                }
            }
        });


        rebootsched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (poweroffsched.isChecked()) {
                    poweroffsched.setChecked(false);
                }
                rebootsched.setChecked(true);
                azione = "riavvia";
            }
        });

        poweroffsched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebootsched.isChecked()) {
                    rebootsched.setChecked(false);
                }
                poweroffsched.setChecked(true);
                azione = "spegni";
            }
        });

        savesched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salva();
            }
        });


    }

    public void salva() {

        if (!rebootsched.isChecked() & !poweroffsched.isChecked() & switchsched.isChecked()) {
            Toast.makeText(getApplicationContext(), R.string.scheduleerror, Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("azionesched", azione);
            editor.apply();
            editor.putInt("orasched", ora);
            editor.apply();
            editor.putInt("minsched", minuti);
            editor.apply();
            editor.putInt("notificationcount", notcount);
            editor.apply();

            if (!azione.equals("niente")) {
                impostasched();
            } else {
                cancellarip();
            }

            if (notification == 1) {
                editor.putInt("notificationonboot", 1);
                editor.apply();
            } else {
                editor.putInt("notificationonboot", 0);
                editor.apply();
            }


        }


    }


    public void impostasched() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        AlarmManager mAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Scheduling.this, SchedulingStart.class);
        intent.putExtra("uur", "1e");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, ora);
        calendar.set(Calendar.MINUTE, minuti);
        calendar.set(Calendar.SECOND, 0);
        if (minuti <= minute & ora <= hour) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            Toast.makeText(getApplicationContext(), getString(R.string.scheduleconfirm1) + " " + getString(R.string.scheduleconfirm2) +
                    " " + ora + ":" + oratx, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.scheduleconfirm1) + " " + getString(R.string.scheduleconfirm3) +
                            " " + ora + ":" + oratx, Toast.LENGTH_LONG).show();
        }

        mAlarmManger.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancellarip() {
        AlarmManager mAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Scheduling.this, SchedulingStart.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManger.cancel(pendingIntent);
        SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        azione = "niente";
        editor.putString("azionesched", azione);
        editor.apply();
    }

    public void dismissac() {
        SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("azionesched", "niente1");
        editor.apply();
        finish();
    }

    @Override
    public void onBackPressed() {
        salva();
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


}
