package decofo.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import decofo.entities.Site;

@Stateless
public class SiteManager {

	@PersistenceContext(unitName = "myTestDatabaseUnit")
	private EntityManager em;
	
	public Site createSite(Site m) {
		em.persist(em.contains(m) ? m : em.merge(m));
		return m;
	}
	
	public Site findSite(String code) {
		return em.find(Site.class, code);
	}
}
