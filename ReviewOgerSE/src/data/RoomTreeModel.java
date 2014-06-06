/**
 * This class holds the root node of the room tree model
 */
package data;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class RoomTreeModel {

	// TODO
	static DefaultTreeModel model = new DefaultTreeModel(new SlotNode());

	/**
	 * @return the current model
	 */
	public static DefaultTreeModel getModel() {
		return model;
	}

	/**
	 * @return true, if any reviews exist
	 */
	public static boolean hasReviews() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		 Enumeration<DefaultMutableTreeNode> currentNode = root.preorderEnumeration();
		    while(currentNode.hasMoreElements()){
		      if((currentNode.nextElement()).isLeaf()){
		    	  return true;
		      }
		    }
		    return false;
	}

}
