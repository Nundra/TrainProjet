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
import javafx.scene.image.ImageView;

/**
 *
 * @author Nundra
 */
public class CaseControler implements EventHandler {
    int i;
    int j;
    private TrainModel model;
    private ImageView img;

    public CaseControler(int _i, int _j, TrainModel m, ImageView img) {
        this.i = _i;
        this.j = _j;
        this.model=m;
        this.img=img;
    }

    @Override
    public void handle(Event event) {
        System.out.println("Case cliqué : "+model.getCase(i, j));
        System.out.println("pose cliqué : "+i+" "+j);
        System.out.println("pose valide : "+model.getDecors(i, j).isValide());
        if(model.getMode().equals("construction")){
            if(model.getDecors(i, j).isEditable() && model.getCase(i, j)==0){
                model.poserRail(i, j);
            }else if(model.getDecors(i, j).isEditable() && model.getCase(i, j)==3){
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