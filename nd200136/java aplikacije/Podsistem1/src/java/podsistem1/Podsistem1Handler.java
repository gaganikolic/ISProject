/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import models.Korisnik;
import models.Mesto;
import returns.KorisnikReturn;
import returns.MestoReturn;

/**
 *
 * @author lenovo
 */
public class Podsistem1Handler {
    private final EntityManager entityManager;
    private final JMSContext context;
    private final Queue requestsQueue, responsesQueue;
    private final Topic topic;
    
    public Podsistem1Handler(EntityManager entityManager, JMSContext context, Queue requestQueue, Queue responsesQueue, Topic topic) {
        this.entityManager = entityManager;
        this.context = context;
        this.requestsQueue = requestQueue;
        this.responsesQueue = responsesQueue;
        this.topic = topic;
    }
    
    public ObjectMessage zahteviMesto(String request, ObjectMessage objMessage) throws JMSException {
        EntityTransaction transaction = entityManager.getTransaction();
        
        switch(request) {
            case "dodajGrad":
                String naziv = (String) objMessage.getStringProperty("Naziv");
                Mesto noviGrad = new Mesto();
                noviGrad.setNaziv(naziv);
                MestoReturn mr = new MestoReturn();
                mr.setNaziv(naziv);
                mr.setId(0);
                try {
                    transaction.begin();
                    entityManager.persist(noviGrad);
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                
                return context.createObjectMessage(mr);
            case "dohvatiSveGradove":
                List<Mesto> gradovi = null;
                List<MestoReturn> gradoviReturn = new ArrayList<>();
                try {
                    transaction.begin();
                    gradovi = entityManager.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                    
                    for(Mesto grad: gradovi) {
                        MestoReturn mestoReturn = new MestoReturn();
                        mestoReturn.setNaziv(grad.getNaziv());
                        mestoReturn.setId(grad.getIdMesto());
                        gradoviReturn.add(mestoReturn);
                    }
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                return context.createObjectMessage((Serializable) gradoviReturn);
            default:
                return null;
        }
    }
    
    public ObjectMessage zahteviKorisnik(String request, ObjectMessage objMessage) throws JMSException {
        EntityTransaction transaction = entityManager.getTransaction();
        Korisnik korisnik = new Korisnik();
        Mesto mesto = null;
        KorisnikReturn korisnikReturn = null;
        
        switch(request) {
            case "dodajKorisnika":
                String ime = objMessage.getStringProperty("ime");
                String email = objMessage.getStringProperty("email");
                String godiste = objMessage.getStringProperty("godiste");
                String pol = objMessage.getStringProperty("pol");
                int idMesto = objMessage.getIntProperty("idMesto");
                
                try {
                    transaction.begin();
                    mesto = entityManager.find(Mesto.class, idMesto);
                    
                    korisnik.setIme(ime);
                    korisnik.setEmail(email);
                    korisnik.setGodiste(godiste);
                    korisnik.setPol(pol);
                    korisnik.setIdMesto(mesto);
                    
                    entityManager.persist(korisnik);
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                korisnikReturn = new KorisnikReturn();
                korisnikReturn.setEmail(email);
                korisnikReturn.setIme(ime);
                korisnikReturn.setNazivMesta(mesto.getNaziv());
                
                return context.createObjectMessage(korisnikReturn);
            case "promeniEmail":
                String stariEmail = objMessage.getStringProperty("stariEmail");
                String noviEmail = objMessage.getStringProperty("noviEmail");
                
                try {
                    transaction.begin();
                    int u = entityManager.createQuery("UPDATE Korisnik k SET k.email = :noviEmail WHERE k.email = :stariEmail")
                        .setParameter("noviEmail", noviEmail)
                        .setParameter("stariEmail", stariEmail)
                        .executeUpdate();
                    
                    entityManager.flush();
                    entityManager.clear();
                    korisnik = entityManager.find(Korisnik.class, noviEmail);
                    entityManager.refresh(korisnik);
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                korisnikReturn = new KorisnikReturn();
                korisnikReturn.setEmail(noviEmail);
                korisnikReturn.setIme(korisnik.getIme());
                korisnikReturn.setNazivMesta(korisnik.getIdMesto().getNaziv());
                
                return context.createObjectMessage(korisnikReturn);
            case "promeniMesto":
                email = objMessage.getStringProperty("email");
                int idNovoMesto = objMessage.getIntProperty("idMesto");
                Korisnik k = null;
                
                try {
                    transaction.begin();
                    List<Mesto> mesta = entityManager.createNamedQuery("Mesto.findByIdMesto", Mesto.class).setParameter("idMesto", idNovoMesto).getResultList();
                    mesto = mesta.get(0);
                    int u = entityManager.createQuery("UPDATE Korisnik k SET k.idMesto = :idMesto WHERE k.email = :email")
                        .setParameter("idMesto", mesto)
                        .setParameter("email", email)
                        .executeUpdate();
                    
                    entityManager.flush();
                    entityManager.clear();
                    k = entityManager.find(Korisnik.class, email);
                    entityManager.refresh(k);
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                korisnikReturn = new KorisnikReturn();
                korisnikReturn.setEmail(email);
                korisnikReturn.setIme(k.getIme());
                korisnikReturn.setNazivMesta(k.getIdMesto().getNaziv());
                
                return context.createObjectMessage(korisnikReturn);
            case "dohvatiSveKorisnike":
                List<Korisnik> sviKorisnici = null;
                List<KorisnikReturn> korisniciReturn = new ArrayList<>();
                 try {
                    transaction.begin();
                    sviKorisnici = entityManager.createNamedQuery("Korisnik.findAll", Korisnik.class).getResultList();
                    
                    for(Korisnik kor: sviKorisnici){
                        KorisnikReturn kr = new KorisnikReturn();
                        kr.setEmail(kor.getEmail());
                        kr.setIme(kor.getIme());
                        kr.setNazivMesta(kor.getIdMesto().getNaziv());
                        korisniciReturn.add(kr);
                    }
                    
                    transaction.commit();
                } catch(RollbackException e) {
                    return (ObjectMessage) context.createTextMessage("ERROR");
                } finally {
                    if(transaction.isActive()) transaction.rollback();
                }
                return context.createObjectMessage((Serializable) korisniciReturn);
            default:
                return null;
        }
    }
    
    
    public void obradiZahtev() throws JMSException {
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
                case "korisnik":
                    responseMessage = zahteviKorisnik(zahtev, requestMessage);
                    break;
                case "mesto":
                    responseMessage = zahteviMesto(zahtev, requestMessage);
                    Object responseObject = responseMessage.getObject();
                if (responseObject instanceof MestoReturn) {
                    MestoReturn mr = (MestoReturn) responseObject;
                    System.out.println("Response: MestoReturn - ID: " + mr.getId() + ", Naziv: " + mr.getNaziv());
                }
                    break;
                default:
                    System.out.println("Podsistem 1 ne moze da obradi zahtev: " + zahtev);
                    continue;
                }
                producer.send(responsesQueue, responseMessage);

                System.out.println("Zahtev " + zahtev + " je obradjen!");
            }
        }
    }    
    
    
    
}
