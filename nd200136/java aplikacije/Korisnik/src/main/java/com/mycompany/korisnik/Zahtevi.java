/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.korisnik;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import returns.*;

/**
 *
 * @author lenovo
 */
public class Zahtevi {
    private final ZahteviAPI zahteviAPI;
    
    public Zahtevi(ZahteviAPI zahteviAPI) {
        this.zahteviAPI = zahteviAPI;
    }
    
    public void kreirajMesto(String naziv) throws IOException {
        if(naziv == null) {
            System.out.println("Niste uneli sve podatke.");
            return;
        }
        
        Call<MestoReturn> call = zahteviAPI.kreirajMesto(naziv);
        Response<MestoReturn> response = call.execute();        
        if (response.isSuccessful()) {
            System.out.println("Uspesno je dodat grad: " + response.body().getNaziv());
        } else {
            System.out.println("Greska pri kreiranju grada.");
        }
    }
    
    public List<MestoReturn> dohvatiSveGradove() throws IOException {
        Call<List<MestoReturn>> call = zahteviAPI.dohvatiSveGradove();
        Response<List<MestoReturn>> response = call.execute();
        List<MestoReturn> gradovi = null;
        if (response.isSuccessful()) {
            gradovi = response.body();
            System.out.println("Lista svih gradova: ");
            for(MestoReturn mesto: gradovi){
                System.out.println(mesto.getId() + ". " + mesto.getNaziv());
            }            
        } else {
            System.out.println("Greska pri dohvatanju gradova.");
        }
        return gradovi;
    }
    
    public boolean daLiPostojiMesto(int idMesto) throws IOException {
        Call<List<MestoReturn>> call = zahteviAPI.dohvatiSveGradove();
        Response<List<MestoReturn>> response = call.execute();
        if (response.isSuccessful()) {
            List<MestoReturn> gradovi = response.body();
            for(MestoReturn mesto: gradovi){
                if(mesto.getId() == idMesto) return true;
            }            
        }
        return false;
    }
    
    public boolean daLiPostojiKorisnik(String email) throws IOException {
        Call<List<KorisnikReturn>> call = zahteviAPI.dohvatiSveKorisnike();
        Response<List<KorisnikReturn>> response = call.execute();        
        if (response.isSuccessful()) {
            List<KorisnikReturn> korisnici = response.body();
            if (korisnici.stream().anyMatch(k -> (k.getEmail().equals(email)))) {
                return true;
            }
        } 
        return false;
    }
    
