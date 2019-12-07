package com.example.tourmate.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.tourmate.pojos.EventExpense;
import com.example.tourmate.pojos.TourmateEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference expenseRef;
    private FirebaseUser firebaseUser;
    public MutableLiveData<List<EventExpense>> expenseLD = new MutableLiveData<>();

    public ExpenseRepository(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(firebaseUser.getUid());
        expenseRef = userRef.child("Expense");
    }

    public void addNewExpenseToRTDB(EventExpense expense){

        String expenseID = expenseRef.push().getKey();
        expense.setExpenseID(expenseID);
        expenseRef.child(expense.getEventID()).child(expenseID).setValue(expense);

    }

    public MutableLiveData<List<EventExpense>> getAllExpensesByEventId(String eventId){
        expenseRef.child(eventId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<EventExpense> eventExpenses = new ArrayList<>();
                for (DataSnapshot d :dataSnapshot.getChildren()){
                    eventExpenses.add(d.getValue(EventExpense.class));
                }
                expenseLD.postValue(eventExpenses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return expenseLD;
    }
}
