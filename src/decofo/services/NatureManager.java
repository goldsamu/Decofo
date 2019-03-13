package decofo.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

	public Nature findNatureByName(String name) {
		TypedQuery<Nature> q = em.createQuery("From Nature n where n.name = '" + name + "'", Nature.class);
		return q.getSingleResult();
	}

	public List<Nature> findAllNature() {
		return em.createQuery("From Nature", Nature.class).getResultList();
	}

}
