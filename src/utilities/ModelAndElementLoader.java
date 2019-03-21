package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import decofo.entities.Element;
import decofo.entities.Model;
import decofo.entities.Nature;
import decofo.entities.Person;
import decofo.services.ElementManager;
import decofo.services.ModelManager;
import decofo.services.NatureManager;
import decofo.services.PersonManager;

@Singleton
@Startup
@DependsOn({ "PersonManager", "PersonLoader", "ModelManager", "ElementManager", "NatureLoader", "SiteLoader" })
public class ModelAndElementLoader {
	@EJB
	private PersonManager pm;
	@EJB
	private ModelManager mm;
	@EJB
	private ElementManager em;
	@EJB
	private NatureManager nm;

	@PostConstruct
	private void lunch() {
		/*List<Model> allModels = mm.findAllModel();
		if (allModels == null)*/
			readXML("/un-exemple-de-fichier.xml");
	}

	public void readXML(String file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(getClass().getResourceAsStream(file));
			DocumentTraversal traversal = (DocumentTraversal) document;

			NodeIterator iterator = traversal.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT,
					null, true);

			List<Nature> naturesList = nm.findAllNature();
			List<String> nameNaturesList = new ArrayList<String>();
			for (Nature nature : naturesList) {
				nameNaturesList.add(nature.getName());
			}

			Person person = pm.findPersonByLogin("g13017841");
			Model model = null;
			for (Node node = iterator.nextNode(); node != null; node = iterator.nextNode()) {
				Model m = model;
				if (node.getNodeName().equals("mention")) {
					model = new Model();
					model.getResponsibles().add(person);
					Element formation = new Element();
					NodeList childList = node.getChildNodes();
					for (int i = 0; i < childList.getLength(); i++) {
						if (!childList.item(i).getTextContent().trim().equals("")) {
							switch (i) {
							case 1:
								formation.setCode(childList.item(i).getTextContent());
								break;
							case 3:
								formation.setName(childList.item(i).getTextContent());
								break;
							case 5:
								formation.setCredits(Float.parseFloat(childList.item(i).getTextContent()));
								break;
							}
						}
					}
					formation.setNature(nm.findNature(NatureManager.ROOTNATURE));
					model.setCode(formation.getCode());
					model.setName(formation.getName());
					formation.setModel(model);
					model.getElements().add(formation);
					model = mm.createModel(model, person);
				}

				if (nameNaturesList.contains(node.getNodeName())) {
					Element element = new Element();
					Nature nature = nm.findNatureByName(node.getNodeName());
					element.setNature(nature);
					element.setModel(m);
					NodeList childList = node.getChildNodes();
					for (int i = 0; i < childList.getLength(); i++) {
						if (!childList.item(i).getTextContent().trim().equals("")) {
							switch (i) {
							case 1:
								element.setCode(childList.item(i).getTextContent());
								break;

							case 3:
								element.setName(childList.item(i).getTextContent());
								break;
							case 5:
								element.setCredits(Float.parseFloat(childList.item(i).getTextContent()));
								break;
							case 7:
								element.setHoursLM(Float.parseFloat(childList.item(i).getTextContent()));
								break;
							case 9:
								element.setHoursTC(Float.parseFloat(childList.item(i).getTextContent()));
								break;
							case 11:
								element.setHoursPW(Float.parseFloat(childList.item(i).getTextContent()));
								break;
							case 13:
								element.setThresholdLM(Integer.parseInt(childList.item(i).getTextContent()));
								break;
							case 15:
								element.setThresholdTC(Integer.parseInt(childList.item(i).getTextContent()));
								break;
							case 17:
								element.setThresholdPW(Integer.parseInt(childList.item(i).getTextContent()));
								break;
							}
						}
					}
					em.createElement(element, person);

				}

				if (node.getNodeName().equals("lien")) {
					String codeFather = "";
					String codeChild = "";
					NodeList childList = node.getChildNodes();
					for (int i = 0; i < childList.getLength(); i++) {
						if (!childList.item(i).getTextContent().trim().equals("")) {
							switch (i) {
							case 1:
								codeFather = childList.item(i).getTextContent();
								break;
							case 3:
								codeChild = childList.item(i).getTextContent();
								break;
							}
						}
					}
					Element fatherElement = em.findElement(codeFather);
					Element childElement = em.findElement(codeChild);

					fatherElement.getChildren().add(childElement);
					em.updateElement(fatherElement, person);

				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
