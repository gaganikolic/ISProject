/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdPretplata", query = "SELECT p FROM Pretplata p WHERE p.idPretplata = :idPretplata"),
    @NamedQuery(name = "Pretplata.findByTrajanje", query = "SELECT p FROM Pretplata p WHERE p.trajanje = :trajanje"),
    @NamedQuery(name = "Pretplata.findByDatumVremePocetka", query = "SELECT p FROM Pretplata p WHERE p.datumVremePocetka = :datumVremePocetka"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPretplata")
    private Integer idPretplata;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private int cena;
    @JoinColumn(name = "KorisnikEmail", referencedColumnName = "Email")
    @ManyToOne(optional = false)
    private Korisnik korisnikEmail;
    @JoinColumn(name = "IdPaket", referencedColumnName = "IdPaket")
    @ManyToOne(optional = false)
    private Paket idPaket;

    public Pretplata() {
    }

    public Pretplata(Integer idPretplata) {
        this.idPretplata = idPretplata;
    }

    public Pretplata(Integer idPretplata, int trajanje, Date datumVremePocetka, int cena) {
        this.idPretplata = idPretplata;
        this.trajanje = trajanje;
        this.datumVremePocetka = datumVremePocetka;
        this.cena = cena;
    }

    public Integer getIdPretplata() {
        return idPretplata;
    }

    public void setIdPretplata(Integer idPretplata) {
        this.idPretplata = idPretplata;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik getKorisnikEmail() {
        return korisnikEmail;
    }

    public void setKorisnikEmail(Korisnik korisnikEmail) {
        this.korisnikEmail = korisnikEmail;
    }

    public Paket getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(Paket idPaket) {
        this.idPaket = idPaket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPretplata != null ? idPretplata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idPretplata == null && other.idPretplata != null) || (this.idPretplata != null && !this.idPretplata.equals(other.idPretplata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Pretplata[ idPretplata=" + idPretplata + " ]";
    }
    
}
