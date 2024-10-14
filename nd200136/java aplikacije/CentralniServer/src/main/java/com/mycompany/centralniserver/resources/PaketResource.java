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
import returns.PaketReturn;

/**
 *
 * @author lenovo
 */
@Path("paket")
@Produces(MediaType.APPLICATION_JSON)
public class PaketResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem3Response")
    private Queue queuePodsistem3Response;
    
    @Resource(lookup = "queuePodsistem3Request")
    private Queue queuePodsistem3Request;
    
    @POST
    public Response kreirajPaket(@FormParam("TrenutnaCena") int trenutnaCena) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("TrenutnaCena", trenutnaCena);
            objMessage.setStringProperty("resource", "paket");
            objMessage.setStringProperty("request", "dodajPaket");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                PaketReturn paket = (PaketReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(paket).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @GET
    public Response dohvatiSvePakete() throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("resource", "paket");
            objMessage.setStringProperty("request", "dohvatiSvePakete");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<PaketReturn> paketi = (List<PaketReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(paketi).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @PUT
    @Path("/cena")
    public Response promeniCenuPaketa(
            @FormParam("Cena") int cena,
            @FormParam("idPaket") int idPaket) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("Cena", cena);
            objMessage.setIntProperty("idPaket", idPaket);
            objMessage.setStringProperty("resource", "paket");
            objMessage.setStringProperty("request", "promeniCenuPaketa");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                PaketReturn paket = (PaketReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(paket).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
}
