package com.example.tourmate.pojos;

public class EventExpense {
    private String expenseID;
    private String eventID;
    private String expenseName;
    private Double expenseAmount;
    private String  expenseDate;

    public EventExpense() {
        ///required
    }

    public EventExpense(String expenseID, String eventID, String expenseName, Double expenseAmount, String expenseDate) {
        this.expenseID = expenseID;
        this.eventID = eventID;
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
