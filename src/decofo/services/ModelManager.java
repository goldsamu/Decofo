package decofo.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Nature;
import decofo.entities.Person;

@Stateless
public class ModelManager {

    @PersistenceContext(unitName = "decofoDatabaseUnit")
    private EntityManager em;

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

    public List<Person> findResponsibles(String code) {
	Model m = em.find(Model.class, code);
	if (m != null) {
	    m.getResponsibles().size();
	    return m.getResponsibles();
	}
	return new ArrayList<Person>();
    }

    /**
     * to save a model
     * 
     * @param m the model that we want to save
     * @return Model saved model
     */
    public Model createModel(Model m, Person user) {
	if (user.isAdmin()) {
	    if (m.getElements().isEmpty()) {
		Element element = new Element();
		element.setCode(m.getCode());
		element.setName(m.getName());
		Nature nature = nm.findNature(NatureManager.ROOTNATURE);
		element.setNature(nature);
		element.setCredits(60f);
		element.setModel(m);
		m.getElements().add(element);
	    }
	    em.persist(m);
	}
	return m;
    }

    /**
     * to destroy the model that is passed on argument
     * 
     * @param m model to destroy
     */
    public void deleteModel(Model m, Person user) {
	if (user.isAdmin()) {
	    Model model = em.find(Model.class, m.getCode());
	    em.remove(model);
	}
    }

    /**
     * to udpate a model
     * 
     * @param m the model that we want to update
     * @return Model updated model
     */
    public Model updateModel(Model m, Person user) {
	if (user.isAdmin())
	    m = em.merge(m);

	return m;
    }
}
