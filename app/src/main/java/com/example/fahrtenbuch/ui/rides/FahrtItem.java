package com.example.fahrtenbuch.ui.rides;


import java.util.Date;

public class FahrtItem {

    private Date datum = null;
    private String von = "";
    private String ziel = "";
    private int km = 27;
    private int dauer = 0;
    private String ortkategorie = "";

    public FahrtItem(Date datum, String von, String ziel, int km) {
        this.datum = datum;
        this.von = von;
        this.ziel = ziel;
        this.km = km;
    }

    public FahrtItem(Date datum, String von, String ziel, int km, int dauer) {
        this.datum = datum;
        this.von = von;
        this.ziel = ziel;
        this.km = km;
        this.dauer = dauer;
    }
    public FahrtItem(Date datum, String von, String ziel, int km, String ortkategorie) {
        this.datum = datum;
        this.von = von;
        this.ziel = ziel;
        this.km = km;
        this.ortkategorie = ortkategorie;
    }

    public FahrtItem(Date datum, String von, String ziel, int km, int dauer, String ortkategorie) {
        this.datum = datum;
        this.von = von;
        this.ziel = ziel;
        this.km = km;
        this.dauer = dauer;
        this.ortkategorie = ortkategorie;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getVon() {
        return von;
    }

    public void setVon(String von) {
        this.von = von;
    }

    public String getZiel() {
        return ziel;
    }

    public void setZiel(String ziel) {
        this.ziel = ziel;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getDauer() {
        return dauer;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public String getOrtkategorie() {
        return ortkategorie;
    }

    public void setOrtkategorie(String ortkategorie) {
        this.ortkategorie = ortkategorie;
    }
}
