package decofo.web;

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

/**
 * 
 * @author mahdi hassan djilal ManagedBean of Model
 */
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
    
    private Model theModel;
    private String responsible;
    private List<Model> models;

    @PostConstruct
    public void init() {
	theModel = new Model();
	this.models = modelmanager.findAllModel();
	System.out.println("Create " + this);
    }

    public List<Model> getModels() {
	return models;
    }

    public void setModels(List<Model> models) {
	this.models = models;
    }

    public ModelManager getModelmanager() {
	return modelmanager;
    }

    public void setModelmanager(ModelManager modelmanager) {
	this.modelmanager = modelmanager;
    }

    public Model getTheModel() {
	return theModel;
    }

    public void setTheModel(Model theModel) {
	this.theModel = theModel;
    }

    /*
     * the methods of model controller
     */
    /**
     * after creation of a new model, this return us the new model
     * 
     * @return theModel
     */
    public void newModel(Person user) {
	System.out.println(theModel);
	Person p = pm.findPersonByLogin(responsible);
	theModel.getResponsibles().add(p);
	modelmanager.createModel(theModel, user);
	models = modelmanager.findAllModel();
	theModel = new Model();
	PrimeFaces.current().executeScript("PF('addMaquetteDialog').hide();");
	
    }

    /**
     * to show a model that it code is passed on argument
     * 
     * @param code String code of Model
     * @return String name of the web page that we want to reduct to user
     */
    public String showModel(String code) {
	theModel = modelmanager.findModel(code);
	return "detailModel";
    }

    /**
     * to destroy a model
     * 
     * @param codeModel String code of model that we want to destroy it
     * @return String name of the web page that we want to reduct to user
     */
    public String removeModel(String codeModel, Person user) {
	theModel = modelmanager.findModel(codeModel);
	modelmanager.deleteModel(theModel, user);
	return "listModels";
    }

    /**
     * to edit a model
     * 
     * @param model object of Model that we want to edit
     * @return String name of the web page that we want to reduct to user
     */
    public String editModel(Model model, Person user) {
	theModel = modelmanager.updateModel(model, user);
	return "listModels";
    }

    public String getResponsible() {
	return responsible;
    }

    public void setResponsible(String responsible) {
	this.responsible = responsible;
    }
}
