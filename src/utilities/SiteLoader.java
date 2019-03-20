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

import decofo.entities.Site;
import decofo.services.SiteManager;

@Singleton
@Startup
@DependsOn("SiteManager")
public class SiteLoader {

	@EJB
	private SiteManager sm;

	@PostConstruct
	private void lunch() {
		/*List<Site> allSites = sm.findAllSite();
		if (allSites == null)*/
			browserXML("/sites.xml");
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
				if (node.getNodeName().equals("site")) {
					Site site = new Site();
					NodeList childList = node.getChildNodes();
					for (int i = 0; i < childList.getLength(); i++) {
						if (!childList.item(i).getTextContent().trim().equals("")) {
							switch (i) {
							case 1:
								site.setCode(childList.item(i).getTextContent());
								break;

							case 3:
								site.setName(childList.item(i).getTextContent());
								break;
							}
						}
					}
					sm.createSite(site);
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
