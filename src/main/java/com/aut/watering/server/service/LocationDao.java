package com.aut.watering.server.service;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.Location;

@Component
public class LocationDao {

	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public void saveLocation (Location location){
		log.error("Entre!");
		try{
			Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
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