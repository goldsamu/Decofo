package decofo.web;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import decofo.entities.Element;
import decofo.services.ElementManager;

@ManagedBean
@SessionScoped
public class ElementController {

    private Element theElement;
    @EJB
    private ElementManager elementManager;
    
    @PostConstruct
    public void init() {
	theElement = new Element();
	System.out.println("Create " + this);
    }

    public Element getTheElement() {
	return theElement;
    }

    public void setTheElement(Element theElement) {
	this.theElement = theElement;
    }
    
    
}
