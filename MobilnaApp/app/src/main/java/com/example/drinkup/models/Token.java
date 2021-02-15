package com.example.drinkup.models;

import java.io.Serializable;
import java.util.Date;

public class Token implements Serializable {
    public String id;
    public int ponudaId;
    public Ponuda ponuda;
    public Date datumKreiranja;
    public Boolean iskoristen;
    public int korisnikId;
    public String qr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPonudaId() {
        return ponudaId;
    }

    public void setPonudaId(int ponudaId) {
        this.ponudaId = ponudaId;
    }

    public Ponuda getPonuda() {
        return ponuda;
    }

    public void setPonuda(Ponuda ponuda) {
        this.ponuda = ponuda;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Boolean getIskoristen() {
        return iskoristen;
    }

    public void setIskoristen(Boolean iskoristen) {
        this.iskoristen = iskoristen;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
}
