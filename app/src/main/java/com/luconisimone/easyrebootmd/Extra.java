package com.luconisimone.easyrebootmd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.stericson.RootTools.RootTools;

import java.util.Locale;

public class Extra extends AppCompatActivity {

    Drawer result = null;
    int root = 0;
    int movecard = 0;
    String testoemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Button sendemail = (Button) findViewById(R.id.buttonsendemail);
        Button buttonxda = (Button) findViewById(R.id.buttonxda);
        Button stringfile = (Button) findViewById(R.id.buttondownloadstring);
        Button credits = (Button) findViewById(R.id.buttoncredits);
        Button github = (Button) findViewById(R.id.buttongit);

        TextView rootyesno = (TextView) findViewById(R.id.rootextra);
        ImageView immagineroot = (ImageView) findViewById(R.id.rootimageextra);
        TextView busyboxinstallato = (TextView) findViewById(R.id.busyboxinstalledextra);
        TextView deviceinfo = (TextView) findViewById(R.id.deviceinfoextra);
        TextView rootgiven = (TextView) findViewById(R.id.rootgivenextra);
        TextView androidversion = (TextView) findViewById(R.id.androidversionextra);
        rootgiven.setVisibility(View.VISIBLE);
        TextView installbusybox = (TextView) findViewById(R.id.installbusyboxextra);
        CardView infocard = (CardView) findViewById(R.id.card_view_rootextra);
        CardView translcard = (CardView) findViewById(R.id.card_view_translation);

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
                            Intent myIntent = new Intent(Extra.this, MainActivity.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Extra.this.startActivity(myIntent);
                        } else if (id == 2) {
                            Intent myIntent = new Intent(Extra.this, Stats.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Extra.this.startActivity(myIntent);

                        } else if (id == 3) {
                            Intent myIntent = new Intent(Extra.this, Scheduling.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Extra.this.startActivity(myIntent);

                        } else if (id == 6) {
                            Intent myIntent = new Intent(Extra.this, Settings.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Extra.this.startActivity(myIntent);
                        }
                        return false;
                    }

                })
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        result.setSelection(4);

        SpannableString content = new SpannableString(installbusybox.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        installbusybox.setText(content);

        SharedPreferences sharePref = getSharedPreferences("Dati", Context.MODE_PRIVATE);
        movecard = sharePref.getInt("movecard", 0);

        if (movecard == 0) {
            infocard.setVisibility(View.GONE);

        } else {
            infocard.setVisibility(View.VISIBLE);
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


        String deviceinfost = android.os.Build.BRAND.toUpperCase() + " " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
        deviceinfo.setText(deviceinfost);
        androidversion.setText(android.os.Build.VERSION.RELEASE);

        testoemail = "- Please do not delete this text -" + "\n" + "Device: " + deviceinfost + "\n" + "Android Version: " + android.os.Build.VERSION.RELEASE + "\n";
        if (root == 1) {
            testoemail = testoemail + "Root: Yes" + "\n";
        } else {
            testoemail = testoemail + "Root: No" + "\n";
        }

        if (RootTools.isBusyboxAvailable()) {
            testoemail = testoemail + "Busybox: Yes";
        } else {
            testoemail = testoemail + "Busybox: No";
        }

        testoemail = testoemail + "\n" + "- Write Below -";

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/simoneluconi/EasyReboot"));
                startActivity(browserIntent);
            }
        });

        installbusybox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=stericson.busybox"));
                startActivity(browserIntent);
            }
        });

        buttonxda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/android/apps-games/easy-reboot-material-design-android-4-1-t3111954"));
                startActivity(browserIntent);
            }
        });

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "sharpdroidmail@gmail.com", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Easy Reboot");
                intent.putExtra(Intent.EXTRA_TEXT, testoemail);
                startActivity(Intent.createChooser(intent, "Email:"));
            }
        });

        stringfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.localize.im/projects/ks"));
                startActivity(browserIntent);
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(Extra.this)
                        .title(R.string.creditstranslation)
                        .content(getString(R.string.creditsitaliantranslation) + "\n" + getString(R.string.creditsenglishtranslation)
                                + "\n" + getString(R.string.creditschinesetranslation) + "\n"
                                + getString(R.string.creditsrussiantranslation) + "\n" +
                                getString(R.string.creditsukrainiantranslation) + "\n" + getString(R.string.creditspolishtranslation) + "\n"
                                + getString(R.string.creditsdutchtranslation) + "\n" + getString(R.string.creditsfrenchtranslation) + "\n" + getString(R.string.creditsspanishtranslation)
                                + "\n" + getString(R.string.creditsgermantranslation) + "\n" + getString(R.string.creditsportuguesetranslation) + "\n" + getString(R.string.creditsviettranslation) + "\n" + getString(R.string.creditsczechtranslation) + "\n" +
                                getString(R.string.creditsslovaktranslation))
                        .positiveText(android.R.string.yes)
                        .neutralText(R.string.more)
                        .theme(Theme.LIGHT)
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/showpost.php?p=61271038&postcount=2"));
                                startActivity(browserIntent);
                            }
                        })
                        .show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            Intent myIntent = new Intent(Extra.this, MainActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Extra.this.startActivity(myIntent);
        }

    }

}
