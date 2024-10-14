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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import returns.GledanjeReturn;

/**
 *
 * @author lenovo
 */
@Path("gledanjeVidea")
@Produces(MediaType.APPLICATION_JSON)
public class GledanjeResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem3Response")
    private Queue queuePodsistem3Response;
    
    @Resource(lookup = "queuePodsistem3Request")
    private Queue queuePodsistem3Request;
    
    @POST
    public Response kreirajGledanje(
            @FormParam("idVideo") int idVideo,
            @FormParam("KorisnikEmail") String korisnikEmail,
            @FormParam("sekundeOdgledane") int sekundeOdgledane) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("KorisnikEmail", korisnikEmail);
            objMessage.setIntProperty("sekundeOdgledane", sekundeOdgledane);
            objMessage.setStringProperty("resource", "gledanje");
            objMessage.setStringProperty("request", "dodajGledanje");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                GledanjeReturn gledanje = (GledanjeReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(gledanje).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @POST
    @Path("/dohvatiGledanja")
    public Response dohvatiGledanjaVidea(@FormParam("idVideo") int idVideo) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("resource", "gledanje");
            objMessage.setStringProperty("request", "dohvatiGledanjaKorisnika");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<GledanjeReturn> gledanja = (List<GledanjeReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(gledanja).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @PUT
    public Response azurirajGledanje(
            @FormParam("idGledanje") int idGledanje,
            @FormParam("sekundeOdgledane") int sekundeOdgledane) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idGledanje", idGledanje);
            objMessage.setIntProperty("sekundeOdgledane", sekundeOdgledane);
            objMessage.setStringProperty("resource", "gledanje");
            objMessage.setStringProperty("request", "azurirajGledanje");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<GledanjeReturn> gledanja = (List<GledanjeReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(gledanja).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    
    
}
