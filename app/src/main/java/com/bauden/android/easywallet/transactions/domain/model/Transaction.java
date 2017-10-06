/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions.domain.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

public final class Transaction {

    @NonNull
    private final String mId;

    @NonNull
    private final String mTitle;

    @NonNull
    private final Date mDate;

    private final double mAmount;

    public Transaction(@NonNull String mId, @NonNull String mTitle, @NonNull Date mDate, double mAmount) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mAmount = mAmount;
    }

    public Transaction(@NonNull String mTitle, @NonNull Date mDate, double mAmount) {
        this(UUID.randomUUID().toString(), mTitle, mDate, mAmount);
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public Date getDate() {
        return mDate;
    }

    public double getAmount() {
        return mAmount;
    }
}
