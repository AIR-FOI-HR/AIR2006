package com.example.drinkup.models;

import java.io.Serializable;

public class Objekt implements Serializable
{
    public int id;
    public String naziv;
    public String grad;
    public String ulica;
    public String adresa;
    public String radnoVrijeme;
    public String kontakt;
    public Float longituda;
    public Float latituda;
    public boolean aktivan;

    public Objekt() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getRadnoVrijeme() {
        return radnoVrijeme;
    }

    public void setRadnoVrijeme(String radnoVrijeme) {
        this.radnoVrijeme = radnoVrijeme;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public Float getLongituda() {
        return longituda;
    }

    public void setLongituda(Float longituda) {
        this.longituda = longituda;
    }

    public Float getLatituda() {
        return latituda;
    }

    public void setLatituda(Float latituda) {
        this.latituda = latituda;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }
}
