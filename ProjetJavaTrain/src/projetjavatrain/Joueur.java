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
        this.argent= 100000;
        historiqueProduction = new ArrayList<>();
    }
    
    public void reset(){
        score = 0;
        argent = 10000;
        historiqueProduction.clear();
    }
    
    public void deduireArgent(int i){
        setArgent(getArgent() - i);
    }
    
    public void upScore(Bien b,int i){
        System.out.println(""+i);
        score = score + (b.getValeur()*i);
        upArgent(b,i);
    }
    
    public void upArgent(Bien b,int i){
        setArgent(getArgent() + (b.getValeur()*i));
    }

    /**
     * @return the argent
     */
    public int getArgent() {
        return argent;
    }
    public int getScore() {
        return score;
    }

    /**
     * @param argent the argent to set
     */
    public void setArgent(int argent) {
        this.argent = argent;
    }
}
