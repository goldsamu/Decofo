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
    private List<String> codesFathersEdit;
    private List<String> codesChildrenEdit;
    private List<SiteField> siteFields;
    private List<SiteField> siteFieldsEdit;

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
	codesFathersEdit = new ArrayList<String>();
	codesChildrenEdit = new ArrayList<String>();
	siteFields = new ArrayList<SiteField>();
	siteFields.add(new SiteField());
	siteFieldsEdit = new ArrayList<SiteField>();
	siteFieldsEdit.add(new SiteField());
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

    public List<Element> findPotentialFathers(String codeNature) {
	return elementManager.findPotentialFathers(modelController.getTheModel(), codeNature);
    }

    public List<Element> findPotentialChildren(String codeNature) {
	return elementManager.findPotentialChildren(modelController.getTheModel(), codeNature);
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
	selectedNature = "";
	codesFathers = new ArrayList<String>();
	codesChildren = new ArrayList<String>();
	siteFields = new ArrayList<SiteField>();
	siteFields.add(new SiteField());
	PrimeFaces.current().executeScript("PF('addElementDialog').hide();");
    }

    public void editElement(Person user) {
	System.out.println("theElement : " + theElement);
	if (theElement.getNature().getCode().equals("AN")) {
	    for (SiteField field : siteFieldsEdit) {
		theElement.getSites().put(siteManager.findSite(field.getSiteCode()), field.getEffectif());
	    }
	}

	theElement.setFathers(new ArrayList<Element>());
	theElement.setChildren(new ArrayList<Element>());
	for (String father : codesFathersEdit) {
	    theElement.getFathers().add(elementManager.findElementWithChildren(father));
	}
	for (String child : codesChildrenEdit) {
	    theElement.getChildren().add(elementManager.findElement(child));
	}

	elementManager.updateElement(theElement, user);

	for (Element father : theElement.getFathers()) {
	    father.getChildren().add(theElement);
	    elementManager.updateElement(father, user);
	}

	PrimeFaces.current().executeScript("PF('editElementDialog').hide();");
    }

    public void initEdit() {
	codesFathersEdit = new ArrayList<String>();
	codesChildrenEdit = new ArrayList<String>();

	for (Element e : elementManager.findFathers(theElement.getCode())) {
	    codesFathersEdit.add(e.getCode());
	}
	for (Element e : elementManager.findChildren(theElement.getCode())) {
	    codesChildrenEdit.add(e.getCode());
	}
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

    public List<String> getCodesFathersEdit() {
	return codesFathersEdit;
    }

    public void setCodesFathersEdit(List<String> codesFathersEdit) {
	this.codesFathersEdit = codesFathersEdit;
    }

    public List<String> getCodesChildrenEdit() {
	return codesChildrenEdit;
    }

    public void setCodesChildrenEdit(List<String> codesChildrenEdit) {
	this.codesChildrenEdit = codesChildrenEdit;
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

    public List<SiteField> getSiteFieldsEdit() {
	return siteFieldsEdit;
    }

    public void setSiteFieldsEdit(List<SiteField> siteFieldsEdit) {
	this.siteFieldsEdit = siteFieldsEdit;
    }

    public List<Site> findAllSites() {
	return siteManager.findAllSite();
    }

    public void onButtonRemoveFieldClick(SiteField siteField, List<SiteField> fields) {
	fields.remove(siteField);
    }

    public void onButtonAddFieldClick(List<SiteField> fields) {
	fields.add(new SiteField());
    }

    public boolean isResponsible(Person user) {
	return elementManager.isResponsible(modelController.getTheModel(), user);
    }

    public void removeElement(Person user) {
	if (theElement != null) {
	    elementManager.removeElement(theElement, user);
	}
    }
}
