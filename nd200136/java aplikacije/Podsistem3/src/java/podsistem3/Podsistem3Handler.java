/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import models.*;
import returns.GledanjeReturn;
import returns.OcenaReturn;
import returns.PaketReturn;
import returns.PretplataReturn;

/**
 *
 * @author lenovo
 */
class Podsistem3Handler {

    private final EntityManager entityManager;
    private final JMSContext context;
    private final Queue requestsQueue, responsesQueue;

    Podsistem3Handler(EntityManager entityManager, JMSContext context, Queue requestQueue, Queue responseQueue) {
        this.entityManager = entityManager;
        this.context = context;
        this.requestsQueue = requestQueue;
        this.responsesQueue = responseQueue;
    }

    public ObjectMessage zahteviPaket(String request, ObjectMessage objMessage) throws JMSException {
        EntityTransaction transaction = entityManager.getTransaction();

        if (request.equals("dodajPaket")) {
            int trenutnaCena = (int) objMessage.getIntProperty("TrenutnaCena");
            Paket paket = new Paket();
            PaketReturn paketReturn = new PaketReturn();
            paket.setTrenutnaCena(trenutnaCena);

            try {
                transaction.begin();
                entityManager.persist(paket);
                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            paketReturn.setIdPaket(0);
            paketReturn.setTrenutnaCena(trenutnaCena);
            return context.createObjectMessage(paketReturn);
        } else if (request.equals("dohvatiSvePakete")) {
            List<PaketReturn> paketiReturn = new ArrayList<>();
            List<Paket> paketi = null;

            try {
                transaction.begin();
                paketi = entityManager.createNamedQuery("Paket.findAll", Paket.class).getResultList();

                for (Paket p : paketi) {
                    PaketReturn pr = new PaketReturn();
                    pr.setTrenutnaCena(p.getTrenutnaCena());
                    pr.setIdPaket(p.getIdPaket().intValue());
                    paketiReturn.add(pr);
                }

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            return context.createObjectMessage((Serializable) paketiReturn);
        } else if (request.equals("promeniCenuPaketa")) {
            int idPaket = objMessage.getIntProperty("idPaket");
            int cena = objMessage.getIntProperty("Cena");
            Paket p = null;

            try {
                transaction.begin();
                p = entityManager.find(Paket.class, idPaket);

                entityManager.createQuery("UPDATE Paket p SET p.trenutnaCena = :cena WHERE p.idPaket = :idPaket")
                        .setParameter("cena", cena)
                        .setParameter("idPaket", idPaket)
                        .executeUpdate();

                entityManager.flush();
                entityManager.clear();
                p = entityManager.find(Paket.class, idPaket);
                entityManager.refresh(p);

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            PaketReturn pr = new PaketReturn();
            pr.setIdPaket(idPaket);
            pr.setTrenutnaCena(cena);
            return context.createObjectMessage(pr);
        }
        return null;
    }

    public ObjectMessage zahteviOcene(String request, ObjectMessage objMessage) throws JMSException {
        EntityTransaction transaction = entityManager.getTransaction();

        if (request.equals("dodajOcenu")) {
            int ocena = (int) objMessage.getIntProperty("Ocena");
            int idVideo = (int) objMessage.getIntProperty("idVideo");
            String korisnikEmail = objMessage.getStringProperty("KorisnikEmail");
            Ocena novaOcena = new Ocena();
            Korisnik korisnik = null;
            Video video = null;

            try {
                transaction.begin();
                List<Video> videi = entityManager.createNamedQuery("Video.findByIdVideo", Video.class).setParameter("idVideo", idVideo).getResultList();
                video = videi.get(0);
                List<Korisnik> korisnici = entityManager.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", korisnikEmail).getResultList();
                korisnik = korisnici.get(0);

                novaOcena.setOcena(ocena);
                novaOcena.setIdVideo(video);
                novaOcena.setKorisnikEmail(korisnik);
                novaOcena.setDatumVremeOcenjivanja(new Date());

                entityManager.persist(novaOcena);
                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            OcenaReturn ocenaReturn = new OcenaReturn();
            ocenaReturn.setIdVideo(idVideo);
            ocenaReturn.setKorisnikEmail(korisnikEmail);
            ocenaReturn.setNazivVidea(video.getNaziv());
            ocenaReturn.setOcena(ocena);
            ocenaReturn.setIdOcena(0);
            return context.createObjectMessage(ocenaReturn);
        } else if (request.equals("dohvatiOceneZaVideo")) {
            int idVideoOcene = objMessage.getIntProperty("idVideo");
            List<OcenaReturn> oceneReturn = new ArrayList<>();
            List<Ocena> ocene = null;

            try {
                transaction.begin();
                Video videoId = entityManager.find(Video.class, idVideoOcene);
                ocene = entityManager.createQuery("SELECT o FROM Ocena o WHERE o.idVideo = :idVideo", Ocena.class).setParameter("idVideo", videoId).getResultList();

                for (Ocena o : ocene) {
                    OcenaReturn or = new OcenaReturn();
                    or.setIdVideo(o.getIdOcena());
                    or.setKorisnikEmail(o.getKorisnikEmail().getEmail());
                    or.setNazivVidea(o.getIdVideo().getNaziv());
                    or.setOcena(o.getOcena());
                    or.setIdOcena(o.getIdOcena());
                    oceneReturn.add(or);
                }

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            return context.createObjectMessage((Serializable) oceneReturn);
        } else if (request.equals("obrisiOcenu")) {
            int idOcenaBrisanje = objMessage.getIntProperty("idOcena");
            OcenaReturn ocenaBrisanje = new OcenaReturn();
            ocenaBrisanje.setIdVideo(0);
            ocenaBrisanje.setKorisnikEmail(null);
            ocenaBrisanje.setNazivVidea(null);
            ocenaBrisanje.setOcena(0);
            ocenaBrisanje.setIdOcena(0);

            try {
                transaction.begin();
                entityManager.createQuery("DELETE FROM Ocena WHERE idOcena = :idOcena").setParameter("idOcena", idOcenaBrisanje).executeUpdate();
                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            return context.createObjectMessage(ocenaBrisanje);
        } else if (request.equals("promeniOcenu")) {
            int idOcena = objMessage.getIntProperty("idOcena");
            int novaVrednostOcene = objMessage.getIntProperty("novaOcena");
            Ocena o = null;

            try {
                transaction.begin();
                int u = entityManager.createQuery("UPDATE Ocena o SET o.ocena = :ocena WHERE o.idOcena = :idOcena")
                        .setParameter("ocena", novaVrednostOcene)
                        .setParameter("idOcena", idOcena)
                        .executeUpdate();

                entityManager.flush();
                entityManager.clear();
                o = entityManager.find(Ocena.class, idOcena);
                entityManager.refresh(o);

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            OcenaReturn or = new OcenaReturn();
            or.setIdOcena(o.getIdOcena());
            or.setIdVideo(o.getIdVideo().getIdVideo());
            or.setKorisnikEmail(o.getKorisnikEmail().getEmail());
            or.setNazivVidea(o.getIdVideo().getNaziv());
            or.setOcena(novaVrednostOcene);
            return context.createObjectMessage(or);
        }
        return null;
    }

    public ObjectMessage zahteviPretplate(String request, ObjectMessage objMessage) throws JMSException {
        EntityTransaction transaction = entityManager.getTransaction();

        if (request.equals("dodajPretplatu")) {
            int idPakta = (int) objMessage.getIntProperty("idPaket");
            String korisnikEmail = objMessage.getStringProperty("KorisnikEmail");
            Pretplata pretplata = new Pretplata();

            try {
                transaction.begin();
                Korisnik korisnik = entityManager.find(Korisnik.class, korisnikEmail);
                Paket paket = entityManager.find(Paket.class, idPakta);

                pretplata.setCena(paket.getTrenutnaCena());
                pretplata.setIdPaket(paket);
                pretplata.setDatumVremePocetka(new Date());
                pretplata.setKorisnikEmail(korisnik);
                pretplata.setTrajanje(30);
                entityManager.persist(pretplata);

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            PretplataReturn pretplataReturn = new PretplataReturn();
            pretplataReturn.setCena(pretplata.getCena());
            pretplataReturn.setDatumIVremePocetka(pretplata.getDatumVremePocetka());
            pretplataReturn.setIdPaket(idPakta);
            pretplataReturn.setKorisnikEmail(korisnikEmail);
            pretplataReturn.setTrajanje(pretplata.getTrajanje());
            return context.createObjectMessage(pretplataReturn);
        } else if (request.equals("dohvatiPretplateZaKorisnika")) {
            String korisnik = objMessage.getStringProperty("korisnikEmail");
            List<PretplataReturn> pretplateReturn = new ArrayList<>();
            List<Pretplata> pretplate = null;

            try {
                transaction.begin();
                Korisnik k = entityManager.find(Korisnik.class, korisnik);
                pretplate = entityManager.createQuery("SELECT p FROM Pretplata p WHERE p.korisnikEmail = :korisnikEmail", Pretplata.class).setParameter("korisnikEmail", k).getResultList();

                for (Pretplata p : pretplate) {
                    PretplataReturn pr = new PretplataReturn();
                    pr.setCena(p.getCena());
                    pr.setDatumIVremePocetka(p.getDatumVremePocetka());
                    pr.setIdPaket(p.getIdPaket().getIdPaket());
                    pr.setKorisnikEmail(korisnik);
                    pr.setTrajanje(30);
                    pretplateReturn.add(pr);
                }

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            return context.createObjectMessage((Serializable) pretplateReturn);
        }
        return null;
    }

    public ObjectMessage zahteviGledanje(String request, ObjectMessage objMessage) throws JMSException {
         EntityTransaction transaction = entityManager.getTransaction();

        if (request.equals("dodajGledanje")) {
            int idVideo = (int) objMessage.getIntProperty("idVideo");
            String korisnikEmail = objMessage.getStringProperty("KorisnikEmail");
            int sekundeOdgledane = (int) objMessage.getIntProperty("sekundeOdgledane");
            Gledanje gledanje = new Gledanje();
            Video video = null;
            
            try {
                transaction.begin();
                Korisnik korisnik = entityManager.find(Korisnik.class, korisnikEmail);
                video = entityManager.find(Video.class, idVideo);

                gledanje.setDatumVremePocetkaGledanja(new Date());
                gledanje.setIdVideo(video);
                gledanje.setKorisnikEmail(korisnik);
                gledanje.setSekundeNastavljanja(sekundeOdgledane);
                gledanje.setSekundeOdgledane(sekundeOdgledane);
                entityManager.persist(gledanje);

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            GledanjeReturn gr = new GledanjeReturn();
            gr.setKorisnikEmail(korisnikEmail);
            gr.setNazivVidea(video.getNaziv());
            gr.setSekundeNastavljanja(0);
            gr.setSekundeOdgledano(sekundeOdgledane);
            gr.setIdGledanje(0);
            return context.createObjectMessage(gr);
        } else if (request.equals("dohvatiGledanjaKorisnika")) {
            int idVideo = objMessage.getIntProperty("idVideo");
            List<GledanjeReturn> gledanjaReturn = new ArrayList<>();
            List<Gledanje> gledanja = null;

            try {
                transaction.begin();
                Video v = entityManager.find(Video.class, idVideo);
                gledanja = entityManager.createQuery("SELECT g FROM Gledanje g WHERE g.idVideo = :idVideo", Gledanje.class).setParameter("idVideo", v).getResultList();

                for (Gledanje g : gledanja) {
                    GledanjeReturn gr = new GledanjeReturn();
                    gr.setKorisnikEmail(g.getKorisnikEmail().getEmail());
                    gr.setNazivVidea(g.getIdVideo().getNaziv());
                    gr.setSekundeNastavljanja(g.getSekundeNastavljanja());
                    gr.setSekundeOdgledano(g.getSekundeOdgledane());
                    gr.setIdGledanje(g.getIdGledanje());
                    gr.setIdVideo(idVideo);
                    gledanjaReturn.add(gr);
                }

                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            return context.createObjectMessage((Serializable) gledanjaReturn);
        } else if (request.equals("azurirajGledanje")) {
            int idGledanje = (int) objMessage.getIntProperty("idGledanje");
            int sekundeOdgledane = (int) objMessage.getIntProperty("sekundeOdgledane");
            Gledanje gledanje = null;
            
            try {
                transaction.begin();
                gledanje = entityManager.find(Gledanje.class, idGledanje);
                int u = entityManager.createQuery("UPDATE Gledanje g SET g.sekundeNastavljanja = :sekNastavljanja, g.sekundeOdgledane = :sekOdgledane WHERE g.idGledanje = :idGledanje")
                        .setParameter("sekNastavljanja", gledanje.getSekundeNastavljanja() + sekundeOdgledane)
                        .setParameter("sekOdgledane", gledanje.getSekundeOdgledane() + sekundeOdgledane)
                        .setParameter("idGledanje", idGledanje)
                        .executeUpdate();

                entityManager.flush();
                entityManager.clear();
                gledanje = entityManager.find(Gledanje.class, idGledanje);
                entityManager.refresh(gledanje);
                
                transaction.commit();
            } catch (RollbackException e) {
                return (ObjectMessage) context.createTextMessage("ERROR");
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            GledanjeReturn gr = new GledanjeReturn();
            gr.setKorisnikEmail(gledanje.getKorisnikEmail().getEmail());
            gr.setNazivVidea(gledanje.getIdVideo().getNaziv());
            gr.setSekundeNastavljanja(gledanje.getSekundeNastavljanja() + sekundeOdgledane);
            gr.setSekundeOdgledano(sekundeOdgledane);
            gr.setIdGledanje(gledanje.getIdGledanje());
            gr.setIdVideo(gledanje.getIdVideo().getIdVideo());
            return context.createObjectMessage(gr);
        }
        return null;
        
    }
    
    void obradiZahtev() throws JMSException {
        try (JMSConsumer consumer = context.createConsumer(requestsQueue);) {
            JMSProducer producer = context.createProducer();
            while (true) {
                System.out.println("Ceka se zahtev");
                ObjectMessage requestMessage = (ObjectMessage) consumer.receive();

                String zahtevZa = requestMessage.getStringProperty("resource");
                String zahtev = requestMessage.getStringProperty("request");

                System.out.println("Pristigao je zahtev " + zahtev + ".");

                ObjectMessage responseMessage = null;
                switch (zahtevZa) {
                    case "paket":
                        responseMessage = zahteviPaket(zahtev, requestMessage);
                        break;
                    case "ocena":
                        responseMessage = zahteviOcene(zahtev, requestMessage);
                        break;
                    case "pretplata":
                        responseMessage = zahteviPretplate(zahtev, requestMessage);
                        break;
                    case "gledanje":
                        responseMessage = zahteviGledanje(zahtev, requestMessage);
                        break;
                    default:
                        System.out.println("Podsistem 3 ne moze da obradi zahtev: " + zahtev);
                        continue;
                }
                producer.send(responsesQueue, responseMessage);

                System.out.println("Zahtev " + zahtev + " je obradjen!");
            }
        }
    }

}
