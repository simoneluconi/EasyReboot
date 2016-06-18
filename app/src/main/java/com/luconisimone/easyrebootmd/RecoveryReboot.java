package com.luconisimone.easyrebootmd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class RecoveryReboot extends Activity {

    int riavviorecovery = 0;
    MaterialDialog dialogbuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        riavviorecovery = sharePref.getInt("riavviorecovery" , 0 );



        dialogbuilder = new MaterialDialog.Builder(this)
                .title(R.string.riavviorecoveryazione)
                .content(R.string.widgetrebootrecoveryconfirm)
                .theme(Theme.LIGHT)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

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


        dialogbuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}