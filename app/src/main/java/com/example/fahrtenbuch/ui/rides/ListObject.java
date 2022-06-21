package com.example.fahrtenbuch.ui.rides;

import java.util.Date;

public abstract class ListObject {
    public static final int TYPE_FAHRT = 0;
    public static final int TYPE_DATE = 1;

    abstract public int getType();

    abstract public Date getDatum();
    abstract public void setDatum(Date date);
}
