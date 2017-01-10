/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import java.util.ArrayList;

/**
 *
 * @author Nundra
 */
public class Bien {
    private String nom;
    private int quantite;
    private int valeur;
    private ArrayList<String> composant;
    private ArrayList<Integer> composantQt;
    private ArrayList<Boolean> composantValid;
    
    public Bien(String _n, int _q, int _v){
        composant = new ArrayList<>();
        composantQt = new ArrayList<>();
        composantValid = new ArrayList<>();
        nom = _n;
        quantite = _q;
        valeur = _v;
    }
    
    public void addComposant(String b,int qt){
        composant.add(b);
        composantQt.add(qt);
        composantValid.add(false);
    }
    
    public boolean estFaisable(Usine u){
        boolean bool = true;
        for(String b:composant){
            for(Bien b2:u.getDepot()){
                if(b.equals(b2.getNom())){
                    if(b2.quantite >= composantQt.get(composant.indexOf(b))){
                        composantValid.set((composant.indexOf(b)), true);
                    }
                }
            }
        }
        
        for(boolean bool2:composantValid){
            if(bool2 == false)bool = bool2;
        }
        composantValid.clear();
        for(String b:composant){
            composantValid.add(false);
        }
        return bool;
    }
    
    public int getQtNeeded(Bien b){
        int total = 0;
        for(String b2:composant){
            if(b2.equals(b.getNom())){
                total = (composantQt.get(composant.indexOf(b2)));
            }
        }
        return total;
    }
    
    public Boolean need(Bien b){
        for(String b2:composant){
            if(b2.equals(b.getNom()))return true;
        }
        return false;
    }
    
    /**
     * @return the valeur
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * @param valeur the valeur to set
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /**
     * @return the quantite
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * @param quantite the quantite to set
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public void retirerQuantite(int qt) {
        this.quantite = quantite - qt;
    }

    void produire(int i) {
        this.quantite = quantite + i;
    }
    
    void reset(){
        this.quantite = 1;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public ArrayList<String> listeComposant(){
        if(!composant.isEmpty())return composant;
        return null;
    }
    
    
    @Override
    public String toString(){
        return nom;
    }
    public String toStringAll(){
        return nom+" qté : "+quantite;
    }
}
