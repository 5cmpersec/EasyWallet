/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bauden.android.easywallet.R;
import com.bauden.android.easywallet.util.ActivityUtils;

public class TransactionsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private TransactionsPresenter mTransactionsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        TransactionsFragment transactionsFragment =
                (TransactionsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (transactionsFragment == null) {
            transactionsFragment = TransactionsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), transactionsFragment,
                    R.id.contentFrame);
        }
    }
}
