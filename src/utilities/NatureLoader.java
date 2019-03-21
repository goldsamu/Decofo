package utilities;

import java.io.IOException;
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
import decofo.entities.Nature;
import decofo.entities.Person;
import decofo.services.NatureManager;

@Singleton
@Startup
@DependsOn("NatureManager")
public class NatureLoader {

	@EJB
	private NatureManager nm;

	@PostConstruct
	private void lunch() {
		/*List<Nature> allNatures = nm.findAllNature();
		if (allNatures == null)*/
			browserXML("/natures.xml");
	}

	public void browserXML(String file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(getClass().getResourceAsStream(file));
			DocumentTraversal traversal = (DocumentTraversal) document;

			NodeIterator iterator = traversal.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT,
					null, true);

			for (Node node = iterator.nextNode(); node != null; node = iterator.nextNode()) {
				if (node.getNodeName().equals("nature")) {
					Nature nature = new Nature();
					NodeList childList = node.getChildNodes();
					for (int i = 0; i < childList.getLength(); i++) {
						if (!childList.item(i).getTextContent().trim().equals("")) {
							switch (i) {
							case 1:
								nature.setCode(childList.item(i).getTextContent());
								break;

							case 3:
								nature.setName(childList.item(i).getTextContent());
								break;
							case 5:
								nature.setNodeType(childList.item(i).getTextContent());
								break;
							}
						}
					}
					nm.createNature(nature);
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
					Nature fatherNature = nm.findNature(codeFather);
					Nature childNature = nm.findNature(codeChild);

					fatherNature.getChildren().add(childNature);
					nm.updateNature(fatherNature);

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
