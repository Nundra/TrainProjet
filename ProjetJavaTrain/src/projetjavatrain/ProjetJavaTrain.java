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
    VBox v1 = new VBox(2);
    HBox h1 = new HBox(2);
    int numCols = 15 ;
    int numRows = 10 ;
    
    ArrayList<Timeline> listeTime = new ArrayList<>();
    
    static Timeline timeline = null;
        
    
    public void initMap(){
        for(int i = 0; i<numCols; i++){
            for(int j = 0; j<numRows; j++){
                Decors d = tm.getDecors(i, j);
                ImageView img = new ImageView(d.getImg());
                img.setFitWidth(50);
                img.setPreserveRatio(true);
                GridPane.setConstraints(img, i, j);
                grille.getChildren().add(img);
                img.setOnMouseClicked(new CaseControler(i,j,tm,img));
            }
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints(50);
            grille.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(50);
            grille.getRowConstraints().add(rowConst);         
        }
        
        Label l = new Label("Train Simulator 2017");
        v1.getChildren().add(l);
        Button bt1 = new Button("Construction");
        bt1.setOnAction(new btConsControler(tm,bt1));
        
        Button bt2 = new Button("New game");
        bt2.setOnAction(new btNewController(tm,bt2));
        
        h1.getChildren().addAll(bt1,bt2);
        v1.getChildren().add(h1);
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
        launch(args);
    }
    
    public void update(){
        grille.getChildren().clear();
        initMap();
        if(timeline == null){
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(5000),
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
    
}
