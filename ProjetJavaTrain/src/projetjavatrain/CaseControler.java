/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

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
        System.out.println("Case cliqué : "+model.getCase(i, j)+" - i"+i+",j"+j);
        System.out.println("image cliqué : "+model.getDecors(i, j).getImg());
        System.out.println(model.getDecors(i,j).isEditable());
        if(model.getMode().equals("construction")){
            if(model.getDecors(i, j).isEditable() && model.getCase(i, j)==0){
                model.poserRail(i, j);
            }else if(model.getDecors(i, j).isEditable() && model.getCase(i, j)==3){
                model.retirerRail(i, j);
            }
        }else if(model.getMode().equals("destruction")){
            if(model.getDecors(i, j) instanceof Rail){
                model.destruction(i,j);
            }
        } else if(model.getMode().equals("pose")){
            if(model.getCase(i, j)==0){
                Bien str = ProjetJavaTrain.cmb.getSelectionModel().getSelectedItem();
                model.poseVille(i,j,str);
            }
        }else{
            if(model.getDecors(i, j) instanceof Ville || 
                    model.getDecors(i, j) instanceof Train){
                model.avertirTxtInterface(model.getDecors(i,j));
            }
        }
        if(model.getCase(i, j)==5){
            Rail r = (Rail) model.getDecors(i, j);
            //System.out.println("id rail : "+r.getId());
        }
    }
}