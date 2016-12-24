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

    private Bien typeRessource;
    private ArrayList<Bien> stock;
    private ArrayList<Bien> depot;
    private int vitesse;
    private int lvl;
    private Joueur j;

    public Usine(int x, int y, Image img, boolean b, Bien bien, Joueur j) {
        super(x, y, img, b, bien, j);
        depot = new ArrayList<>();
        vitesse = 1;
        lvl = 1;
        this.typeRessource = bien;
        stock.add(typeRessource);
        this.j=j;
    }
    
    @Override
    public void produire() {
        if (typeRessource.estFaisable(this)) {
            for (Bien b:stock) {
                b.setQuantite(b.getQuantite()-b.getQtNeeded(typeRessource));
                b.produire(5);
            }
        }
        j.upScore(typeRessource);
    }
    
    @Override
    public void decharger(Train t) {
        for (Bien b:t.getCharge()) {
            if (b == typeRessource) {
                
            }
        }
    }


}
