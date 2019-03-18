package decofo.tests;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.services.ElementManager;
import decofo.services.ModelManager;

public class TestModelAndElementLoader {

	static EJBContainer container;
	static ModelManager   mm;
	static ElementManager em;

	@BeforeClass
	public static void beforeAll() throws NamingException {
		container = EJBContainer.createEJBContainer();
		mm = (ModelManager) container.getContext().lookup("java:global/Decofo/ModelManager");
		em = (ElementManager) container.getContext().lookup("java:global/Decofo/ElementManager");
	}

	@AfterClass
	public static void afterAll() {
		container.close();
	}

	@Test
	public void testInject() {
		Assert.assertNotNull(mm);
		Assert.assertNotNull(em);
	}
	
	@Test
	public void testLoaderModelAndElement() {
		List<Model> listModels = mm.findAllModel();
		assertNotNull(listModels);
		for (Model model : listModels) {
			System.err.println("Maquettes : " + model.getCode());
		}
		
		Element parcours = em.findElement("P100");
		assertNotNull(parcours);
		System.err.println(parcours.getCode());
		
		Element formation = em.findChildren("P100").get(0);
		assertNotNull(formation);
		System.err.println(formation.getCode());
		
		Element semestre = em.findChildren("P100").get(0);
		assertNotNull(semestre);
		System.err.println(semestre.getCode());	
	}

}
