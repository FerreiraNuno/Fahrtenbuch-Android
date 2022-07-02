package com.example.fahrtenbuch.db;


import java.util.Date;

public class ExpenseItem extends ListObject {
    private int expenseId;
    private int expenseAmmount;
    private Date expenseTime;
    private int expenseType;


    public ExpenseItem(int expenseId, int expenseAmmount, Date datum, int expenseType) {
        this.expenseId = expenseId;
        this.expenseAmmount = expenseAmmount;
        this.expenseTime = datum;
        this.expenseType = expenseType;

    }

    public int getExpenseId() {
        return expenseId;
    }

    public int getExpenseAmmount() {
        return expenseAmmount;
    }

    public int getExpenseType() {
        return expenseType;
    }

    @Override
    public int getType() {
        return TYPE_EINTRAG;
    }

    @Override
    public Date getDatum() {
        return expenseTime;
    }

    @Override
    public void setDatum(Date date) {
        this.expenseTime = date;
    }
}
