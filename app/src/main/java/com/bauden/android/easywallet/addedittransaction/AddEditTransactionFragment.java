/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bauden.android.easywallet.R;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditTransactionFragment extends Fragment implements AddEditTransactionContract.View {

    public static final String ARGUMENT_EDIT_TRANSACTION_ID = "EDIT_TRANSACTION_ID";

    private AddEditTransactionContract.Presenter mPresenter;

    private EditText mTitle;

    private EditText mAmount;

    private TextView mTransactionType;

    private Button mBtnDate;

    private Date mCurrentDate;

    public AddEditTransactionFragment() {}

    public static AddEditTransactionFragment newInstance() {
        return new AddEditTransactionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addtransaction_frag, container, false);

        mTitle = (EditText) root.findViewById(R.id.description_edittext);
        mAmount = (EditText) root.findViewById(R.id.amount_edittext);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_transaction_done);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPresenter.saveTransaction(mTitle.getText().toString(),
                        new Date(), Double.parseDouble(mAmount.getText().toString()));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddEditTransactionContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showTransactionsList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
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
