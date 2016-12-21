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
public class btNewController implements EventHandler {
    private TrainModel model;
    Button bt;
    
    public btNewController(TrainModel m, Button bt) {
        this.model=m;
        this.bt=bt;
    }
    
    @Override
    public void handle(Event event) {
        System.out.println("new game");
        model.newGame();
    }
    
}
