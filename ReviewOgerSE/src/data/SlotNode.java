package data;

import javax.swing.tree.DefaultMutableTreeNode;

public class SlotNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5242086827394327638L;


	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#isLeaf()
	 */
	public boolean isLeaf(){
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public String toString(){
		if(this.isRoot()){
			return "Slots";
		}
		return ((Slot) this.getUserObject()).getFormatedDate();
		
	}
	
}
