package com.client.bazaarse.fragement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.client.bazaarse.utility.DetectConnection;
import com.client.bazaarse.R;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminPannel extends Fragment {
    private WebView webview;
    private View view;
    private  Context context;

    private SwipeRefreshLayout mySwipeRefreshLayout;
    private ProgressDialog progressDialog;
    private LinearLayout first, second;
    private Button refresh;
    private static final String url = "http://bazaarse.epizy.com";


    public AdminPannel() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_admin_pannel, container, false);
        context = getContext();

        // getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        // progressBar =  view.findViewById(R.id.progressBar1);


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        //  progressDialog.setProgress(0);
       // progressDialog.show();

        //  progressBar.setMessage("Loading...");


        mySwipeRefreshLayout = view.findViewById(R.id.swipeContainer);
        first = view.findViewById(R.id.lin1);
        second = view.findViewById(R.id.lin2);
        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent);
        webview = view.findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });




        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (isNetworkStatusAvialable(Objects.requireNonNull(getContext()))) {
                    Toast.makeText(getContext(), "internet available", Toast.LENGTH_SHORT).show();


                    assert getFragmentManager() != null;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(AdminPannel.this).attach(AdminPannel.this).commit();

                }
                else
                {
                    Toast.makeText(getContext(), " please connect your device Internet", Toast.LENGTH_SHORT).show();
                }
            }

        });

        AdminPannel.CustomWebViewClient c = new AdminPannel.CustomWebViewClient();
        webview.setWebViewClient(c);

        webview.clearCache(true);
        webview.clearHistory();


        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mySwipeRefreshLayout.setRefreshing(false);
                        webview.reload();
                    }
                }, 3500);
            }
        });


        //  webview.getSettings().setBuiltInZoomControls(true);
        if (DetectConnection.checkInternetConnection(Objects.requireNonNull(getContext()))) {
            first.setVisibility(View.VISIBLE);
            second.setVisibility(View.GONE);

            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Check your internet connection and try again.");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {



                }
            });

            alertDialog.show();
        } else {
            webview.loadUrl(url);
            // progressDialog.show();
        }
        return view;
    }

    private class CustomWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains("http://bazaarse.epizy.com")) {
                view.loadUrl(url);
                if (progressDialog.isShowing()) {

                    progressDialog.show();
                }


            } else {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }


            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

            }

        }


        @Override
        public void onLoadResource(WebView view, String url) {

            if (DetectConnection.checkInternetConnection(context)) {
                // webview.loadUrl("file:///android_asset/demo.html"); //Change path if it is not correct
                first.setVisibility(View.VISIBLE);
                second.setVisibility(View.GONE);


            }

        }
    }

    private static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null) {
                return netInfos.isConnected();
            }
        }
        return false;


    }
}

