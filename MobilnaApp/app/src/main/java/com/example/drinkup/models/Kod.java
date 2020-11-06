package com.example.drinkup.models;

import java.util.Date;

public class Kod {
    public int Id;
    public Date datumKreiranja;

    public Kod() {}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }
}
