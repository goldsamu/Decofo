package decofo.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Model;

/**
 * 
 * @author mahdi hassan djilal
 *
 */
@Stateless
public class ModelManager {

	@PersistenceContext(unitName = "decofoDatabaseUnit")
	private EntityManager em;

	public List<Model> findCourses() {
		return em.createQuery("Select m From Model m", Model.class).getResultList();
	}

	public Model findCourse(String code) {
		return em.find(Model.class, code);
	}

	public Model saveCourse(Model m) {
		if (m.getCode() == null) {
			em.persist(m);
		} else {
			m = em.merge(m);
		}
		return m;
	}

	public void deleteCourse(Model m) {
		m = em.merge(m);
		em.remove(m);
	}

	public Model updatePerson(Model m) {
		m = em.merge(m);
		return m;
	}
}
