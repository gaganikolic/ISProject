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
import javax.jms.TextMessage;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import returns.VideoReturn;

/**
 *
 * @author lenovo
 */
@Path("video")
@Produces(MediaType.APPLICATION_JSON)
public class VideoResource {
    @Resource(lookup = "connectionFactoryVideo")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem2Request")
    private Queue queuePodsistem2Request;
    
    @Resource(lookup = "queuePodsistem2Response")
    private Queue queuePodsistem2Response;
    
    @POST
    public Response kreirajVideo(
            @FormParam("Naziv") String naziv,
            @FormParam("Trajanje") int trajanje,
            @FormParam("VlasnikEmail") String vlasnikEmail) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("Naziv", naziv);
            objMessage.setIntProperty("Trajanje", trajanje);
            objMessage.setStringProperty("VlasnikEmail", vlasnikEmail);
            objMessage.setStringProperty("resource", "video");
            objMessage.setStringProperty("request", "dodajVideo");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                VideoReturn v = (VideoReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(v).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @GET
    public Response dohvatiSveVidee() throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("resource", "video");
            objMessage.setStringProperty("request", "dohvatiSveVidee");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                List<VideoReturn> videi = (List<VideoReturn>) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(videi).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @PUT
    @Path("/naziv")
    public Response promeniNazivVidea(
            @FormParam("Nov naziv") String novNaziv, 
            @FormParam("idVideo") int idVideo) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setStringProperty("novNaziv", novNaziv);
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("resource", "video");
            objMessage.setStringProperty("request", "promeniNaziv");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                VideoReturn v = (VideoReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(v).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @PUT
    @Path("/kategorija") 
    public Response dodajKategorijuVideu(
            @FormParam("idKategorija") int idKategorija, 
            @FormParam("idVideo") int idVideo) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idKategorija", idKategorija);
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("resource", "video");
            objMessage.setStringProperty("request", "dodajKategoriju");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                VideoReturn v = (VideoReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(v).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @POST
    @Path("/kategorije") 
    public Response dohvatiKategorijeVidea(@FormParam("idVideo") int idVideo) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("resource", "video");
            objMessage.setStringProperty("request", "dohvatiKategorijeVidea");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                VideoReturn v = (VideoReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(v).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
    @POST
    @Path("/brisanje") 
    public Response obrisiVideo(@FormParam("idVideo") int idVideo) throws JMSException {
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(queuePodsistem2Response)) {
            JMSProducer producer = context.createProducer();
            
            ObjectMessage objMessage = context.createObjectMessage();
            objMessage.setIntProperty("idVideo", idVideo);
            objMessage.setStringProperty("resource", "video");
            objMessage.setStringProperty("request", "obrisiVideo");
            
            producer.send(queuePodsistem2Request, objMessage);
            
            //cekanje na obradu zahteva
            
            Message msg = consumer.receive();
            if(msg instanceof ObjectMessage){
                ObjectMessage messageReceive = (ObjectMessage) msg;
                VideoReturn v = (VideoReturn) messageReceive.getObject();
                return Response.status(Response.Status.OK).entity(v).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR!").build();
        }
    }
    
}
