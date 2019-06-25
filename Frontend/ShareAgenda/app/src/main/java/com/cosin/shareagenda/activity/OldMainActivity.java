package com.cosin.shareagenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cosin.shareagenda.R;

import com.cosin.shareagenda.util.HandleMenu;
import com.cosin.shareagenda.util.AppHelper;

public abstract class OldMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawer;
    public NavigationView navigationView;
    public android.support.design.widget.CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind view
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        loadContentView();

        // init navigationView
        navigationView.setNavigationItemSelectedListener(this);
        if (AppHelper.getUserInfo() != null) {
            View headerView = navigationView.getHeaderView(0);
            TextView username = headerView.findViewById(R.id.username);
            username.setText(AppHelper.getUserInfo().getNickname());
        }

        loadData();

        initView();
    }

    // need be override in derived class
    protected abstract void loadContentView();

    // need be override in derived class
    protected abstract void loadData();

    // need be override in derived class
    protected abstract void initView();

    public void openDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        HandleMenu.routeMain(this, item);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
