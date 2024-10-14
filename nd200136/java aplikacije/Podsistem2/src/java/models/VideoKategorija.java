/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "video_kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VideoKategorija.findAll", query = "SELECT v FROM VideoKategorija v"),
    @NamedQuery(name = "VideoKategorija.findByIdVideoKategorija", query = "SELECT v FROM VideoKategorija v WHERE v.idVideoKategorija = :idVideoKategorija")})
public class VideoKategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVideoKategorija")
    private Integer idVideoKategorija;
    @JoinColumn(name = "IdKategorija", referencedColumnName = "IdKategorija")
    @ManyToOne(optional = false)
    private Kategorija idKategorija;
    @JoinColumn(name = "IdVideo", referencedColumnName = "IdVideo")
    @ManyToOne(optional = false)
    private Video idVideo;

    public VideoKategorija() {
    }

    public VideoKategorija(Integer idVideoKategorija) {
        this.idVideoKategorija = idVideoKategorija;
    }

    public Integer getIdVideoKategorija() {
        return idVideoKategorija;
    }

    public void setIdVideoKategorija(Integer idVideoKategorija) {
        this.idVideoKategorija = idVideoKategorija;
    }

    public Kategorija getIdKategorija() {
        return idKategorija;
    }

    public void setIdKategorija(Kategorija idKategorija) {
        this.idKategorija = idKategorija;
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
        hash += (idVideoKategorija != null ? idVideoKategorija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideoKategorija)) {
            return false;
        }
        VideoKategorija other = (VideoKategorija) object;
        if ((this.idVideoKategorija == null && other.idVideoKategorija != null) || (this.idVideoKategorija != null && !this.idVideoKategorija.equals(other.idVideoKategorija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.VideoKategorija[ idVideoKategorija=" + idVideoKategorija + " ]";
    }
    
}
