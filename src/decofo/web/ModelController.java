package decofo.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;

import decofo.entities.Model;
import decofo.entities.Person;
import decofo.services.ModelManager;
import decofo.services.PersonManager;

@ManagedBean(name = "modelView")
@SessionScoped
public class ModelController {

    /**
     * the attributs of model controller modelmanager objec of ModleManager is an
     * statless EJB themodel is an object of Model
     */
    @EJB
    private ModelManager modelmanager;

    @EJB
    private PersonManager pm;

    private Model newModel;
    private Model theModel;
    private List<String> responsibles;
    private List<String> responsiblesEdit;
    private boolean myModels;

    @PostConstruct
    public void init() {
	newModel = new Model();
	theModel = new Model();
	responsibles = new ArrayList<String>();
	responsiblesEdit = new ArrayList<String>();
	System.out.println("Create " + this);
    }

    public List<Model> getModels() {
	return modelmanager.findAllModel();
    }

    public Model getNewModel() {
	return newModel;
    }

    public void setNewModel(Model newModel) {
	this.newModel = newModel;
    }

    public Model getTheModel() {
	return theModel;
    }

    public void setTheModel(Model theModel) {
	this.theModel = theModel;
    }

    /**
     * after creation of a new model, this return us the new model
     * 
     * @return theModel
     */
    public void createModel(Person user) {
	for (String r : responsibles) {
	    Person p = pm.findPersonByLogin(r);
	    newModel.getResponsibles().add(p);
	}
	modelmanager.createModel(newModel, user);
	newModel = new Model();
	responsibles = new ArrayList<String>();
	PrimeFaces.current().executeScript("PF('addModelDialog').hide();");
    }

    /**
     * to show a model that it code is passed on argument
     * 
     * @param code String code of Model
     * @return String name of the web page that we want to reduct to user
     */
    public String showModel(String code) {
	theModel = modelmanager.findModel(code);
	return "visu_maquette";
    }

    /**
     * to destroy a model
     * 
     * @param codeModel String code of model that we want to destroy it
     */
    public void removeModel(String codeModel, Person user) {
	Model m = modelmanager.findModel(codeModel);
	modelmanager.deleteModel(m, user);
    }

    /**
     * to edit a model
     */
    public void editModel(Person user) {
	theModel.setResponsibles(new ArrayList<Person>());
	for (String r : responsiblesEdit) {
	    Person p = pm.findPersonByLogin(r);
	    theModel.getResponsibles().add(p);
	}
	modelmanager.updateModel(theModel, user);
	PrimeFaces.current().executeScript("PF('editModelDialog').hide();");
    }

    public void initEdit(Model model) {
	responsiblesEdit = new ArrayList<String>();
	theModel = model;
	for (Person p : modelmanager.findResponsibles(model.getCode())) {
	    responsiblesEdit.add(p.getLogin());
	}
    }

    public List<String> getResponsibles() {
	return responsibles;
    }

    public void setResponsibles(List<String> responsibles) {
	this.responsibles = responsibles;
    }

    public List<String> getResponsiblesEdit() {
	return responsiblesEdit;
    }

    public void setResponsiblesEdit(List<String> responsiblesEdit) {
	this.responsiblesEdit = responsiblesEdit;
    }

    public boolean isMyModels() {
	return myModels;
    }

    public void setMyModels(boolean myModels) {
	this.myModels = myModels;
    }

    public List<Person> findResponsibles(String code) {
	return modelmanager.findResponsibles(code);
    }
}
