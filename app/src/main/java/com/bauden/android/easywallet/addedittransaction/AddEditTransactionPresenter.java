/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bauden.android.easywallet.UseCase;
import com.bauden.android.easywallet.UseCaseHandler;
import com.bauden.android.easywallet.addedittransaction.domain.usecase.GetTransaction;
import com.bauden.android.easywallet.addedittransaction.domain.usecase.SaveTransaction;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditTransactionPresenter implements AddEditTransactionContract.Presenter {

    private final AddEditTransactionContract.View mAddEditTransactionView;

    private final GetTransaction mGetTransaction;

    private final SaveTransaction mSaveTransaction;

    private final UseCaseHandler mUseCaseHandler;

    @Nullable
    private String mTransactionId;

    public AddEditTransactionPresenter(@NonNull UseCaseHandler useCaseHandler,
                                       @Nullable String transactionId,
                                       @NonNull AddEditTransactionContract.View addEditTransactionView,
                                       @NonNull GetTransaction getTransaction,
                                       @NonNull SaveTransaction saveTransaction) {
        mUseCaseHandler = checkNotNull(useCaseHandler);
        mTransactionId = transactionId;
        mAddEditTransactionView = checkNotNull(addEditTransactionView);
        mGetTransaction = checkNotNull(getTransaction);
        mSaveTransaction = checkNotNull(saveTransaction);

        mAddEditTransactionView.setPresenter(this);
    }

    @Override
    public void saveTransaction(String title, Date date, double amount) {
        if (isNewTransaction()) {
            createTransaction(title, date, amount);
        }
    }

    @Override
    public void populateTransactions() {

    }

    @Override
    public void start() {

    }

    private boolean isNewTransaction() {
        return mTransactionId == null;
    }

    private void createTransaction(String title, Date date, double amount) {
        Transaction transaction = new Transaction(title, date, amount);
        mUseCaseHandler.execute(mSaveTransaction, new SaveTransaction.RequestValues(transaction),
                new UseCase.UseCaseCallback<SaveTransaction.ResponseValue>() {
                    @Override
                    public void onSuccess(SaveTransaction.ResponseValue response) {
                        mAddEditTransactionView.showTransactionsList();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
