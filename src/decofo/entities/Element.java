package decofo.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Element {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String code;

	@Basic(optional = false)
	@Column(nullable = false)
	private String nature;

	@Basic(optional = false)
	@Column(nullable = false)
	private String name;

	private float credits;
	private float hoursLM;
	private float hoursTC;
	private float hoursPW;

	@Basic(optional = false)
	@Column(nullable = false)
	private int thresholdLM;

	@Basic(optional = false)
	@Column(nullable = false)
	private int thresholdTC;

	@Basic(optional = false)
	@Column(nullable = false)
	private int thresholdPW;

	private Map<Site, Integer> sites;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinTable(name = "father-child", joinColumns = { @JoinColumn(name = "code") },
	inverseJoinColumns = { @JoinColumn(name = "code") })
	private List<Element> children;

	@ManyToMany(mappedBy = "children", fetch = FetchType.LAZY)
	private List<Element> fathers;
	

	public Element() {
		super();
	}

	public Element(String nature, String name, float hoursLM, float hoursTC, float hoursPW, int thresholdLM,
			int thresholdTC, int thresholdPW, float credits, Map<Site, Integer> sites, List<Element> fathers,
			List<Element> children) {
		super();
		this.nature = nature;
		this.name = name;
		this.hoursLM = hoursLM;
		this.hoursTC = hoursTC;
		this.hoursPW = hoursPW;
		this.thresholdLM = thresholdLM;
		this.thresholdTC = thresholdTC;
		this.thresholdPW = thresholdPW;
		this.credits = credits;
		this.sites = sites;
		this.fathers = fathers;
		this.children = children;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getHoursLM() {
		return hoursLM;
	}

	public void setHoursLM(float hoursLM) {
		this.hoursLM = hoursLM;
	}

	public float getHoursTC() {
		return hoursTC;
	}

	public void setHoursTC(float hoursTC) {
		this.hoursTC = hoursTC;
	}

	public float getHoursPW() {
		return hoursPW;
	}

	public void setHoursPW(float hoursPW) {
		this.hoursPW = hoursPW;
	}

	public int getThresholdLM() {
		return thresholdLM;
	}

	public void setThresholdLM(int thresholdLM) {
		this.thresholdLM = thresholdLM;
	}

	public int getThresholdTC() {
		return thresholdTC;
	}

	public void setThresholdTC(int thresholdTC) {
		this.thresholdTC = thresholdTC;
	}

	public int getThresholdPW() {
		return thresholdPW;
	}

	public void setThresholdPW(int thresholdPW) {
		this.thresholdPW = thresholdPW;
	}

	public float getCredits() {
		return credits;
	}

	public void setCredits(float credits) {
		this.credits = credits;
	}

	public Map<Site, Integer> getSites() {
		return sites;
	}

	public void setSites(Map<Site, Integer> sites) {
		this.sites = sites;
	}

	public List<Element> getFathers() {
		return fathers;
	}

	public void setFathers(List<Element> fathers) {
		this.fathers = fathers;
	}

	public List<Element> getChildren() {
		return children;
	}

	public void setChildren(List<Element> children) {
		this.children = children;
	}

}
