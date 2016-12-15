/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Nundra
 */
public class ProjetJavaTrain extends Application implements Observateur{
    TrainModel tm = new TrainModel();
    SplitPane root = new SplitPane();
    StackPane sp1 = new StackPane();
    StackPane sp2 = new StackPane();
    GridPane grille = new GridPane();
    
    public void initMap(int numCols, int numRows, GridPane grille){
        for(int i = 0; i<numCols; i++){
            for(int j = 0; j<numRows; j++){
                Decors d = tm.getDecors(i, j);
                ImageView img = new ImageView(d.getImg());
                img.setFitWidth(50);
                img.setPreserveRatio(true);
                GridPane.setConstraints(img, i, j);
                grille.getChildren().add(img);
                img.setOnMouseClicked(new CaseControler(i,j,tm));
            }
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        grille.setGridLinesVisible(true);
        int numCols = 15 ;
        int numRows = 10 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints(50);
            grille.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(50);
            grille.getRowConstraints().add(rowConst);         
        }
        
        initMap(numCols,numRows,grille);
        
        Button bt = new Button("Construction");
        bt.setOnAction(new btConsControler(tm,bt));
        sp1.getChildren().add(bt);
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

    @Override
    public void avertir(int i, int j) {
            Decors d = tm.getDecors(i, j);
            ImageView img = new ImageView(d.getImg());
            img.setFitWidth(50);
            img.setPreserveRatio(true);
            GridPane.setConstraints(img, i, j);
            grille.getChildren().add(img);
    }
    
}
