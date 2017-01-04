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
 * @author Nicolas
 */
public class Usine extends Ville{
    private ArrayList<Bien> depot;

    public Usine(int x, int y, Image img, boolean b, Bien bien, Joueur j) {
        super(x, y, img, b, bien, j);
        depot = new ArrayList<>();
    }
    
    @Override
    public void produire() {
        if (getTypeRessource().estFaisable(this)) {
            for (Bien b:depot) {
                b.retirerQuantite(getTypeRessource().getQtNeeded(b));
            }
            getTypeRessource().produire(5);
            System.out.println("pot produit");
            System.out.println("pot "+getTypeRessource().getQuantite());
        }
        getJ().upScore(getTypeRessource());
    }
    
    @Override
    public void decharger(Train t) {
        Bien[] temp = t.getCharge();
        if(t.isCharged()){
            for(int i = 0; i<temp.length;i++){
                if(temp[i] != null){
                    if(getTypeRessource().need(temp[i])){
                            for(Bien b:depot){
                                if(b.getNom().equals(temp[i].getNom())){
                                    b.produire(1);
                                    temp[i]=null;
                                    break;
                                }
                            }
                            if(temp[i] != null){
                                depot.add(temp[i]);
                                temp[i]=null;
                            }
                    }else{
                        for(Bien b:getStock()){
                            if(b.getNom().equals(temp[i].getNom())){
                                b.produire(1);
                                temp[i]=null;
                                break;
                            }
                        }
                        if(temp[i] != null){
                            getStock().add(temp[i]);
                            temp[i]=null;
                        }
                    }
                }
            }
        }
        
        charger(t);
    }
    
    public ArrayList<Bien> getDepot(){
        return this.depot;
    }

}
