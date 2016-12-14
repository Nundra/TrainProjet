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
public class Decors {
    private int x;
    private int y;
    private Image img;
    private boolean editable;
    
    public Decors(int x, int y, Image img, boolean b){
        this.x=x;
        this.y=y;
        this.img=img;
        this.editable=b;
    }
}
