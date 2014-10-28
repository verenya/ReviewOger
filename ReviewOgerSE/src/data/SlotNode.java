/**
 * This class represents a node in a tree if this node represents a slot. Slots are no leafs!
 *
 */
package data;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author verena
 * 
 */
public class SlotNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5242086827394327638L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
	 */
	public boolean isLeaf() {
		// slots are never leafes
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString() {
		if (this.isRoot()) {
			return "Slots";
		}
		return ((Slot) this.getUserObject()).getFormatedDate();

	}

}
