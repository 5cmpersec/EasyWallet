/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load transactions from the data sources into a cache.
 *
 */
public class TransactionsRepository implements TransactionsDataSource {

    private static TransactionsRepository sINSTANCE = null;

    private final TransactionsDataSource mTransactionsLocalDataSource;

    @VisibleForTesting
    Map<String, Transaction> mCachedTransactions;

    @VisibleForTesting
    boolean mCacheIsDirty = false;

    private TransactionsRepository(@NonNull TransactionsDataSource localSource) {
        mTransactionsLocalDataSource = checkNotNull(localSource);
    }

    public static TransactionsRepository getInstance(TransactionsDataSource transactionsLocalDataSource) {
        if (sINSTANCE == null) {
            sINSTANCE = new TransactionsRepository(transactionsLocalDataSource);
        }
        return sINSTANCE;
    }

    public static void destroyInstance() {
        sINSTANCE = null;
    }

    @Override
    public void getTransactions(@NonNull final LoadTransactionsCallback callback) {
        if (mCachedTransactions != null && !mCacheIsDirty) {
            callback.onTransactionsLoaded(new ArrayList<>(mCachedTransactions.values()));
            return;
        }

        mTransactionsLocalDataSource.getTransactions(new LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                refreshCache(transactions);
                callback.onTransactionsLoaded(new ArrayList<>(mCachedTransactions.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTransaction(@NonNull String id, @NonNull final GetTransactionCallback callback) {
        checkNotNull(id);
        checkNotNull(callback);

        Transaction cachedTransaction = getTransactionById(id);

        if (cachedTransaction != null) {
            callback.onTransactionLoaded(cachedTransaction);
            return;
        }

        mTransactionsLocalDataSource.getTransaction(id, new GetTransactionCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                callback.onTransactionLoaded(transaction);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveTransaction(@NonNull Transaction transaction) {
        checkNotNull(transaction);
        mTransactionsLocalDataSource.saveTransaction(transaction);

        if (mCachedTransactions == null) {
            mCachedTransactions = new LinkedHashMap<>();
        }
        mCachedTransactions.put(transaction.getId(), transaction);
    }

    @Override
    public void deleteTransaction(@NonNull String id) {
        checkNotNull(id);
        mTransactionsLocalDataSource.deleteTransaction(id);

        mCachedTransactions.remove(id);
    }

    @Nullable
    private Transaction getTransactionById(@NonNull String id) {
        checkNotNull(id);
        if (mCachedTransactions == null || mCachedTransactions.isEmpty()) {
            return null;
        } else {
            return mCachedTransactions.get(id);
        }
    }

    private void refreshCache(List<Transaction> transactions) {
        if (mCachedTransactions == null) {
            mCachedTransactions = new LinkedHashMap<>();
        }
        mCachedTransactions.clear();
        for (Transaction t : transactions) {
            mCachedTransactions.put(t.getId(), t);
        }
        mCacheIsDirty = false;
    }
}
