/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author Nundra
 */
public class CaseControler implements EventHandler {
    int i;
    int j;
    private TrainModel model;

    public CaseControler(int _i, int _j, TrainModel m) {
        this.i = _i;
        this.j = _j;
        this.model=m;
    }

    @Override
    public void handle(Event event) {
        if(model.getMode().equals("construction")){
            if(model.getDecors(i, j).isEditable()){
                model.poserRail(i, j);
            }
            if(model.getCase(i, j)==3){
                model.retirerRail(i, j);
            }
        }
    }
}
/*new EventHandler<MouseEvent>()
                                {
                                    @Override
                                    public void handle(MouseEvent e)
                                    {
                                        Node source = (Node)e.getSource() ;
                                        Integer colIndex = GridPane.getColumnIndex(source);
                                        Integer rowIndex = GridPane.getRowIndex(source);
                                        System.out.println("click :"+colIndex+rowIndex);
                                    }
                                }*/