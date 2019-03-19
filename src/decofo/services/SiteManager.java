package decofo.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Site;

@Stateless
public class SiteManager {

	@PersistenceContext(unitName = "decofoDatabaseUnit")
	private EntityManager em;
	
	public Site createSite(Site m) {
		em.persist(em.contains(m) ? m : em.merge(m));
		return m;
	}
	
	public Site findSite(String code) {
		return em.find(Site.class, code);
	}
	
	public List<Site> findAllSite() {
		return em.createQuery("From Site", Site.class).getResultList();
	}
}
