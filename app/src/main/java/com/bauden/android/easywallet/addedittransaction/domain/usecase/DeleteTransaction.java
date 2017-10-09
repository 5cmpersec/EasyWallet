/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction.domain.usecase;

import android.support.annotation.NonNull;

import com.bauden.android.easywallet.UseCase;
import com.bauden.android.easywallet.data.source.TransactionsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class DeleteTransaction extends UseCase<DeleteTransaction.RequestValues, DeleteTransaction.ResponseValue> {

    private final TransactionsRepository mTransactionRepository;

    public DeleteTransaction(@NonNull TransactionsRepository transactionsRepository) {
        checkNotNull(transactionsRepository);
        mTransactionRepository = transactionsRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mTransactionRepository.deleteTransaction(requestValues.getTransactionId());

        getUseCaseCallback().onSuccess(new ResponseValue());
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

    }
}
