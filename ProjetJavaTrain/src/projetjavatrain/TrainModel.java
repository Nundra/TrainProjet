/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 *
 * @author Nundra
 */
public class TrainModel {
//Taille de la grille
    private int col = 20;
    private int row = 15;
    
    private int board[][];
    private String mode;
    private ArrayList<Decors> listeDecors = new ArrayList<>();
    private ArrayList<Ville> listeVille = new ArrayList<>();
    private ArrayList<Train> listeTrain = new ArrayList<>();
    private ArrayList<Bien> listeBien = new ArrayList<>();
    ArrayList<Observateur> obs;
    
     Decors currentDecors = null;
    
    private Joueur joueur = new Joueur();
    
    Image blank = new Image("img/blank.png");
    Image villeImg = new Image("img/villeLait.png");
    Image villeLaitImg = new Image("img/villeLait.png");
    Image villePotImg = new Image("img/villePot.png");
    Image villePlastiqueImg = new Image("img/villePlastique.png");
    Image railH = new Image("img/railH.png");
    Image railV = new Image("img/railV.png");
    Image railBD = new Image("img/railBD.png");
    Image railBG = new Image("img/railBG.png");
    Image railDH = new Image("img/railDH.png");
    Image railGH = new Image("img/railGH.png");
    Image valid = new Image("img/valid.png");
    Image train = new Image("img/train.png");
    
    private Bien lait = new Bien("lait",1,10);
    private Bien plastique = new Bien("plastique",1,10);
    private Bien pot = new Bien("pot",0,20);
    
    private Ville villePlastique = new Ville("ville des plastique",3,9,villePlastiqueImg,false,plastique,joueur);
    private Ville villeLait = new Ville("Ferme du lait ",12,12,villeLaitImg,false,lait,joueur);
    private Ville villePot = new Usine("Usine de pot",16,2,villePotImg,false,pot,joueur);
    
    ArrayList<Rail> testRailTemp = new ArrayList<>();
    ArrayList<Rail> testRailFinal = new ArrayList<>();
    
    public TrainModel(){
        this.obs = new ArrayList<>();
        this.mode = "game";
        this.board = new int [col][row];
        
        pot.addComposant("lait", 5);
        pot.addComposant("plastique", 5);
                        listeVille.add(villePlastique);
                            listeVille.add(villePot);
                                listeVille.add(villeLait);
        listeBien.add(lait);
        listeBien.add(plastique);
        listeBien.add(pot);
    }
    
    public Joueur getJoueur(){
        return this.joueur;
    }
    public ArrayList<Bien> getListeBien(){
        return this.listeBien;
    }
    
    public void newGame(){
        this.mode="game";
        joueur.reset();
        this.board = new int [col][row];
        
        for(int i = 0; i<col; i++){
            for(int j = 0; j<row; j++){
                Decors m = null;
                if(
                //placement montagne et eau
                (i==0) || (i==19) || (j==0) || (j==14) ||
                (i==5 && j==1) || (i==6 && j==1) || (i==7 && j==1) || (i==8 && j==1) || (i==9 && j==1) || (i==10 && j==1) ||
                (i==5 && j==6) || (i==6 && j==6) || (i==7 && j==6) || (i==8 && j==6) || (i==9 && j==6) || (i==10 && j==6) ||
                (i==5 && j==7) || (i==6 && j==7) || (i==7 && j==7) || (i==8 && j==7) || (i==9 && j==7) || (i==10 && j==7) || (i==11 && j==7) ||
                (i==6 && j==8) || (i==7 && j==8) || (i==8 && j==8) || (i==9 && j==8) || (i==10 && j==8) || (i==11 && j==8) ||
                (i==7 && j==9) || (i==8 && j==9) || (i==9 && j==9) || (i==10 && j==9) ||

                (i==18 && j==10) || (i==19 && j==10) ||
                (i==18 && j==11) || (i==19 && j==11) ||
                (i==18 && j==12) || (i==19 && j==12) ||
                (i==18 && j==13) || (i==19 && j==13) ||
                (i==17 && j==14) || (i==18 && j==14) || (i==19 && j==14)
                
                ){
                    m = new Decors(i,j,blank,false);
                    board[i][j] = 1;
                }else if((i==3 && j==9)){
                        m = villePlastique;
                        board[i][j] = 2;
                    }
                    else if((i==16 && j==2)){
                            m = villePot;
                            board[i][j] = 2;
                        }
                        else if((i==12 && j==12)){
                            m = villeLait;
                                board[i][j] = 2;
                        }
                            else {
                                m = new Decors(i,j,blank,false);
                                board[i][j] = 0;
                            }
                listeDecors.add(m);
            }
        }
        btConsControler.reset();
        btDestControler.reset();
        Rail.resetId();
        avertirNewGameAllObservateurs();
    }
    
