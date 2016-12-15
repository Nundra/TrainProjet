/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Nundra
 */
public class TrainModel {
    private int board[][];
    private String mode;
    private ArrayList<Decors> listeDecors = new ArrayList<>();
    ArrayList<Observateur> obs;
    
    Image montagne = new Image("img/montagne.png");
    Image ville = new Image("img/ville.png");
    Image plaine = new Image("img/plaine.png");
    Image rail = new Image("img/rail.png");
    
    public TrainModel(){
        this.obs = new ArrayList<>();
        this.mode = "game";
        this.board = new int [15][10];
        
        for(int i = 0; i<15; i++){
            for(int j = 0; j<10; j++){
                Decors m = null;
                if(
                //placement montagne
                    (i==10 && j==2) || (i==10 && j==3) || (i==11 && j==3) || (i==11 && j==2)
                     || (i==5 && j==5) || (i==0) || (i==14) || (j==0) || (j==9)
                        ){
                            m = new Decors(i,j,montagne,false);
                            listeDecors.add(m);
                            board[i][j] = 1;
                }else if( 
                //placement ville
                    (i==10 && j==5) || (i==6 && j==7 ) || (i==4 && j==3)
                        ){
                            m = new Decors(i,j,ville,false);
                            listeDecors.add(m);
                            board[i][j] = 2;
                }else
                //placement plaine
                {
                    m = new Decors(i,j,plaine,true);
                    listeDecors.add(m);
                    board[i][j] = 0;
                }
            }
        }
    }
    
    public void register(Observateur o){
        this.obs.add(o);
    }
    
    public void unregister(Observateur o){
        this.obs.remove(o);
    }
    
    public void avertirAllObservateurs(int i, int j){
        for(Observateur o:obs){
            o.avertir(i,j);
        }
    }
    
    public void poserRail(int i, int j){
        listeDecors.remove(getDecors(i,j));
        Decors d = new Rail(i,j,rail,false);
        listeDecors.add(d);
        board[i][j] = 3;
        avertirAllObservateurs(i,j);
    }
    
    public void retirerRail(int i, int j){
        listeDecors.remove(getDecors(i,j));
        Decors d = new Plaine(i,j,plaine,true);
        listeDecors.add(d);
        board[i][j] = 0;
        avertirAllObservateurs(i,j);
    }
    
    public Decors getDecors(int i, int j){
        Decors decors = null;
        for(Decors d:listeDecors){
            if(d.getX()==i && d.getY()==j)decors = d;
        }
        return decors;
    }

    public int getCase(int i, int j){
        return board[i][j];
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
    
    
    
}
