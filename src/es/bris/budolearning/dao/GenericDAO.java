package es.bris.budolearning.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "budolearning")
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	

}
