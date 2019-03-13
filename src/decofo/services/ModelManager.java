package decofo.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Nature;
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

	@EJB
	private PersonManager personManager;

	@EJB
	private NatureManager nm;

	/**
	 * to return a list of model
	 * 
	 * @return List<Model> list of Model that is saved in the base
	 */
	public List<Model> findAllModel() {
		return em.createQuery("Select m From Model m", Model.class).getResultList();
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

	/**
	 * to save a model
	 * 
	 * @param m the model that we want to save
	 * @return Model saved model
	 */
	public Model createModel(Model m) {
		 if(isAdmin()) {  
		Element element = new Element();
		element.setCode(m.getCode());
		element.setName(m.getName());
		Nature nature = nm.findNature("FO"); // A changer si le code sur natures.xml a changé
		element.setNature(nature);
		m.getElements().add(element);
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
	 * @param m model to destroy
	 */
	public void deleteModel(Model m) {
		m = em.merge(m);
		em.remove(m);
	}

	/**
	 * to udpate a model
	 * 
	 * @param m the model that we want to update
	 * @return Model updated model
	 */
	public Model updateModel(Model m) {
		if(this.isAdmin() || this.isResponsible(m));
			m = em.merge(m);

		return m;
	}

	/**
	 * this methode test when an user want to update a model if this user is the
	 * responsible of the model on update.
	 * 
	 * @param m is an object of Model
	 * @return a boolean(True or False)
	 */
	public boolean isResponsible(Model m) {
		boolean response = false;
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
	
	/*public String generate(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 
																							
		String pass = "";
		for (int x = 0; x < length; x++) {
			int i = (int) Math.floor(Math.random() * 62); 
			pass += chars.charAt(i);
		}
		//System.err.println(pass);
		return pass;
	}*/
}
