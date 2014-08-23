package io;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class IODialog {
	/**
	 * Shows the save dialog and checks if file already exists
	 * 
	 * @return the selected file
	 */
	//TODO überschreiben tut nicht
	public static File showSaveDialog() {
		JFileChooser fc = new JFileChooser();
		File file = null;
		fc.setFileFilter(new OgerDialogFilter());
		int returnValue = fc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			// correct ending
			if (!file.getAbsolutePath().endsWith(".oger")) {
				file = new File(file.getAbsolutePath() + ".oger");
			}
			// if file exists, ask if overwrite
			if (file.exists()) {
				int result = JOptionPane
						.showOptionDialog(
								null,
								"Die Datei existiert bereits. Möchten Sie überschreiben?",
								"Warnung", JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null,
								null);
				if (result == JOptionPane.YES_OPTION) {
					return file;
				} else {
					// no overwriting -> show open dialog again
					return showSaveDialog();
				}
			} else {
				return file;
			}
		} else {
			// canceled
			return null;
		}
	}

}
