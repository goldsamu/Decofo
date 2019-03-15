package decofo.tests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Site;
import decofo.services.SiteManager;

public class TestSiteLoader {
	
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
	public void testCreateAndFindNature() {
		List<Site> listSites = sm.findAllSite();
		assertNotNull(listSites);
		for (Site site : listSites) {
			System.err.println(site.toString());
		}
	}

}
