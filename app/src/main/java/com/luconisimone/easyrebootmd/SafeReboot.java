package com.luconisimone.easyrebootmd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class SafeReboot extends Activity {

    int riavviosicuro = 0;
    MaterialDialog dialogbuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        riavviosicuro = sharePref.getInt("riavviosicuro" , 0 );


        dialogbuilder = new MaterialDialog.Builder(this)
                .title(R.string.widgetsafereboot)
                .content(R.string.widgetsaferebootconfirm)
                .theme(Theme.LIGHT)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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
                            Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
                            riavviosicuro = riavviosicuro - 1;
                            SharedPreferences sharedPref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt("riavviosicuro", riavviosicuro);
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

