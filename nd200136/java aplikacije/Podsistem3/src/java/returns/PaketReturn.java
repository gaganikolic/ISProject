/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import java.io.Serializable;

/**
 *
 * @author lenovo
 */
public class PaketReturn implements Serializable{
    private int trenutnaCena;
    private int idPaket;
    
    private static final long serialVersionUID = 1L;
    
    public int getTrenutnaCena() {
        return trenutnaCena;
    }

    public int getIdPaket() {
        return idPaket;
    }

    public void setTrenutnaCena(int trenutnaCena) {
        this.trenutnaCena = trenutnaCena;
    }

    public void setIdPaket(int idPaket) {
        this.idPaket = idPaket;
    }
    
    
}
