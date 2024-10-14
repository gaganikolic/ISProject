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
public class MestoReturn implements Serializable{
    private String naziv;
    private int id;
    
    private static final long serialVersionUID = 1L;
    
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
