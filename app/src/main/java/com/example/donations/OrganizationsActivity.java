package com.example.donations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donations.adapter.OrganizationListAdapter;
import com.example.donations.model.Organization;
import com.example.donations.service.DownloadOrganizationsService;
import com.example.donations.viewmodel.OrganizationViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrganizationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private Handler handler;
    private SearchView searchView = null;
    private int organizationCategoriesNumber = 0;

    private OrganizationViewModel organizationViewModel;

    private OrganizationListAdapter organizationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations);

        handler = new Handler();
        organizationViewModel = ViewModelProviders.of(OrganizationsActivity.this).get(OrganizationViewModel.class);

        initViews();
        initList();
        initObservers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu, menu);

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        startProgress();
        Thread serviceThread = new Thread() {
            public void run() {
                Intent startServiceIntent = new Intent(OrganizationsActivity.this, DownloadOrganizationsService.class);
                startServiceIntent.setAction("FetchOrganizations");
                startService(startServiceIntent);
            }
        };
        serviceThread.start();
    }


    public void showCategoriesPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.organization_categories_popup, null);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.organization_categories_radio_group);
        RadioGroup.LayoutParams radioLayoutParams;

        for(int i=0; i<organizationCategoriesNumber; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText("asdasd");
            radioLayoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioGroup.addView(radioButton, radioLayoutParams);
        }


        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView,width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.organizations_toolbar);
        progressBar = findViewById(R.id.organizations_progress_bar);
    }

    private void initList() {
        recyclerView = findViewById(R.id.organizations_recycler_view);
        organizationListAdapter = new OrganizationListAdapter(OrganizationsActivity.this, new ArrayList<Organization>());
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(organizationListAdapter);
    }

    private void initObservers() {
        organizationViewModel.getOrganizations().observe(OrganizationsActivity.this, new Observer<List<Organization>>() {
            @Override
            public void onChanged(List<Organization> organizations) {
                organizationListAdapter.updateData(organizations);
            }
        });
    }

    private void startProgress() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <=10; i+=2) {
                    final int finalProgress = i;
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalProgress);
                        }
                    });
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }).start();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            organizationCategoriesNumber = intent.getIntExtra("organizationCategoriesNumber", 0);
            System.out.println("DOBIV: " + organizationCategoriesNumber);
        }
    };

    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("FetchOrganizations"));
    }

    protected void onPause (){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
