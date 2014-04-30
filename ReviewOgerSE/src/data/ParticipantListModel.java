package data;

import javax.swing.AbstractListModel;

public class ParticipantListModel extends AbstractListModel<Participant>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Participant getElementAt(int arg0) {
		return ReviewPlan.participants.get(arg0);
	}

	@Override
	public int getSize() {
		return ReviewPlan.participants.size();
	}

}
