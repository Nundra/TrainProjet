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
public class btUpgradeController  implements EventHandler {
    private TrainModel model;
    
    public btUpgradeController(TrainModel m) {
        this.model=m;
    }
    
    @Override
    public void handle(Event event) {
        if(model.currentDecors instanceof Ville){
            Ville v = (Ville) model.currentDecors;
            v.upgradeVille(model.getJoueur());
        }
        if(model.currentDecors instanceof Train){
            Train t = (Train) model.currentDecors;
            model.avertirUpdateTimeLine(t);
        }
    }
    
}
