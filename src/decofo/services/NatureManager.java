package decofo.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Nature;

@Stateless
public class NatureManager {

	@PersistenceContext(unitName = "myTestDatabaseUnit")
	private EntityManager em;
	
	public Nature createNature(Nature m) {
		em.persist(em.contains(m) ? m : em.merge(m));
		return m;
	}
	
	public Nature findNature(String code) {
		return em.find(Nature.class, code);
	}
}
