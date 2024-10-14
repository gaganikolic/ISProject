package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Korisnik;
import models.Paket;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-07-03T20:10:00")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Korisnik> korisnikEmail;
    public static volatile SingularAttribute<Pretplata, Integer> trajanje;
    public static volatile SingularAttribute<Pretplata, Paket> idPaket;
    public static volatile SingularAttribute<Pretplata, Integer> cena;
    public static volatile SingularAttribute<Pretplata, Integer> idPretplata;
    public static volatile SingularAttribute<Pretplata, Date> datumVremePocetka;

}