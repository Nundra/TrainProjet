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
public class btConsControler implements EventHandler {
    private TrainModel model;
    private static Button bt;
    private static Boolean on;
    
    public btConsControler(TrainModel m, Button bt) {
        this.model=m;
        this.bt=bt;
        this.on = false;
    }
    
    public static void reset(){
        on = false;
        bt.setText("Construction");
    }
    
    @Override
    public void handle(Event event) {
        if(on){
            model.setMode("game");
            bt.setText("Construction");
            on = false;
            model.finConstruction();
            model.avertirFinBtAll();
        }else{
            System.out.println("construction started");
            model.setMode("construction");
            bt.setText("Fin");
            on = true;
            model.startConstruction();
            model.avertirStartBtAll(bt);
        }
        model.avertirAllObservateurs();
    }
    
}
