/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Nundra
 */
public class Decors {
    private int x;
    private int y;
    private Image img;
    private boolean editable;
    private boolean valide;
    private ImageView view;
    
    public Decors(int x, int y, Image img, boolean b){
        this.x=x;
        this.y=y;
        this.img=img;
        this.editable=b;
        valide = false;
        this.view = new ImageView(img);
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the img
     */
    public Image getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(Image img) {
        this.img = img;
        this.view.setImage(img);
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the valide
     */
    public boolean isValide() {
        return valide;
    }

    /**
     * @param valide the valide to set
     */
    public void setValide(boolean valide) {
        this.valide = valide;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the view
     */
    public ImageView getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(ImageView view) {
        this.view = view;
    }
}
