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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    boolean mapInit = false;
    boolean pause = false;
    
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
    HBox poserVille = new HBox();

//Gestion des timer
    ArrayList<KeyFrame> listeKeyFrame = new ArrayList<>();
    Timeline time = new Timeline();

//Current decors pour l'affichage de l'élément sélectionné
    static Decors currentDecors = null;

//tout les éléments textuel
        Label l = new Label("Train Simulator 2017");
        
        Button bt1 = new Button("Construction");
        Button bt2 = new Button("New game");
        Button bt3 = new Button("Destruction de ligne");
        Button bt4 = new Button("Pause");
        
        ArrayList<Button> listeBt = new ArrayList<>();
        
        static Label scoreTxt = new Label ("Score : "+tm.getJoueur().getScore());
        static Label upTxt = new Label ("Sélectionner un train ou une ville à améliorer");
        static Button btUpgrade = new Button ("lvl+");
        
        static Label infoJoueurLabel = new Label ("Historique de production");
        static TextArea infoJoueurTxt= new TextArea(tm.getJoueur().toString());
    
        
        static Label poseVilleJoueur = new Label ("Poser une ville cout : 25 000");
        static ComboBox<Bien> cmb = new ComboBox<>();
        static Button btPose = new Button("Créer la ville");
        
        
    public void initMap(int col, int row){
        grille.getChildren().clear();
        for(int i = 0; i<col; i++){
            for(int j = 0; j<row; j++){
                Decors d = tm.getDecors(i, j);
                ImageView img = d.getView();
                img.setFitWidth(40);
                img.setPreserveRatio(true);
                GridPane.setConstraints(img, i, j);
                grille.getChildren().add(img);
                img.setOnMouseClicked(new CaseControler(i,j,tm,img));
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
        time.setRate(15);
        
        update(col,row);
    }
    
    @Override
    public void start(Stage primaryStage) {
        listeBt.add(bt1);
        listeBt.add(bt3);
        listeBt.add(bt4);
        listeBt.add(btPose);
        
        for (int i = 0; i < tm.getCol(); i++) {
            ColumnConstraints colConst = new ColumnConstraints(40);
            grille.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < tm.getRow(); i++) {
            RowConstraints rowConst = new RowConstraints(40);
            grille.getRowConstraints().add(rowConst);         
        }
        
        btUpgrade.setDisable(true);
        infoJoueurTxt.setEditable(false);
        bt1.setOnAction(new btConsControler(tm,bt1));
        bt2.setOnAction(new btNewController(tm,bt2));
        bt3.setOnAction(new btDestControler(tm,bt3));
        bt4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pause){
                    time.play();
                    bt4.setText("Pause");
                    pause = false;
                    avertirFinBt();
                }
                else {
                    time.pause();
                    bt4.setText("Play");
                    pause = true;
                    avertirStartBt(bt4);
                }
            }
        });
        btUpgrade.setOnAction(new btUpgradeController(tm));
        btPose.setOnAction(new btPoseControler(tm,btPose));
        
        for(Bien str:tm.getListeBien()){
            cmb.getItems().add(str);
        }
        btBox.getChildren().addAll(bt1,bt3,bt4);
        jBox.getChildren().addAll(upTxt,btUpgrade);
        poserVille.getChildren().addAll(poseVilleJoueur,cmb,btPose);
        
        btBox.setVisible(false);
        scoreTxt.setVisible(false);
        jBox.setVisible(false);
        infoJoueurLabel.setVisible(false);
        infoJoueurTxt.setVisible(false);
        cmb.setVisible(false);
        poserVille.setVisible(false);
        
        btPose.setDisable(true);
        
        v1.getChildren().addAll(l,bt2,btBox,scoreTxt,jBox,poserVille,infoJoueurLabel,infoJoueurTxt);
        sp1.getChildren().add(v1);
        sp2.getChildren().add(grille);
        root.getItems().addAll(sp1,sp2);
        Scene scene = new Scene(root, 1250, 600);
        
        //CSS
        sp2.setId("sp2");
        sp1.setId("sp1");
        bt2.setId("bt2");
        scene.getStylesheets().add("projetjavatrain/style.css");
        
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
                ImageView img = (ImageView) getNodeByRowColumnIndex(j,i,grille);
                img.setImage(d.getImg());
            } 
        }
    }
    

    @Override
    public void avertir(int col, int row) {
        update(col,row);
    }
    
    @Override
    public void avertirNewTimeLine(Train t){
        int vitesse = t.getVitesse()*500;
        
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
        int vitesse = t.getVitesse()*2000;
        if(!pause)time.stop();
        time.getKeyFrames().remove(t.getTimeline());
        KeyFrame kf = new KeyFrame(
                    Duration.millis(vitesse),
                    ae -> {
                        tm.bougerTrain(t);
                    });
        time.getKeyFrames().add(kf);
        if(!pause)time.play();
    }
    
    @Override
    public void avertirNewGame(int col, int row){
        btBox.setVisible(true);
        scoreTxt.setVisible(true);
        jBox.setVisible(true);
        infoJoueurLabel.setVisible(true);
        infoJoueurTxt.setVisible(true);
        cmb.setVisible(true);
        poserVille.setVisible(true);
        sp2.setStyle("-fx-background-image: url('img/bg.jpg')");
        
        time.stop();
        for(KeyFrame t:time.getKeyFrames()){
            t=null;
        }
        time.getKeyFrames().clear();
        initMap(col,row);
        avertirFinBt();
        bt4.setText("Pause");
        pause = false;
        btPose.setDisable(true);
    }
    @Override
    public void avertirPause(){
        time.pause();
    }
    @Override
    public void avertirFinPause(){
        time.play();
    }
    
    @Override
    public void avertirTxtInterface(Decors d){
        currentDecors = d;
        updateTxtUi(d);
    }
    @Override
    public void avertirFinBt(){
        for(Button b:listeBt){
            b.setDisable(false);
        }
        btPoseControler.reset();
    }
    @Override
    public void avertirStartBt(Button bt){
        for(Button b:listeBt){
            if(b!=bt)b.setDisable(true);
        }
    }
 
    public static void updateTxtUi(Decors d){
        infoJoueurTxt.setText(tm.getJoueur().toString());
        
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
        
        if(tm.getJoueur().getArgent()<25000){
            btPose.setDisable(true);
        }else{
            btPose.setDisable(false);
        }
    }
    
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
    
}
