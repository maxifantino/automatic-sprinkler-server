package com.aut.watering.server.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.Location;

@Component
public class LocationDao {

	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void saveLocation (Location location){
		log.error("Entre!");
		try{
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			log.error("About to save location");
			session.persist(location);
			log.error("Persisted");
			tx.commit();
			session.close();	
		}
		catch (Exception e) {
			log.error("Error",e);
		}
	}

}