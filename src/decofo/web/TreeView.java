package decofo.web;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import decofo.entities.Element;
import decofo.services.ElementManager;

@ManagedBean(name = "treeView")
@ViewScoped
public class TreeView {

    @EJB
    private ElementManager elementManager;

    @ManagedProperty(value = "#{elementController}")
    private ElementController elementController;
    
    private TreeNode root;
    private TreeNode orphan;
    private TreeNode selectedElement;

    @PostConstruct
    public void init() {
	root = new DefaultTreeNode(new Element(), null);
	orphan = new DefaultTreeNode(new Element(), null);
	TreeNode node = new DefaultTreeNode(elementManager.findRoot(elementController.getModelController().getTheModel()), root);
	if (!elementManager.findChildren(((Element) node.getData()).getCode()).isEmpty()) {
	    node.getChildren().add(new DefaultTreeNode(new Element()));
	}
	elementController.setTheElement((Element) node.getData());
    }

    public TreeNode getRoot() {
	return root;
    }

    public void setRoot(TreeNode root) {
	this.root = root;
    }

    public TreeNode getOrphan() {
	return orphan;
    }

    public void setOrphan(TreeNode orphan) {
	this.orphan = orphan;
    }

    public TreeNode getSelectedElement() {
	return selectedElement;
    }

    public void setSelectedElement(TreeNode selectedElement) {
	this.selectedElement = selectedElement;
    }

    public ElementController getElementController() {
        return elementController;
    }

    public void setElementController(ElementController elementController) {
        this.elementController = elementController;
    }

    public void onNodeExpand(NodeExpandEvent event) {
	TreeNode node = event.getTreeNode();
	TreeNode childNode;
	Element element = (Element) node.getData();
	if (node.getChildCount() == 1 && ((Element) node.getChildren().get(0).getData()).getCode() == null) {
	    node.getChildren().remove(0);
	    for (Element childElement : elementManager.findChildren(element.getCode())) {
		childNode = new DefaultTreeNode(childElement);
		if (!elementManager.findChildren(childElement.getCode()).isEmpty()) {
		    childNode.getChildren().add(new DefaultTreeNode(new Element()));
		}
		node.getChildren().add(childNode);
	    }
	}
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
	elementController.setTheElement((Element) selectedElement.getData());
    }
}
