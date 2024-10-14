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
public class GledanjeReturn implements Serializable{
    private int sekundeNastavljanja;
    private int sekundeOdgledano;
    private String nazivVidea;
    private String korisnikEmail;
    private Integer idVideo;
    private int idGledanje;
    
    private static final long serialVersionUID = 1L;

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

    public int getSekundeNastavljanja() {
        return sekundeNastavljanja;
    }

    public void setSekundeNastavljanja(int sekundeNastavljanja) {
        this.sekundeNastavljanja = sekundeNastavljanja;
    }

    public int getSekundeOdgledano() {
        return sekundeOdgledano;
    }

    public void setSekundeOdgledano(int sekundeOdgledano) {
        this.sekundeOdgledano = sekundeOdgledano;
    }

    public Integer getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Integer idVideo) {
        this.idVideo = idVideo;
    }

    public int getIdGledanje() {
        return idGledanje;
    }

    public void setIdGledanje(int idGledanje) {
        this.idGledanje = idGledanje;
    }
    
    
    
}
