package com.aut.watering.server.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Patch;

@Service
public class GardenDao {
	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	public Garden getGarden (Integer id){
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Garden where id= :id");
		query.setParameter("id", id);
		Garden garden = (Garden)query.uniqueResult();
		return garden;
	}
	
	public void saveGarden (Garden garden){
		log.error ("Llegueeeeee dao" + garden.toString());
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(garden);
		tx.commit();
		log.error("MaxiDao: " + garden.toString());
		session.close();
	}
	
	public Patch getSprinkler(Integer id){
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Patch where id= :id");
		query.setParameter("id", id);
		Patch patch = (Patch)query.uniqueResult();
		return patch;
	}

	public Patch getSprinkler(String patchCode){
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Patch where patchCode= :patch_code");
		query.setParameter("patch_code", patchCode);
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

	public void deleteGarden (Garden garden){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(garden);
		tx.commit();
		session.close();
	}
}

