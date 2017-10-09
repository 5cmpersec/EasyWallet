/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bauden.android.easywallet.R;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import java.util.List;

public class TransactionsFragment extends Fragment implements TransactionsContract.View {

    public TransactionsFragment() {
    }

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.transactions_frag, container, false);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(TransactionsContract.Presenter presenter) {

    }

    @Override
    public void showTransactions(List<Transaction> transactions) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
