package com.example.fahrtenbuch.db;

import java.util.Date;

public abstract class ListObject {
    public static final int TYPE_EINTRAG = 0;
    public static final int TYPE_DATE = 1;

    abstract public int getType();

    abstract public Date getDatum();
    abstract public void setDatum(Date date);
}
