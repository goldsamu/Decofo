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

	@PersistenceContext(unitName = "myTestDatabaseUnit")
	private EntityManager em;

	/**
	 * to save a model
	 * 
	 * @param m the model that we want to save
	 * @return Model saved model
	 */
	public Model createModel(Model m) {
		em.persist(em.contains(m) ? m : em.merge(m));
		return m;
	}

	/**
	 * to udpate a model
	 * 
	 * @param m the model that we want to update
	 * @return Model updated model
	 */
	public Model updateModel(Model m) {
		return em.merge(m);
	}

	/**
	 * to destroy the model that is passed on argument
	 * 
	 * @param m model to destroy
	 */
	public void removeModel(Model m) {
		em.remove(em.contains(m) ? m : em.merge(m));
	}

	/**
	 * to return a list of model
	 * 
	 * @return List<Model> liste of Model that is saved in the base
	 */
	public List<Model> findAllModel() {
		return em.createQuery("Select From Model ", Model.class).getResultList();
	}

	/**
	 * to find a model that it code is passed on argument
	 * 
	 * @param code code of the model that we want to find it
	 * @return Model model that it code is specified on argument
	 */
	public Model findModel(String code) {
		return em.find(Model.class, code);
	}

}