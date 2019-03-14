package decofo.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Person;

/**
 * 
 * @author mahdi hassan djilal
 *
 */
@Stateless
public class ModelManager {

	@PersistenceContext(unitName = "myTestDatabaseUnit")
	private EntityManager em;
	@EJB
	private PersonManager personManager;
	// private ElementManager elemenManager;
	private boolean admin = false;

	/**
	 * to return a list of model
	 * 
	 * @return List<Model> liste of Model that is saved in the base
	 */
	public List<Model> findAllModel() {
		return em.createQuery("Select m From Model m", Model.class).getResultList();
	}

	/**
	 * to find a model that it code is passed on argument
	 * 
	 * @param code
	 *            code of the model that we want to find it
	 * @return Model model that it code is specified on argument
	 */
	public Model findModel(String code) {
		return em.find(Model.class, code);
	}

	/**
	 * 
	 * @param m
	 *            object of Model(Maquuette)
	 * @param element
	 *            default element(Formation)
	 * @return model
	 */
	public Model createModel(Model m) {
		if (m.getElements().size() == 1) {
			admin = true;
		} else {
			admin = false;
		}
		if (admin) {
			if (m.getCode() == null) {
				em.persist(m);
			} else {
				m = em.merge(m);
			}
		}

		return m;
	}

	/**
	 * to destroy the model that is passed on argument
	 * 
	 * @param m
	 *            model to destroy
	 */
	public void deleteModel(Model m) {
		m = em.merge(m);
		em.remove(m);
	}

	/**
	 * to udpate a model
	 * 
	 * @param m
	 *            the model that we want to update
	 * @return Model updated model
	 */
	public Model updateModel(Model m) {
		if (this.isAdmin() || this.isResponsible(m))
			;
		m = em.merge(m);
		return m;
	}

	/**
	 * this methode test when an user want to update a model if this user is the
	 * responsible of the model on update.
	 * 
	 * @param m
	 *            is an object of Model
	 * @return a boolean(True or False)
	 */
	public boolean isResponsible(Model m) {
		boolean response = false;
		personManager.setUser(m.getResponsibles().get(0));
		if (m.getResponsibles().size() == 1) {
			if (m.getResponsibles().get(0).getLogin() == personManager.getUser().getLogin()) {
				response = true;
			} else {
				response = false;
			}
		} else {
			for (Person p : m.getResponsibles()) {
				if (p.getLogin() == personManager.getUser().getLogin()) {
					response = true;
				} else {
					response = false;
				}
			}
		}

		return response;
	}

	/**
	 * this method test if an user is an administrator or not
	 * 
	 * @return admin
	 */
	public boolean isAdmin() {
		boolean admin;
		if (personManager.getUser().isAdmin()) {
			admin = true;
		} else {
			admin = false;
		}
		return admin;
	}
}
