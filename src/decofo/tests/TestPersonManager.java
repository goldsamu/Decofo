package decofo.tests;

import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import decofo.entities.Person;
import decofo.services.PersonManager;


public class TestPersonManager {
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
    
    @Test(expected = EJBException.class)
    public void testCreatePerson()
    {
	Person p = new Person() ;
	p.setId("CreatePersonId");
	p.setName("CreatePersonNom");
	p.setEmail("CreatePersonMail");
	p.setAdmin(false);
	p.setStatus("CreatePersonStatus");
	p.setLogin("CreatePersonLogin");
	
	pm.createPerson(p);
	pm.check(p);
	
	Assert.assertEquals(pm.getUser().getLogin(), p.getLogin());
	
	pm.createPerson(p);

	pm.removePerson(p);
    }
    
    @Test
    public void testCheck()
    {
	Person p = new Person() ;
	p.setId("CheckId");
	p.setName("CheckNom");
	p.setEmail("CheckMail");
	p.setAdmin(false);
	p.setStatus("CheckStatus");
	p.setLogin("CheckLogin");
	
	pm.check(p);
	
	Assert.assertEquals(pm.getUser().getLogin(), p.getLogin());

	pm.removePerson(p);
    }
    
    
}
