package decofo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

@Entity
public class Nature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    private String code;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String nodeType;

    @JoinTable(name = "nature_child", joinColumns = {
	    @JoinColumn(name = "father", referencedColumnName = "code") }, inverseJoinColumns = {
		    @JoinColumn(name = "child", referencedColumnName = "code") })
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Nature> children;

    @ManyToMany(mappedBy = "children", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Nature> fathers;

    @Version()
    private long version = 0;

    public Nature() {
	super();
	this.children = new ArrayList<Nature>();
	this.fathers = new ArrayList<Nature>();
    }

    public Nature(String code, String name) {
	super();
	this.code = code;
	this.name = name;
	this.children = new ArrayList<Nature>();
	this.fathers = new ArrayList<Nature>();
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

    public String getNodeType() {
	return nodeType;
    }

    public void setNodeType(String nodeType) {
	this.nodeType = nodeType;
    }

    public List<Nature> getChildren() {
	return children;
    }

    public void setChildren(List<Nature> children) {
	this.children = children;
    }

    public List<Nature> getFathers() {
	return fathers;
    }

    public void setFathers(List<Nature> fathers) {
	this.fathers = fathers;
    }

    public long getVersion() {
	return version;
    }

    public void setVersion(long version) {
	this.version = version;
    }

    @Override
    public String toString() {
	return "Nature [Code = " + code + ", Name = " + name + ", NodeType = " + nodeType + ", Version = " + version
		+ "]";
    }
}
