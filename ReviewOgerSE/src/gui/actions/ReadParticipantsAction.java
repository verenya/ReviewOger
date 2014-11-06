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
package gui.actions;

import io.FileProcessor;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import main.Main;

/**
 * This class implements an abstract action that is called when a list of
 * participants should be read
 */
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
