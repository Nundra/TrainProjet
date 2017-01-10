/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author Nundra
 */
public class Ville extends Decors{
    private String nom;
    private Bien typeRessource;
    private ArrayList<Bien> stock;
    private int vitesse;
    private int lvl;
    private Joueur j;

    public Ville(String nom, int x, int y, Image img, boolean b, Bien bien, Joueur j) {
        super(x, y, img, b);
        this.nom = nom;
        stock = new ArrayList<>();
        vitesse = 1;
        lvl = 1;
        this.typeRessource = bien;
        stock.add(typeRessource);
        this.j=j;
    }
    
    public int getLvl(){
        return this.lvl;
    }
    public int getVitesse(){
        return this.vitesse;
    }
    
    public void produire(){
        typeRessource.produire(vitesse);
        j.upScore(typeRessource,vitesse);
        j.addHistorique(vitesse,typeRessource);
    }
    
    public ArrayList<Bien> getStock(){
        return stock;
    }
    
    public String afficherStock(){
        String str = "Stock ville en "+getX()+" "+getY()+" - ";
        for(Bien b:stock){
            str += b.toString()+" - ";
        }
        return str;
    }
    
    public boolean isUp(Joueur j){
        if(j.getArgent() >= getCoutDeUp()){
            return true;
        }else{
            return false;
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
        return 1000*lvl*lvl;
    }

    public Bien getTypeRessource() {
        return typeRessource;
    }
    
    public void charger(Train t) {
        int compteur = 0;
        while(!t.isFull()){
            for(Bien b:stock){
                if(b.getQuantite() > 0){
                    t.addCharge(new Bien(b.getNom(),1,b.getValeur()));
                    b.produire(-1);
                }else{
                    compteur++;
                }
            }
            if(compteur >= t.getCharge().length){
                break;
            }
        }
    }
    
    public void decharger(Train t) {
        Bien[] temp = t.getCharge();
        if(t.isCharged()){
            for(int i = 0; i<temp.length;i++){
                if(temp[i] != null){
                    for(Bien b:stock){
                        if(b.getNom().equals(temp[i].getNom())){
                            b.produire(1);
                            temp[i]=null;
                            break;
                        }
                    }
                    if(temp[i] != null){
                        stock.add(temp[i]);
                        temp[i]=null;
                    }
                }
            }
        }
        
        charger(t);
    }
    
    public Joueur getJ(){
        return this.j;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    @Override
    public String toString(){
       String str = "";
       str += this.nom+"\nstock : \n";
       for(Bien b:stock){
           str += b.toStringAll()+"\n";
       }
       return str;
    }
}
