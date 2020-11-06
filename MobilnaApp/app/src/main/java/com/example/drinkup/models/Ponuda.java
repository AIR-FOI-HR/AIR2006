package com.example.drinkup.models;

public class Ponuda {
    public int Id;
    public String naslov;
    public String opis;
    public Float cijena;
    public int brojTokena;
    public String vrstaPonude;

    public Ponuda() {}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Float getCijena() {
        return cijena;
    }

    public void setCijena(Float cijena) {
        this.cijena = cijena;
    }

    public int getBrojTokena() {
        return brojTokena;
    }

    public void setBrojTokena(int brojTokena) {
        this.brojTokena = brojTokena;
    }

    public String getVrstaPonude() {
        return vrstaPonude;
    }

    public void setVrstaPonude(String vrstaPonude) {
        this.vrstaPonude = vrstaPonude;
    }
}
