/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction.domain.usecase;

import android.support.annotation.NonNull;

import com.bauden.android.easywallet.UseCase;
import com.bauden.android.easywallet.data.source.TransactionsDataSource;
import com.bauden.android.easywallet.data.source.TransactionsRepository;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetTransaction extends UseCase<GetTransaction.RequestValues, GetTransaction.ResponseValue> {

    private final TransactionsRepository mTransactionsRepository;

    public GetTransaction(@NonNull TransactionsRepository transactionsRepository) {
        checkNotNull(transactionsRepository);
        mTransactionsRepository = transactionsRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mTransactionsRepository.getTransaction(requestValues.getTransactionId(), new TransactionsDataSource.GetTransactionCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                if (transaction != null) {
                    ResponseValue responseValue = new ResponseValue(transaction);
                    getUseCaseCallback().onSuccess(responseValue);
                } else {
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String mTransactionId;

        public RequestValues(@NonNull String transactionId) {
            checkNotNull(transactionId);
            mTransactionId = transactionId;
        }

        public String getTransactionId() {
            return mTransactionId;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private Transaction mTransaction;

        public ResponseValue(@NonNull Transaction transaction) {
            checkNotNull(transaction);
            mTransaction = transaction;
        }

        public Transaction getTransaction() {
            return mTransaction;
        }

    }
}
