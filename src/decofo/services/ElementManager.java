package decofo.services;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Element;

@Stateless
public class ElementManager {

	@PersistenceContext(unitName = "myTestDatabaseUnit")
    protected EntityManager em;

	protected  Element createElement(Element e) {
		em.persist(em.contains(e) ? e : em.merge(e));
	    return e;
	}
	
	protected  Element findElement(String code) {
	    return em.find(Element.class, code);
	}

	protected Element updateElement(Element e) {
	    return em.merge(e);
	}

	protected void removeElement(Element e) {
	    em.remove(em.contains(e) ? e : em.merge(e));
	}

}
