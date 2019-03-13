package utilities;

import java.io.File;
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

import org.apache.webbeans.util.SpecializationUtil;
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
import decofo.services.ElementManager;
import decofo.services.ModelManager;
import decofo.services.NatureManager;

@Singleton
@Startup
@DependsOn({ "ModelManager", "ElementManager", "NatureLoader", "SiteLoader" })
public class ModelAndElementLoader {

	@EJB
	private ModelManager mm;
	@EJB
	private ElementManager em;
	@EJB
	private NatureManager nm;

	@PostConstruct
	private void lunch() {
		readXML("un-exemple-de-fichier.xml");
	}

	public void readXML(String file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(file));
			DocumentTraversal traversal = (DocumentTraversal) document;

			NodeIterator iterator = traversal.createNodeIterator(document.getDocumentElement(), NodeFilter.SHOW_ELEMENT,
					null, true);
			
			List<Nature> naturesList = nm.findAllNature();
			List<String> nameNaturesList = new ArrayList<String>();
			for (Nature nature : naturesList) {
				nameNaturesList.add(nature.getName());
			}
			/*for (String name : nameNaturesList) {
				System.err.println(name);
			}*/
			for (Node node = iterator.nextNode(); node != null; node = iterator.nextNode()) {

				if (node.getNodeName().equals("mention")) {
					
					Model model = new Model();
					model.setCode(generate(5));
					model.setName("NameMaquette");
					model = mm.createModel(model);
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
					// System.out.println(model.toString()); // Pour tester si on lit les données
					// depuis le fichier .xml
					model.getElements().set(0, formation);
					mm.updateModel(model);
				}
				
				if(nameNaturesList.contains(node.getNodeName())){
					//System.err.println("-------------------------------------------------------------------->");
					Element element = new Element();
					element.setCode(generate(10));
		            Nature nature = nm.findNatureByName(node.getNodeName());
		            element.setNature(nature);
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
		            //System.out.println(element.getName());
		            em.createElement(element);
		            
				}
				
				if(node.getNodeName().equals("lien")) {
					
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
	
	
	public String generate(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; 
																							
		String pass = "";
		for (int x = 0; x < length; x++) {
			int i = (int) Math.floor(Math.random() * 62); 
			pass += chars.charAt(i);
		}
		//System.err.println(pass);
		return pass;
	}

}
