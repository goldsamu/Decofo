package decofo.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

	/**
	 * the attributs of model controller
	 * modelmanager objec of ModleManager is an statless EJB
	 * themodel is an object of Model
	 */
	@EJB
	private ModelManager modelmanager;
	private Model theModel;
	
	@PostConstruct
	public void init()
	{
		System.out.println("Create " + this);
	}
	/*
	 * the methods of model controller
	 */
	/**
	 * after creation of a new model, this return us the new model
	 * @return theModel
	 */
	public String newModel(Person user) {
		modelmanager.createModel(theModel, user);
		theModel = new Model();
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
	public String removeModel(String codeModel, Person user) {
		theModel = modelmanager.findModel(codeModel);
		modelmanager.deleteModel(theModel, user);
		return "listeModels";
	}
	/**
	 * to edit a model
	 * @param model object of Model that we want to edit
	 * @return String name of the web page that we want to reduct to user
	 */
	public String editModel(Model model, Person user) {
		theModel = modelmanager.updateModel(model, user);
		return"listModels";
	}
	

	
	
	
}
