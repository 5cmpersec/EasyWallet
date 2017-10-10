/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions;

import com.bauden.android.easywallet.BasePresenter;
import com.bauden.android.easywallet.BaseView;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import java.util.List;

public interface TransactionsContract {

    interface Presenter extends BasePresenter {

        void addNewTransaction();

        void loadTransactions();

        void result(int requestCode, int resultCode);
    }

    interface View extends BaseView<Presenter> {

        void showAddNewTransaction();

        void showSuccessfullyAddedTransaction();

        void showTransactions(List<Transaction> transactions);

        void showLoadingTransactionsError();

        boolean isActive();
    }
}
