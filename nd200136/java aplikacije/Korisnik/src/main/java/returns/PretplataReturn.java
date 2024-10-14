/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lenovo
 */
public class PretplataReturn implements Serializable{
    private int idPaket;
    private int cena;
    private String korisnikEmail;
    private Date datumIVremePocetka;
    private int Trajanje;
    
    private static final long serialVersionUID = 1L;

    public int getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(int idPaket) {
        this.idPaket = idPaket;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public String getKorisnikEmail() {
        return korisnikEmail;
    }

    public void setKorisnikEmail(String korisnikEmail) {
        this.korisnikEmail = korisnikEmail;
    }

    public Date getDatumIVremePocetka() {
        return datumIVremePocetka;
    }

    public void setDatumIVremePocetka(Date datumIVremePocetka) {
        this.datumIVremePocetka = datumIVremePocetka;
    }

    public int getTrajanje() {
        return Trajanje;
    }

    public void setTrajanje(int Trajanje) {
        this.Trajanje = Trajanje;
    }
    
    
}
