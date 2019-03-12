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
/**
 * 
 * @author mahdi hassan djilal
 * this will test the ModelManager
 * 
 */
public class TestModelManager {
	/**
	 * attributs for TestModelManager
	 * container objet of EJBContainer and mm objet ModelManager
	 */
	static EJBContainer container;
	static ModelManager mm;
	/**
	 * here we initialize the the EJB that we want to use
	 * @throws NamingException
	 */
	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/ModelManager";
		container = EJBContainer.createEJBContainer();
		mm = (ModelManager) container.getContext().lookup(name);
	}
	/**
	 * after operation, we close the conainer EJB
	 */
	@AfterClass
	public static void afterAll() {
		container.close();
	}
	/**
	 * this method test is a method
	 * this method test if the mm attribut is null or not
	 */
	@Test
	public void testInject() {
		Assert.assertNotNull(mm);
	}
	/**
	 * this method will if we can create a model 
	 */
	@Test
	public void testCreateAndFindElement() {
		Model model = new Model("CodeMaque", "ModelName");
		mm.createModel(model);

		Model modelFindInTheBase = mm.findModel("CodeMaque");
		assertNotNull(modelFindInTheBase);
		Assert.assertEquals(modelFindInTheBase.getName(), model.getName());
	}
}