package decofo.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Nature implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id()
	private String code;

	@Basic(optional = false)
	private String name;

	@Version()
	private long version = 0;

	public Nature() {
		super();
	}

	public Nature(String code, String name) {
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

	@Override
	public String toString() {
		return "Nature [Code = " + code + ", Name = " + name + ", Version = " + version + "]";
	}

}
