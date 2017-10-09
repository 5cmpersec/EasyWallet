/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bauden.android.easywallet.R;

import java.util.Date;

public class AddEditTransactionFragment extends Fragment implements AddEditTransactionContract.View {

    private AddEditTransactionContract.Presenter mPresenter;

    private EditText mTitle;

    private EditText mAmount;

    private TextView mTransactionType;

    private Button mDate;

    public AddEditTransactionFragment() {}

    public static AddEditTransactionFragment newInstance() {
        return new AddEditTransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addtransaction_frag, container, false);

        return root;
    }


    @Override
    public void setPresenter(AddEditTransactionContract.Presenter presenter) {

    }

    @Override
    public void showTransactionsList() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDate(Date date) {

    }

    @Override
    public void setAmount(double amount) {

    }
}
