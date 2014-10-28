/**
 * This class implements an abstract action that is called when a list of participants should be read
 */
package gui.actions;

import io.FileProcessor;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import main.Main;

public class ReadParticipantsAction extends AbstractAction {

	private static final long serialVersionUID = 195034471242396979L;

	@Override
	public void actionPerformed(ActionEvent arg0) {

		final JFileChooser fileChooser = new JFileChooser();

		int returnValue = fileChooser.showOpenDialog(null);

		// file choser
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			FileProcessor reader = new FileProcessor();

			reader.readParticipantList(file.getAbsolutePath());
			Main.setSaved(false);
		}
	}

}
