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
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIdGledanje", query = "SELECT g FROM Gledanje g WHERE g.idGledanje = :idGledanje"),
    @NamedQuery(name = "Gledanje.findByDatumVremePocetkaGledanja", query = "SELECT g FROM Gledanje g WHERE g.datumVremePocetkaGledanja = :datumVremePocetkaGledanja"),
    @NamedQuery(name = "Gledanje.findBySekundeNastavljanja", query = "SELECT g FROM Gledanje g WHERE g.sekundeNastavljanja = :sekundeNastavljanja"),
    @NamedQuery(name = "Gledanje.findBySekundeOdgledane", query = "SELECT g FROM Gledanje g WHERE g.sekundeOdgledane = :sekundeOdgledane")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdGledanje")
    private Integer idGledanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePocetkaGledanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetkaGledanja;
    @Column(name = "SekundeNastavljanja")
    private Integer sekundeNastavljanja;
    @Column(name = "SekundeOdgledane")
    private Integer sekundeOdgledane;
    @JoinColumn(name = "KorisnikEmail", referencedColumnName = "Email")
    @ManyToOne(optional = false)
    private Korisnik korisnikEmail;
    @JoinColumn(name = "IdVideo", referencedColumnName = "IdVideo")
    @ManyToOne(optional = false)
    private Video idVideo;

    public Gledanje() {
    }

    public Gledanje(Integer idGledanje) {
        this.idGledanje = idGledanje;
    }

    public Gledanje(Integer idGledanje, Date datumVremePocetkaGledanja) {
        this.idGledanje = idGledanje;
        this.datumVremePocetkaGledanja = datumVremePocetkaGledanja;
    }

    public Integer getIdGledanje() {
        return idGledanje;
    }

    public void setIdGledanje(Integer idGledanje) {
        this.idGledanje = idGledanje;
    }

    public Date getDatumVremePocetkaGledanja() {
        return datumVremePocetkaGledanja;
    }

    public void setDatumVremePocetkaGledanja(Date datumVremePocetkaGledanja) {
        this.datumVremePocetkaGledanja = datumVremePocetkaGledanja;
    }

    public Integer getSekundeNastavljanja() {
        return sekundeNastavljanja;
    }

    public void setSekundeNastavljanja(Integer sekundeNastavljanja) {
        this.sekundeNastavljanja = sekundeNastavljanja;
    }

    public Integer getSekundeOdgledane() {
        return sekundeOdgledane;
    }

    public void setSekundeOdgledane(Integer sekundeOdgledane) {
        this.sekundeOdgledane = sekundeOdgledane;
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
        hash += (idGledanje != null ? idGledanje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.idGledanje == null && other.idGledanje != null) || (this.idGledanje != null && !this.idGledanje.equals(other.idGledanje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Gledanje[ idGledanje=" + idGledanje + " ]";
    }
    
}
