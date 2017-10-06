/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransactionsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "transactions.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String DATETIME_TYPE = " INTEGER";

    private static final String CURRENCY_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TransactionsPersistenceContract.TransactionEntry.TABLE_NAME + " (" +
                    TransactionsPersistenceContract.TransactionEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    TransactionsPersistenceContract.TransactionEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    TransactionsPersistenceContract.TransactionEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TransactionsPersistenceContract.TransactionEntry.COLUMN_NAME_DATE + DATETIME_TYPE + COMMA_SEP +
                    TransactionsPersistenceContract.TransactionEntry.COLUMN_NAME_AMOUNT + CURRENCY_TYPE +
                    " )";

    public TransactionsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
