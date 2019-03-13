package decofo.services;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Element;

@Stateless
public class ElementManager {

	@PersistenceContext(unitName = "myTestDatabaseUnit")
    private EntityManager em;

	public  Element createElement(Element e) {
		em.persist(em.contains(e) ? e : em.merge(e));
	    return e;
	}
	
	public  Element findElement(String code) {
	    return em.find(Element.class, code);
	}

	public Element updateElement(Element e) {
	    return em.merge(e);
	}

	public void removeElement(Element e) {
	    em.remove(em.contains(e) ? e : em.merge(e));
	}

}
