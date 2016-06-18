package com.luconisimone.easyrebootmd;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

public class RebootDownload extends Activity {

    MaterialDialog dialogbuilder;
    int riavviodownload = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        riavviodownload = sharePref.getInt("riavviodownload", 0);

        dialogbuilder =new MaterialDialog.Builder(this)
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

