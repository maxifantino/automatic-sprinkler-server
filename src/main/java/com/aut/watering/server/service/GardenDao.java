package com.aut.watering.server.service;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aut.watering.server.dto.Garden;
import com.aut.watering.server.dto.Location;
import com.aut.watering.server.dto.Patch;

@Service
public class GardenDao {
	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;

    public Garden getGarden (Integer id){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Query query = session.createQuery("from Garden where id= :id");
		query.setParameter("id", id);
		Garden garden = (Garden)query.uniqueResult();
		return garden;
	}
    
    public List <Garden> getGardenByUserId (Integer userId){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Query query = session.createQuery("from Garden where user_id= :userId");
		query.setParameter("userId", userId);
		List<Garden> gardens = (List<Garden>)query.getResultList();
		return gardens;
	}
    
	public void saveGarden (Garden garden){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();
		session.persist(garden);
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

	public Patch getSprinkler(String patchCode){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Query query = session.createQuery("from Patch where patchCode= :patch_code");
		query.setParameter("patch_code", patchCode);
		Patch patch = (Patch)query.uniqueResult();
		return patch;
	}

	public void savePatch (Patch patch){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();
		session.persist(patch);
		tx.commit();
		session.close();
	}

	public void deleteGarden (Garden garden){
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();		
		session.remove(session.contains(garden) ? garden : session.merge(garden));
		tx.commit();		
		session.close();
	}
	
	public void mergeGarden(Garden garden) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();		
		session.merge(garden);
		
		tx.commit();
		session.close();
	}
	
	public void mergeLocation (Location location) {
		Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Transaction tx = session.beginTransaction();		
		session.merge(location);
		
		tx.commit();
		session.close();
		
	}
}

