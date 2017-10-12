/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.bauden.android.easywallet.UseCaseHandler;
import com.bauden.android.easywallet.addedittransaction.AddEditTransactionActivity;
import com.bauden.android.easywallet.transactions.domain.usecase.DeleteTransaction;
import com.bauden.android.easywallet.transactions.domain.usecase.GetTransactions;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionsPresenter implements TransactionsContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;

    private final TransactionsContract.View mTransactionsView;

    private final GetTransactions mGetTransactions;

    private final DeleteTransaction mDeleteTransaction;

    public TransactionsPresenter(@NonNull UseCaseHandler useCaseHandler,
                                 @NonNull TransactionsContract.View transactionsView,
                                 @NonNull GetTransactions getTransactions,
                                 @NonNull DeleteTransaction deleteTransaction) {

        mUseCaseHandler = checkNotNull(useCaseHandler);
        mTransactionsView = checkNotNull(transactionsView);
        mGetTransactions = checkNotNull(getTransactions);
        mDeleteTransaction = checkNotNull(deleteTransaction);

        mTransactionsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTransactions();
    }

    @Override
    public void addNewTransaction() {
        mTransactionsView.showAddNewTransaction();
    }

    @Override
    public void loadTransactions() {
        mUseCaseHandler.execute(mGetTransactions, new GetTransactions.RequestValues(),
                new GetTransactions.UseCaseCallback<GetTransactions.ResponseValue>() {
                    @Override
                    public void onSuccess(GetTransactions.ResponseValue response) {
                        if (!mTransactionsView.isActive()) {
                            return;
                        }
                        mTransactionsView.showTransactions(response.getTransactions());
                    }

                    @Override
                    public void onError() {
                        if (!mTransactionsView.isActive()) {
                            return;
                        }
                        mTransactionsView.showLoadingTransactionsError();
                    }
                });
    }

    @Override
    public void deleteTransaction(@NonNull String transactionId) {
        mUseCaseHandler.execute(mDeleteTransaction, new DeleteTransaction.RequestValues(transactionId),
                new DeleteTransaction.UseCaseCallback<DeleteTransaction.ResponseValue>() {

                    @Override
                    public void onSuccess(DeleteTransaction.ResponseValue response) {
                        mTransactionsView.showSuccessfullyDeletedTransaction();
                        loadTransactions();
                    }

                    @Override
                    public void onError() {
                        mTransactionsView.showDeletingTransactionError();
                    }
                });
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddEditTransactionActivity.REQUEST_ADD_NEW_TRANSACTION == requestCode
                && Activity.RESULT_OK == resultCode) {
            mTransactionsView.showSuccessfullyAddedTransaction();
        }
    }
}
