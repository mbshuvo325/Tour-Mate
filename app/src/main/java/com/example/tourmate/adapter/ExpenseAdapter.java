package com.example.tourmate.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate.R;
import com.example.tourmate.pojos.EventExpense;
import com.example.tourmate.viewmodels.ExpenseViewModel;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private Context context;
    private List<EventExpense> expenseList;
    private ExpenseViewModel expenseViewModel = new ExpenseViewModel();

    public ExpenseAdapter(Context context, List<EventExpense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.expense_row,parent,false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        holder.expenseName.setText(expenseList.get(position).getExpenseName());
        holder.expenseAmount.setText(String.valueOf(expenseList.get(position).getExpenseAmount()));
        holder.expenseDate.setText(expenseList.get(position).getExpenseDate());
        final EventExpense expense = expenseList.get(position);
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater()
                        .inflate(R.menu.expense_row_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.expenseEdit:

                                break;
                            case R.id.expenseDelete:
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Delete Expense..!");
                                builder.setMessage("Do you want to delete expense..?");
                                builder.setIcon(R.drawable.ic_delete_24dp);
                                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        expenseViewModel.DeleteExpense(expense);
                                        final AlertDialog dialog = builder.create();
                                        dialog.dismiss();
                                        Toast.makeText(context, "Successfully delete", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        final AlertDialog dialog = builder.create();
                                        dialog.dismiss();
                                    }
                                });
                                final AlertDialog dialog = builder.create();
                                dialog.show();

                                break;
                        }

                        return true;
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder{
        private TextView expenseName,expenseAmount,expenseDate,info;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.expenseName);
            expenseAmount = itemView.findViewById(R.id.expenseAmount);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            info = itemView.findViewById(R.id.expense_info_btn);
        }
    }
}
