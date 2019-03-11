package decofo.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Model;
import decofo.entities.Person;

/**
 * 
 * @author mahdi hassan djilal
 *
 */
@Stateless
public class ModelManager {
	
	@PersistenceContext(unitName = "decofoDatabaseUnit")
	private EntityManager em;
	private PersonManager pm;
	/**
	 * to return a list of model
	 * @return List<Model> liste of Model that is saved in the base
	 */
	public List<Model> findModel() {
		return em.createQuery("Select m From Model m", Model.class).getResultList();
	}
	/**
	 * to find a model that it code is passed on argument
	 * @param code code of the model that we want to find it
	 * @return Model model that it code is specified on argument
	 */
	public Model findModel(String code) {
		return em.find(Model.class, code);
	}
	/**
	 * to save a model
	 * @param m the model that we want to save
	 * @return Model saved model
	 */
	public Model saveModel(Model m) {
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
	public void deleteModel(Model m) {
		m = em.merge(m);
		em.remove(m);
	}
	/**
	 * to udpate a model
	 * @param m the model that we want to update
	 * @return Model updated model
	 */
	public Model updateModel(Model m) {
		m = em.merge(m);
		return m;
	}
	/**
	 * this methode test when an user want to update a model if this user is the responsible of the model on update. 
	 * @param m is an object of Model
	 * @return a boolean(True or False)
	 */
	public boolean isResponsible(Model m) {
		boolean response = false;
		if(m.getResponsables().size()==1) {
			if(m.getResponsables().get(0).getLogin() == pm.getUser().getLogin()) {
				response = true;
			}else {
				response = false;
			}
		}else {
			for(Person p:m.getResponsables()) {
				if(p.getLogin() == pm.getUser().getLogin()) {
					response = true;
				}else {
					response = false;
				}
			}
		}
		
		return response;
	}
}
