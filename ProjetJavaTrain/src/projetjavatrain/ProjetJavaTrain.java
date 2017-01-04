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
    StackPane sp1 = new StackPane();
    StackPane sp2 = new StackPane();
    GridPane grille = new GridPane();
    VBox v1 = new VBox(5);
    HBox h1 = new HBox(2);
    HBox h2 = new HBox(2);
    HBox h3 = new HBox(2);
    int numCols = 15 ;
    int numRows = 10 ;
    
    ArrayList<Timeline> listeTime = new ArrayList<>();
    ArrayList<ImageView> listeImg = new ArrayList<>();
    
    static Decors currentDecors;
    
    static Timeline timeline = null;
        
        Label l = new Label("Train Simulator 2017");
        Button bt1 = new Button("Construction");
        Button bt2 = new Button("New game");
        static Label currentElem = new Label ("Elem");
        static Label currentElem1 = new Label ("Elem1");
        static Label currentValue1 = new Label ("Value1");
        
    
    public void initMap(){
        for(int i = 0; i<numCols; i++){
            for(int j = 0; j<numRows; j++){
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
        
        if(timeline == null){
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(1000),
                    ae -> {
                        for(Ville v:tm.getListeVille()){
                            v.produire();
                        }
                    }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
            listeTime.add(timeline);
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        bt1.setOnAction(new btConsControler(tm,bt1));
        bt2.setOnAction(new btNewController(tm,bt2));
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints(50);
            grille.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(50);
            grille.getRowConstraints().add(rowConst);         
        }
        
        h1.getChildren().addAll(bt1,bt2);
        h2.getChildren().addAll(currentElem1,currentValue1);
        
        v1.getChildren().addAll(l,h1,currentElem,h2,h3);
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
    
    public void update(){
        for(int i = 0; i<numCols; i++){
            for(int j = 0; j<numRows; j++){
                Decors d = tm.getDecors(i, j);
                ImageView img = listeImg.get(i*10+j);
                if(img != d.getView()){
                    img.setImage(d.getImg());
                }
            } 
        }
    }
    

    @Override
    public void avertir() {
        update();
    }
    
    @Override
    public void AvertirNewTimeLine(Train t){
        int vitesse;
        if(t.getVitesse()==3){
            vitesse = 1650;
        }else{
            vitesse = 5000 / t.getVitesse();
        }
            Timeline time = new Timeline(new KeyFrame(
                    Duration.millis(vitesse),
                    ae -> {
                        tm.bougerTrain(t);
                    }));
            time.setCycleCount(Animation.INDEFINITE);
            time.play();
            t.newTimeline(time);
            listeTime.add(time);
    }
    
    @Override
    public void avertirUpdateTimeLine(Train t){
        Timeline time = listeTime.get(listeTime.indexOf(t.getTimeline()));
        int vitesse;
        if(t.getVitesse()==3){
            vitesse = 1650;
        }else{
            vitesse = 5000 / t.getVitesse();
        }
            time = new Timeline(new KeyFrame(
                    Duration.millis(vitesse),
                    ae -> {
                        tm.bougerTrain(t);
                    }));
            time.setCycleCount(Animation.INDEFINITE);
            time.play();
            t.newTimeline(time);
            listeTime.add(time);
    }
    
    @Override
    public void avertirNewGame(){
        for(Timeline t:listeTime){
            t.stop();
            t=null;
        }
        listeTime.clear();
        listeImg.clear();
        initMap();
        update();
    }
    @Override
    public void avertirPause(){
        for(Timeline t:listeTime){
            t.stop();
        }
    }
    @Override
    public void avertirFinPause(){
        for(Timeline t:listeTime){
            t.play();
        }
    }
    
    @Override
    public void avertirTxtInterface(Decors d){
        currentDecors = d;
    }
 
    public static void updateTxtUi(Decors d){
        if(d instanceof Ville){
            Ville v = (Ville) d;
            currentElem.setText("Ville");
            currentElem1.setText("Ressource : "+v.getStock().get(0).getNom());
            currentValue1.setText("= "+v.getStock().get(0).getQuantite());
        }
        if(d instanceof Train){
            Train t = (Train) d;
            currentElem.setText("Train");
            Bien[] tab = t.getCharge();
            currentElem1.setText("Ressource : "+tab[0]+" "
                    +tab[1]+" "+tab[2]+" "+tab[3]
                    +" "+tab[4]);
            currentValue1.setText("");
        }else{
            
        }
    }
    
}
