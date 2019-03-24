package decofo.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.jasig.cas.client.authentication.AttributePrincipal;

import decofo.entities.Person;
import decofo.services.PersonManager;

@ManagedBean
@SessionScoped
public class PersonController {

    @EJB
    private PersonManager personManager;

    @PostConstruct
    public void init() {
	System.out.println("Create " + this);
    }

    /**
     * Collect the data about the user from the CAS and give them to the manager.
     */
    public void connect() {
	if (!isConnected()) {
	    AttributePrincipal principal = (AttributePrincipal) FacesContext.getCurrentInstance().getExternalContext()
		    .getUserPrincipal();
	    final Map<String, Object> attributes = principal.getAttributes();
	    if (attributes != null) {
		Person user = new Person();
		user.setLogin(attributes.get("uid").toString());
		user.setStatus(attributes.get("eduPersonPrimaryAffiliation").toString());
		user.setEmail(attributes.get("mail").toString());
		user.setName(attributes.get("displayName").toString());
		personManager.check(user);

		System.out.println("Connexion : " + personManager.getUser());
	    } else {
		System.out.println("<pre>The attribute map is empty. Review your CAS filter configurations.</pre>");
	    }
	}
    }

    /**
     * Invalidate the session and redirect to the home page.
     * 
     * @throws IOException
     */
    public void logout() throws IOException {
	personManager.logout();
	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	FacesContext.getCurrentInstance().getExternalContext().redirect(
		"https://ident.univ-amu.fr/cas/logout?service=http%3A%2F%2Flocalhost%3A8080%2FDecofo%2Fhome.xhtml");
    }

    public Map<String, Person> findAllPersons() {
	Map<String, Person> map = new HashMap<String, Person>();
	for(Person p : personManager.findAllPersons())
	{
	    map.put(p.getName(), p);
	}
	return map;
    }

    public boolean isConnected() {
	return personManager.getUser().getLogin() != null;
    }

    public boolean isAdmin() {
	return personManager.getUser().isAdmin();
    }

    public Person getUser() {
	return personManager.getUser();
    }
}
