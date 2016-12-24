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
    private Bien typeRessource;
    private ArrayList<Bien> stock;
    private int vitesse;
    private int lvl;
    private Joueur j;

    public Ville(int x, int y, Image img, boolean b, Bien bien, Joueur j) {
        super(x, y, img, b);
        stock = new ArrayList<>();
        vitesse = 1;
        lvl = 1;
        this.typeRessource = bien;
        stock.add(typeRessource);
        this.j=j;
    }
    
    public void produire(){
        if(typeRessource !=null){
            for(Bien b:stock){
                b.produire();
            }
            j.upScore(typeRessource);
        }
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

    public Bien getTypeRessource() {
        return typeRessource;
    }
}
