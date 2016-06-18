package com.luconisimone.easyrebootmd;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.Theme;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.afollestad.materialdialogs.MaterialDialog;


public class BootloaderReboot extends Activity {

    int riavviobootloader = 0;
    MaterialDialog dialogbuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        riavviobootloader = sharePref.getInt("riavviobootloader", 0);

        dialogbuilder = new MaterialDialog.Builder(this)
                .title(R.string.riavviobootloaderazione)
                .content(R.string.widgetrebootbootloaderconfirm)
                .theme(Theme.LIGHT)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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

        dialogbuilder.setOnDismissListener(new DialogInterface.OnDismissListener()

                                           {
                                               @Override
                                               public void onDismiss(DialogInterface dialogInterface) {
                                                   finish();
                                               }
                                           }

        );


    }

    @Override
    public void onBackPressed() {
        finish();
    }

}


