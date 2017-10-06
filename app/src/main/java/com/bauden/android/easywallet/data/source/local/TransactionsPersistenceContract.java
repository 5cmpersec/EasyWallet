/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.data.source.local;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the transactions locally.
 */
public class TransactionsPersistenceContract {

    private TransactionsPersistenceContract() {}

    public static abstract class TransactionEntry implements BaseColumns {
        public static final String TABLE_NAME = "transaction";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }
}
