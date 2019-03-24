package decofo.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.PrimeFaces;

import decofo.entities.Element;
import decofo.entities.Nature;
import decofo.entities.Person;
import decofo.entities.Site;
import decofo.services.ElementManager;
import decofo.services.NatureManager;
import decofo.services.SiteManager;

@ManagedBean
@SessionScoped
public class ElementController {

    private Element newElement;
    private Element theElement;
    private String selectedNature;
    private List<String> codesFathers;
    private List<String> codesChildren;
    private List<SiteField> siteFields;

    @ManagedProperty(value = "#{modelView}")
    private ModelController modelController;

    @EJB
    private ElementManager elementManager;

    @EJB
    private NatureManager natureManager;

    @EJB
    private SiteManager siteManager;

    @PostConstruct
    public void init() {
	selectedNature = "";
	newElement = new Element();
	theElement = new Element();
	codesFathers = new ArrayList<String>();
	codesChildren = new ArrayList<String>();
	siteFields = new ArrayList<SiteField>();
	siteFields.add(new SiteField());
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

    public List<Nature> findAllNature() {
	return natureManager.findAllNatureWithoutRoot();
    }

    public List<Element> findPotentialFathers() {
	return elementManager.findPotentialFathers(modelController.getTheModel(), selectedNature);
    }

    public List<Element> findPotentialChildren() {
	return elementManager.findPotentialChildren(modelController.getTheModel(), selectedNature);
    }

    public void createElement(Person user) {
	newElement.setModel(modelController.getTheModel());
	newElement.setNature(natureManager.findNature(selectedNature));
	if (selectedNature.equals("AN")) {
	    for (SiteField field : siteFields) {
		System.out.println("Sites : " + field.getSiteCode() + " " + field.getEffectif());
		newElement.getSites().put(siteManager.findSite(field.getSiteCode()), field.getEffectif());
	    }
	}

	for (String father : codesFathers) {
	    newElement.getFathers().add(elementManager.findElementWithChildren(father));
	}
	for (String child : codesChildren) {
	    newElement.getChildren().add(elementManager.findElement(child));
	}
	elementManager.createElement(newElement, user);

	for (Element father : newElement.getFathers()) {
	    father.getChildren().add(newElement);
	    elementManager.updateElement(father, user);
	}

	newElement = new Element();

	PrimeFaces.current().executeScript("PF('addElementDialog').hide();");
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

    public List<String> getCodesFathers() {
	return codesFathers;
    }

    public void setCodesFathers(List<String> codesFathers) {
	this.codesFathers = codesFathers;
    }

    public List<String> getCodesChildren() {
	return codesChildren;
    }

    public void setCodesChildren(List<String> codesChildren) {
	this.codesChildren = codesChildren;
    }

    public boolean isSelectedNatureEqualUE() {
	return selectedNature.equals("UE");
    }

    public boolean isSelectedNatureEqualAnnee() {
	return selectedNature.equals("AN");
    }

    public List<SiteField> getSiteFields() {
	return siteFields;
    }

    public void setSiteFields(List<SiteField> siteFields) {
	this.siteFields = siteFields;
    }

    public List<Site> findAllSites() {
	return siteManager.findAllSite();
    }

    public void onButtonRemoveFieldClick(SiteField siteField) {
	siteFields.remove(siteField);
    }

    public void onButtonAddFieldClick(AjaxBehaviorEvent event) {
	siteFields.add(new SiteField());
    }
}
