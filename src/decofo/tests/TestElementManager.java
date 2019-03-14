package decofo.tests;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Element;
import decofo.entities.Nature;
import decofo.entities.Site;
import decofo.services.ElementManager;

public class TestElementManager {

	static EJBContainer container;
	static ElementManager em;

	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/ElementManager";
		container = EJBContainer.createEJBContainer();
		em = (ElementManager) container.getContext().lookup(name);
	}

	@AfterClass
	public static void afterAll() {
		container.close();
	}

	@Test
	public void testInject() {
		Assert.assertNotNull(em);
	}

	@Test
	public void testCreateAndFindElement() {
		
		Nature nature = new Nature("codeNature1", "nameNature1");
		nature.setNodeType("ET");
		
		/*Site site1 = new Site("codeSite1", "nameSite1");
		Site site2 = new Site("codeSite2", "nameSite2");
		Map<Site, Integer> sites = new HashMap<Site, Integer>();
		sites.put(site1, Integer.valueOf(100));
		sites.put(site2, Integer.valueOf(200));*/
		
		Element element = new Element("codeElement1", nature, "nameElement1");
		//element.setSites(sites);
		em.createElement(element);
	
		Element eltFromDatabase = em.findElement("codeElement1");
		assertNotNull(eltFromDatabase);
		Assert.assertEquals(eltFromDatabase.getName(), element.getName());
		//Assert.assertEquals(eltFromDatabase.getSites().get(site2).intValue(), 200);
	}

}
