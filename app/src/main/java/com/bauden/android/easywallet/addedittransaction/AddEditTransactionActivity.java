/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bauden.android.easywallet.R;
import com.bauden.android.easywallet.UseCaseHandler;
import com.bauden.android.easywallet.addedittransaction.domain.usecase.GetTransaction;
import com.bauden.android.easywallet.addedittransaction.domain.usecase.SaveTransaction;
import com.bauden.android.easywallet.data.source.TransactionsRepository;
import com.bauden.android.easywallet.data.source.local.TransactionsLocalDataSource;
import com.bauden.android.easywallet.util.ActivityUtils;

public class AddEditTransactionActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_NEW_TRANSACTION = 1;

    public static final int REQUEST_EDIT_TRANSACTION = 2;

    public static final String ARG_EDIT_TRANSACTION_ID = "EDIT_TRANSACTION_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addtransaction_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AddEditTransactionFragment addEditTransactionFragment =
                (AddEditTransactionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (addEditTransactionFragment == null) {
            addEditTransactionFragment = AddEditTransactionFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditTransactionFragment, R.id.contentFrame);
        }

        new AddEditTransactionPresenter(UseCaseHandler.getInstance(),
                null,
                addEditTransactionFragment,
                new GetTransaction(TransactionsRepository.getInstance(new TransactionsLocalDataSource(getApplicationContext()))),
                new SaveTransaction(TransactionsRepository.getInstance(new TransactionsLocalDataSource(getApplicationContext()))));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
