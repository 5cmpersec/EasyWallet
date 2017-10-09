/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bauden.android.easywallet.UseCaseHandler;
import com.bauden.android.easywallet.addedittransaction.domain.usecase.GetTransaction;
import com.bauden.android.easywallet.addedittransaction.domain.usecase.SaveTransaction;

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

    }

    @Override
    public void populateTransactions() {

    }

    @Override
    public void start() {

    }
}
