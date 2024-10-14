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
import returns.OcenaReturn;

/**
 *
 * @author lenovo
 */
@Path("ocena")
@Produces(MediaType.APPLICATION_JSON)
public class OcenaResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem3Response")
    private Queue queuePodsistem3Response;
    
    @Resource(lookup = "queuePodsistem3Request")
    private Queue queuePodsistem3Request;
    
    @POST
    public Response kreirajOcenu(
            @FormParam("Ocena") int ocena,
            @FormParam("idVideo") int idVideo,
            @FormParam("KorisnikEmail") String korisnikEmail) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("Ocena", ocena);
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("KorisnikEmail", korisnikEmail);
            objMessage.setStringProperty("resource", "ocena");
            objMessage.setStringProperty("request", "dodajOcenu");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                OcenaReturn o = (OcenaReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(o).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @POST
    @Path("/dohvatiOceneVidea")
    public Response dohvatiOceneZaVideo(@FormParam("idVideo") int idVideo) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("resource", "ocena");
            objMessage.setStringProperty("request", "dohvatiOceneZaVideo");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<OcenaReturn> ocene = (List<OcenaReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(ocene).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @POST
    @Path("/brisanjeOcene") 
    public Response obrisiOcenu(@FormParam("idOcena") int idOcena) throws JMSException {        
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idOcena", idOcena);
            objMessage.setStringProperty("resource", "ocena");
            objMessage.setStringProperty("request", "obrisiOcenu");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                OcenaReturn o = (OcenaReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(o).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @PUT
    public Response promeniOcenu(
            @FormParam("idOcena") int idOcena, 
            @FormParam("novaOcena") int novaOcena) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idOcena", idOcena);
            objMessage.setIntProperty("novaOcena", novaOcena);
            objMessage.setStringProperty("resource", "ocena");
            objMessage.setStringProperty("request", "promeniOcenu");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                OcenaReturn o = (OcenaReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(o).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    
}
