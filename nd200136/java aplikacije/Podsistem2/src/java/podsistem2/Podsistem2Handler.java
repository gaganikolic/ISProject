/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jms.Queue;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import models.Kategorija;
import models.Korisnik;
import models.Video;
import models.VideoKategorija;
import returns.KategorijaReturn;
import returns.VideoReturn;

/**
 *
 * @author lenovo
 */
public class Podsistem2Handler {
    private final EntityManager entityManager;
    private final JMSContext context;
    private final Queue requestsQueue, responsesQueue;
    private final Topic topic;

    Podsistem2Handler(EntityManager entityManager, JMSContext context, Queue requestQueue, Queue responseQueue, Topic topic) {
        this.entityManager = entityManager;
        this.context = context;
        this.requestsQueue = requestQueue;
        this.responsesQueue = responseQueue;
        this.topic = topic;
    }
    
    public ObjectMessage zahteviKategorija(String request, ObjectMessage objMessage) throws JMSException {
        EntityTransaction transaction = entityManager.getTransaction();
        
        switch(request){
            case "dodajKategoriju":
                String naziv = (String) objMessage.getStringProperty("Naziv");
                Kategorija kategorija = new Kategorija();
                KategorijaReturn kategorijaReturn = new KategorijaReturn();
                kategorija.setNaziv(naziv);
                
                try {
                    transaction.begin();
                    entityManager.persist(kategorija);
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                kategorijaReturn.setNaziv(naziv);
                kategorijaReturn.setIdKategorija(0);
                return context.createObjectMessage(kategorijaReturn);
            case "dohvatiSveKategorije":
                List<KategorijaReturn> kategorijeReturn = new ArrayList<>();
                List<Kategorija> kategorije =  null;
                
                try {
                    transaction.begin();
                    kategorije = entityManager.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
                    
                    for(Kategorija k: kategorije) {
                        KategorijaReturn kr = new KategorijaReturn();
                        kr.setNaziv(k.getNaziv());
                        kr.setIdKategorija(k.getIdKategorija().intValue());
                        kategorijeReturn.add(kr);
                    }
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                
                return context.createObjectMessage((Serializable) kategorijeReturn);
            default:
                return null;
        }
    }

    public ObjectMessage zahteviVideo(String request, ObjectMessage objMessage) throws JMSException {
        EntityTransaction transaction = entityManager.getTransaction();
        List<Video> videi =  null;
        Video video = new Video();
        VideoReturn videoReturn = new VideoReturn();
        
        switch(request) {
            case "dodajVideo":
                String naziv = objMessage.getStringProperty("Naziv");
                int trajanje = objMessage.getIntProperty("Trajanje");
                String vlasnikEmail = objMessage.getStringProperty("VlasnikEmail");
                Date datumIVremePostavljanja = new Date();
                
                try {
                    transaction.begin();
                    Korisnik vlasnik = entityManager.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", vlasnikEmail).getSingleResult();
                    video.setNaziv(naziv);
                    video.setTrajanje(trajanje);
                    video.setVlasnikEmail(vlasnik);
                    video.setDatumVremePostavljanja(datumIVremePostavljanja);
                    entityManager.persist(video);
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                
                videoReturn.setDatumIVremePostavljana(datumIVremePostavljanja);
                videoReturn.setNaziv(naziv);
                videoReturn.setTrajanje(trajanje);
                videoReturn.setVlasnikEmail(vlasnikEmail);
                videoReturn.setKategorije(null);
                videoReturn.setIdVideo(0);
                return context.createObjectMessage(videoReturn);
            case "dohvatiSveVidee":
                List<VideoReturn> videiReturn = new ArrayList<>();
                
                try {
                    transaction.begin();
                    videi = entityManager.createNamedQuery("Video.findAll", Video.class).getResultList();
                    
                    for(Video v: videi) {
                        VideoReturn vr = new VideoReturn();
                        vr.setNaziv(v.getNaziv());
                        vr.setDatumIVremePostavljana(v.getDatumVremePostavljanja());
                        vr.setIdVideo(v.getIdVideo());
                        vr.setKategorije(null);
                        vr.setTrajanje(v.getTrajanje());
                        vr.setVlasnikEmail(v.getVlasnikEmail().getEmail());
                        videiReturn.add(vr);
                    }
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                return context.createObjectMessage((Serializable) videiReturn);
            case "promeniNaziv":
                String novNaziv = objMessage.getStringProperty("novNaziv");
                int idVideo = objMessage.getIntProperty("idVideo");
                Video v = null;
                try {
                    transaction.begin();
                    int u = entityManager.createQuery("UPDATE Video v SET v.naziv = :novNaziv WHERE v.idVideo = :idVideo")
                        .setParameter("novNaziv", novNaziv)
                        .setParameter("idVideo", idVideo)
                        .executeUpdate();
                    
                    if(u > 0) {
                        entityManager.flush();
                        entityManager.clear();
                        v = entityManager.find(Video.class, idVideo);  
                        entityManager.refresh(v);  
                    }
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                
                videoReturn.setDatumIVremePostavljana(v.getDatumVremePostavljanja());
                videoReturn.setIdVideo(idVideo);
                videoReturn.setNaziv(novNaziv);
                videoReturn.setKategorije(null);
                videoReturn.setTrajanje(v.getTrajanje());
                videoReturn.setVlasnikEmail(v.getVlasnikEmail().getEmail());
                return context.createObjectMessage(videoReturn);
            case "dodajKategoriju":
                int idKategorija = objMessage.getIntProperty("idKategorija");
                int idVideoKategorija = objMessage.getIntProperty("idVideo");
                Video idVideoZaVideoKategorija = null;
                Kategorija idKategorijaZaVideoKategorija = null;
                VideoKategorija videoKategorija = new VideoKategorija();
                try {
                    transaction.begin();
                    idVideoZaVideoKategorija = entityManager.find(Video.class, idVideoKategorija);  
                    idKategorijaZaVideoKategorija = entityManager.find(Kategorija.class, idKategorija);
                    
                    videoKategorija.setIdKategorija(idKategorijaZaVideoKategorija);
                    videoKategorija.setIdVideo(idVideoZaVideoKategorija);
                    
                    entityManager.persist(videoKategorija);
                    entityManager.flush();
                    entityManager.clear();
                    idVideoZaVideoKategorija = entityManager.find(Video.class, idVideoKategorija);
                    entityManager.refresh(idVideoZaVideoKategorija);
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                
                videoReturn.setDatumIVremePostavljana(idVideoZaVideoKategorija.getDatumVremePostavljanja());
                videoReturn.setIdVideo(idVideoKategorija);
                videoReturn.setNaziv(idVideoZaVideoKategorija.getNaziv());
                for(VideoKategorija vk: idVideoZaVideoKategorija.getVideoKategorijaList()) {
                    KategorijaReturn k = new KategorijaReturn();
                    k.setIdKategorija(vk.getIdKategorija().getIdKategorija());
                    k.setNaziv(vk.getIdKategorija().getNaziv());
                    videoReturn.dodajKategoriju(k);
                }
                videoReturn.setTrajanje(idVideoZaVideoKategorija.getTrajanje());
                videoReturn.setVlasnikEmail(idVideoZaVideoKategorija.getVlasnikEmail().getEmail());
                return context.createObjectMessage(videoReturn);
            case "dohvatiKategorijeVidea":
                int idVideoKategorije = objMessage.getIntProperty("idVideo");
                try {
                    transaction.begin();
                    videi = entityManager.createNamedQuery("Video.findByIdVideo", Video.class).setParameter("idVideo", idVideoKategorije).getResultList();                   
                    video = videi.get(0);  
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                
                videoReturn.setDatumIVremePostavljana(video.getDatumVremePostavljanja());
                videoReturn.setIdVideo(idVideoKategorije);
                videoReturn.setNaziv(video.getNaziv());
                for(VideoKategorija vk: video.getVideoKategorijaList()) {
                    KategorijaReturn k = new KategorijaReturn();
                    k.setIdKategorija(vk.getIdKategorija().getIdKategorija());
                    k.setNaziv(vk.getIdKategorija().getNaziv());
                    videoReturn.dodajKategoriju(k);
                }
                videoReturn.setTrajanje(video.getTrajanje());
                videoReturn.setVlasnikEmail(video.getVlasnikEmail().getEmail());
                return context.createObjectMessage(videoReturn);
            case "obrisiVideo":
                int idVideoBrisanje = objMessage.getIntProperty("idVideo");
                VideoReturn obrisanVideo = new VideoReturn();
                obrisanVideo.setDatumIVremePostavljana(null);
                obrisanVideo.setIdVideo(idVideoBrisanje);
                obrisanVideo.setKategorije(null);
                obrisanVideo.setNaziv(null);
                obrisanVideo.setTrajanje(0);
                obrisanVideo.setVlasnikEmail(null);
                try {
                    transaction.begin();
                    entityManager.createQuery("DELETE FROM Video WHERE idVideo = :idVideo").setParameter("idVideo", idVideoBrisanje).executeUpdate();                   
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                return context.createObjectMessage(obrisanVideo);
            default:
                return null;
        }
        
    }
    
    void obradiZahtev() throws JMSException {
        try (JMSConsumer consumer = context.createConsumer(requestsQueue);) {
            JMSProducer producer = context.createProducer();
            while(true) {
                System.out.println("Ceka se zahtev");
                ObjectMessage requestMessage = (ObjectMessage)consumer.receive();
                producer.send(topic, requestMessage);   

                String zahtevZa = requestMessage.getStringProperty("resource");
                String zahtev = requestMessage.getStringProperty("request");

                System.out.println("Pristigao je zahtev " + zahtev + ".");          

                ObjectMessage responseMessage = null;
                switch(zahtevZa) {
                case "kategorija":
                    responseMessage = zahteviKategorija(zahtev, requestMessage);
                    break;
                case "video":
                    responseMessage = zahteviVideo(zahtev, requestMessage);
                    break;
                default:
                    System.out.println("Podsistem 2 ne moze da obradi zahtev: " + zahtev);
                    continue;
                }
                producer.send(responsesQueue, responseMessage);

                System.out.println("Zahtev " + zahtev + " je obradjen!");
            }
        }
    }
    
}
