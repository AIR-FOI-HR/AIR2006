package com.example.drinkup.models;

public class VrstaPonude {
    public int Id;
    public String naziv;

    public VrstaPonude() {}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
