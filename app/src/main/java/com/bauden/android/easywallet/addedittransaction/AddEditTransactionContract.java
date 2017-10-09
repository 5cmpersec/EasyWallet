/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import com.bauden.android.easywallet.BasePresenter;
import com.bauden.android.easywallet.BaseView;

import java.util.Date;

public interface AddEditTransactionContract {

    interface View extends BaseView<Presenter> {

        void showTransactionsList();

        void setTitle(String title);

        void setDate(Date date);

        void setAmount(double amount);
    }

    interface Presenter extends BasePresenter {

        void saveTransaction(String title, Date date, double amount);

        void populateTransactions();
    }
}
