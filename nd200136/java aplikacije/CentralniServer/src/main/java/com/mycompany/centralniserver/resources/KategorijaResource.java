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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import returns.KategorijaReturn;

/**
 *
 * @author lenovo
 */
@Path("kategorija")
@Produces(MediaType.APPLICATION_JSON)
public class KategorijaResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem2Request")
    private Queue queuePodsistem2Request;
    
    @Resource(lookup = "queuePodsistem2Response")
    private Queue queuePodsistem2Response;
    
    @POST
    public Response kreirajKategoriju(@FormParam("Naziv") String naziv) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("Naziv", naziv);
            objMessage.setStringProperty("resource", "kategorija");
            objMessage.setStringProperty("request", "dodajKategoriju");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                KategorijaReturn k = (KategorijaReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(k).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @GET
    public Response dohvatiSveKategorije() throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("resource", "kategorija");
            objMessage.setStringProperty("request", "dohvatiSveKategorije");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<KategorijaReturn> kategorije = (List<KategorijaReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(kategorije).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    
    
    
    
}
