package decofo.web;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import decofo.entities.Model;
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
	/*
	 * the methods of model controller
	 */
	/**
	 * after creation of a new model, this return us the new model
	 * @return theModel
	 */
	public String newModel() {
		modelmanager.createModel(theModel);
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
	

	
	
	
}
