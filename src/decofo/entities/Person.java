package decofo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    
    private String id;
    
    @Basic(optional = false)
    private String login;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String status;

    @Basic(optional = false)
    private String email;

    @Basic(optional = false)
    private boolean admin;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "Responsibles_Models", joinColumns = { @JoinColumn(name = "id") }, inverseJoinColumns = {
	    @JoinColumn(name = "code") })*/
    //private List<Model> models;

    @Version()
    private long version = 0;

    public Person() {
	//this.models = new ArrayList<Model>();
    }
    
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public boolean isAdmin() {
	return admin;
    }

    public void setAdmin(boolean admin) {
	this.admin = admin;
    }

    /*public List<Model> getModels() {
	return models;
    }

    public void setModels(List<Model> models) {
	this.models = models;
    }*/

    public long getVersion() {
	return version;
    }

    public void setVersion(long version) {
	this.version = version;
    }

    @Override
    public String toString() {
	return "admin : " + admin + " email : " + email + " login : " + login + "name : " + name + "status : " + status + version + id ;
    }

}
