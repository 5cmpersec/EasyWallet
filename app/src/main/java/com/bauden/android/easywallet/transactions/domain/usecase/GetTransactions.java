/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions.domain.usecase;

import android.support.annotation.NonNull;

import com.bauden.android.easywallet.UseCase;
import com.bauden.android.easywallet.data.source.TransactionsDataSource;
import com.bauden.android.easywallet.data.source.TransactionsRepository;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetTransactions extends UseCase<GetTransactions.RequestValues, GetTransactions.ResponseValue> {

    private final TransactionsRepository mTransactionsRepository;

    public GetTransactions(@NonNull TransactionsRepository transactionsRepository) {
        mTransactionsRepository = checkNotNull(transactionsRepository);
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        mTransactionsRepository.getTransactions(new TransactionsDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                getUseCaseCallback().onSuccess(new ResponseValue(transactions));
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        public RequestValues() {

        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Transaction> mTransactions;

        public ResponseValue(@NonNull List<Transaction> transactions) {
            mTransactions = checkNotNull(transactions);
        }

        public List<Transaction> getTransactions() {
            return mTransactions;
        }
    }
}
