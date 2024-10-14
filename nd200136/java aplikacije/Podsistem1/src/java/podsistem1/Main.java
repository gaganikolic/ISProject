/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.persistence.EntityManager;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lenovo
 */
public class Main {
    @Resource(lookup = "connectionFactoryVideo")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "queuePodsistem1Requests")
    private static Queue queuePodsistem1Requests;
    
    @Resource(lookup = "queuePodsistem1Odgovori")
    private static Queue queuePodsistem1Odgovori;
    
    @Resource(lookup = "topicProjekat2024")
    private static Topic topicProjekat2024;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JMSException {
        JMSContext context = connectionFactory.createContext();
        
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Podsistem1PU");
        try {
            EntityManager em = emFactory.createEntityManager();
            Podsistem1Handler handler = new Podsistem1Handler(em, context, queuePodsistem1Requests, queuePodsistem1Odgovori, topicProjekat2024);
            handler.obradiZahtev();
        } finally {
            emFactory.close();
        }
    }
    
}
