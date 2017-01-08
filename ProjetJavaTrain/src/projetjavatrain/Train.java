/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import static projetjavatrain.ProjetJavaTrain.tm;

/**
 *
 * @author Nundra
 */
public class Train extends Decors{
    private int capacite;
    private int vitesse;
    private int lvl;
    private int coutUpgrade;
    private int sens;
    private int idLigne;
    private boolean deplace;
    private KeyFrame timeline;
    private Bien[] charge;
    private Rail r;
    
    public Train(int x, int y, Image img, boolean b, int id) {
        super(x, y, img, b);
        this.capacite = 5;
        this.vitesse = 5;
        this.lvl = 1;
        this.coutUpgrade = 10;
        this.sens = 5;
        this.idLigne = id;
        deplace = false;
        this.charge = new Bien[capacite];
            for(int i=0;i<charge.length;i++){
                charge[i] = null;
            }
    }
    
    public void setRail(Rail r){
        this.r=r;
    }
    public Rail getRail(){
        return r;
    }
    public boolean isUp(Joueur j){
        if(vitesse == 1){
            return false;
        }
        if(j.getArgent() >= getCoutDeUp()){
            return true;
        }else{
            return false;
        }
    }
    
    public int getCoutDeUp(){
        return 100*lvl*lvl;
    }
    
    public int getLvl(){
        return this.lvl;
    }
    
    public String upgradeTrain(Joueur j){
        if(isUp(j)){
            j.deduireArgent(coutUpgrade);
            this.lvl++;
            this.capacite = capacite*lvl;
            this.charge = new Bien[capacite];
                for(int i=0;i<charge.length;i++){
                    charge[i] = new Bien("",0,0);
                }
            this.vitesse--;
            this.coutUpgrade = 100*lvl*lvl;
            return "Train upgrade";
        }
        return "fond insuffisant";
    }
    
    public void changerSens(){
        if(sens == 6)sens = 5;
            else if(sens == 5) sens = 6;
    }
    
    public int getNewSens(){
        if(sens == 6)return 5;
            else return  6;
    }
    
    public int getSens(){
        return sens;
    }
    
    public int getIdLigne(){
        return idLigne;
    }
    
    public void setNewCoord(int x, int y){
        setX(x);
        setY(y);
    }
    public boolean estDeplace(){
        return deplace;
    }
    public void setDeplace(boolean b){
        deplace = b;
    }
    
    public void newTimeline(KeyFrame time){
        this.timeline=time;
    }
    
    public KeyFrame getTimeline(){
        return timeline;
    }
    
    public int getVitesse(){
        return vitesse;
    }

    /**
     * @return the charge
     */
    public Bien[] getCharge() {
        return charge;
    }

    /**
     * @param charge the charge to set
     */
    public void setCharge(Bien[] charge) {
        this.charge = charge;
    }
    
    public Boolean isFull(){
        for(int i = 0; i < charge.length; i++){
            if(charge[i] == null){
                return false;
            }
        }
        return true;
    }
    
    public Boolean isCharged(){
        for(int i = 0; i < charge.length; i++){
            if(charge[i] != null){
                return true;
            }
        }
        return false;
    }
    
    public void addCharge(Bien t){
        for(int i = 0; i < charge.length; i++){
            if(charge[i] == null){
                charge[i] = t;
                break;
            }
        }
    }
}
