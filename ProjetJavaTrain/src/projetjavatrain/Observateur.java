/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjavatrain;

import javafx.scene.image.ImageView;

/**
 *
 * @author Nundra
 */
public interface Observateur {
    public abstract void avertir(int col, int row);
    public abstract void avertirNewTimeLine(Train t);
    public abstract void avertirUpdateTimeLine(Train t);
    public abstract void avertirNewGame(int col, int row);
    public abstract void avertirPause();
    public abstract void avertirFinPause();
    public abstract void avertirTxtInterface(Decors d);
}
