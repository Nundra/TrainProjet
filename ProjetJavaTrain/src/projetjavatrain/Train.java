/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import javafx.scene.image.Image;

/**
 *
 * @author Nundra
 */
public class Train extends Decors{
    private int capacite;
    private int vitesse;
    private int niveau;
    private int coutUpgrade;
    private int sens;
    private int idLigne;
    private boolean deplace;
    
    public Train(int x, int y, Image img, boolean b, int id) {
        super(x, y, img, b);
        this.capacite = 5;
        this.vitesse = 1;
        this.niveau = 1;
        this.coutUpgrade = 100;
        this.sens = 5;
        this.idLigne = id;
        deplace = false;
    }
    
    public String upgradeTrain(Joueur j){
        if(j.getArgent() >= coutUpgrade){
            j.deduireArgent(coutUpgrade);
            this.niveau++;
            this.capacite = capacite*niveau;
            this.vitesse++;
            this.coutUpgrade = 100*niveau*niveau;
            return "Train upgrade";
        }
        return "fond insuffisant";
    }
    
    public void changerSens(){
        System.out.println("old sens : "+sens);
        if(sens == 6)sens = 5;
            else if(sens == 5) sens = 6;
        System.out.println("Sens changé à : "+sens);
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
}
