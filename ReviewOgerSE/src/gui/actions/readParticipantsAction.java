/**
 * This class implements an abstract action that is called when a list of participants should be read
 */
package gui.actions;

import io.FileReader;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

public class readParticipantsAction extends AbstractAction {

	private static final long serialVersionUID = 195034471242396979L;

	@Override
	public void actionPerformed(ActionEvent arg0) {

		final JFileChooser fileChooser = new JFileChooser();

		int returnValue = fileChooser.showOpenDialog(null);

		// file chosen
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			FileReader reader = new FileReader();

			reader.readParticipantList(file.getAbsolutePath());

		}
	}

}
