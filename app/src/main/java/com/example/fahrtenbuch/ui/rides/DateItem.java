package com.example.fahrtenbuch.ui.rides;

import java.util.Date;

public class DateItem extends ListObject {
    private Date date;

    DateItem(Date date) {
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