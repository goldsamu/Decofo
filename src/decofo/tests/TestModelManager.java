package decofo.tests;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
	static EJBContainer container,container1;
	static ModelManager mm;
	@EJB
	static PersonManager pm;
	@EJB
	static ElementManager em;
	
	/**
	 * here we initialize the the EJB that we want to use
	 * @throws NamingException
	 */
	@BeforeClass
	public static void beforeAll() throws NamingException {
		final String name = "java:global/Decofo/ModelManager";
		container = EJBContainer.createEJBContainer();
		Object object = container.getContext().lookup(name);
		mm = (ModelManager) object;
		
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
		Element formation = new Element();
		Nature nature = new Nature("na1","Formation");
		//formation.setNature(nature);
		formation.setCode("ele1");
		List<Element> elements = new ArrayList<Element>();
		elements.add(formation);
		Person res = new Person();
		res.setLogin("logine");
		res.setAdmin(false);
		res.setName("jilal");
		res.setEmail("mahdihssajilal@gmail.com");
		res.setStatus("student");
		List<Person> responsibles = new ArrayList<Person>();
		responsibles.add(res);
		model.setResponsibles(responsibles);
		model.setElements(elements);
		mm.createModel(model);
		Model modelFindInTheBase = mm.findModel("CodeMaque");
		Assert.assertNotNull(modelFindInTheBase);
		Assert.assertEquals(modelFindInTheBase.getName(), model.getName());
	}
	@Test
	public void testIsResponsible() {
		pm = new PersonManager();
		Model model = new Model("CodeMaque", "ModelName");
		Element formation = new Element();
		Nature nature = new Nature("na1","Formation");
		//formation.setNature(nature);
		formation.setCode("ele1");
		List<Element> elements = new ArrayList<Element>();
		elements.add(formation);
		Person res = new Person();
		res.setLogin("logine");
		res.setAdmin(false);
		res.setName("jilal");
		res.setEmail("mahdihssajilal@gmail.com");
		res.setStatus("student");
		pm.setUser(res);
		List<Person> responsibles = new ArrayList<Person>();
		responsibles.add(res);
		model.setResponsibles(responsibles);
		model.setElements(elements);
		boolean ok = mm.isResponsible(model);
		Assert.assertTrue(ok);
	}


}