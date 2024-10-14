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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import returns.OcenaReturn;
import returns.PretplataReturn;

/**
 *
 * @author lenovo
 */
@Path("pretplata")
@Produces(MediaType.APPLICATION_JSON)
public class PretplataResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem3Response")
    private Queue queuePodsistem3Response;
    
    @Resource(lookup = "queuePodsistem3Request")
    private Queue queuePodsistem3Request;
    
    @POST
    public Response kreirajPretplatu(
            @FormParam("idPaket") int idPaket,
            @FormParam("KorisnikEmail") String korisnikEmail) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idPaket", idPaket);
            objMessage.setStringProperty("KorisnikEmail", korisnikEmail);
            objMessage.setStringProperty("resource", "pretplata");
            objMessage.setStringProperty("request", "dodajPretplatu");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                PretplataReturn pretplata = (PretplataReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(pretplata).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @POST
    @Path("/dohvatiPretplate")
    public Response dohvatiPretplateKorisnika(@FormParam("korisnikEmail") String korisnikEmail) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem3Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("korisnikEmail", korisnikEmail);
            objMessage.setStringProperty("resource", "pretplata");
            objMessage.setStringProperty("request", "dohvatiPretplateZaKorisnika");
            
            producer.send(queuePodsistem3Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<PretplataReturn> pretplate = (List<PretplataReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(pretplate).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
}
