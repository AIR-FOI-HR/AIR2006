package com.example.drinkup.models;

public class Ponuda {
    private int id;
    private String naslov;
    private String opis;
    private Float cijena;
    private int brojTokena;
    private int vrstaPonude;
    private int objektId;

    public Ponuda() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getVrstaPonude() {
        return vrstaPonude;
    }

    public void setVrstaPonude(int vrstaPonude) {
        this.vrstaPonude = vrstaPonude;
    }

    public int getObjektId() {
        return objektId;
    }

    public void setObjektId(int objektId) {
        this.objektId = objektId;
    }
}
