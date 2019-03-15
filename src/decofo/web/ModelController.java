package decofo.web;

import java.util.*;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Person;
import decofo.services.ModelManager;
/**
 * 
 * @author mahdi hassan djilal
 * ManagedBean of Model
 */
@ManagedBean
@SessionScoped
public class ModelController {

	private static final int ArrayList = 0;
	/**
	 * the attributs of model controller
	 * modelmanager objec of ModleManager is an statless EJB
	 * themodel is an object of Model and the list of responsible of this model and it elements
	 */
	@EJB
	private ModelManager modelmanager;
	private Model theModel;
	private List<Person> responsibles = null;
	private List<Element> elements = null;
	
	public ModelController() {
		responsibles = new ArrayList<Person>();
		elements = new ArrayList<Element>();
	}
	
	/*
	 * the methods of model controller
	 */
	/**
	 *redirection to display the models
	 * @return theModel
	 */
	public String newModel() {
		theModel = new Model();
		return "newModel";
	}
	/**
	 * Saving a new model
	 * @return
	 */
	public String saveModel() {
		theModel.setElements(elements);
		theModel.setResponsibles(responsibles);
		modelmanager.createModel(theModel);
		return "listModels";
	}
	/**
	 * to return the list of model stored in the database
	 * @return List<Model>
	 */
	public List<Model> findAllModels(){
		return modelmanager.findAllModel();
	}
	/**
	 * to show a model that it code is passed on argument
	 * @param code String code of Model
	 * @return String name of the web page that we want to reduct to user
	 */
	public String showModel(String code) {
		theModel = modelmanager.findModel(code);
		return "detailModel";
	}
	/**
	 * to destroy a model
	 * @param codeModel String code of model that we want to destroy it
	 * @return String name of the web page that we want to reduct to user
	 */
	public String removeModel(String codeModel) {
		theModel = modelmanager.findModel(codeModel);
		modelmanager.deleteModel(theModel);
		return "listeModels";
	}
	/**
	 * to edit a model
	 * @param model object of Model that we want to edit
	 * @return String name of the web page that we want to reduct to user
	 */
	public String editModel(Model model) {
		theModel = modelmanager.updateModel(model);
		return"listModels";
	}

	public Model getTheModel() {
		return theModel;
	}

	public void setTheModel(Model theModel) {
		this.theModel = theModel;
	}

	public List<Person> getResponsibles() {
		return responsibles;
	}

	public void setResponsibles(List<Person> responsibles) {
		this.responsibles = responsibles;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	
}
