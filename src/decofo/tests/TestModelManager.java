package decofo.tests;

import static org.junit.Assert.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Model;
import decofo.services.ModelManager;

public class TestModelManager {
	static EJBContainer container;
	static ModelManager em;

	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/ModelManager";
		container = EJBContainer.createEJBContainer();
		em = (ModelManager) container.getContext().lookup(name);
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
		Model model = new Model("CodeMaque", "ModelName");
		em.saveModel(model);
	
		Model modelFindItTheBase = em.findModel("CodeMaque");
		assertNotNull(modelFindItTheBase);
		Assert.assertEquals(modelFindItTheBase.getNom(), model.getNom());
}
}
