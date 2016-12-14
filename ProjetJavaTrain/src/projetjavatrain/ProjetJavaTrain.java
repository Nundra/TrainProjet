/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import javafx.application.Application;
import static javafx.application.Application.launch;
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
public class ProjetJavaTrain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        SplitPane root = new SplitPane();
        StackPane sp1 = new StackPane();
        StackPane sp2 = new StackPane();
        
        GridPane grille = new GridPane();
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
        
        Image montagne = new Image("img/montagne.png");
    
        for(int i = 0; i<numCols; i++){
            for(int j = 0; j<numRows; j++){
                //placement montagne
                if(
                    (i==10 && j==2) || (i==10 && j==3) || (i==11 && j==3) || (i==11 && j==2)
                     || (i==5 && j==5) || (i==0) || (i==14) || (j==0) || (j==9)
                        ){
                            Decors m = new Decors(i,j,montagne,false);
                            ImageView img = new ImageView(montagne);
                            GridPane.setConstraints(img, i, j);
                            grille.getChildren().add(img);
                }
                //placement plaine
                
                //placement ville
            }
        }
        grille.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                for( Node node: grille.getChildren()) {

                    if( node instanceof Label) {
                        if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                            System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
                        }
                    }
                }
            }
        });

                
        sp1.getChildren().add(new Button("Button Two"));
        sp2.getChildren().add(grille);
        root.getItems().addAll(sp1,sp2);
        Scene scene = new Scene(root, 1200, 500);
        
        primaryStage.setTitle("Train Simulator 2017");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
