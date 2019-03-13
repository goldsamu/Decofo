package decofo.tests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Nature;
import decofo.services.NatureManager;


public class TestNatureLoader {

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
	
	//@Test
	public void testFindAllNature() {
		List<Nature> listNatures = nm.findAllNature();
		assertNotNull(listNatures);
		for (Nature nature : listNatures) {
			System.err.println(nature.toString());
		}
	}

}
