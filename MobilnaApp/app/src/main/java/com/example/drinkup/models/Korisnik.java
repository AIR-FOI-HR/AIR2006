package com.example.drinkup.models;

public class Korisnik {
    public int Id;
    public String OIB;
    public String ime;
    public String prezime;
    public String email;
    public String lozinka;
    public int spol;
    public int ulogaID;
    public boolean status;

    public Korisnik() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOIB() {
        return OIB;
    }

    public void setOIB(String OIB) {
        this.OIB = OIB;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public int getSpol() {
        return spol;
    }

    public void setSpol(int spol) {
        this.spol = spol;
    }

    public int getUlogaID() {
        return ulogaID;
    }

    public void setUlogaID(int ulogaID) {
        this.ulogaID = ulogaID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}