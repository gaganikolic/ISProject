package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Korisnik;
import models.Video;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-07-03T20:10:00")
@StaticMetamodel(Gledanje.class)
public class Gledanje_ { 

    public static volatile SingularAttribute<Gledanje, Integer> idGledanje;
    public static volatile SingularAttribute<Gledanje, Integer> sekundaNastavljanja;
    public static volatile SingularAttribute<Gledanje, Korisnik> korisnikEmail;
    public static volatile SingularAttribute<Gledanje, Video> idVideo;
    public static volatile SingularAttribute<Gledanje, Date> datumVremePocetkaGledanja;
    public static volatile SingularAttribute<Gledanje, Integer> sekundeOdgledane;

}