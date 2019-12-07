package com.example.tourmate.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate.pojos.EventExpense;
import com.example.tourmate.repos.ExpenseRepository;

import java.util.List;

public class ExpenseViewModel extends ViewModel {
    private ExpenseRepository repository;
    public MutableLiveData<List<EventExpense>> expenseLD = new MutableLiveData<>();

    public ExpenseViewModel() {
        repository = new ExpenseRepository();
    }
    public void saveExpense(EventExpense expense){
        repository.addNewExpenseToRTDB(expense);
    }

    public void getAllExpenes(String eventId){
        expenseLD = repository.getAllExpensesByEventId(eventId);
    }
}
