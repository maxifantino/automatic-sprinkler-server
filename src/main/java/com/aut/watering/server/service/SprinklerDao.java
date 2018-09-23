package com.aut.watering.server.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.Patch;

@Service
public class SprinklerDao {

	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	public Patch getSprinkler (String code){
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Patch where patchCode= :code");
		query.setParameter("code", code);
		Patch patch = (Patch)query.uniqueResult();
		return patch;
	}
	
	public void saveSprinkler (Patch sprinkler){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(sprinkler);
		tx.commit();
		session.close();
	}
	
	public Patch getSprinkler(Integer id){
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Patch where id= :id");
		query.setParameter("id", id);
		Patch patch = (Patch)query.uniqueResult();
		return patch;
	}


	public void savePatch (Patch patch){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(patch);
		tx.commit();
		session.close();
	}

	public void deleteSprinkler (Patch patch){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(patch);
		tx.commit();
		session.close();
	}
}
