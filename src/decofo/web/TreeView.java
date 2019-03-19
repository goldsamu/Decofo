package decofo.web;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import decofo.entities.Element;
import decofo.services.ElementManager;

@ManagedBean(name = "treeView")
@ViewScoped
public class TreeView {
    @EJB
    private ElementManager em;

    private TreeNode root;
    private TreeNode selectedElement;

    @PostConstruct
    public void init() {
	root = new DefaultTreeNode(new Element(), null);

	TreeNode node = new DefaultTreeNode(em.findElement("M100"), root);
	node.getChildren().add(new DefaultTreeNode(new Element()));
    }

    public TreeNode getRoot() {
	return root;
    }

    public void setRoot(TreeNode root) {
	this.root = root;
    }

    public TreeNode getSelectedElement() {
	return selectedElement;
    }

    public void setSelectedElement(TreeNode selectedElement) {
	this.selectedElement = selectedElement;
    }

    public void onNodeExpand(NodeExpandEvent event) {
	TreeNode node = event.getTreeNode();
	TreeNode childNode;
	Element element = (Element) node.getData();
	if (node.getChildCount() == 1 && ((Element) node.getChildren().get(0).getData()).getCode() == null) {
	    node.getChildren().remove(0);
	    for (Element childElement : em.findChildren(element.getCode())) {
		childNode = new DefaultTreeNode(childElement);
		if (!em.findChildren(childElement.getCode()).isEmpty()) {
		    childNode.getChildren().add(new DefaultTreeNode(new Element()));
		}
		node.getChildren().add(childNode);
	    }
	}
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
	FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
