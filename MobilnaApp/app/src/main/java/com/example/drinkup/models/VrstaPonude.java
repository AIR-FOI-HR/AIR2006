package com.example.drinkup.models;

import java.io.Serializable;

public class VrstaPonude implements Serializable {
    public int id;
    public String naziv;

    public VrstaPonude() {}

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
}
