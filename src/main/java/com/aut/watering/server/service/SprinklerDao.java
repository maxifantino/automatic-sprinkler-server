package com.aut.watering.server.service;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.Patch;

@Service
public class SprinklerDao {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public Patch getSprinkler (String code){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Query query = session.createQuery("from Patch where patchCode= :code");
		query.setParameter("code", code);
		Patch patch = (Patch)query.uniqueResult();
		return patch;
	}
	
	public void saveSprinkler (Patch sprinkler){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();
		session.persist(sprinkler);
		tx.commit();
		session.close();
	}
	
	public Patch getSprinkler(Integer id){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Query query = session.createQuery("from Patch where id= :id");
		query.setParameter("id", id);
		Patch patch = (Patch)query.uniqueResult();
		return patch;
	}


	public void savePatch (Patch patch){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();
		session.merge(patch);
		tx.commit();
		session.close();
	}

	public void deleteSprinkler (Patch patch){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();
		session.remove(session.contains(patch) ? patch : session.merge(patch));
		tx.commit();
		session.close();
	}
}

