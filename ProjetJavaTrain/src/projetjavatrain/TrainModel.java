/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import static projetjavatrain.ProjetJavaTrain.timeline;
import static projetjavatrain.ProjetJavaTrain.tm;

/**
 *
 * @author Nundra
 */
public class TrainModel {
    private int board[][];
    private String mode;
    private ArrayList<Decors> listeDecors = new ArrayList<>();
    private ArrayList<Ville> listeVille = new ArrayList<>();
    private ArrayList<Train> listeTrain = new ArrayList<>();
    ArrayList<Observateur> obs;
    
    private Joueur joueur;
    
    Image montagne = new Image("img/montagne.png");
    Image ville = new Image("img/ville.png");
    Image plaine = new Image("img/plaine.png");
    Image railH = new Image("img/railH.png");
    Image railV = new Image("img/railV.png");
    Image railBD = new Image("img/railBD.png");
    Image railBG = new Image("img/railBG.png");
    Image railDH = new Image("img/railDH.png");
    Image railGH = new Image("img/railGH.png");
    Image valid = new Image("img/valid.png");
    Image train = new Image("img/train.png");
    Image trainHG = new Image("img/trainHG.png");
    Image trainHD = new Image("img/trainHD.png");
    Image trainVH = new Image("img/trainVH.png");
    Image trainVB = new Image("img/trainVB.png");
    
    private Bien lait = new Bien("lait",1,10);
    private Bien plastique = new Bien("plastique",1,10);
    private Bien pot = new Bien("pot",0,20);
    
    private ArrayList<Bien> listeBien = new ArrayList<>();
    ArrayList<Rail> dTemp = new ArrayList<>();
    
    ArrayList<Rail> testRailTemp = new ArrayList<>();
    ArrayList<Rail> testRailFinal = new ArrayList<>();
    
    public TrainModel(){
        for(int i = 0; i < 100; i++){
            dTemp.add(null);
        }
    
        this.obs = new ArrayList<>();
        this.mode = "game";
        this.board = new int [15][10];
        listeBien.add(lait);
        listeBien.add(plastique);
        listeBien.add(pot);
        this.joueur = new Joueur();
        
        pot.addComposant(lait, 5);
        pot.addComposant(plastique, 5);
    }
    
