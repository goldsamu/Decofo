package utilities;

public class CodesFatherChild {
	private String codeFather;
	private String codeChild;
	
	public String getCodeFather() {
		return codeFather;
	}

	public void setCodeFather(String codeFather) {
		this.codeFather = codeFather;
	}

	public String getCodeChild() {
		return codeChild;
	}

	public void setCodeChild(String codeChild) {
		this.codeChild = codeChild;
	}

	public CodesFatherChild(String codeFather, String codeChild) {
		super();
		this.codeFather = codeFather;
		this.codeChild = codeChild;
	}

	@Override
	public String toString() {
		return "CodesFatherChild [codeFather=" + codeFather + ", codeChild=" + codeChild + "]";
	}

}
