package decofo.tests;

import static org.junit.Assert.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Nature;
import decofo.entities.Person;
import decofo.services.ElementManager;
import decofo.services.ModelManager;
import decofo.services.NatureManager;
import decofo.services.PersonManager;

public class TestElementManager {

	static EJBContainer container;
	static ElementManager em;
	static ModelManager mm;
	static PersonManager pm;
	static NatureManager nm;

	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/ElementManager";
		container = EJBContainer.createEJBContainer();
		em = (ElementManager) container.getContext().lookup(name);
		mm = (ModelManager) container.getContext().lookup("java:global/Decofo/ModelManager");
		pm = (PersonManager) container.getContext().lookup("java:global/Decofo/PersonManager");
		nm = (NatureManager) container.getContext().lookup("java:global/Decofo/NatureManager");
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
		
		nm.createNature(nature);
		
		/*Site site1 = new Site("codeSite1", "nameSite1");
		Site site2 = new Site("codeSite2", "nameSite2");
		Map<Site, Integer> sites = new HashMap<Site, Integer>();
		sites.put(site1, Integer.valueOf(100));
		sites.put(site2, Integer.valueOf(200));*/
		
		Person p = new Person();
		p.setLogin("CreateAndFindElement");
		p.setAdmin(true);
		p.setName("CreateAndFindElement");
		p.setEmail("CreateAndFindElement");
		p.setStatus("CreateAndFindElement");
		
		pm.createPerson(p);
		
		Model model = new Model("CreateAndFindElement", "CreateAndFindElement");
		model.getResponsibles().add(p);
		mm.createModel(model, p);
		
		Element element = new Element("codeElement1", nature, "nameElement1");
		//element.setSites(sites);
		element.setModel(model);
		em.createElement(element, p);
	
		Element eltFromDatabase = em.findElement("codeElement1");
		assertNotNull(eltFromDatabase);
		Assert.assertEquals(eltFromDatabase.getName(), element.getName());
		//Assert.assertEquals(eltFromDatabase.getSites().get(site2).intValue(), 200);
	}

}
