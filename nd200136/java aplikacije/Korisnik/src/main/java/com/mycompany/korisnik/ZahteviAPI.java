/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.korisnik;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import returns.*;

/**
 *
 * @author lenovo
 */
public interface ZahteviAPI {
    @FormUrlEncoded
    @POST("mesto")
    Call<MestoReturn> kreirajMesto(@Field("Naziv") String naziv);

    @GET("mesto")
    Call<List<MestoReturn>> dohvatiSveGradove();

    @FormUrlEncoded
    @POST("korisnik")
    Call<KorisnikReturn> kreirajKorisnika(
        @Field("Ime") String ime,
        @Field("Email") String email,
        @Field("Godiste") String godiste,
        @Field("Pol") String pol,
        @Field("IdMesto") int idMesto
    );

    @GET("korisnik")
    Call<List<KorisnikReturn>> dohvatiSveKorisnike();

    @FormUrlEncoded
    @PUT("korisnik/email")
    Call<KorisnikReturn> promeniEmail(
        @Field("Stari Email") String stariEmail,
        @Field("Novi Email") String noviEmail
    );

    @FormUrlEncoded
    @PUT("korisnik/mesto")
    Call<KorisnikReturn> promeniIdMesto(
        @Field("Email") String email,
        @Field("IdMesto") int idMesto
    );
    
    @FormUrlEncoded
    @POST("kategorija")
    Call<KategorijaReturn> kreirajKategoriju(@Field("Naziv") String naziv);
    
    @GET("kategorija")
    Call<List<KategorijaReturn>> dohvatiSveKategorije();
    
    @FormUrlEncoded
    @POST("video")
    Call<VideoReturn> kreirajVideo(
        @Field("Naziv") String naziv,
        @Field("Trajanje") int trajanje,
        @Field("VlasnikEmail") String vlasnikEmail
    );
    
    @GET("video")
    Call<List<VideoReturn>> dohvatiSveVidee();
    
    @FormUrlEncoded
    @PUT("video/naziv")
    Call<VideoReturn> promeniNazivVidea(
        @Field("Nov naziv") String novNaziv,
        @Field("idVideo") int idVideo
    );
    
    @FormUrlEncoded
    @PUT("video/kategorija")
    Call<VideoReturn> dodajKategorijuVideu(
        @Field("idKategorija") int idKategorija,
        @Field("idVideo") int idVideo
    );
    
    @FormUrlEncoded
    @POST("video/kategorije")
    Call<VideoReturn> dohvatiKategorijeVidea(
        @Field("idVideo") int idVideo
    );
    
    @FormUrlEncoded
    @POST("video/brisanje")
    Call<VideoReturn> obrisiVideo(
        @Field("idVideo") int idVideo
    );
    
    @FormUrlEncoded
    @POST("paket")
    Call<PaketReturn> kreirajPaket(@Field("TrenutnaCena") int trenutnaCena);
    
    @GET("paket")
    Call<List<PaketReturn>> dohvatiSvePakete();
    
    @FormUrlEncoded
    @PUT("paket/cena")
    Call<PaketReturn> promeniCenuPaketa(
        @Field("Cena") int cena,
        @Field("idPaket") int idPaket
    );
    
    @FormUrlEncoded
    @POST("ocena")
    Call<OcenaReturn> kreirajOcenu(
            @Field("Ocena") int ocena,
            @Field("idVideo") int idVideo,
            @Field("KorisnikEmail") String KorisnikEmail
    );
    
    @FormUrlEncoded
    @POST("ocena/dohvatiOceneVidea")
    Call<List<OcenaReturn>> dohvatiOceneZaVideo(
            @Field("idVideo") int idVideo
    );
    
    @FormUrlEncoded
    @POST("ocena/brisanjeOcene")
    Call<OcenaReturn> obrisiOcenu(
        @Field("idOcena") int idOcena
    );
    
    @FormUrlEncoded
    @PUT("ocena")
    Call<OcenaReturn> promeniOcenu(
        @Field("idOcena") int idOcena,
        @Field("novaOcena") int novaOcena
    );
    
    @FormUrlEncoded
    @POST("pretplata")
    Call<PretplataReturn> kreirajPretplatu(
            @Field("idPaket") int idPaket,
            @Field("KorisnikEmail") String KorisnikEmail
    );
    
    @FormUrlEncoded
    @POST("pretplata/dohvatiPretplate")
    Call<List<PretplataReturn>> dohvatiPretplateKorisnika(
            @Field("korisnikEmail") String korisnikEmail
    );
    
    @FormUrlEncoded
    @POST("gledanjeVidea")
    Call<GledanjeReturn> kreirajGledanje(
        @Field("idVideo") int idVideo,
        @Field("KorisnikEmail") String KorisnikEmail,
        @Field("sekundeOdgledane") int sekundeOdgledane
    );
    
    @FormUrlEncoded
    @POST("gledanjeVidea/dohvatiGledanja")
    Call<List<GledanjeReturn>> dohvatiGledanjaVidea(
            @Field("idVideo") int idVideo
    );
    
    @FormUrlEncoded
    @PUT
    Call<GledanjeReturn> azurirajGledanje(
        @Field("idGledanje") int idGledanje,
        @Field("sekundeOdgledane") int sekundeOdgledane
    );
}
