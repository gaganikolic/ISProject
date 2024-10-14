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
public class OcenaReturn implements Serializable{
    private int ocena;
    private int idVideo;
    private String nazivVidea;
    private String korisnikEmail;
    private int idOcena;

    private static final long serialVersionUID = 1L;
    
    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }

    public String getNazivVidea() {
        return nazivVidea;
    }

    public void setNazivVidea(String nazivVidea) {
        this.nazivVidea = nazivVidea;
    }

    public String getKorisnikEmail() {
        return korisnikEmail;
    }

    public void setKorisnikEmail(String korisnikEmail) {
        this.korisnikEmail = korisnikEmail;
    }

    public int getIdOcena() {
        return idOcena;
    }

    public void setIdOcena(int idOcena) {
        this.idOcena = idOcena;
    }
    
    
    
}
