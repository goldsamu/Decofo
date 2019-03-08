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
	/**
	 * to return a list of model
	 * @return List<Model> liste of Model that is saved in the base
	 */
	public List<Model> findCourses() {
		return em.createQuery("Select m From Model m", Model.class).getResultList();
	}
	/**
	 * to find a model that it code is passed on argument
	 * @param code code of the model that we want to find it
	 * @return Model model that it code is specified on argument
	 */
	public Model findCourse(String code) {
		return em.find(Model.class, code);
	}
	/**
	 * to save a model
	 * @param m the model that we want to save
	 * @return Model saved model
	 */
	public Model saveCourse(Model m) {
		if (m.getCode() == null) {
			em.persist(m);
		} else {
			m = em.merge(m);
		}
		return m;
	}
	/**
	 * to destroy the model that is passed on argument
	 * @param m model to destroy
	 */
	public void deleteCourse(Model m) {
		m = em.merge(m);
		em.remove(m);
	}
	/**
	 * to udpate a model
	 * @param m the model that we want to update
	 * @return Model updated model
	 */
	public Model updatePerson(Model m) {
		m = em.merge(m);
		return m;
	}
}
