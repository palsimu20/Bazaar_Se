package com.client.bazaarse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Toast;

import com.client.bazaarse.R;
import com.client.bazaarse.fragement.AdminPannel;
import com.client.bazaarse.fragement.DeliveryPartner;
import com.client.bazaarse.fragement.Distrubter;
import com.client.bazaarse.fragement.PrivacyPolicy;
import com.client.bazaarse.fragement.Super_Store;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    WebView webview;
    Context context;
    public static final String url = "http://bazaarse.epizy.com/delivery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        toolbar = findViewById(R.id.toolbar1);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        setSupportActionBar(toolbar);



        DrawerLayout drawerLayout =  findViewById(R.id.my_drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorBlack));
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);
        selectDrawerItem(R.id.notifi);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.isChecked())
            menuItem.setChecked(false);

        else
            menuItem.setChecked(true);
        selectDrawerItem(menuItem.getItemId());
        return true;
    }

    public void selectDrawerItem(int menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;

        switch (menuItem) {
            case R.id.notifi:
                fragment = new Super_Store();
                break;

            case R.id.partener:
                fragment = new DeliveryPartner();
                break;

            case R.id.delivery:
                fragment = new Distrubter();
                break;
            case R.id.about:
                AboutUs();
                break;
            case R.id.privacy:
                fragment = new PrivacyPolicy();
                //Toast.makeText(getApplicationContext(),"empty text",Toast.LENGTH_LONG).show();
                break;
            case R.id.admin:
                fragment = new AdminPannel();
                break;

            case R.id.name:
                UrlLink();
                break;

            default:
                fragment = new Super_Store();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment).addToBackStack("tag");
            ft.commit();
        }
        DrawerLayout drawer =  findViewById(R.id.my_drawer);
        drawer.closeDrawer(GravityCompat.START);

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.titlemenu, menu);


        return true;

    }

    //
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.exit1:

                ShareData();
                return true;

            case R.id.exit:
                logExit();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logExit() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirm!");
        builder.setMessage("Are You Sure?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);


            }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void AboutUs()
    {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Bazaar Se Online Grocery Shopping : Choose from a wide range of grocery, baby care products, personal care products, fresh fruits &amp; vegetables online. Pay Online &amp; Avail exclusive discounts on various products @ India&#x27;s Best Online Grocery store.\n" +
                "✔ Best Prices &amp; Offers ✔ Cash on Delivery ✔ Easy Returns");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void ShareData() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Your body here";
        String shareSub = "Your subject here";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share Via"));

    }
    private void UrlLink() {


        Uri uri = Uri.parse("http://wa.me/917054874357?text=Hello"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout =  findViewById(R.id.my_drawer);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        else
        {
            FragmentManager fragmentManager=getSupportFragmentManager();


            if (fragmentManager.getBackStackEntryCount()>1)
            {
                fragmentManager.popBackStack();
            }



            else{
                if (doubleBackToExitPressedOnce) {
                    fragmentManager.popBackStack();
                    super.onBackPressed();
                    return;
                }
                this.doubleBackToExitPressedOnce=true;


                Toast.makeText(this, getResources().getString(R.string.open), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);


            }




        }


        }
    }
