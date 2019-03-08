package decofo.tests;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
}
