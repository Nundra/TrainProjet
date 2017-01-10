/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author Nundra
 */
public class btPoseControler implements EventHandler {
    private TrainModel model;
    private static Button bt;
    private static Boolean on;
    
    public btPoseControler(TrainModel m, Button bt) {
        this.model=m;
        this.bt=bt;
        this.on = false;
    }
    
    public static void reset(){
        on = false;
        bt.setText("Créer la ville");
    }
    
    @Override
    public void handle(Event event) {
        if(on){
            model.setMode("game");
            bt.setText("Créer la ville");
            on = false;
            model.avertirFinBtAll();
        }else{
            model.setMode("pose");
            bt.setText("Annuler");
            on = true;
            model.avertirStartBtAll(bt);
        }
        model.avertirAllObservateurs();
    }
    
}
