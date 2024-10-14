/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package returns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class VideoReturn implements Serializable{
    private String naziv;
    private int trajanje;
    private Date datumIVremePostavljana;
    private String vlasnikEmail;
    private List<KategorijaReturn> kategorije;
    private int idVideo;
    
    private static final long serialVersionUID = 1L;
    
    public VideoReturn() {
        kategorije = new ArrayList<>();
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumIVremePostavljana() {
        return datumIVremePostavljana;
    }

    public void setDatumIVremePostavljana(Date datumIVremePostavljana) {
        this.datumIVremePostavljana = datumIVremePostavljana;
    }

    public String getVlasnikEmail() {
        return vlasnikEmail;
    }

    public void setVlasnikEmail(String vlasnikEmail) {
        this.vlasnikEmail = vlasnikEmail;
    }

    public List<KategorijaReturn> getKategorije() {
        return kategorije;
    }

    public void setKategorije(List<KategorijaReturn> kategorije) {
        this.kategorije = kategorije;
    }
    
    public void dodajKategoriju(KategorijaReturn k) {
        kategorije.add(k);
    }

    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }
    
    
    
}
