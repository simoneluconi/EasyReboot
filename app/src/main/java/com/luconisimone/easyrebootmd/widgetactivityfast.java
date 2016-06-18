package com.luconisimone.easyrebootmd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class widgetactivityfast extends Activity {

    int riavvio = 0;
    int riavviorecovery = 0;
    int riavvioveloce = 0;
    int riavviosicuro = 0;
    int riavviobootloader = 0;
    int spegni = 0;
    int riavviodownload = 0;

    int mostraeasyrebootv = 0;
    int mostrariavviovelocev = 0;
    int mostrariavviov = 0;
    int mostrariavviorecoveryv=0;
    int mostrariavviobootloaderv=0;
    int mostraspegniv = 0;
    int mostrasystemui = 0;
    int mostrariavviodownloadv= 0;
    MaterialDialog dialogbuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgetactivityfast);
        Button openapp = (Button) findViewById(R.id.widgetopenapp1);
        Button reboot = (Button) findViewById(R.id.widgetnormalreboot1);
        Button rebootfast = (Button) findViewById(R.id.widgetfastreboot1);
        Button rebootrecovery = (Button) findViewById(R.id.widgetrecoveryreboot1);
        Button rebootbootloader = (Button) findViewById(R.id.widgetbootloaderreboot1);
        Button rebootdownloadmode = (Button) findViewById(R.id.widgetdownloadmode1);
        Button poweroff = (Button) findViewById(R.id.widgetpoweroff1);
        Button systemui = (Button) findViewById(R.id.widgetrebootui);

        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        riavvio = sharePref.getInt("riavvio" , 0 );
        riavviorecovery = sharePref.getInt("riavviorecovery" , 0 );
        riavvioveloce = sharePref.getInt("riavvioveloce" , 0 );
        riavviobootloader = sharePref.getInt("riavviobootloader" , 0 );
        spegni = sharePref.getInt("spegni" , 0 );
        riavviosicuro = sharePref.getInt("riavviosicuro" , 0 );
        mostraeasyrebootv = sharePref.getInt("mostraeasyrebootv", 1);
        mostrariavviovelocev = sharePref.getInt("mostrariavviovelocev" ,1);
        mostrariavviov = sharePref.getInt("mostrariavviov",1);
        mostrariavviorecoveryv = sharePref.getInt("mostrariavviorecoveryv",1);
        mostrariavviobootloaderv = sharePref.getInt("mostrariavviobootloaderv",1);
        mostraspegniv = sharePref.getInt("mostraspegniv",1);
        mostrasystemui = sharePref.getInt("mostrasystemui",1);
        mostrariavviodownloadv = sharePref.getInt("mostradownload",1);
        riavviodownload = sharePref.getInt("riavviodownload", 0);

        if (!android.os.Build.BRAND.toUpperCase().equals("SAMSUNG")) {
            mostrariavviodownloadv = 0;
        }

        if (mostraeasyrebootv==1) {
           openapp.setVisibility(View.VISIBLE);
        } else {
            openapp.setVisibility(View.GONE);
        }

        if (mostrasystemui==1) {
            systemui.setVisibility(View.VISIBLE);
        } else {
            systemui.setVisibility(View.GONE);
        }

        if (mostrariavviovelocev==1) {
            rebootfast.setVisibility(View.VISIBLE);
        } else {
            rebootfast.setVisibility(View.GONE);
        }

        if (mostrariavviov==1) {
            reboot.setVisibility(View.VISIBLE);
        } else {
            reboot.setVisibility(View.GONE);
        }

        if (mostrariavviorecoveryv==1){
            rebootrecovery.setVisibility(View.VISIBLE);
        } else {
            rebootrecovery.setVisibility(View.GONE);
        }

        if (mostrariavviobootloaderv==1){
            rebootbootloader.setVisibility(View.VISIBLE);
        } else {
            rebootbootloader.setVisibility(View.GONE);
        }

        if (mostrariavviodownloadv==1) {
            rebootdownloadmode.setVisibility(View.VISIBLE);
        } else {
            rebootdownloadmode.setVisibility(View.GONE);
        }

        if (mostraspegniv==1) {
            poweroff.setVisibility(View.VISIBLE);
        } else {
            poweroff.setVisibility(View.GONE);
        }

        systemui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(widgetactivityfast.this, rebootsystemui.class);
                widgetactivityfast.this.startActivity(myIntent);
            }
        });


        openapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(widgetactivityfast.this, MainActivity.class);
                widgetactivityfast.this.startActivity(myIntent);
            }
        });

        reboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbuilder = new MaterialDialog.Builder(widgetactivityfast.this)
                        .title(R.string.widgetnormalreboot)
                        .content(R.string.widgetrebootnormalconfirm)
                        .theme(Theme.LIGHT)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                try {
                                    Command command = new Command(0, "su", "reboot");

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
                                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                                    riavvio = riavvio - 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("riavvio", riavvio);
                                    editor.apply();
                                }

                                finish();
                            }

                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        })
                        .iconRes(R.drawable.ericonsmall)
                        .show();
            }
        });

        rebootfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbuilder = new MaterialDialog.Builder(widgetactivityfast.this)
                        .title(R.string.widgetfastreboot)
                        .content(R.string.widgetrebootfastconfirm)
                        .theme(Theme.LIGHT)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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
                                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                                    riavvioveloce = riavvioveloce - 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("riavvioveloce", riavvioveloce);
                                    editor.apply();
                                }

                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                               finish();
                           }
                       })
                        .iconRes(R.drawable.ericonsmall)
                        .show();
            }
        });

        rebootrecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbuilder = new MaterialDialog.Builder(widgetactivityfast.this)
                        .title(R.string.riavviorecoveryazione)
                        .content(R.string.widgetrebootrecoveryconfirm)
                        .theme(Theme.LIGHT)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Command command = new Command(0, "su", "reboot recovery");

                                try {
                                    riavviorecovery = riavviorecovery + 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("riavviorecovery", riavviorecovery);
                                    editor.apply();

                                    RootTools.getShell(true).add(command);


                                } catch (IOException | TimeoutException ex) {
                                    riavviorecovery = riavviorecovery - 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("riavviorecovery", riavviorecovery);
                                    editor.apply();
                                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                                } catch (RootDeniedException ex) {
                                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                                    riavviorecovery = riavviorecovery - 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("riavviorecovery", riavviorecovery);
                                    editor.apply();
                                }

                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        })
                        .iconRes(R.drawable.ericonsmall)
                        .show();

            }
        });

        rebootbootloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbuilder = new MaterialDialog.Builder(widgetactivityfast.this)
                        .title(R.string.riavviobootloaderazione)
                        .content(R.string.widgetrebootbootloaderconfirm)
                        .theme(Theme.LIGHT)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Command command = new Command(0, "su", "reboot bootloader");

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
                                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                                    riavviobootloader = riavviobootloader - 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("riavviobootloader", riavviobootloader);
                                    editor.apply();
                                }

                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        })
                        .iconRes(R.drawable.ericonsmall)
                        .show();
            }
        });

        rebootdownloadmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbuilder = new MaterialDialog.Builder(widgetactivityfast.this)
                        .title(R.string.rebootdownloadazione)
                        .content(R.string.widgetdownloadmodeconfirm)
                        .theme(Theme.LIGHT)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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

                                }
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                               finish();
                           }
                       })
                        .iconRes(R.drawable.ericonsmall)
                        .show();
            }
        });

        poweroff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbuilder = new MaterialDialog.Builder(widgetactivityfast.this)
                        .title(R.string.spegnitesto)
                        .content(R.string.widgetpoweroffconfirm)
                        .theme(Theme.LIGHT)
                        .positiveText(android.R.string.yes)
                        .negativeText(android.R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Command command = new Command(0, "su", "reboot -p");
                                Toast.makeText(getApplicationContext(), R.string.wait8, Toast.LENGTH_LONG).show();

                                try {
                                    spegni = spegni + 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("spegni", spegni);
                                    editor.apply();

                                    RootTools.getShell(true).add(command);


                                } catch (IOException | TimeoutException ex) {
                                    Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
                                    spegni = spegni - 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("spegni", spegni);
                                    editor.apply();
                                } catch (RootDeniedException ex) {
                                    Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                                    spegni = spegni - 1;
                                    SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putInt("spegni", spegni);
                                    editor.apply();
                                }

                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                 finish();
                             }
                         })
                        .iconRes(R.drawable.ericonsmall)
                        .show();
            }
        });
    }



}
