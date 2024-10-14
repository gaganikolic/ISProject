/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import models.Korisnik;
import models.Video;

/**
 *
 * @author lenovo
 */
public class Main implements MessageListener{
    @Resource(lookup = "connectionFactoryVideo")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem3Response")
    private static Queue queuePodsistem3Response;
    
    @Resource(lookup = "queuePodsistem3Request")
    private static Queue queuePodsistem3Request;
    
    @Resource(lookup = "topicProjekat2024")
    private static Topic topic;
    
    /**
     * @param args the command line arguments
     */
    public static JMSContext context = null;
    public static EntityManagerFactory emFactory = null;
    public static EntityManager em = null;
    
    public static void main(String[] args) throws JMSException {
        context = connectionFactory.createContext();
        
        emFactory = Persistence.createEntityManagerFactory("Podsistem3PU");
        try (JMSConsumer consumer = context.createConsumer(topic);){
            em = emFactory.createEntityManager();
            consumer.setMessageListener(new Main());
            Podsistem3Handler handler = new Podsistem3Handler(em, context, queuePodsistem3Request, queuePodsistem3Response);
            handler.obradiZahtev();
        } finally {
            emFactory.close();
        }
    }
    
    @Override
    public void onMessage(Message message) {
        EntityTransaction transaction = em.getTransaction();
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objMessage = (ObjectMessage) message;
                String request = objMessage.getStringProperty("request");
                
                if ("dodajKorisnika".equals(request)) {
                    String ime = objMessage.getStringProperty("ime");
                    String email = objMessage.getStringProperty("email");
                    String godiste = objMessage.getStringProperty("godiste");
                    String pol = objMessage.getStringProperty("pol");
                    int idMesto = objMessage.getIntProperty("idMesto");
                    Korisnik korisnik = new Korisnik();
                    try {
                        transaction.begin();
                        korisnik.setIme(ime);
                        korisnik.setEmail(email);
                        korisnik.setGodiste(godiste);
                        korisnik.setPol(pol);
                        korisnik.setIdMesto(idMesto);
                        em.persist(korisnik);
                        transaction.commit();
                    } finally {
                        if(transaction.isActive()) transaction.rollback();
                    } 
                } else if ("promeniEmail".equals(request)) {
                    String stariEmail = objMessage.getStringProperty("stariEmail");
                    String noviEmail = objMessage.getStringProperty("noviEmail");
                    try {
                        transaction.begin();
                        int u = em.createQuery("UPDATE Korisnik k SET k.email = :noviEmail WHERE k.email = :stariEmail")
                            .setParameter("noviEmail", noviEmail)
                            .setParameter("stariEmail", stariEmail)
                            .executeUpdate();
                        em.flush();
                        em.clear();
                        transaction.commit();
                    }  finally {
                        if(transaction.isActive()) transaction.rollback();
                    }
                } else if ("promeniMesto".equals(request)) {
                    String email = objMessage.getStringProperty("email");
                    int idNovoMesto = objMessage.getIntProperty("idMesto");
                    try {
                        transaction.begin();
                        int u = em.createQuery("UPDATE Korisnik k SET k.idMesto = :idMesto WHERE k.email = :email")
                            .setParameter("idMesto", idNovoMesto)
                            .setParameter("email", email)
                            .executeUpdate();
                        em.flush();
                        em.clear();
                        transaction.commit();
                    }  finally {
                        if(transaction.isActive()) transaction.rollback();
                    }
                } else if ("dodajVideo".equals(request)) {
                    String naziv = objMessage.getStringProperty("Naziv");
                    int trajanje = objMessage.getIntProperty("Trajanje");
                    String vlasnikEmail = objMessage.getStringProperty("VlasnikEmail");
                    Date datumIVremePostavljanja = new Date();
                    Video video = new Video();
                    try {
                        transaction.begin();
                        Korisnik vlasnik = em.find(Korisnik.class, vlasnikEmail);
                        video.setNaziv(naziv);
                        video.setTrajanje(trajanje);
                        video.setVlasnikEmail(vlasnik);
                        video.setDatumVremePostavljanja(datumIVremePostavljanja);
                        em.persist(video);
                        transaction.commit();
                    }  finally {
                        if(transaction.isActive()) transaction.rollback();
                    }
                } else if ("promeniNaziv".equals(request)) {
                    String novNaziv = objMessage.getStringProperty("novNaziv");
                    int idVideo = objMessage.getIntProperty("idVideo");
                    try {
                        transaction.begin();
                        em.createQuery("UPDATE Video v SET v.naziv = :novNaziv WHERE v.idVideo = :idVideo")
                            .setParameter("novNaziv", novNaziv)
                            .setParameter("idVideo", idVideo)
                            .executeUpdate();
                        transaction.commit();
                    } finally {
                        if(transaction.isActive()) transaction.rollback();
                    }
                } else if ("obrisiVideo".equals(request)) {
                    int idVideoBrisanje = objMessage.getIntProperty("idVideo");
                    try {
                        transaction.begin();
                        em.createQuery("DELETE FROM Video WHERE idVideo = :idVideo").setParameter("idVideo", idVideoBrisanje).executeUpdate();                   
                        transaction.commit();
                    } finally {
                        if(transaction.isActive()) transaction.rollback();
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
}
