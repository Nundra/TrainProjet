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
        this.argent= 0;
        historiqueProduction = new ArrayList<>();
    }
    
    public void reset(){
        score = 0;
        argent = 0;
        getHistoriqueProduction().clear();
    }
    
    public void deduireArgent(int i){
        setArgent(getArgent() - i);
    }
    
    public void upScore(Bien b,int i){
        score = score + (b.getValeur()*i);
        upArgent(b,i);
    }
    
    public void upArgent(Bien b,int i){
        setArgent(getArgent() + (b.getValeur()*i));
    }
    
    public void addHistorique(int qt,Bien b){
        boolean done = false;
        for(Bien b1:historiqueProduction){
            if(b1.getNom().equals(b.getNom())){
                b1.produire(qt);
                done = true;
            }
        }
        if(!done){
            historiqueProduction.add(new Bien(b.getNom(),qt,b.getValeur()));
        }
    }
    
    public void upScoreVille(){
        score += 1000;
        deduireArgent(10000);
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

    /**
     * @return the historiqueProduction
     */
    public ArrayList<Bien> getHistoriqueProduction() {
        return historiqueProduction;
    }
    
    @Override
    public String toString(){
        String str = "";
        for(Bien b:historiqueProduction){
            str += b.toStringAll()+"\n";
        }
        return str;
    }
}
