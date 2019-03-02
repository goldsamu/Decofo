package decofo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Basic(optional=false)
	private String name;

	@Basic(optional=false)
	private String status;

	@Basic(optional=false)
	private String email;

	@Basic(optional=false)
	private boolean admin;

	@Basic(optional=false)
	private List<Model> models;

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

}
