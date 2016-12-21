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
    private ArrayList<Bien> composant;
    private ArrayList<Integer> composantQt;
    
    public Bien(String _n, int _q, int _v){
        composant = new ArrayList<>();
        composantQt = new ArrayList<>();
        nom = _n;
        quantite = _q;
        valeur = _v;
    }
    
    @Override
    public String toString(){
        return nom+" "+quantite;
    }
    
    public void addComposant(Bien b,int qt){
        composant.add(b);
        composantQt.add(qt);
    }
    
    public void estFaisable(Ville v){
        //bool b = false;
        for(Bien b:composant){
            for(Bien b2:v.getStock()){
                if(b == b2){
                    if(b2.quantite == composantQt.get(composant.indexOf(b))){
                        System.out.println("COMPOSANT OK");
                    }
                }
            }
        }
        //return false;
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

    void produire() {
        this.quantite++;
    }
    
    void reset(){
        this.quantite = 1;
    }
}
