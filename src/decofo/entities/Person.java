package decofo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The login ID of the user.
     */
    @Id()
    @Column(name = "login", length = 25, nullable = false)
    private String login;

    /**
     * The full name of the user.
     */
    @Basic(optional = false)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * The status inside AMU (student, teacher...)
     */
    @Basic(optional = false)
    @Column(name = "status", length = 25, nullable = false)
    private String status;

    /**
     * Email of the user.
     */
    @Basic(optional = false)
    @Column(name = "email", length = 75, nullable = false)
    private String email;

    /**
     * If the user is an admin or not.
     */
    @Basic(optional = false)
    private boolean admin;

    /**
     * The models for which he is responsible.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "Responsibles_Models", joinColumns = { @JoinColumn(name = "login") }, inverseJoinColumns = {
	    @JoinColumn(name = "code") })
    private List<Model> models;

    @Version()
    private long version = 0;

    public Person() {
	this.models = new ArrayList<Model>();
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

    public List<Model> getModels() {
	return models;
    }

    public void setModels(List<Model> models) {
	this.models = models;
    }

    public long getVersion() {
	return version;
    }

    public void setVersion(long version) {
	this.version = version;
    }

    @Override
    public String toString() {
	return login;
    }

}
