package com.example.fahrtenbuch.db;


import java.util.Date;

public class ExpenseItem extends ListObject {
    private int expenseId;
    private int expenseAmmount;
    private Date expenseTime;
    private int expenseType;
    private int expenseInterval;


    public ExpenseItem(int expenseId, int expenseAmmount, Date datum, int expenseType, int expenseInterval) {
        this.expenseId = expenseId;
        this.expenseAmmount = expenseAmmount;
        this.expenseTime = datum;
        this.expenseType = expenseType;
        this.expenseInterval = expenseInterval;
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

    public int getExpenseInterval() {
        return expenseInterval;
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
