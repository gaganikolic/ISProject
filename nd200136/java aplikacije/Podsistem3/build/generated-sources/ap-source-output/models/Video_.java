package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Gledanje;
import models.Korisnik;
import models.Ocena;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-07-07T15:30:42")
@StaticMetamodel(Video.class)
public class Video_ { 

    public static volatile SingularAttribute<Video, Integer> idVideo;
    public static volatile SingularAttribute<Video, Korisnik> vlasnikEmail;
    public static volatile ListAttribute<Video, Gledanje> gledanjeList;
    public static volatile SingularAttribute<Video, Integer> trajanje;
    public static volatile ListAttribute<Video, Ocena> ocenaList;
    public static volatile SingularAttribute<Video, String> naziv;
    public static volatile SingularAttribute<Video, Date> datumVremePostavljanja;

}