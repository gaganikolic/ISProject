package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Korisnik;
import models.Video;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-07-03T20:10:00")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Korisnik> korisnikEmail;
    public static volatile SingularAttribute<Ocena, Video> idVideo;
    public static volatile SingularAttribute<Ocena, Integer> idOcena;
    public static volatile SingularAttribute<Ocena, Date> datumVremeOcenjivanja;
    public static volatile SingularAttribute<Ocena, Integer> ocena;

}