package decofo.entities;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

/**
 * 
 * @author mahdi hassan djilal
 */
@Entity
public class Model implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * the Model entity attributs
	 */
	@Id
	@Basic(optional = false)
	private String code;

	@Basic(optional = false)
	private String name;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	private List<Person> responsibles;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Element> elements;

	//the Model entity contructors

	public Model() {
		super();
		this.responsibles = new ArrayList<Person>();
		this.elements = new ArrayList<Element>();
	}

	/**
	 * constructor with two parameters 
	 * @param code Model code
	 * @param nom  Model Name
	 */
	public Model(String code, String nom) {
		super();
		this.code = code;
		this.name = nom;
		this.responsibles = new ArrayList<Person>();
		this.elements = new ArrayList<Element>();
	}
	
	//Getters and setters
	/**
	 * to have Model code
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * to set Model code
	 * @param code 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * return name of Model
	 * @return Nom
	 */
	public String getName() {
		return name;
	}

	/**
	 * to set name of Model
	 * @param nom
	 */
	public void setName(String nom) {
		this.name = nom;
	}

	/**
	 * to have the list of Model responsible
	 * @return responsibles
	 */
	public List<Person> getResponsibles() {
		return responsibles;
	}

	/**
	 * to set the list of person who edited the Model
	 * @param responsables
	 */
	public void setResponsibles(List<Person> responsables) {
		this.responsibles = responsables;
	}

	/**
	 * to return the list of elements that the model contains
	 * @return elements
	 */
	public List<Element> getElements() {
		return elements;
	}

	/**
	 * to set the list of elements
	 * @param elements
	 */
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	// the toString method to have a full descrition of Model entity
	@Override
	public String toString() {
		return "Model [Code=" + code + ", Name=" + name + ", responsibles=" + responsibles + ", Elements=" + elements
				+ "]";
	}
}
