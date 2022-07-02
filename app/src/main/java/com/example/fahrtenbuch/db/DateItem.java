package com.example.fahrtenbuch.db;

import java.util.Date;

public class DateItem extends ListObject {
    private Date date;

    public DateItem(Date date) {
        this.date = date;
    }

    public Date getDatum() {
        return date;
    }

    public void setDatum(Date date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }

}