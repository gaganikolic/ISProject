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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIdOcena", query = "SELECT o FROM Ocena o WHERE o.idOcena = :idOcena"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatumVremeOcenjivanja", query = "SELECT o FROM Ocena o WHERE o.datumVremeOcenjivanja = :datumVremeOcenjivanja")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdOcena")
    private Integer idOcena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ocena")
    private int ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremeOcenjivanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeOcenjivanja;
    @JoinColumn(name = "KorisnikEmail", referencedColumnName = "Email")
    @ManyToOne(optional = false)
    private Korisnik korisnikEmail;
    @JoinColumn(name = "IdVideo", referencedColumnName = "IdVideo")
    @ManyToOne(optional = false)
    private Video idVideo;

    public Ocena() {
    }

    public Ocena(Integer idOcena) {
        this.idOcena = idOcena;
    }

    public Ocena(Integer idOcena, int ocena, Date datumVremeOcenjivanja) {
        this.idOcena = idOcena;
        this.ocena = ocena;
        this.datumVremeOcenjivanja = datumVremeOcenjivanja;
    }

    public Integer getIdOcena() {
        return idOcena;
    }

    public void setIdOcena(Integer idOcena) {
        this.idOcena = idOcena;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumVremeOcenjivanja() {
        return datumVremeOcenjivanja;
    }

    public void setDatumVremeOcenjivanja(Date datumVremeOcenjivanja) {
        this.datumVremeOcenjivanja = datumVremeOcenjivanja;
    }

    public Korisnik getKorisnikEmail() {
        return korisnikEmail;
    }

    public void setKorisnikEmail(Korisnik korisnikEmail) {
        this.korisnikEmail = korisnikEmail;
    }

    public Video getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Video idVideo) {
        this.idVideo = idVideo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOcena != null ? idOcena.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.idOcena == null && other.idOcena != null) || (this.idOcena != null && !this.idOcena.equals(other.idOcena))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Ocena[ idOcena=" + idOcena + " ]";
    }
    
}
