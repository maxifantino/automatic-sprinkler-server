package com.aut.watering.server.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.User;

@Service
public class UserDao {

	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	public User getUser (String username){
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from User where userName= :username");
		query.setParameter("login", username);
		User user = (User)query.uniqueResult();
		return user;
	}
	
	public void saveUser (User user){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(user);
		tx.commit();
		session.close();
	}
}
