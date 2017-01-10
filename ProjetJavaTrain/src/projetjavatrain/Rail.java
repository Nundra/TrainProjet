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
    private static int idTotal;
    private int id;
    private boolean railTeste; // servira a tester une ligne de rail
    private String orientation;
    
    public Rail(int x, int y, Image img, boolean b) {
        super(x, y, img, b);
        this.id = 0;
        this.railTeste = false;
    }
    
    public void setOrientation(String str){
        this.orientation=str;
    }
    public String getOrientation(){
        return orientation;
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

    public boolean estTeste(){
        return this.railTeste;
    }
    
    public void setTeste(boolean bool){
        this.railTeste = bool;
    }
}
