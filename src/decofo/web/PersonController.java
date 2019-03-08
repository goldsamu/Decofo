package decofo.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.jasig.cas.client.authentication.AttributePrincipal;

import decofo.services.PersonManager;

@ManagedBean
@SessionScoped
public class PersonController {

    @EJB
    private PersonManager personManager;

    public void connect() {

	AttributePrincipal principal = (AttributePrincipal) FacesContext.getCurrentInstance().getExternalContext()
		.getUserPrincipal();
	final Map attributes = principal.getAttributes();
	if (attributes != null) {
	    Iterator attributeNames = attributes.keySet().iterator();
	    if (attributeNames.hasNext()) {
		for (; attributeNames.hasNext();) {
		    String attributeName = (String) attributeNames.next();
		    System.out.println(attributeName);
		    final Object attributeValue = attributes.get(attributeName);
		    if (attributeValue instanceof List) {
			final List values = (List) attributeValue;
			System.out.println("<strong>Multi-valued attribute: " + values.size() + "</strong>");
			for (Object value : values) {
			    System.out.println("<li>" + value + "</li>");
			}
		    } else {
			System.out.println(attributeValue);
		    }
		}
	    } else {
		System.out.print("No attributes are supplied by the CAS server.</p>");
	    }
	} else {
	    System.out.println("<pre>The attribute map is empty. Review your CAS filter configurations.</pre>");
	}
    }

    public void logout() throws IOException {
	personManager.logout();
	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	FacesContext.getCurrentInstance().getExternalContext().redirect("https://ident.univ-amu.fr/cas/logout?service=http%3A%2F%2Flocalhost%3A8080%2FDecofo%2Fhome.xhtml");
    }

    public boolean isConnected() {
	return personManager.getUser().getLogin() != null;
    }
}
