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
public class Rail extends Decors{
    private int chemin[];
    private static int idTotal;
    private int id;
    public Rail(int x, int y, Image img, boolean b) {
        super(x, y, img, b);
        id = 0;
        chemin = new int[4];
    }

    public void setupChemin(int i,int j,int x,int y){
        if(i != -1)this.chemin[0]=i;
        if(j != -1)this.chemin[1]=j;
        if(x != -1)this.chemin[2]=x;
        if(y != -1)this.chemin[3]=y;
    }
    
    public void updateId(){
        id = idTotal;
    }
    
    public void newId(){
        idTotal++;
    }
    
    public static void resetId(){
        idTotal = 0;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the chemin
     */
    public int[] getChemin() {
        return chemin;
    }
}
