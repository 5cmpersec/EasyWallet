/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.bauden.android.easywallet.UseCaseHandler;
import com.bauden.android.easywallet.addedittransaction.AddEditTransactionActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionsPresenter implements TransactionsContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    private final TransactionsContract.View mTransactionsView;

    public TransactionsPresenter(@NonNull UseCaseHandler useCaseHandler,
                                 @NonNull TransactionsContract.View transactionsView) {

        mUseCaseHandler = checkNotNull(useCaseHandler);
        mTransactionsView = checkNotNull(transactionsView);

        mTransactionsView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void addNewTransaction() {
        mTransactionsView.showAddNewTransaction();
    }

    @Override
    public void loadTransactions() {

    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddEditTransactionActivity.REQUEST_ADD_NEW_TRANSACTION == requestCode
                && Activity.RESULT_OK == resultCode) {
            mTransactionsView.showSuccessfullyAddedTransaction();
        }
    }
}
