package com.example.drinkup.models;

import java.util.Date;

public class Kod {
    public int id;
    public Date datumKreiranja;

    public Kod() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }
}
