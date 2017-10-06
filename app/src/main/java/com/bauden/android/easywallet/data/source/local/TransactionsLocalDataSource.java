/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.bauden.android.easywallet.data.source.TransactionsDataSource;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;
import com.bauden.android.easywallet.data.source.local.TransactionsPersistenceContract.TransactionEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class TransactionsLocalDataSource implements TransactionsDataSource {

    private static TransactionsLocalDataSource sINSTANCE;

    private TransactionsDbHelper mDbHelper;

    public TransactionsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new TransactionsDbHelper(context);
    }

    public static TransactionsLocalDataSource getInstance(@NonNull Context context) {
        if (sINSTANCE == null) {
            sINSTANCE = new TransactionsLocalDataSource(context);
        }
        return sINSTANCE;
    }


    @Override
    public void getTransactions(@NonNull LoadTransactionsCallback callback) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TransactionEntry.COLUMN_NAME_ENTRY_ID,
                TransactionEntry.COLUMN_NAME_TITLE,
                TransactionEntry.COLUMN_NAME_DATE,
                TransactionEntry.COLUMN_NAME_AMOUNT
        };

        Cursor c = db.query(
                TransactionEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_TITLE));
                Date date = new Date(c.getLong(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_DATE)));
                double amount = (double) c.getLong(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_AMOUNT)) / 100.d;

                Transaction transaction =  new Transaction(itemId, title, date, amount);
                transactions.add(transaction);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (transactions.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onTransactionsLoaded(transactions);
        }
    }

    @Override
    public void getTransaction(@NonNull String id, @NonNull GetTransactionCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TransactionEntry.COLUMN_NAME_ENTRY_ID,
                TransactionEntry.COLUMN_NAME_TITLE,
                TransactionEntry.COLUMN_NAME_DATE,
                TransactionEntry.COLUMN_NAME_AMOUNT
        };

        Cursor c = db.query(
                TransactionEntry.TABLE_NAME, projection, null, null, null, null, null);

        Transaction transaction = null;

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_TITLE));
                Date date = new Date(c.getLong(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_DATE)));
                double amount = (double) c.getLong(c.getColumnIndexOrThrow(TransactionEntry.COLUMN_NAME_AMOUNT)) / 100.d;

                transaction =  new Transaction(itemId, title, date, amount);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (transaction == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onTransactionLoaded(transaction);
        }
    }

    @Override
    public void saveTransaction(@NonNull Transaction transaction) {
        checkNotNull(transaction);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TransactionEntry.COLUMN_NAME_ENTRY_ID, transaction.getId());
        values.put(TransactionEntry.COLUMN_NAME_TITLE, transaction.getTitle());
        values.put(TransactionEntry.COLUMN_NAME_DATE, transaction.getDate().getTime());
        values.put(TransactionEntry.COLUMN_NAME_AMOUNT, (long) transaction.getAmount() * 100);

        db.insert(TransactionEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void deleteTransaction(@NonNull String id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = TransactionEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { id };

        db.delete(TransactionEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
}
