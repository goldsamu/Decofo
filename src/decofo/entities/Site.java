package decofo.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;


public class Site implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id()
	private String code ;
	
	@Basic(optional = false)
	private String name ; 
	
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

	public Site(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	
	
}

