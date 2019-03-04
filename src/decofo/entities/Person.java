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
public class Person implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic(optional = false)
	private String name;

	@Basic(optional = false)
	private String status;

	@Basic(optional = false)
	private String email;

	@Basic(optional = false)
	private boolean admin;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "Model", joinColumns = { @JoinColumn(name = "id") },
			inverseJoinColumns = { @JoinColumn(name = "code") })
	private List<Model> models;
	
	@Version()
	private long version = 0;

	@Transient
	public static long updateCounter = 0;

	@PreUpdate
	public void beforeUpdate()
	{
		System.err.println("PreUpdate of " + this);
	}

	@PostUpdate
	public void afterUpdate()
	{
		System.err.println("PostUpdate of " + this);
		updateCounter++;
	}

	public Person()
	{
		this.models = new ArrayList<Model>();
	}

	public Person(String name, String status, String email, boolean admin)
	{
		this.name = name;
		this.status = status;
		this.email = email;
		this.admin = admin;
		this.models = new ArrayList<Model>();
	}

	public Person(String name, String status, String email, boolean admin, List<Model> models)
	{
		this.name = name;
		this.status = status;
		this.email = email;
		this.admin = admin;
		this.models = models;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isAdmin()
	{
		return admin;
	}

	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}

	public List<Model> getModels()
	{
		return models;
	}

	public void setModels(List<Model> models)
	{
		this.models = models;
	}
	
	public long getVersion()
	{
		return version;
	}

	public void setVersion(long version)
	{
		this.version = version;
	}
	
	@Override
	public String toString()
	{
		return "id = " + id + " name = " + name + " status = " + status + " email = " + email;
	}

}
