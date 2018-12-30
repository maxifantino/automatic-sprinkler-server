package com.aut.watering.server.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aut.watering.server.dto.User;

@Component
public class UserDao {

	private Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public User getUser (String username){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
	
		Query query = session.createQuery("from User where userName= :username");
		query.setParameter("username", username);
		User user = (User)query.uniqueResult();
		log.error("User: " + username);
		log.error("User: " + user.toString());
		return user;
	}
	
	public void saveUser (User user){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();
		session.persist(user);
		tx.commit();
		session.close();
		log.error ("USer: " + user.getUsername());
	}

	public User getUser(Integer userId) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		
		Query query = session.createQuery("from User where id = :userId");
		query.setParameter("userId", userId);
		User user = (User)query.uniqueResult();
		return user;
	}
}
