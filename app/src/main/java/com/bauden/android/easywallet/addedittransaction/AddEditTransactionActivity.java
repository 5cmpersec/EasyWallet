/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bauden.android.easywallet.R;
import com.bauden.android.easywallet.util.ActivityUtils;

public class AddEditTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addtransaction_act);

        AddEditTransactionFragment addEditTransactionFragment =
                (AddEditTransactionFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (addEditTransactionFragment == null) {
            addEditTransactionFragment = AddEditTransactionFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditTransactionFragment, R.id.contentFrame);
        }
    }
}
