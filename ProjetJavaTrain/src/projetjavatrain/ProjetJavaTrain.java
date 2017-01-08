/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Nundra
 */

public class ProjetJavaTrain extends Application implements Observateur{
    static TrainModel tm = new TrainModel();
    
    SplitPane root = new SplitPane();
//panneau de gauche
    StackPane sp1 = new StackPane();
//panneau de droite
    StackPane sp2 = new StackPane();
//ma grille
    GridPane grille = new GridPane();
//Mise en forme de la page de gauche
    VBox v1 = new VBox();
    HBox btBox = new HBox();
    HBox jBox = new HBox();
    HBox infoVilleTxt = new HBox();

//Gestion des timer
    ArrayList<KeyFrame> listeKeyFrame = new ArrayList<>();
    ArrayList<ImageView> listeImg = new ArrayList<>();
    Timeline time = new Timeline();

//Current decors pour l'affichage de l'élément sélectionné
    static Decors currentDecors = null;

//tout les éléments textuel
        Label l = new Label("Train Simulator 2017");
        
        Button bt1 = new Button("Construction");
        Button bt2 = new Button("New game");
        Button bt3 = new Button("Destruction de ligne");
        
        static Label scoreTxt = new Label ("Score : "+tm.getJoueur().getScore());
        static Label upTxt = new Label ("Sélectionner un train ou une ville à améliorer");
        static Button btUpgrade = new Button ("lvl+");
        
        static ArrayList<TextArea> listeVilleInfoTxt = new ArrayList<>();
        static TextArea ville1= new TextArea();
        static TextArea ville2= new TextArea();
        static TextArea ville3= new TextArea();
    
    public void initMap(int col, int row){
        for (int i = 0; i < col; i++) {
            ColumnConstraints colConst = new ColumnConstraints(50);
            grille.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < row; i++) {
            RowConstraints rowConst = new RowConstraints(50);
            grille.getRowConstraints().add(rowConst);         
        }
        
        for(int i = 0; i<col; i++){
            for(int j = 0; j<row; j++){
                Decors d = tm.getDecors(i, j);
                ImageView img = d.getView();
                img.setFitWidth(50);
                img.setPreserveRatio(true);
                GridPane.setConstraints(img, i, j);
                grille.getChildren().add(img);
                img.setOnMouseClicked(new CaseControler(i,j,tm,img));
                
                listeImg.add(img);
            }
        }
        
        KeyFrame kf = new KeyFrame(
                Duration.millis(5000),
                ae -> {
                    for(Ville v:tm.getListeVille()){
                        v.produire();
                    }
                });
        time.getKeyFrames().add(kf);
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
        
        
        update(col,row);
    }
    
    @Override
    public void start(Stage primaryStage) {
        sp2.setStyle("-fx-background-image: url('img/bg.jpg')");
        btUpgrade.setDisable(true);
        ville1.setEditable(false);
        ville2.setEditable(false);
        ville3.setEditable(false);
        bt1.setOnAction(new btConsControler(tm,bt1));
        bt2.setOnAction(new btNewController(tm,bt2));
        bt3.setOnAction(new btDestControler(tm,bt3));
        btUpgrade.setOnAction(new btUpgradeController(tm));
        
        btBox.getChildren().addAll(bt1,bt2,bt3);
        jBox.getChildren().addAll(upTxt,btUpgrade);
        listeVilleInfoTxt.add(ville1);listeVilleInfoTxt.add(ville2);listeVilleInfoTxt.add(ville3);
        infoVilleTxt.getChildren().addAll(ville1,ville2,ville3);
        
        v1.getChildren().addAll(l,btBox,scoreTxt,jBox,infoVilleTxt);
        sp1.getChildren().add(v1);
        sp2.getChildren().add(grille);
        root.getItems().addAll(sp1,sp2);
        Scene scene = new Scene(root, 1200, 500);
        
        primaryStage.setTitle("Train Simulator 2017");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        tm.register(this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Timeline updateUI = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    ae -> {
                        updateTxtUi(currentDecors);
                    }));
            updateUI.setCycleCount(Animation.INDEFINITE);
            updateUI.play();
        launch(args);
    }
    
    public void update(int col, int row){
        for(int i = 0; i<col; i++){
            for(int j = 0; j<row; j++){
                Decors d = tm.getDecors(i, j);
                ImageView img = listeImg.get(i*10+j);
                if(img != d.getView()){
                    img.setImage(d.getImg());
                }
            } 
        }
    }
    

    @Override
    public void avertir(int col, int row) {
        update(col,row);
    }
    
    @Override
    public void avertirNewTimeLine(Train t){
        int vitesse = t.getVitesse()*1000;
        
           KeyFrame kf = new KeyFrame(
                    Duration.millis(vitesse),
                    ae -> {
                        tm.bougerTrain(t);
                    });
            t.newTimeline(kf);
            time.stop();
            time.getKeyFrames().add(kf);
            time.play();
    }
    
    @Override
    public void avertirUpdateTimeLine(Train t){
        int vitesse = t.getVitesse()*1000;
        time.stop();
        time.getKeyFrames().remove(t.getTimeline());
        KeyFrame kf = new KeyFrame(
                    Duration.millis(vitesse),
                    ae -> {
                        tm.bougerTrain(t);
                    });
        time.getKeyFrames().add(kf);
        time.play();
        /*time = new Timeline(new KeyFrame(
                    Duration.millis(vitesse),
                    ae -> {
                        tm.bougerTrain(t);
                    }));
            time.setCycleCount(Animation.INDEFINITE);*/
    }
    
    @Override
    public void avertirNewGame(int col, int row){
        time.stop();
        for(KeyFrame t:time.getKeyFrames()){
            t=null;
        }
        time.getKeyFrames().clear();
        listeImg.clear();
        initMap(col,row);
    }
    @Override
    public void avertirPause(){
        time.stop();
    }
    @Override
    public void avertirFinPause(){
        time.play();
    }
    
    @Override
    public void avertirTxtInterface(Decors d){
        currentDecors = d;
    }
 
    public static void updateTxtUi(Decors d){
        for(Ville v:tm.getListeVille()){
            listeVilleInfoTxt.get(tm.getListeVille().indexOf(v)).setText(v.toString());
        }
        
        scoreTxt.setText("Score : "+tm.getJoueur().getScore()+" && Argent : "+tm.getJoueur().getArgent());
        
        if(d instanceof Ville){
            Ville v = (Ville) d;
            upTxt.setText(v.getNom()+" lvl "+v.getLvl());
            btUpgrade.setText("Upgrader "+v.getCoutDeUp());
            if(v.isUp(tm.getJoueur())){
                btUpgrade.setDisable(false);
            }else{
                btUpgrade.setDisable(true);
            }
        }
        if(d instanceof Train){
            Train t = (Train) d;
            upTxt.setText("Train lvl : "+t.getLvl());
            btUpgrade.setText("Upgrader "+t.getCoutDeUp());
            if(t.isUp(tm.getJoueur())){
                btUpgrade.setDisable(false);
            }else{
                btUpgrade.setDisable(true);
            }
        }else{
            
        }
    }
    
}
