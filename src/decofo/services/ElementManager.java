package decofo.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Person;

@Stateless
public class ElementManager {

    @PersistenceContext(unitName = "decofoDatabaseUnit")
    private EntityManager em;

    public Element createElement(Element e, Person user) {
	if (isResponsible(e.getModel(), user))
	    em.persist(em.contains(e) ? e : em.merge(e));
	return e;
    }

    public Element findElement(String code) {
	return em.find(Element.class, code);
    }
    
    public List<Element> findFathers(String code) {
	Element e = em.find(Element.class, code);
	e.getFathers().size();
	return e.getFathers();
    }
    
    public List<Element> findChildren(String code) {
	Element e = em.find(Element.class, code);
	e.getChildren().size();
	return e.getChildren();
    }

    public Element updateElement(Element e, Person user) {
	System.out.println("updateElement : " + em.find(Element.class, e.getCode()));
	if (isResponsible(e.getModel(), user))
	    return em.merge(e);
	return e;
    }

    public void removeElement(Element e, Person user) {
	if (isResponsible(e.getModel(), user))
	    em.remove(em.contains(e) ? e : em.merge(e));
    }

    public boolean isResponsible(Model model, Person user) {
	Model m = em.find(Model.class, model.getCode());
	System.out.println("Responsibles : " + m.getResponsibles());
	for (Person p : m.getResponsibles()) {
	    if (p.getLogin().equals(user.getLogin()))
		return true;
	}
	return false;
    }

}
