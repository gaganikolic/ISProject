package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.VideoKategorija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-07-03T23:11:53")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile ListAttribute<Kategorija, VideoKategorija> videoKategorijaList;
    public static volatile SingularAttribute<Kategorija, Integer> idKategorija;

}