/*******************************************************************************
 * Copyright (c) 2014 Verena Käfer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/gpl.html
 *
 * Contributors:
 * Verena Käfer - initial version
 *******************************************************************************/
package data;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class represents a node in a tree if this node represents a slot. Slots
 * are no leafs!
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
