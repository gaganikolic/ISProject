/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.resources;

import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import returns.KorisnikReturn;

/**
 *
 * @author lenovo
 */
@Path("korisnik")
@Produces(MediaType.APPLICATION_JSON)
public class KorisnikResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem1Requests")
    private Queue queuePodsistem1Requests;
    
    @Resource(lookup = "queuePodsistem1Odgovori")
    private Queue queuePodsistem1Odgovori;
    
    @POST
    public Response kreirajKorisnika(
            @FormParam("Ime") String ime,
            @FormParam("Email") String email,
            @FormParam("Godiste") String godiste,
            @FormParam("Pol") String pol,
            @FormParam("IdMesto") int idMesto) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem1Odgovori)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("ime", ime);
            objMessage.setStringProperty("email", email);
            objMessage.setStringProperty("godiste", godiste);
            objMessage.setStringProperty("pol", pol);
            objMessage.setIntProperty("idMesto", idMesto);
            objMessage.setStringProperty("resource", "korisnik");
            objMessage.setStringProperty("request", "dodajKorisnika");
            
            producer.send(queuePodsistem1Requests, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                KorisnikReturn k = (KorisnikReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(k).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @PUT
    @Path("/email")
    public Response promeniEmailAdresu(
            @FormParam("Stari Email") String stariEmail, 
            @FormParam("Novi Email") String noviEmail) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem1Odgovori)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("noviEmail", noviEmail);
            objMessage.setStringProperty("stariEmail", stariEmail);
            objMessage.setStringProperty("resource", "korisnik");
            objMessage.setStringProperty("request", "promeniEmail");
            
            producer.send(queuePodsistem1Requests, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                KorisnikReturn k = (KorisnikReturn) messageReceive.getObject();
                
                return Response.status(Response.Status.OK).entity(k).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @PUT
    @Path("/mesto")
    public Response promeniMesto(
            @FormParam("Email") String email, 
            @FormParam("IdMesto") int idMesto) throws JMSException {        
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem1Odgovori)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("email", email);
            objMessage.setIntProperty("idMesto", idMesto);
            objMessage.setStringProperty("resource", "korisnik");
            objMessage.setStringProperty("request", "promeniMesto");
            
            producer.send(queuePodsistem1Requests, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                KorisnikReturn k = (KorisnikReturn) messageReceive.getObject();
                
                return Response.status(Response.Status.OK).entity(k).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    
    @GET
    public Response dohvatiSveKorisnike() throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem1Odgovori)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("resource", "korisnik");
            objMessage.setStringProperty("request", "dohvatiSveKorisnike");
            
            producer.send(queuePodsistem1Requests, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<KorisnikReturn> korisnici = (List<KorisnikReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(korisnici).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
}
