/*
 * Copyright 2017, The EasyWallet Project
 *
 */

package com.bauden.android.easywallet.addedittransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bauden.android.easywallet.R;
import com.bauden.android.easywallet.transactions.TransactionsContract;
import com.google.common.base.Strings;
import com.kyleduo.switchbutton.SwitchButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditTransactionFragment extends Fragment implements AddEditTransactionContract.View {

    private AddEditTransactionContract.Presenter mPresenter;

    private EditText mTitle;

    private EditText mAmount;

    private TextView mTransactionType;

    private Button mBtnDate;

    private Date mCurrentDate;

    private SwitchButton mSwitch;

    private boolean mIsIncome;

    public AddEditTransactionFragment() {}

    public static AddEditTransactionFragment newInstance() {
        return new AddEditTransactionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addtransaction_frag, container, false);

        mTitle = (EditText) root.findViewById(R.id.description_edittext);
        mAmount = (EditText) root.findViewById(R.id.amount_edittext);
        mIsIncome = false;

        mCurrentDate = new Date();
        mBtnDate = (Button) root.findViewById(R.id.date_button);
        mBtnDate.setText(formatDate(mCurrentDate));
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(mCurrentDate);
                mBtnDate.setText(formatDate(mCurrentDate));
            }
        });

        mSwitch = (SwitchButton) root.findViewById(R.id.transaction_type_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
                mIsIncome = isChecked;
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_transaction_done);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                trySaveTransaction();
            }
        });
        fab.setImageResource(R.drawable.ic_done_24dp);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddEditTransactionContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showTransactionsList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDate(Date date) {
        mCurrentDate = date;
        mBtnDate.setText(formatDate(date));
    }

    @Override
    public void setAmount(double amount) {
        mAmount.setText(String.format(Locale.getDefault(), "%.2f", Math.abs(amount)));
    }

    @Override
    public void setTransactionIsIncome(boolean isIncome) {
        mIsIncome = isIncome;
        mSwitch.setChecked(mIsIncome);
    }

    @Override
    public void showTransactionError() {
        showMessage("Transaction Error");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showDatePickerDialog(final Date date) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mCurrentDate = cal.getTime();
                mBtnDate.setText(formatDate(mCurrentDate));
            }
        };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                R.style.DatePickerTheme,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private String formatDate(final Date date) {
        SimpleDateFormat formatter =
                new SimpleDateFormat(getResources().getString(R.string.transaction_date_format),
                Locale.getDefault());
        return formatter.format(date);
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    private void trySaveTransaction() {
        if (Strings.isNullOrEmpty(mTitle.getText().toString().trim()) ||
                Strings.isNullOrEmpty(mAmount.getText().toString().trim())) {
            showMessage("Empty Input is not allowed");
        } else {

            try {
                Double amount = Double.parseDouble(mAmount.getText().toString());
                if (!mIsIncome) {
                    amount = amount * (-1);
                }
                mPresenter.saveTransaction(mTitle.getText().toString().trim(),
                        mCurrentDate, amount);
            } catch (NumberFormatException e) {
                showMessage("Can not parse amount");
            }

        }
    }

}
