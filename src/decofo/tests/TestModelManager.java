package decofo.tests;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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
import decofo.services.*;

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
	static EJBContainer container1,container;
	static ModelManager mm;

	static PersonManager pm;

	/**
	 * here we initialize the the EJB that we want to use
	 * @throws NamingException
	 */
	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/ModelManager";
		container = EJBContainer.createEJBContainer();
		mm = (ModelManager) container.getContext().lookup(name);
		pm = (PersonManager) container.getContext().lookup("java:global/Decofo/PersonManager");
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
	public void testCreateAndFindModel() {
		Model model = new Model("CodeMaque", "ModelName");
		
		Person res = new Person();
		res.setLogin("logine");
		res.setAdmin(true);
		res.setName("jilal");
		res.setEmail("mahdihssajilal@gmail.com");
		res.setStatus("student");
		
		List<Person> responsibles = new ArrayList<Person>();
		responsibles.add(pm.createPerson(res));
		pm.check(res);
		model.setResponsibles(responsibles);
		mm.createModel(model, pm.getUser());

		Model modelFindInTheBase = mm.findModel("CodeMaque");
		
		Assert.assertNotNull(modelFindInTheBase);
		Assert.assertEquals(modelFindInTheBase.getName(), model.getName());
	}
}
