package decofo.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Nature;
import decofo.entities.Person;

@Stateless
public class ElementManager {

    @PersistenceContext(unitName = "decofoDatabaseUnit")
    private EntityManager em;

    @EJB
    private NatureManager nm;

    public Element createElement(Element e, Person user) {
	if (isResponsible(e.getModel(), user))
	    em.persist(e);
	return e;
    }

    public Element findElement(String code) {
	return em.find(Element.class, code);
    }

    public Element findElementWithChildren(String code) {
	Element e = em.find(Element.class, code);
	e.getChildren().size();
	return e;
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

    public Element findRoot(Model model) {
	TypedQuery<Element> q = em.createQuery("FROM Element WHERE Nature_Code = :nature AND Model_Code = :model",
		Element.class);
	q.setParameter("model", model.getCode());
	q.setParameter("nature", NatureManager.ROOTNATURE);
	return q.getSingleResult();
    }

    public List<Element> findPotentialFathers(Model model, String codeNature) {
	if (!codeNature.equals("")) {
	    TypedQuery<Element> q = em.createQuery("FROM Element WHERE Model_Code = :model AND Nature_Code IN :nature",
		    Element.class);
	    q.setParameter("model", model.getCode());
	    q.setParameter("nature", nm.findFathers(codeNature));

	    return q.getResultList();
	}
	return new ArrayList<Element>();
    }

    public List<Element> findPotentialChildren(Model model, String codeNature) {
	if (!codeNature.equals("")) {
	    List<Nature> children = nm.findChildren(codeNature);
	    if (children.size() > 0) {
		TypedQuery<Element> q = em.createQuery(
			"FROM Element WHERE Model_Code = :model AND Nature_Code IN :nature", Element.class);
		q.setParameter("model", model.getCode());
		q.setParameter("nature", children);

		return q.getResultList();
	    }
	}
	return new ArrayList<Element>();
    }

    public Element updateElement(Element e, Person user) {
	if (isResponsible(e.getModel(), user)) {
	    return em.merge(e);
	}
	return e;
    }

    public void removeElement(Element e, Person user) {
	if (isResponsible(e.getModel(), user))
	    em.remove(e);
    }

    public boolean isResponsible(Model model, Person user) {
	Model m = em.find(Model.class, model.getCode());
	for (Person p : m.getResponsibles()) {
	    if (p.getLogin().equals(user.getLogin()))
		return true;
	}
	return false;
    }

}