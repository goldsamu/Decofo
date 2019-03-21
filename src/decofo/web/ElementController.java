package decofo.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import decofo.entities.Element;
import decofo.entities.Nature;
import decofo.services.ElementManager;
import decofo.services.NatureManager;

@ManagedBean
@SessionScoped
public class ElementController {

    private Element newElement;
    private Element theElement;
    private String selectedNature;
    
    @ManagedProperty(value = "#{modelView}")
    private ModelController modelController;
    
    @EJB
    private ElementManager elementManager;
    
    @EJB
    private NatureManager natureManager;
    
    @PostConstruct
    public void init() {
	newElement = new Element();
	theElement = new Element();
	System.out.println("Create " + this);
    }

    public Element getTheElement() {
	return theElement;
    }

    public void setTheElement(Element theElement) {
	this.theElement = theElement;
    }
    
    public ModelController getModelController() {
        return modelController;
    }

    public void setModelController(ModelController modelController) {
        this.modelController = modelController;
    }
    
    public List<Nature> findAllNature()
    {
	return natureManager.findAllNatureWithoutRoot();
    }
    
    public List<Element> findPotentialFathers()
    {
	return elementManager.findPotentialFathers(modelController.getTheModel(), selectedNature);
    }
    
    public Element createElement()
    {
	return null;
    }
    
    public Element getNewElement() {
	return newElement;
    }

    public void setNewElement(Element newElement) {
	this.newElement = newElement;
    }

    public String getSelectedNature() {
	return selectedNature;
    }

    public void setSelectedNature(String selectedNature) {
	this.selectedNature = selectedNature;
    }
}
