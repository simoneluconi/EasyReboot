package com.luconisimone.easyrebootmd;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Simone on 31/08/2015.
 */
public class rebootsystemui extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Command command = new Command(0, "su", "pkill com.android.systemui");
        try {
            RootTools.getShell(true).add(command);


        } catch (IOException | TimeoutException ex) {
            Toast.makeText(getApplicationContext(), R.string.noroot, Toast.LENGTH_LONG).show();
        } catch (RootDeniedException ex) {
                Toast.makeText(getApplicationContext(), R.string.rootaccessdenied, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}


