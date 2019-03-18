package decofo.tests;

import static org.junit.Assert.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Element;
import decofo.entities.Nature;
import decofo.services.NatureManager;

public class TestNatureManager {

	static EJBContainer container;
	static NatureManager nm;

	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/NatureManager";
		container = EJBContainer.createEJBContainer();
		nm = (NatureManager) container.getContext().lookup(name);
	}

	@AfterClass
	public static void afterAll() {
		container.close();
	}

	@Test
	public void testInject() {
		Assert.assertNotNull(nm);
	}
	
	@Test
	public void testCreateAndFindNature() {
		
		Nature nature1 = new Nature("codeNature1", "nameNature1");
		Nature nature2 = new Nature("codeNature2", "nameNature2");
		
		nature1.setNodeType("ET");
		nature2.setNodeType("OU");
		
		nm.createNature(nature1);
		nm.createNature(nature2);
	
		Nature natureFromDatabase = nm.findNature("codeNature2");
		assertNotNull(natureFromDatabase);
		Assert.assertEquals(natureFromDatabase.getName(), nature2.getName());
	}
}
