/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bauden.android.easywallet.R;
import com.bauden.android.easywallet.addedittransaction.AddEditTransactionActivity;
import com.bauden.android.easywallet.transactions.domain.model.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionsFragment extends Fragment implements TransactionsContract.View {

    private TransactionsContract.Presenter mPresenter;

    private TransactionsListAdapter mListAdapter;

    public TransactionsFragment() {
    }

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.transactions_frag, container, false);

        ListView listView = (ListView) root.findViewById(R.id.transactions_list);
        listView.setAdapter(mListAdapter);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addNewTransaction();
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Transaction> tmp = new ArrayList<>(3);
        tmp.add(0, new Transaction("Eat Lunch", new Date(), 60000));
        tmp.add(0, new Transaction("Party", new Date(), 1000000));
        tmp.add(0, new Transaction("Eat Dinner", new Date(), 25000));
        mListAdapter = new TransactionsListAdapter(tmp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void setPresenter(@NonNull TransactionsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showAddNewTransaction() {
        Intent intent = new Intent(getContext(), AddEditTransactionActivity.class);
        startActivityForResult(intent, AddEditTransactionActivity.REQUEST_ADD_NEW_TRANSACTION);
    }

    @Override
    public void showSuccessfullyAddedTransaction() {
        showMessage("New transaction saved.");
    }

    @Override
    public void showTransactions(List<Transaction> transactions) {
        mListAdapter.replaceData(transactions);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    private static class TransactionsListAdapter extends BaseAdapter {

        private List<Transaction> mTransactions;

        public TransactionsListAdapter(List<Transaction> transactions) {
            setList(transactions);
        }

        public void replaceData(List<Transaction> transactions) {
            setList(transactions);
            notifyDataSetChanged();
        }

        private void setList(List<Transaction> transaction) {
            mTransactions = checkNotNull(transaction);
        }

        @Override
        public int getCount() {
            return mTransactions.size();
        }

        @Override
        public Transaction getItem(int i) {
            return mTransactions.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.transaction_item, viewGroup, false);
            }

            final Transaction transaction = getItem(i);
            TextView title = (TextView) rowView.findViewById(R.id.transaction_title);
            title.setText(transaction.getTitle());

            TextView amount = (TextView) rowView.findViewById(R.id.transaction_amount);
            amount.setText(transaction.getAmount() + " - "+ transaction.getDate().toString());

            return rowView;
        }
    }

}
