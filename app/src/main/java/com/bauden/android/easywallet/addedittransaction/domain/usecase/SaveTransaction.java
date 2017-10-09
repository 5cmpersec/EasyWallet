/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction.domain.usecase;

import android.support.annotation.NonNull;

import com.bauden.android.easywallet.UseCase;
import com.bauden.android.easywallet.data.source.TransactionsRepository;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import static com.google.common.base.Preconditions.checkNotNull;

public class SaveTransaction extends UseCase<SaveTransaction.RequestValues, SaveTransaction.ResponseValue> {

    private final TransactionsRepository mTransactionsRepository;

    public SaveTransaction(@NonNull TransactionsRepository transactionsRepository) {
        checkNotNull(transactionsRepository);
        mTransactionsRepository = transactionsRepository;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        Transaction transaction = requestValues.getTransaction();
        mTransactionsRepository.saveTransaction(transaction);

        getUseCaseCallback().onSuccess(new ResponseValue(transaction));
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final Transaction mTransaction;

        public RequestValues(@NonNull Transaction transaction) {
            checkNotNull(transaction);
            mTransaction = transaction;
        }

        public Transaction getTransaction() {
            return mTransaction;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Transaction mTransaction;

        public ResponseValue(@NonNull Transaction transaction) {
            checkNotNull(transaction);
            mTransaction = transaction;
        }

        public Transaction getTransaction() {
            return mTransaction;
        }
    }
}
