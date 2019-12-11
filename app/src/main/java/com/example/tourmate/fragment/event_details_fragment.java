package com.example.tourmate.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tourmate.R;
import com.example.tourmate.adapter.ExpenseAdapter;
import com.example.tourmate.helper.EventUtils;
import com.example.tourmate.pojos.EventExpense;
import com.example.tourmate.viewmodels.ExpenseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class event_details_fragment extends Fragment {

    private RecyclerView expenseRV;
    private ExpenseAdapter adapter;
    private FloatingActionButton addExpense;
    private String  eventId = null;
    private ExpenseViewModel expenseViewModel;
    private TextView showTotalExpense;


    public event_details_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null){
            eventId = bundle.getString("id");
        }
        expenseViewModel.getAllExpenes(eventId);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showTotalExpense = view.findViewById(R.id.totalExpense);


        expenseViewModel.expenseLD.observe(this, new Observer<List<EventExpense>>() {
            @Override
            public void onChanged(List<EventExpense> eventExpenses) {

                expenseRV = view.findViewById(R.id.expenseRV);
                adapter = new ExpenseAdapter(getActivity(),eventExpenses);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                expenseRV.setLayoutManager(llm);
                expenseRV.setAdapter(adapter);
                Double totalExpense = 0.0;
                for (EventExpense ex: eventExpenses){
                    totalExpense += ex.getExpenseAmount();
                }
                showTotalExpense.setText(String.valueOf(totalExpense));
            }
        });
        expenseRV = view.findViewById(R.id.expenseRV);
        addExpense = view.findViewById(R.id.add_expense);

        ///expenseAdding
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialogview = inflater.inflate(R.layout.add_expense_dialog,null);
                builder.setTitle("Add Expense");
                builder.setIcon(R.drawable.ic_add_expense_24dp);

                final EditText expenseName = dialogview.findViewById(R.id.expense_name);
                final EditText expenseAmount = dialogview.findViewById(R.id.expense_Amount);
                final Button saveExpense = dialogview.findViewById(R.id.save_expense);
                final Button Cancel = dialogview.findViewById(R.id.cancel_dialog);
                builder.setView(dialogview);
                final AlertDialog dialog = builder.create();
                dialog.show();

                saveExpense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = expenseName.getText().toString();
                        String Amount = expenseAmount.getText().toString();
                        if (name.isEmpty()){
                            expenseName.setError("Expense Name Should Not Empty!");
                        }
                        else if (Amount.isEmpty()){
                            expenseAmount.setError("Amount Should Not Empty!");
                        }
                        else {
                            EventExpense expense = new EventExpense(null,eventId,name,Double.valueOf(Amount),
                                    EventUtils.getCurrentDate());
                            expenseViewModel.saveExpense(expense);
                            dialog.dismiss();
                        }
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
