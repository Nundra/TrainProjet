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
public class Joueur {
    private int score;
    private int argent;
    private ArrayList<Bien> historiqueProduction;
    
    public Joueur(){
        score = 0;
        setArgent(0);
        historiqueProduction = new ArrayList<>();
    }
    
    public void reset(){
        score = 0;
        setArgent(0);
        historiqueProduction.clear();
    }
    
    public void deduireArgent(int i){
        setArgent(getArgent() - i);
    }
    
    public void upScore(Bien b){
        score = score + b.getValeur();
        upArgent(b);
    }
    
    public void upArgent(Bien b){
        setArgent(getArgent() + b.getValeur());
    }

    /**
     * @return the argent
     */
    public int getArgent() {
        return argent;
    }

    /**
     * @param argent the argent to set
     */
    public void setArgent(int argent) {
        this.argent = argent;
    }
}