    public void newGame(){
        this.mode="game";
        joueur.reset();
        this.board = new int [15][10];
        int compteur = 0;
        
        listeVille.clear();
        
        for(Bien b:listeBien){
            if(b != null)b.reset();
        }
        
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
                            if(compteur < 2){
                                m = new Ville(i,j,ville,false,listeBien.get(compteur),this.joueur);
                            }else {
                                m = new Usine(i,j,ville,false,listeBien.get(compteur),this.joueur);
                            }
                            compteur++;
                            listeDecors.add(m);
                            listeVille.add((Ville) m);
                            board[i][j] = 2;
                }else
                //placement plaine
                {
                    m = new Decors(i,j,plaine,false);
                    listeDecors.add(m);
                    board[i][j] = 0;
                }
            }
        }
        btConsControler.reset();
        Rail.resetId();
        for(Observateur o:obs){
            o.avertirNewGame();
        }
    }
    
    public void register(Observateur o){
        this.obs.add(o);
    }
    
    public void unregister(Observateur o){
        this.obs.remove(o);
    }
    
    public void avertirAllObservateurs(){
        for(Observateur o:obs){
            o.avertir();
        }
    }
    public void avertirPauseAllObservateurs(){
        for(Observateur o:obs){
            o.avertirPause();
        }
    }
    public void avertirFinPauseAllObservateurs(){
        for(Observateur o:obs){
            o.avertirFinPause();
        }
    }
    public void avertirTxtInterface(Decors d){
        for(Observateur o:obs){
            o.avertirTxtInterface(d);
        }
    }
    
    
    public void startConstruction(){
        for(Ville v:listeVille){
            setCaseAutour(v.getX(),v.getY(),true);
        }
        avertirPauseAllObservateurs();
    }
    
    public void finConstruction(){
        for(int i = 0; i<15; i++){
            for(int j = 0; j<10; j++){
                if(getCase(i,j)==0){
                        getDecors(i,j).setImg(plaine);
                }
                if(getCase(i,j)==2){
                        if(board[i][j-1]==3){
                                testLigne(i,j-1);
                        }
                        if(board[i][j+1]==3){
                                testLigne(i,j+1);
                        }
                        if(board[i-1][j]==3){
                                testLigne(i-1,j);
                        }
                        if(board[i+1][j]==3){
                                testLigne(i+1,j);
                        }
                }
            }
        }
        
        for(int i = 0; i<15; i++){
            for(int j = 0; j<10; j++){
                if(getCase(i,j)==3){
                    listeDecors.remove(getDecors(i,j));
                    Decors d = new Plaine(i,j,plaine,true);
                    listeDecors.add(d);
                    board[i][j] = 0;
                }
            }
        }
        avertirFinPauseAllObservateurs();
    }
    
    public void testLigne(int _i, int _j){
        boolean close = false;
        int i = _i;
        int j = _j;
        Rail r = (Rail) getDecors(i,j);
        
        while(close == false){
            r = (Rail) getDecors(i,j);
            testRailTemp.add(r);
            int[] c = r.getChemin();
            if(c[0]==c[2] && c[1]==c[3]){
                if(board[i][j-1]==2 || board[i][j+1]==2 || board[i-1][j]==2 || board[i+1][j]==2){
                    close = true;
                }else{
                    break;
                }
            }else{
                    i = c[2];
                    j = c[3];
                }
        }
        
        if(close){
            r.newId();
            for(Rail dTemp: testRailTemp){
                testRailFinal.add(dTemp);
                dTemp.updateId();
                board[dTemp.getX()][dTemp.getY()] = 5;
            }
            testRailTemp.clear();
            Train t = new Train(i,j,train,false,r.getId());
                for(Observateur o:obs){
                    o.AvertirNewTimeLine(t);
                }
            listeTrain.add(t);
            poserTrain((Train) t,i,j);
        }else{
                for(Rail dTemp:testRailTemp){
                    board[dTemp.getX()][dTemp.getY()] = 3;
                }
                testRailTemp.clear();
            }
        
    }
    
    public void poserTrain(Train t, int i, int j){
        dTemp.set(t.getIdLigne(),(Rail) getDecors(i,j));
        listeDecors.remove(dTemp.get(t.getIdLigne()));
        listeDecors.add(t);
        board[i][j]=10;
        avertirAllObservateurs();
    }
    
    public void bougerTrain(Train t){
        int i = t.getX();
        int j = t.getY();
        int x = 0;
        int y = 0;
        if( ( getCase(i+1,j) == t.getSens() ) ){
            if( ((Rail) getDecors(i+1,j)).getId() == t.getIdLigne() ){
                x = i+1;
                y=j;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(dTemp.get(t.getIdLigne()));
                board[i][j] = t.getNewSens();
                dTemp.set(t.getIdLigne(),(Rail) getDecors(x,y));
                listeDecors.remove(dTemp.get(t.getIdLigne()));
                t.setX(x);
                t.setY(y);
                board[x][y]=10;
                listeDecors.add(t);
            }
        }else if( ( getCase(i-1,j) == t.getSens() ) ){
            if( ((Rail) getDecors(i-1,j)).getId() == t.getIdLigne() ){
                x = i-1;y=j;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(dTemp.get(t.getIdLigne()));
                board[i][j] = t.getNewSens();
                dTemp.set(t.getIdLigne(),(Rail) getDecors(x,y));
                listeDecors.remove(dTemp.get(t.getIdLigne()));
                t.setX(x);
                t.setY(y);
                board[x][y]=10;
                listeDecors.add(t);
            }
        }else if( ( getCase(i,j+1) == t.getSens() ) ){
            if( ((Rail) getDecors(i,j+1)).getId() == t.getIdLigne() ){
                x = i;y=j+1;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(dTemp.get(t.getIdLigne()));
                board[i][j] = t.getNewSens();
                dTemp.set(t.getIdLigne(),(Rail) getDecors(x,y));
                listeDecors.remove(dTemp.get(t.getIdLigne()));
                t.setX(x);
                t.setY(y);
                board[x][y]=10;
                listeDecors.add(t);
            }
        }else if( ( getCase(i,j-1) == t.getSens() ) ){
            if( ((Rail) getDecors(i,j-1)).getId() == t.getIdLigne() ){
                x = i;y=j-1;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(dTemp.get(t.getIdLigne()));
                board[i][j] = t.getNewSens();
                dTemp.set(t.getIdLigne(),(Rail) getDecors(x,y));
                listeDecors.remove(dTemp.get(t.getIdLigne()));
                t.setX(x);
                t.setY(y);
                board[x][y]=10;
                listeDecors.add(t);
            }
        }else if(getCase(i+1,j)==2 || getCase(i-1,j)==2 || getCase(i,j+1)==2 || getCase(i,j-1)==2){
            t.changerSens();
            Ville v;
            if(getCase(i+1,j)==2){
                v = (Ville) getDecors(i+1,j);
                v.decharger(t);
            }
            if(getCase(i-1,j)==2){
                v = (Ville) getDecors(i-1,j);
                v.decharger(t);
            }
            if(getCase(i,j+1)==2){
                v = (Ville) getDecors(i,j+1);
                v.decharger(t);
            }
            if(getCase(i,j-1)==2){
                v = (Ville) getDecors(i,j-1);
                v.decharger(t);
            }
        }
        avertirAllObservateurs();
        
    }
    
    public Image orienterTrain(int i, int j){
        Train t = (Train) getDecors(i,j);
        if(getCase(i+1,j) == t.getSens())return trainHD;
        if(getCase(i-1,j) == t.getSens())return trainHG;
        if(getCase(i,j+1) == t.getSens())return trainVB;
        if(getCase(i,j-1) == t.getSens())return trainVH;
        return null;
    }
    
    public void poserRail(int i, int j){
        listeDecors.remove(getDecors(i,j));
        Rail d = new Rail(i,j,orienterRail(i,j),true);
        listeDecors.add(d);
        board[i][j] = 3;
        setCaseAutour(i,j,true);
            if(board[i-1][j]==3){
                Rail d2 = (Rail) getDecors(i-1,j);
                if(!d2.estTeste()){
                    d.setupChemin(i-1, j, i-1, j);
                    setCaseAutour(i-1,j,false);
                    d2.setEditable(false);
                        if(board[i-1][j-1] == 3 || board[i-1][j-1] == 2){
                            d2.setImg(railDH);
                            d2.setupChemin(i-1,j-1,i,j);
                        }
                        if(board[i-1][j+1] == 3 || board[i-1][j+1] == 2){
                            d2.setImg(railBD);
                            d2.setupChemin(i-1,j+1,i,j);
                        }
                        if(board[i-2][j] == 3 || board[i-2][j] == 2){
                            d2.setImg(railH);
                            d2.setupChemin(i-2,j,i,j);
                        }
                    d2.setTeste(true);
                }
            }
            if(board[i+1][j]==3){
                Rail d2 = (Rail) getDecors(i+1,j);
                if(!d2.estTeste()){
                    d.setupChemin(i+1, j, i+1, j);
                    setCaseAutour(i+1,j,false);
                    d2.setEditable(false);
                        if(board[i+1][j-1] == 3 || board[i+1][j-1] == 2){
                            d2.setImg(railGH);
                            d2.setupChemin(i+1,j-1,i,j);
                        }
                        if(board[i+1][j+1] == 3 || board[i+1][j+1] == 2){
                            d2.setImg(railBG);
                            d2.setupChemin(i+1,j+1,i,j);
                        }
                        if(board[i+2][j] == 3 || board[i+2][j] == 2){
                            d2.setImg(railH);
                            d2.setupChemin(i+2,j,i,j);
                        }
                    d2.setTeste(true);
                }
            }
            if(board[i][j-1]==3){
                Rail d2 = (Rail) getDecors(i,j-1);
                if(!d2.estTeste()){
                    d.setupChemin(i, j-1, i, j-1);
                    setCaseAutour(i,j-1,false);
                    d2.setEditable(false);
                        if(board[i-1][j-1] == 3 || board[i-1][j-1] == 2){
                            d2.setImg(railBG);
                            d2.setupChemin(i-1,j-1,i,j);
                        }
                        if(board[i+1][j-1] == 3 || board[i+1][j-1] == 2){
                            d2.setImg(railBD);
                            d2.setupChemin(i+1,j-1,i,j);
                        }
                        if(board[i][j-2] == 3 || board[i][j-2] == 2){
                            d2.setImg(railV);
                            d2.setupChemin(i,j-2,i,j);
                        }
                    d2.setTeste(true);
                }
            }
            if(board[i][j+1]==3){
                Rail d2 = (Rail) getDecors(i,j+1);
                if(!d2.estTeste()){
                d.setupChemin(i, j+1, i, j+1);
                setCaseAutour(i,j+1,false);
                d2.setEditable(false);
                    if(board[i-1][j+1] == 3 || board[i-1][j+1] == 2){
                        d2.setImg(railGH);
                        d2.setupChemin(i-1,j+1,i,j);
                    }
                    if(board[i+1][j+1] == 3 || board[i+1][j+1] == 2){
                        d2.setImg(railDH);
                        d2.setupChemin(i+1,j+1,i,j);
                    }
                    if(board[i][j+2] == 3 || board[i][j+2] == 2){
                        d2.setImg(railV);
                        d2.setupChemin(i,j+2,i,j);
                    }
                    d2.setTeste(true);
                }
            }
        avertirAllObservateurs();
    }
    
    public void retirerRail(int i, int j){
        listeDecors.remove(getDecors(i,j));
        Decors d = new Plaine(i,j,plaine,true);
        listeDecors.add(d);
        board[i][j] = 0;
        setCaseAutour(i,j,false);
            if(board[i-1][j]==3){
                setCaseAutour(i-1,j,true);
                getDecors(i-1,j).setEditable(true);
            }
            if(board[i+1][j]==3){
                setCaseAutour(i+1,j,true);
                getDecors(i+1,j).setEditable(true);
            }
            if(board[i][j-1]==3){
                setCaseAutour(i,j-1,true);
                getDecors(i,j-1).setEditable(true);
            }
            if(board[i][j+1]==3){
                setCaseAutour(i,j+1,true);
                getDecors(i,j+1).setEditable(true);
            }
            if(board[i-1][j]==2 || board[i+1][j]==2 || board[i][j-1]==2 || board[i][j+1]==2)getDecors(i,j).setImg(valid);
        avertirAllObservateurs();
    }
    
    public void setCaseAutour(int i,int j, boolean bool){
        int val = 1;
        if(getCase(i,j)==2)val=0;
        int compteur = 0;
            if(board[i-1][j]==0){
                getDecors(i-1,j).setEditable(bool);
                if(bool && (testRail(i-1,j)==val))getDecors(i-1,j).setImg(valid);
                if(testRail(i-1,j)>val)getDecors(i-1,j).setEditable(false);
                if(!bool)getDecors(i-1,j).setImg(plaine);
            }
            if(board[i+1][j]==0){
                getDecors(i+1,j).setEditable(bool);
                if(bool && (testRail(i+1,j)==val))getDecors(i+1,j).setImg(valid);
                if(testRail(i+1,j)>val)getDecors(i+1,j).setEditable(false);
                if(!bool)getDecors(i+1,j).setImg(plaine);
            }
            if(board[i][j-1]==0){
                getDecors(i,j-1).setEditable(bool);
                if(bool && (testRail(i,j-1)==val))getDecors(i,j-1).setImg(valid);
                if(testRail(i,j-1)>val)getDecors(i,j-1).setEditable(false);
                if(!bool)getDecors(i,j-1).setImg(plaine);
            }
            if(board[i][j+1]==0){
                getDecors(i,j+1).setEditable(bool);
                if(bool && (testRail(i,j+1)==val))getDecors(i,j+1).setImg(valid);
                if(testRail(i,j+1)>val)getDecors(i,j+1).setEditable(false);
                if(!bool)getDecors(i,j+1).setImg(plaine);
            }
        if(compteur >1){
            getDecors(i,j).setEditable(false);
        }
    }
    
    public int testRail(int i, int j){
        int r = 0;
        
        if(board[i-1][j]==3)r++;
        if(board[i+1][j]==3)r++;
        if(board[i][j-1]==3)r++;
        if(board[i][j+1]==3)r++;
        
        return r;
    }
    
    public Image orienterRail(int i, int j){
        if(board[i-1][j] == 2){
            if(board[i][j-1]== 3){
                return railGH;
            }else if(board[i][j+1]==3){
                return railBG;
            }else return railH;
        }
        if(board[i+1][j] == 2){
            if(board[i][j-1]==3){
                return railDH;
            }else if(board[i][j+1]==3){
                return railBD;
            }else return railH;
        }
        if(board[i][j-1] == 2){
            if(board[i-1][j]==3){
                return railGH;
            }else if(board[i+1][j]==3){
                return railDH;
            }else return railV;
        }
        if(board[i][j+1] == 2){
            if(board[i-1][j]==3){
                return railBG;
            }else if(board[i+1][j]==3){
                return railBD;
            }else return railV;
        }
        if(board[i-1][j] == 3 || board[i+1][j] == 3)return railH;
            return railV;
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

    /**
     * @return the listeVille
     */
    public ArrayList<Ville> getListeVille() {
        return listeVille;
    } 
}
