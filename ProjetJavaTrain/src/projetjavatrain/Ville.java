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
public class Ville {
    private Bien typeRessource;
    private ArrayList<Bien> stock;
    private int vitesse;
    private int lvl;
    
    public Ville(Bien b){
        typeRessource = b;
        stock = new ArrayList<>();
        vitesse = 1;
        lvl = 1;
    }
    
    public void produire(){
        if(typeRessource !=null){
            stock.add(typeRessource);
        }
    }
    
    public void upgradeVille(Joueur j){
        if(j.getArgent() >= getCoutDeUp()){
            j.deduireArgent(getCoutDeUp());
            lvl++;
            vitesse++;
        }
    }
    
    public int getCoutDeUp(){
        return 100*lvl*lvl;
    }
}