    public void kreirajKorisnika(String ime, String email, String godiste, String pol, int idMesto) throws IOException {
        if(ime == null || email == null || godiste == null || new Integer(idMesto) == null) {
            System.out.println("Niste uneli sve podatke.");
            return;
        }
        if(daLiPostojiKorisnik(email)) {
            System.out.println("Ne mozete kreirati korisnika sa ovom email adresom.");
            return;
        }
        
        Call<KorisnikReturn> call = zahteviAPI.kreirajKorisnika(ime, email, godiste, pol, idMesto);
        Response<KorisnikReturn> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("Kreiran je korisnik " + response.body().getIme() + " sa email adresom " + response.body().getEmail());
        } else {
            System.out.println("Greska pri kreiranju korisnika.");
        }
    }

    public List<KorisnikReturn> dohvatiSveKorisnike() throws IOException {
        Call<List<KorisnikReturn>> call = zahteviAPI.dohvatiSveKorisnike();
        Response<List<KorisnikReturn>> response = call.execute();
        List<KorisnikReturn> korisnici = response.body();
        if (response.isSuccessful()) {
            System.out.println("Lista svih korisnika: ");
            for(KorisnikReturn korisnik: korisnici){
                System.out.println(korisnik.getIme() + "/" + korisnik.getEmail());
            }
            
        } else {
            System.out.println("Greska pri dohvatanju korisnika.");
        }
        return korisnici;
    }
    
    public void promeniEmail(String stariEmail, String noviEmail) throws IOException{
        if(stariEmail == null || noviEmail == null) {
            System.out.println("Niste uneli sve podatke.");
            return;
        }
        if(!daLiPostojiKorisnik(stariEmail)) {
            System.out.println("Ne mozete promeniti email adresu nepostojecem korisniku.");
            return;
        }
        
        Call<KorisnikReturn> call = zahteviAPI.promeniEmail(stariEmail, noviEmail);
        Response<KorisnikReturn> response = call.execute();
        if (response.isSuccessful()) {
            KorisnikReturn korisnik = response.body();
            System.out.println("Promenjena je email adresa korisnika " + korisnik.getIme() + " na " + korisnik.getEmail());           
        } else {
            System.out.println("Greska pri promeni email adrese.");
        }
    }
    
    public void promeniIdMesta(String email, int idMesto) throws IOException{
        if(email == null || new Integer(idMesto)== null) {
            System.out.println("Niste uneli sve podatke.");
            return;
        }
        if(!daLiPostojiKorisnik(email) || !daLiPostojiMesto(idMesto)) {
            System.out.println("Ne mozete promeniti idMesta korisniku.");
            return;
        }
        
        Call<KorisnikReturn> call = zahteviAPI.promeniIdMesto(email, idMesto);
        Response<KorisnikReturn> response = call.execute();
        if (response.isSuccessful()) {
            KorisnikReturn korisnik = response.body();
            System.out.println("Promenjeno je mesto korisnika " + korisnik.getIme() + " na " + korisnik.getNazivMesta());           
        } else {
            System.out.println("Greska pri promeni idMesta.");
        }
    }
    
    
    
    
    public boolean proveriPostojanjeKategorije(String naziv) throws IOException {
        Call<List<KategorijaReturn>> call = zahteviAPI.dohvatiSveKategorije();
        Response<List<KategorijaReturn>> response = call.execute();
        if (response.isSuccessful()) {
            List<KategorijaReturn> kategorije = response.body();
            if (kategorije.stream().anyMatch(kategorija -> (kategorija.getNaziv().equals(naziv)))) {
                return true;
            }
        }
        return false;
    }
    
    public void kreirajKategoriju(String naziv) throws IOException {
        if(naziv == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(proveriPostojanjeKategorije(naziv)) {
            System.out.println("Kategorija pod tim nazivom vec postoji.");
            return;
        }
        
        Call<KategorijaReturn> call = zahteviAPI.kreirajKategoriju(naziv);
        Response<KategorijaReturn> response = call.execute();        
        if (response.isSuccessful()) {
            System.out.println("Kreirana je kategorija " + response.body().getNaziv());
        } else {
            System.out.println("Greska pri kreiranju kategorije.");
        }
    }
     
    public List<KategorijaReturn> dohvatiSveKategorije() throws IOException {
        Call<List<KategorijaReturn>> call = zahteviAPI.dohvatiSveKategorije();
        Response<List<KategorijaReturn>> response = call.execute();
        List<KategorijaReturn> kategorije = null;
        if (response.isSuccessful()) {
            kategorije = response.body();
            System.out.println("Lista svih kategorija: ");
            for(KategorijaReturn kategorija: kategorije){
                System.out.println(kategorija.getIdKategorija() + ". " + kategorija.getNaziv());
            }
        } else {
            System.out.println("Greska pri dohvatanju kategorija.");
        }
        return kategorije;
    } 
    
    public boolean daLiPostojiVideo(int idVideo) throws IOException {
        Call<List<VideoReturn>> call = zahteviAPI.dohvatiSveVidee();
        Response<List<VideoReturn>> response = call.execute();
        List<VideoReturn> videi = response.body();
        if (response.isSuccessful()) {
            if (videi.stream().anyMatch(video -> (video.getIdVideo() == idVideo))) {
                return true;
            }
        } 
        return false;
    }
    
    public void kreirajVideo(String naziv, int trajanje, String vlasnikEmail) throws IOException{
        if(naziv == null || vlasnikEmail == null || new Integer(trajanje) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiKorisnik(vlasnikEmail)) {
            System.out.println("Ne postoji korisnik koji moze biti vlasnik ovog videa.");
            return;
        }
        
        Call<VideoReturn> call = zahteviAPI.kreirajVideo(naziv, trajanje, vlasnikEmail);
        Response<VideoReturn> response = call.execute();
        if (response.isSuccessful()) {
            VideoReturn video = response.body();
            System.out.println("Kreiran je video " + video.getNaziv() + "(" + video.getTrajanje() + ")" + " od strane korisnika " + video.getVlasnikEmail());
            
        } else {
            System.out.println("Greska pri kreiranju videa.");
        }
    }
    
    public List<VideoReturn> dohvatiSveVidee() throws IOException {
        Call<List<VideoReturn>> call = zahteviAPI.dohvatiSveVidee();
        Response<List<VideoReturn>> response = call.execute();
        List<VideoReturn> videi = response.body();
        if (response.isSuccessful()) {
            for(VideoReturn video: videi){
                System.out.println(video.getIdVideo() + ". " + video.getNaziv());
            }
        } else {
            System.out.println("Greska pri dohvatanju videa.");
        }
        return videi;
    }
    
    public void promeniNazivVidea(String novNaziv, int idVidea) throws IOException {
        if(novNaziv == null || new Integer(idVidea) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiVideo(idVidea)) {
            System.out.println("Nije moguce promeniti naziv nepostojeceg videa.");
            return;
        }
        Call<VideoReturn> call = zahteviAPI.promeniNazivVidea(novNaziv, idVidea);
        Response<VideoReturn> response = call.execute();
        if (response.isSuccessful()) {
            VideoReturn video = response.body();
            System.out.println("Videu " + video.getIdVideo()+ " je promenjen naziv na " + video.getNaziv());           
        } else {
            System.out.println("Greska pri promeni naziva videa.");
        }
    }
    
    public void dodajKategorijuVideu(int idKategorija, int idVidea) throws IOException {
        if(new Integer(idVidea) == null || new Integer(idKategorija) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiVideo(idVidea)) {
            System.out.println("Nepostojeci video/kategorija.");
            return;
        }
        
        Call<VideoReturn> call = zahteviAPI.dodajKategorijuVideu(idKategorija, idVidea);
        Response<VideoReturn> response = call.execute();
        if (response.isSuccessful()) {
            VideoReturn video = response.body();
            System.out.println("Kategorije videa " + video.getNaziv() + " su: ");
            for(KategorijaReturn kr: video.getKategorije()) {
                System.out.println(kr.getNaziv());
            }
        } else {
            System.out.println("Greska pri dodavanju kategorije videu.");
        }
    }
    
    public void dohvatiKategorijeVidea(int idVideo) throws IOException {
        if(new Integer(idVideo) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiVideo(idVideo)) {
            System.out.println("Zadati video ne postoji.");
            return;
        }
        Call<VideoReturn> call = zahteviAPI.dohvatiKategorijeVidea(idVideo);
        Response<VideoReturn> response = call.execute();
        if (response.isSuccessful()) {
            VideoReturn video = response.body();
            for(KategorijaReturn kategorija: video.getKategorije()){
                System.out.println(kategorija.getNaziv());
            }
        } else {
            System.out.println("Greska pri dohvatanju kategorija videa.");
        }
    }
    
    public void brisanjeVidea(int idVideo, String vlasnikEmail) throws IOException {
        if(vlasnikEmail == null || new Integer(idVideo) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        } 
        if(!daLiPostojiVideo(idVideo)) {
            System.out.println("Zadati video ne postoji.");
            return;
        }
        
        //Provera da li je korisnik i vlasnik videa
        List<VideoReturn> videi = dohvatiSveVidee();
        String vlasnik = null;
        for(VideoReturn video: videi) {
            if(video.getVlasnikEmail().equals(vlasnikEmail) && video.getIdVideo() == idVideo) {
                vlasnik = video.getVlasnikEmail();
                System.out.println(vlasnik);
                break;
            }
        }
        
        if(vlasnik == null) {
            System.out.println("Ne mozete obrisati video jer niste njegov vlasnik ili taj korisnik ne postoji.");
            return;
        }
        
        Call<VideoReturn> call = zahteviAPI.obrisiVideo(idVideo);
        Response<VideoReturn> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("Video je obrisan.");
        } else {
            System.out.println("Greska pri brisanju videa.");
        }
        
    }
    
    
    
    
    public boolean daLiPostojiPaket(int idPaket) throws IOException {
        Call<List<PaketReturn>> call = zahteviAPI.dohvatiSvePakete();
        Response<List<PaketReturn>> response = call.execute();
        if (response.isSuccessful()) {
            List<PaketReturn> paketi = response.body();
            if (paketi.stream().anyMatch(paket -> (paket.getIdPaket() == idPaket))) {
                return true;
            }
        }
        return false;
    }
    
    public void kreirajPaket(int trenutnaCena) throws IOException {
        if(new Integer(trenutnaCena) == null) {
             System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        Call<PaketReturn> call = zahteviAPI.kreirajPaket(trenutnaCena);
        Response<PaketReturn> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("Kreiran je nov paket cija je trenutna cena " + response.body().getTrenutnaCena());
        } else {
            System.out.println("Greska pri kreiranju paketa.");
        }
    }
    
    public List<PaketReturn> dohvatiSvePakete() throws IOException {
        Call<List<PaketReturn>> call = zahteviAPI.dohvatiSvePakete();
        Response<List<PaketReturn>> response = call.execute();
        List<PaketReturn> paketi = null;
        if (response.isSuccessful()) {
            paketi = response.body();
            System.out.println("Paketi:");
            for(PaketReturn paket: paketi){
                System.out.println(paket.getIdPaket() + ". " + paket.getTrenutnaCena());
            }
        } else {
            System.out.println("Greska pri dohvatanju paketa.");
        }
        return paketi;
    }
    
    public void promeniCenuPaketa(int cena, int idPaketa) throws IOException {
        if(new Integer(cena) == null || new Integer(idPaketa) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiPaket(idPaketa)) {
            System.out.println("Nije moguce promeniti cenu nepostojecem paketu.");
            return;
        }
        
        Call<PaketReturn> call = zahteviAPI.promeniCenuPaketa(cena, idPaketa);
        Response<PaketReturn> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("Promenjena je cena paketa " + response.body().getIdPaket() + " na " + response.body().getTrenutnaCena());
        } else {
            System.out.println("Greska pri promeni cene paketa.");
        }
    }
     
    public boolean daLiPostojiOcena(int idOcena, int idVideo) throws IOException {
        Call<List<OcenaReturn>> call = zahteviAPI.dohvatiOceneZaVideo(idVideo);
        Response<List<OcenaReturn>> response = call.execute();
        if (response.isSuccessful()) {
            List<OcenaReturn> ocene = response.body();
            if (ocene.stream().anyMatch(ocena -> (ocena.getIdOcena() == idOcena))) {
                return true;
            }
        }
        return false;
    }
    
    public void kreirajOcenu(int ocena, int idVideo, String korisnikEmail) throws IOException {
        if(new Integer(idVideo) == null || new Integer(ocena) == null || korisnikEmail == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiKorisnik(korisnikEmail) || !daLiPostojiVideo(idVideo)) {
            System.out.println("Korisnik i/ili video ne postoji.");
            return;
        }
        
        Call<OcenaReturn> call = zahteviAPI.kreirajOcenu(ocena, idVideo, korisnikEmail);
        Response<OcenaReturn> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("Ocenili ste video " + response.body().getNazivVidea() + " sa ocenom " + response.body().getOcena());
        } else {
            System.out.println("Greska pri kreiranju ocene.");
        }
    }
    
    public void brisanjeOcene(int idOcena, int idVideo) throws IOException {
        if(new Integer(idVideo) == null || new Integer(idOcena) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiVideo(idVideo) || !daLiPostojiOcena(idOcena, idVideo)) {
            System.out.println("Video/ocena ne postoji.");
            return;
        }
        
        Call<OcenaReturn> call = zahteviAPI.obrisiOcenu(idOcena);
        Response<OcenaReturn> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("Ocena je obrisana.");
        } else {
            System.out.println("Greska pri brisanju ocene.");
        }
    }
    
    public List<OcenaReturn> dohvatiOceneZaVideo(int idVideo) throws IOException {
        if(new Integer(idVideo) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return null;
        }
        if(!daLiPostojiVideo(idVideo)) {
            System.out.println("Video ne postoji.");
            return null;
        }
         
        Call<List<OcenaReturn>> call = zahteviAPI.dohvatiOceneZaVideo(idVideo);
        Response<List<OcenaReturn>> response = call.execute();
        List<OcenaReturn> ocene = null;
        if (response.isSuccessful()) {
            ocene = response.body();
            if(ocene.isEmpty()) {
                System.out.println("Video trenutno nema ni jednu ocenu.");
                return null;
            }
            
            System.out.println("Video: " + ocene.get(0).getNazivVidea());
            for(OcenaReturn ocena: ocene){
                System.out.println(ocena.getIdOcena() + ". " + ocena.getKorisnikEmail() + " - " + ocena.getOcena());
            }
        } else {
            System.out.println("Greska pri dohvatanju ocena.");
        }
        return ocene;
    }
     
    public void promeniOcenu(int idOcena, int novaOcena, int idVideo) throws IOException{
        if(new Integer(idVideo) == null || new Integer(idOcena) == null || new Integer(novaOcena) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        //da li treba da proverim da li je taj korisnik ocenio
        Call<OcenaReturn> call = zahteviAPI.promeniOcenu(idOcena, novaOcena);
        Response<OcenaReturn> response = call.execute();
        if (response.isSuccessful()) {
            OcenaReturn ocena = response.body();
            System.out.println("Promenjena je ocena za video " + ocena.getNazivVidea() + " na " + ocena.getOcena());           
        } else {
            System.out.println("Greska pri promeni ocene.");
        }
    }
     
    public List<PretplataReturn> dohvatiPretplateKorisnika(String korisnikEmail) throws IOException {
        if(korisnikEmail == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return null;
        }
        if(!daLiPostojiKorisnik(korisnikEmail)) {
            System.out.println("Korisnik ne postoji.");
            return null;
        }
        
        Call<List<PretplataReturn>> call = zahteviAPI.dohvatiPretplateKorisnika(korisnikEmail);
        Response<List<PretplataReturn>> response = call.execute();
        List<PretplataReturn> pretplate = null;
        if (response.isSuccessful()) {
            pretplate = response.body();
            System.out.println("Pretplate korisnina " + pretplate.get(0).getKorisnikEmail());
            for(PretplataReturn pretplata: pretplate){
                LocalDateTime pocetak = LocalDateTime.ofInstant(pretplata.getDatumIVremePocetka().toInstant(), ZoneId.systemDefault());
                System.out.println("Cena: " + pretplata.getCena() + " Vreme isticanja: " + pocetak.plusDays(30));
            }
        } else {
            System.out.println("Greska pri dohvatanju pretplata.");
        }
        return pretplate != null ? pretplate : Collections.emptyList();
    }
     
    public boolean daLiKorisnikImaPretplatu(String korisnikEmail) throws IOException {
        Call<List<PretplataReturn>> call = zahteviAPI.dohvatiPretplateKorisnika(korisnikEmail);
        Response<List<PretplataReturn>> response = call.execute();
        if (response.isSuccessful()) {
            List<PretplataReturn> pretplate = response.body();
            //korisnik nema ni jednu pretplatu
            if (pretplate == null || pretplate.isEmpty()) return true;
            //proveravanje do kad pretplate koje korisnik ima vaze
            Date danas = new Date();
            LocalDateTime danasLocalDateTime = danas.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            for(PretplataReturn p: pretplate) {
                LocalDateTime pocetak = LocalDateTime.ofInstant(p.getDatumIVremePocetka().toInstant(), ZoneId.systemDefault());
                LocalDateTime kraj = pocetak.plusDays(30);
                if (kraj.isAfter(danasLocalDateTime)) return false;
            }
        }
        return true;
    }
    
    public void kreirajPretplatu(int idPaket, String korisnikEmail) throws IOException {
        if(korisnikEmail == null || new Integer(idPaket) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiKorisnik(korisnikEmail)) {
            System.out.println("Korisnik ne postoji.");
            return;
        }
        if(!daLiKorisnikImaPretplatu(korisnikEmail)) {
            System.out.println("Korisnik " + korisnikEmail + " ima pretplatu koja je aktivna. Nije moguce kreirati novu.");
            return;
        }
        
        Call<PretplataReturn> call = zahteviAPI.kreirajPretplatu(idPaket, korisnikEmail);
        Response<PretplataReturn> response = call.execute();
        if (response.isSuccessful()) {
            PretplataReturn pretplata = response.body();
            LocalDateTime pocetak = LocalDateTime.ofInstant(pretplata.getDatumIVremePocetka().toInstant(), ZoneId.systemDefault());
            System.out.println("Dodali ste novu pretplatu koja vazi do " + pocetak.plusDays(30));
        } else {
            System.out.println("Greska pri kreiranju pretplate.");
        }
    }
    
    
    public int daLiJeKorisnikGledaoVideo(String korisnikEmail, int idVideo) throws IOException {
        if(!daLiPostojiVideo(idVideo) || !daLiPostojiKorisnik(korisnikEmail)) {
            System.out.println("Video/korisnik ne postoji.");
            return -1;
        }
        Call<List<GledanjeReturn>> call = zahteviAPI.dohvatiGledanjaVidea(idVideo);
        Response<List<GledanjeReturn>> response = call.execute();
        if (response.isSuccessful()) {
            List<GledanjeReturn> gledanja = response.body();
            for(GledanjeReturn gledanje: gledanja){
                System.out.println(gledanje.getKorisnikEmail());
                System.out.println(gledanje.getIdVideo());
                if(gledanje.getKorisnikEmail().equals(korisnikEmail) && gledanje.getIdVideo() == idVideo) {
                    System.out.println("Vec ste gledali ovaj video. Mozete da nastavite.");
                    return gledanje.getIdGledanje();
                }
            }
        } 
        return -1;
    }
    
    public void kreirajGledanje(int idVideo, String korisnikEmail, int sekundeOdgledane) throws IOException {
        if(korisnikEmail == null || new Integer(idVideo) == null || new Integer(sekundeOdgledane) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return;
        }
        if(!daLiPostojiVideo(idVideo) || !daLiPostojiKorisnik(korisnikEmail)) {
            System.out.println("Video/korisnik ne postoji.");
            return;
        }
        
        Call<GledanjeReturn> call = zahteviAPI.kreirajGledanje(idVideo, korisnikEmail, sekundeOdgledane);
        Response<GledanjeReturn> response = call.execute();
        if (response.isSuccessful()) {
            System.out.println("Odgledali ste " + response.body().getSekundeOdgledano()+ "s videa " + response.body().getNazivVidea());
        } else {
            System.out.println("Greska pri kreiranju gledanja.");
        }
    
    }
    
    public List<GledanjeReturn> dohvatiGledanjaVidea(int idVideo) throws IOException {
        if(new Integer(idVideo) == null) {
            System.out.println("Niste uneli potrebne parametre.");
            return null;
        }
        if(!daLiPostojiVideo(idVideo)) {
            System.out.println("Video ne postoji.");
            return null;
        }
        
        Call<List<GledanjeReturn>> call = zahteviAPI.dohvatiGledanjaVidea(idVideo);
        Response<List<GledanjeReturn>> response = call.execute();
        List<GledanjeReturn> gledanja = null;
        if (response.isSuccessful()) {
            gledanja = response.body();
            System.out.println("Gledanja za video " + gledanja.get(0).getNazivVidea());
            for(GledanjeReturn gledanje: gledanja){
                System.out.println("Korisnik " + gledanje.getKorisnikEmail()+ " je pogledao video.");
            }
        } else {
            System.out.println("Greska pri dohvatanju gledanja za video.");
        }
        return gledanja;
    }
 
    public void nastaviGledanje(int idGledanje, int sekundeOdgledane) throws IOException {
        Call<GledanjeReturn> call = zahteviAPI.azurirajGledanje(idGledanje, sekundeOdgledane);
        Response<GledanjeReturn> response = call.execute();
        if (response.isSuccessful()) {
            GledanjeReturn gledanje = response.body();
            System.out.println("Nastavili ste da gledate video.");
        } else {
            System.out.println("Greska pri nastavljanju gledanja videa.");
        }
    
    }
    
}
