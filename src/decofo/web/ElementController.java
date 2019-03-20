package decofo.web;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import decofo.services.ElementManager;


@ManagedBean
@SessionScoped
public class ElementController {
	
	@EJB
    private ElementManager elementManager;

}
