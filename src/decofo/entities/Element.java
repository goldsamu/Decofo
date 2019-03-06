package decofo.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;


@Entity
public class Element {

	@Id
	private String code;

	@Basic(optional = false)
	@Column(nullable = false)
	private Nature nature;

	@Basic(optional = false)
	@Column(nullable = false)
	private String name;

	private Float credits;
	private Float hoursLM;
	private Float hoursTC;
	private Float hoursPW;
	private Integer thresholdLM;
	private Integer thresholdTC;
	private Integer thresholdPW;

	@ElementCollection
	@CollectionTable(name = "sites_effectifs")
	@MapKeyColumn(name = "SITE_ID")
	private Map<Site, Integer> sites;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinTable(name = "father-child", joinColumns = { @JoinColumn(name = "code") }, inverseJoinColumns = {
			@JoinColumn(name = "code") })
	private List<Element> children;

	@ManyToMany(mappedBy = "children", fetch = FetchType.LAZY)
	private List<Element> fathers;

	public Element() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getCredits() {
		return credits;
	}

	public void setCredits(Float credits) {
		this.credits = credits;
	}

	public Float getHoursLM() {
		return hoursLM;
	}

	public void setHoursLM(Float hoursLM) {
		this.hoursLM = hoursLM;
	}

	public Float getHoursTC() {
		return hoursTC;
	}

	public void setHoursTC(Float hoursTC) {
		this.hoursTC = hoursTC;
	}

	public Float getHoursPW() {
		return hoursPW;
	}

	public void setHoursPW(Float hoursPW) {
		this.hoursPW = hoursPW;
	}

	public Integer getThresholdLM() {
		return thresholdLM;
	}

	public void setThresholdLM(Integer thresholdLM) {
		this.thresholdLM = thresholdLM;
	}

	public Integer getThresholdTC() {
		return thresholdTC;
	}

	public void setThresholdTC(Integer thresholdTC) {
		this.thresholdTC = thresholdTC;
	}

	public Integer getThresholdPW() {
		return thresholdPW;
	}

	public void setThresholdPW(Integer thresholdPW) {
		this.thresholdPW = thresholdPW;
	}

	public Map<Site, Integer> getSites() {
		return sites;
	}

	public void setSites(Map<Site, Integer> sites) {
		this.sites = sites;
	}

	public List<Element> getChildren() {
		return children;
	}

	public void setChildren(List<Element> children) {
		this.children = children;
	}

	public List<Element> getFathers() {
		return fathers;
	}

	public void setFathers(List<Element> fathers) {
		this.fathers = fathers;
	}

}
