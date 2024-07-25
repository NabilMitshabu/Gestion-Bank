package com.example.bankapp;

import javafx.beans.value.ObservableValue;

public class Enregistrement {
    
    public  String num, titulaire,solde, type, decouverte, taux;

    public Enregistrement(String num, String titulaire, String solde, String type, String decouverte, String taux) {
        this.num = num;
        this.titulaire = titulaire;
        this.solde = solde;
        this.type = type;
        this.decouverte = decouverte;
        this.taux = taux;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDecouverte() {
        return decouverte;
    }

    public void setDecouverte(String decouverte) {
        this.decouverte = decouverte;
    }

    public String getTaux() {
        return taux;
    }

    public void setTaux(String taux) {

    }

    public ObservableValue<String> nomProperty() {
        return null;
    }

    public ObservableValue<String> prenomProperty() {
        return null;
    }
}
