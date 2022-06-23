package com.example.fahrtenbuch.ui.rides;


import java.util.Date;

public class FahrtItem extends ListObject{
    private Date datumBeginn;
    private String von;
    private String ziel;
    private int km;
    private Date datumEnde = null;
    private String ortkategorie;

    public FahrtItem(Date datum, int km) {
        this.datumBeginn = datum;
        this.km = km;
    }

    public FahrtItem(Date datum, String von, String ziel, int km, Date datumEnde) {
        this.datumBeginn = datum;
        this.von = von;
        this.ziel = ziel;
        this.km = km;
        this.datumEnde = datumEnde;
    }
    public FahrtItem(Date datum, String von, String ziel, int km, String ortkategorie) {
        this.datumBeginn = datum;
        this.von = von;
        this.ziel = ziel;
        this.km = km;
        this.ortkategorie = ortkategorie;
    }

    public FahrtItem(Date datum, String von, String ziel, int km, Date datumEnde, String ortkategorie) {
        this.datumBeginn = datum;
        this.von = von;
        this.ziel = ziel;
        this.km = km;
        this.datumEnde = datumEnde;
        this.ortkategorie = ortkategorie;
    }

    public Date getDatum() {
        return datumBeginn;
    }


    public void setDatum(Date datum) {
        this.datumBeginn = datum;
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

    public Date getDatumEnde() {
        return datumEnde;
    }

    public void setDatumEnde(Date datumEnde) {
        this.datumEnde = datumEnde;
    }

    public String getOrtkategorie() {
        return ortkategorie;
    }

    public void setOrtkategorie(String ortkategorie) {
        this.ortkategorie = ortkategorie;
    }

    @Override
    public int getType() {
        return TYPE_FAHRT;
    }
}
