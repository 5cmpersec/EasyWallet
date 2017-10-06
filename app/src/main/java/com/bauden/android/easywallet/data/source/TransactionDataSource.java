/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.data.source;

import android.support.annotation.NonNull;

import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import java.util.List;

public interface TransactionDataSource {


    interface LoadTransactionsCallback {

        void onTransactionsLoaded(List<Transaction> transactions);

        void onDataNotAvailable();
    }

    interface GetTransactionCallback {

        void onTransactionLoaded(Transaction transaction);

        void onDataNotAvailable();
    }

    void getTransactions(@NonNull LoadTransactionsCallback callback);

    void getTransaction(@NonNull String id, @NonNull GetTransactionCallback callback);

    void saveTransaction(@NonNull Transaction transaction);

    void deleteTransaction(@NonNull String id);
}
