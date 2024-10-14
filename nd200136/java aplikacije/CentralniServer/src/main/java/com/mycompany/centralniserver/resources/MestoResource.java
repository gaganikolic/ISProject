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
import javax.jms.Topic;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import returns.MestoReturn;

/**
 *
 * @author lenovo
 */
@Path("mesto")
@Produces(MediaType.APPLICATION_JSON)
public class MestoResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem1Requests")
    private Queue queuePodsistem1Requests;
    
    @Resource(lookup = "queuePodsistem1Odgovori")
    private  Queue queuePodsistem1Odgovori;
    
    @POST
    public Response kreirajGrad(@FormParam("Naziv") String naziv) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem1Odgovori)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("Naziv", naziv);
            objMessage.setStringProperty("resource", "mesto");
            objMessage.setStringProperty("request", "dodajGrad");
            
            producer.send(queuePodsistem1Requests, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                MestoReturn grad = (MestoReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(grad).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @GET
    public Response dohvatiSveGradove() throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem1Odgovori)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("resource", "mesto");
            objMessage.setStringProperty("request", "dohvatiSveGradove");
            
            producer.send(queuePodsistem1Requests, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<MestoReturn> gradovi = (List<MestoReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(gradovi).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    
    
}
