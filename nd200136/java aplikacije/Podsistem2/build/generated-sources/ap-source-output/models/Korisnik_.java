package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Video;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-07-07T15:30:38")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, Integer> idMesto;
    public static volatile ListAttribute<Korisnik, Video> videoList;
    public static volatile SingularAttribute<Korisnik, String> godiste;
    public static volatile SingularAttribute<Korisnik, String> pol;
    public static volatile SingularAttribute<Korisnik, String> email;

}