    public void register(Observateur o){
        this.obs.add(o);
    }
    
    public void unregister(Observateur o){
        this.obs.remove(o);
    }
    
    public void avertirAllObservateurs(){
        for(Observateur o:obs){
            o.avertir(col,row);
        }
    }
    public void avertirFinBtAll(){
        for(Observateur o:obs){
            o.avertirFinBt();
        }
    }
    public void avertirStartBtAll(Button bt){
        for(Observateur o:obs){
            o.avertirStartBt(bt);
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
    }public void avertirUpdateTimeLine(Train t){
        t.upgradeTrain(joueur);
        for(Observateur o:obs){
            o.avertirUpdateTimeLine(t);
        }
    }
    public void avertirTxtInterface(Decors d){
        this.currentDecors = d;
        for(Observateur o:obs){
            o.avertirTxtInterface(d);
        }
    }
    public void avertirNewTimeLineAllObservateurs(Train t){
        for(Observateur o:obs){
            o.avertirNewTimeLine(t);
        }
    }
    public void avertirNewGameAllObservateurs(){
        for(Observateur o:obs){
            o.avertirNewGame(col,row);
        }
    }
    
    //REFAIRE AVEC BIEN
 
    public void poseVille(int i, int j, Bien str){
        listeDecors.remove(getDecors(i,j));
        Image img = null;
        Ville v = null;
        if(str.getNom().equals("pot")){
            v = new Usine("ville perso",i,j,getImageVille(str), false, new Bien(str.getNom(),0,str.getValeur()), joueur);
        }else{
            v = new Ville("ville perso",i,j,getImageVille(str), false, new Bien(str.getNom(),0,str.getValeur()), joueur);
        }
        listeDecors.add(v);
        listeVille.add(v);
        mode="game";
        board[i][j]=2;
        joueur.upScoreVille();
        avertirFinBtAll();
        avertirAllObservateurs();
    }
    
    public Image getImageVille(Bien b){
        Image img = null;
        for(Ville v:listeVille){
            if(v.getTypeRessource() == b){
                return v.getImg();
            }
        }
        return img;
    }
    
    public void destruction(int x, int y){
        avertirPauseAllObservateurs();
        Rail r = (Rail) getDecors(x,y);
        int id = r.getId();
        for(Rail r1:testRailFinal){
            if(r1.getId()==id){
                listeDecors.remove(r1);
                Decors m = new Decors(r1.getX(),r1.getY(),blank,false);
                listeDecors.add(m);
                board[r1.getX()][r1.getY()] = 0;
                r1=null;
            }
        }
        avertirAllObservateurs();
    }
    
    
    public void startConstruction(){
        for(Ville v:listeVille){
            setCaseAutour(v.getX(),v.getY(),true);
        }
        avertirPauseAllObservateurs();
    }
    
    public void finConstruction(){
        for(int i = 0; i<col; i++){
            for(int j = 0; j<row; j++){
                if(getCase(i,j)==0){
                        getDecors(i,j).setImg(blank);
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
        
        for(int i = 0; i<col; i++){
            for(int j = 0; j<row; j++){
                if(getCase(i,j)==3){
                    listeDecors.remove(getDecors(i,j));
                    Decors d = new Plaine(i,j,blank,true);
                    listeDecors.add(d);
                    board[i][j] = 0;
                }
            }
        }
        avertirAllObservateurs();
        avertirFinPauseAllObservateurs();
    }
    
    public void testLigne(int _i, int _j){
        boolean close = false;
        int i = _i;
        int j = _j;
        int x = 0;
        int y = 0;
        Rail r = (Rail) getDecors(i,j);
        
        while(close == false){
            r = (Rail) getDecors(i,j);
            testRailTemp.add(r);
            if(board[i][j-1]==3){
                board[i][j]=5;
                j = j-1;
            }else if(board[i][j+1]==3){
                board[i][j]=5;
                j = j+1;
            }else if(board[i-1][j]==3){
                board[i][j]=5;
                i = i-1;
            }else if(board[i+1][j]==3){
                board[i][j]=5;
                i = i+1;
            }else if((board[i][j-1]==2 || board[i][j+1]==2 || board[i-1][j]==2 || board[i+1][j]==2)
                    && ((board[i][j-1]==5 || board[i][j+1]==5 || board[i-1][j]==5 || board[i+1][j]==5)
                    || (board[i][j-1]==2 || board[i][j+1]==2 || board[i-1][j]==2 || board[i+1][j]==2))){
                close = true;
            }else{
                break;
            }
            /*int[] c = r.getChemin();
            if(c[0]==c[2] && c[1]==c[3]){
                if(board[i][j-1]==2 || board[i][j+1]==2 || board[i-1][j]==2 || board[i+1][j]==2){
                    close = true;
                }else{
                    break;
                }
            }else{
                    i = c[2];
                    j = c[3];
                }*/
        }
        
        if(close){
            r.newId();
            for(Rail rail: testRailTemp){
                testRailFinal.add(rail);
                rail.updateId();
            }
            testRailTemp.clear();
            Train t = new Train(i,j,train,false,r.getId());
            avertirNewTimeLineAllObservateurs(t);
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
        t.setRail((Rail) getDecors(i,j));
        listeDecors.remove(t.getRail());
        listeDecors.add(t);
        board[i][j]=10;
        t.adaptImage();
        avertirAllObservateurs();
    }
    
    public void bougerTrain(Train t){
        int i = t.getX();
        int j = t.getY();
        int x = 0;
        int y = 0;
        if( ( getCase(i+1,j) == t.getSens() ) ){
            if( ((Rail) getDecors(i+1,j)).getId() == t.getIdLigne() ){
                x = i+1;y=j;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(t.getRail());
                board[i][j] = t.getNewSens();
                t.setRail((Rail) getDecors(x,y));
                listeDecors.remove(t.getRail());
                t.setX(x);
                t.setY(y);
                board[x][y]=10;
                listeDecors.add(t);
            }
        }else if( ( getCase(i-1,j) == t.getSens() ) ){
            if( ((Rail) getDecors(i-1,j)).getId() == t.getIdLigne() ){
                x = i-1;y=j;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(t.getRail());
                board[i][j] = t.getNewSens();
                t.setRail((Rail) getDecors(x,y));
                listeDecors.remove(t.getRail());
                t.setX(x);
                t.setY(y);
                board[x][y]=10;
                listeDecors.add(t);
            }
        }else if( ( getCase(i,j+1) == t.getSens() ) ){
            if( ((Rail) getDecors(i,j+1)).getId() == t.getIdLigne() ){
                x = i;y=j+1;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(t.getRail());
                board[i][j] = t.getNewSens();
                t.setRail((Rail) getDecors(x,y));
                listeDecors.remove(t.getRail());
                t.setX(x);
                t.setY(y);
                board[x][y]=10;
                listeDecors.add(t);
            }
        }else if( ( getCase(i,j-1) == t.getSens() ) ){
            if( ((Rail) getDecors(i,j-1)).getId() == t.getIdLigne() ){
                x = i;y=j-1;
                listeDecors.remove(getDecors(i,j));
                listeDecors.add(t.getRail());
                board[i][j] = t.getNewSens();
                t.setRail((Rail) getDecors(x,y));
                listeDecors.remove(t.getRail());
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
        t.adaptImage();
        avertirAllObservateurs();
        
    }
    
    public void poserRail(int i, int j){
        listeDecors.remove(getDecors(i,j));
        Rail d = orienterRail(i,j);
        listeDecors.add(d);
        board[i][j] = 3;
        setCaseAutour(i,j,true);
            if(board[i-1][j]==3){
                Rail d2 = (Rail) getDecors(i-1,j);
                if(!d2.estTeste()){
                    setCaseAutour(i-1,j,false);
                    d2.setEditable(false);
                        if(board[i-1][j-1] == 3 || board[i-1][j-1] == 2){
                            d2.setImg(railDH);
                            d2.setOrientation("DH");
                        }
                        if(board[i-1][j+1] == 3 || board[i-1][j+1] == 2){
                            d2.setImg(railBD);
                            d2.setOrientation("BD");
                        }
                        if(board[i-2][j] == 3 || board[i-2][j] == 2){
                            d2.setImg(railH);
                            d2.setOrientation("H");
                        }
                }
            }
            if(board[i+1][j]==3){
                Rail d2 = (Rail) getDecors(i+1,j);
                if(!d2.estTeste()){
                    setCaseAutour(i+1,j,false);
                    d2.setEditable(false);
                        if(board[i+1][j-1] == 3 || board[i+1][j-1] == 2){
                            d2.setImg(railGH);
                            d2.setOrientation("GH");
                        }
                        if(board[i+1][j+1] == 3 || board[i+1][j+1] == 2){
                            d2.setImg(railBG);
                            d2.setOrientation("BG");
                        }
                        if(board[i+2][j] == 3 || board[i+2][j] == 2){
                            d2.setImg(railH);
                            d2.setOrientation("H");
                        }
                }
            }
            if(board[i][j-1]==3){
                Rail d2 = (Rail) getDecors(i,j-1);
                if(!d2.estTeste()){
                    setCaseAutour(i,j-1,false);
                    d2.setEditable(false);
                        if(board[i-1][j-1] == 3 || board[i-1][j-1] == 2){
                            d2.setImg(railBG);
                            d2.setOrientation("BG");
                        }
                        if(board[i+1][j-1] == 3 || board[i+1][j-1] == 2){
                            d2.setImg(railBD);
                            d2.setOrientation("BD");
                        }
                        if(board[i][j-2] == 3 || board[i][j-2] == 2){
                            d2.setImg(railV);
                            d2.setOrientation("V");
                        }
                }
            }
            if(board[i][j+1]==3){
                Rail d2 = (Rail) getDecors(i,j+1);
                if(!d2.estTeste()){
                setCaseAutour(i,j+1,false);
                d2.setEditable(false);
                    if(board[i-1][j+1] == 3 || board[i-1][j+1] == 2){
                        d2.setImg(railGH);
                            d2.setOrientation("GH");
                    }
                    if(board[i+1][j+1] == 3 || board[i+1][j+1] == 2){
                        d2.setImg(railDH);
                            d2.setOrientation("DH");
                    }
                    if(board[i][j+2] == 3 || board[i][j+2] == 2){
                        d2.setImg(railV);
                            d2.setOrientation("V");
                    }
                }
            }
        avertirAllObservateurs();
    }
    
    public void retirerRail(int i, int j){
        listeDecors.remove(getDecors(i,j));
        Decors d = new Plaine(i,j,blank,true);
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
                    else if(testRail(i-1,j)>val)getDecors(i-1,j).setEditable(false);
                        else if(!bool)getDecors(i-1,j).setImg(blank);
            }
            if(board[i+1][j]==0){
                getDecors(i+1,j).setEditable(bool);
                if(bool && (testRail(i+1,j)==val))getDecors(i+1,j).setImg(valid);
                    else if(testRail(i+1,j)>val)getDecors(i+1,j).setEditable(false);
                        else if(!bool)getDecors(i+1,j).setImg(blank);
            }
            if(board[i][j-1]==0){
                getDecors(i,j-1).setEditable(bool);
                if(bool && (testRail(i,j-1)==val))getDecors(i,j-1).setImg(valid);
                    else if(testRail(i,j-1)>val)getDecors(i,j-1).setEditable(false);
                        else if(!bool)getDecors(i,j-1).setImg(blank);
            }
            if(board[i][j+1]==0){
                getDecors(i,j+1).setEditable(bool);
                if(bool && (testRail(i,j+1)==val))getDecors(i,j+1).setImg(valid);
                    else if(testRail(i,j+1)>val)getDecors(i,j+1).setEditable(false);
                        else if(!bool)getDecors(i,j+1).setImg(blank);
            }
        if(compteur >1){
            getDecors(i,j).setEditable(false);
        }
    }
    
    public int testRail(int i, int j){
        int r = 0;
        if(i==0 || j==0 ||i==14 || j==9){
            return r;
        }
        if(board[i-1][j]==3)r++;
        if(board[i+1][j]==3)r++;
        if(board[i][j-1]==3)r++;
        if(board[i][j+1]==3)r++;
        
        return r;
    }
    
    public Rail orienterRail(int i, int j){
        Image img = railV;
        String str = "V";
        if(board[i-1][j] == 2){
            if(board[i][j-1]== 3){
                img = (railGH);
                str = "GH";
            }else if(board[i][j+1]==3){
                img = railBG;
                str = "BG";
            }else {
                img =  railH;
                str = "H";
            }
        }else if(board[i+1][j] == 2){
            if(board[i][j-1]==3){
                img =  railDH;
                str = "DH";
            }else if(board[i][j+1]==3){
                img = railBD;
                str = "BD";
            }else {
                img = railH;
                str = "H";
            }
        }else if(board[i][j-1] == 2){
            if(board[i-1][j]==3){
                img = railGH;
                str = "GH";
            }else if(board[i+1][j]==3){
                img = railDH;
                str = "DH";
            }else {
                img = railV;
                str = "V";
            }
        }else if(board[i][j+1] == 2){
            if(board[i-1][j]==3){
                img = railBG;
                str = "BG";
            }else if(board[i+1][j]==3){
                img = railBD;
                str = "BD";
            }else {
                img = railV;
                str = "V";
            }
        }else if(board[i-1][j] == 3 || board[i+1][j] == 3){
            img = railH;
            str = "H";
        }
        Rail r = new Rail(i,j,img,true);
        r.setOrientation(str);
        return r;
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
    
    public int getCol(){
        return col;
    }
    public int getRow(){
        return row;
    }
}
