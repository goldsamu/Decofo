package decofo.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import decofo.entities.Nature;

@Stateless
public class NatureManager {
    public final static String ROOTNATURE = "FR";
    @PersistenceContext(unitName = "decofoDatabaseUnit")
    private EntityManager em;

    public Nature createNature(Nature m) {
	em.persist(m);
	return m;
    }

    public Nature updateNature(Nature m) {
	return em.merge(m);
    }

    public Nature findNature(String code) {
	return em.find(Nature.class, code);
    }

    public Nature findNatureByName(String name) {
	TypedQuery<Nature> q = em.createQuery("From Nature n where n.name = '" + name + "'", Nature.class);
	return q.getSingleResult();
    }

    public List<Nature> findChildren(String code) {
	Nature n = em.find(Nature.class, code);
	if (n != null) {
	    n.getChildren().size();
	}
	return n.getChildren();
    }

    public List<Nature> findAllNature() {
	return em.createQuery("From Nature", Nature.class).getResultList();
    }

    public List<Nature> findAllNatureWithoutRoot() {
	return em.createQuery("From Nature where Code != '" + ROOTNATURE + "'", Nature.class).getResultList();
    }
}
