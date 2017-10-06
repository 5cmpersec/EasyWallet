/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
