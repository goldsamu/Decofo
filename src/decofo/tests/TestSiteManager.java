package decofo.tests;

import static org.junit.Assert.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Site;
import decofo.services.SiteManager;

public class TestSiteManager {

	static EJBContainer container;
	static SiteManager sm;

	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/SiteManager";
		container = EJBContainer.createEJBContainer();
		sm = (SiteManager) container.getContext().lookup(name);
	}

	@AfterClass
	public static void afterAll() {
		container.close();
	}

	@Test
	public void testInject() {
		Assert.assertNotNull(sm);
	}
	
	@Test
	public void testCreateAndFindSite() {
		
		Site site1 = new Site("codeSite1", "nameSite1");
		Site site2 = new Site("codeSite2", "nameSite2");
		
		sm.createSite(site1);
		sm.createSite(site2);
	
		Site siteFromDatabase = sm.findSite("codeSite2");
		assertNotNull(siteFromDatabase);
		Assert.assertEquals(siteFromDatabase.getName(), site2.getName());
	}
}
