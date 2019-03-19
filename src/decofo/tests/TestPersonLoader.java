package decofo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Person;
import decofo.entities.Site;
import decofo.services.PersonManager;

public class TestPersonLoader {
	
	static EJBContainer container;
	static PersonManager pm;

	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/PersonManager";
		container = EJBContainer.createEJBContainer();
		pm = (PersonManager) container.getContext().lookup(name);
	}

	@AfterClass
	public static void afterAll() {
		container.close();
	}

	@Test
	public void testInject() {
		Assert.assertNotNull(pm);
	}
	
	@Test
	public void testCreateAndFindNature() {
		Person p = pm.findPersonByLogin("g13017841");
		assertEquals(p.getLogin(), "g13017841");
		System.out.println(p);
	}

}
