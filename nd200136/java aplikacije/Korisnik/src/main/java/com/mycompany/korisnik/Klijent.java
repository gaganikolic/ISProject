/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.korisnik;

import java.io.IOException;
import java.util.Scanner;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author lenovo
 */
public class Klijent {
    private static final String BASE_URL = "http://localhost:8080/CentralniServer/resources/";
    private Retrofit retrofit;
    private static ZahteviAPI zahteviAPI;
    private static Zahtevi zahtevi;

    public Klijent() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.zahteviAPI = retrofit.create(ZahteviAPI.class);
        this.zahtevi = new Zahtevi(zahteviAPI);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Klijent korisnik = new Klijent();
        Scanner scanner = new Scanner(System.in);
        String email = null;
        
        while (true) {
            System.out.println("\n");
            System.out.println("Odaberite zahtev:");
            System.out.println("1.  Kreiranje grada");
            System.out.println("2.  Kreiranje korisnika");
            System.out.println("3.  Promena email adrese za korisnika");
            System.out.println("4.  Promena mesta za korisnika");
            System.out.println("5.  Kreiranje kategorije");
            System.out.println("6.  Kreiranje video snimka");
            System.out.println("7.  Promeni naziv video snimka");
            System.out.println("8.  Dodavanje kategorije video snimku");
            System.out.println("9.  Kreiranje paketa");
            System.out.println("10. Promena mesecne cene za paket");
            System.out.println("11. Kreiranje pretplate korisnika na paket");
            System.out.println("12. Kreiranje gledanja video snimka od strane korisnika");
            System.out.println("13. Kreiranje ocene korisnika za video snimak");
            System.out.println("14. Menjanje ocene korisnika za video snimak");
            System.out.println("15. Brisanje ocene korisnika za video snimak");
            System.out.println("16. Brisanje video snimka od strane korisnika koji ga je kreirao");
            System.out.println("17. Dohvatanje svih gradova");
            System.out.println("18. Dohvatanje svih korisnika");
            System.out.println("19. Dohvatanje svih kategorija");
            System.out.println("20. Dohvatanje svih video snimaka");
            System.out.println("21. Dohvatanje kategorija za odredjeni video snimak");
            System.out.println("22. Dohvatanje svih paketa");
            System.out.println("23. Dohvatanje svih pretplata korisnika");
            System.out.println("24. Dohvatanje svih gledanja za video snimak");
            System.out.println("25. Dohvatanje svih ocena za video snimak");
            int izbor = scanner.nextInt(); 
            scanner.nextLine();

            switch (izbor) {
                case 1:
                    System.out.println("Unesite naziv grada: ");
                    String naziv = scanner.nextLine();
                    korisnik.zahtevi.kreirajMesto(naziv);
                    break;
                case 2:
                    System.out.println("Unesite ime korisnika: ");
                    String ime = scanner.nextLine();
                    System.out.println("Unesite email adresu korisnika: ");
                    email = scanner.nextLine();
                    System.out.println("Unesite godiste korisnika: ");
                    String godiste = scanner.nextLine();
                    System.out.println("Unesite pol korisnika: ");
                    String pol = scanner.nextLine();
                    System.out.println("Unesite idMesta korisnika: ");
                    int idMesta = scanner.nextInt();
                    korisnik.zahtevi.kreirajKorisnika(ime, email, godiste, pol, idMesta);
                    break;
                case 3:
                    System.out.println("Unesite staru email adresu korisnika: ");
                    String stariEmail = scanner.nextLine();
                    System.out.println("Unesite novu email adresu korisnika: ");
                    String noviEmail = scanner.nextLine();
                    korisnik.zahtevi.promeniEmail(stariEmail, noviEmail);
                    break;
                case 4:
                    System.out.println("Unesite email adresu korisnika: ");
                    email = scanner.nextLine();
                    korisnik.zahtevi.dohvatiSveGradove();
                    System.out.println("Unesite nov idMesta korisnika: ");
                    int idNovogMesta = scanner.nextInt();
                    korisnik.zahtevi.promeniIdMesta(email, idNovogMesta);
                    break;
                case 5:
                    System.out.println("Unesite naziv kategorije: ");
                    String kategorija = scanner.nextLine();
                    korisnik.zahtevi.kreirajKategoriju(kategorija);
                    break;
                case 6:
                    System.out.println("Unesite naziv videa: ");
                    String nazivVidea = scanner.nextLine();
                    System.out.println("Unesite trajanje videa: ");
                    int trajanje = scanner.nextInt();   
                    scanner.nextLine();
                    System.out.println("Unesite email vlasnika videa: ");
                    String vlasnikEmail = scanner.nextLine();
                    korisnik.zahtevi.kreirajVideo(nazivVidea, trajanje, vlasnikEmail);
                    break;
                case 7:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa kome zelite da promenite naziv: ");
                    int idVideo = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesite nov naziv videa: ");
                    String novNazivVidea = scanner.nextLine();
                    korisnik.zahtevi.promeniNazivVidea(novNazivVidea, idVideo);
                    break;
                case 8:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa kome zelite da dodate kategoriju: ");
                    int idVideoKategorija = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.dohvatiSveKategorije();
                    System.out.println("Unesite broj kategorije koju zelite da dodate videu: ");
                    int idKategorije = scanner.nextInt();
                    korisnik.zahtevi.dodajKategorijuVideu(idKategorije, idVideoKategorija);
                    break;
                case 9:
                    System.out.println("Unesite trenutnu cenu paketa: ");
                    int trenutnaCena = scanner.nextInt();
                    korisnik.zahtevi.kreirajPaket(trenutnaCena);
                    break;
                case 10:
                    korisnik.zahtevi.dohvatiSvePakete();
                    System.out.println("Izaberite paket kojem zelite da promenite cenu: ");
                    int idPaketCena = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesite novu cenu: ");
                    int novaCena = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.promeniCenuPaketa(novaCena, idPaketCena);
                    break;
                case 11:
                    korisnik.zahtevi.dohvatiSvePakete();
                    System.out.println("Izaberite paket: ");
                    int idPaket = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesite svoju email adresu: ");
                    String emailPretplata = scanner.nextLine();
                    korisnik.zahtevi.kreirajPretplatu(idPaket, emailPretplata);
                    break;
                case 12:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite video koji zelite da pogledate: ");
                    int idVideoGledanje = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesite svoju email adresu: ");
                    String emailGledanje = scanner.nextLine();
                    System.out.println("Unesite koliko sekundi zelite da odgledate: ");
                    int sekundiOdgledano = scanner.nextInt();scanner.nextLine();
                    korisnik.zahtevi.kreirajGledanje(idVideoGledanje, emailGledanje, sekundiOdgledano);
                    break;
                case 13:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Unesite id videa koji zelite da ocenite: ");
                    int idVideaZaOcenjivanje = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesite svoju email adresu: ");
                    String korisnikEmail = scanner.nextLine();
                    System.out.println("Unesite ocenu: ");
                    int ocena = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.kreirajOcenu(ocena, idVideaZaOcenjivanje, korisnikEmail);
                    break;
                case 14:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa ciju ocenu zelite da promenite: ");
                    int idVideoPromenaOcene = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.dohvatiOceneZaVideo(idVideoPromenaOcene);
                    System.out.println("Izaberite ocenu koju zelite da promenite: ");
                    int idOcenaPromena = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesite novu ocenu: ");
                    int novaOcena = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.promeniOcenu(idOcenaPromena, novaOcena, idVideoPromenaOcene);
                    break;
                case 15:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa ciju ocenu zelite da obrisete: ");
                    int idVideoOcenaBrisanje = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.dohvatiOceneZaVideo(idVideoOcenaBrisanje);
                    System.out.println("Izaberite broj ocene koju zelite da obrisete: ");
                    int idOcenaBrisanje = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.brisanjeOcene(idOcenaBrisanje, idVideoOcenaBrisanje);
                    break;
                case 16:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa koji zelite da obrisete: ");
                    int idVideoBrisanje = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unesite svoju email adresu: ");
                    String emailBrisanje = scanner.nextLine();
                    korisnik.zahtevi.brisanjeVidea(idVideoBrisanje, emailBrisanje);
                    break;
                case 17:
                    korisnik.zahtevi.dohvatiSveGradove();
                    break;
                case 18:
                    korisnik.zahtevi.dohvatiSveKorisnike();
                    break;
                case 19:
                    korisnik.zahtevi.dohvatiSveKategorije();
                    break;
                case 20:
                    korisnik.zahtevi.dohvatiSveVidee();
                    break;
                case 21:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa kome zelite da pogledate kategorije: ");
                    int idVideoKategorije = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.dohvatiKategorijeVidea(idVideoKategorije);
                    break;
                case 22:
                    korisnik.zahtevi.dohvatiSvePakete();
                    break;
                case 23:
                    System.out.println("Unesite svoju email adresu: ");
                    String emailPretplate = scanner.nextLine();
                    korisnik.zahtevi.dohvatiPretplateKorisnika(emailPretplate);
                    break;
                case 24:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa kome zelite da pogledate gledanja: ");
                    int idVideoPregledGledanja = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.dohvatiGledanjaVidea(idVideoPregledGledanja);
                    break;
                case 25:
                    korisnik.zahtevi.dohvatiSveVidee();
                    System.out.println("Izaberite broj videa kome zelite da pogledate ocene: ");
                    int idVideoOcene = scanner.nextInt();
                    scanner.nextLine();
                    korisnik.zahtevi.dohvatiOceneZaVideo(idVideoOcene);
                    break;
                default:
                    System.out.println("Ne postoji zahtev sa ovim rednim brojem!");
            }
        }
        
    }
}
