/**
 * This class shows a frame with a progress bar while Oger is running
 */
package gui;

import io.EmailDelivery;
import io.FileReader;
import io.IODialog;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import data.Review;
import data.ReviewPlan;
import logic.Matcher;
import logic.RandomFunctions;

public class ExecutingFrame {

	ArrayList<Review> reviews = new ArrayList<Review>();
	boolean foundSolution = false;
	boolean stopped = false;
	JFrame executingFrame;

	/**
	 * @param matcher
	 *            the matcher which does the calculation
	 */
	public void showFrame(Matcher matcher, String endingOption, int rounds,
			int hours, int minutes) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ReviewPlan plan = ReviewPlan.getInstance();

		executingFrame = new JFrame();
		executingFrame.setSize(300, 100);
		executingFrame.setTitle("Berechne");
		JPanel progressPanel = new JPanel();

		JProgressBar progressBar = new JProgressBar(1, 100);
		progressBar.setValue(0);

		progressPanel.add(progressBar);

		executingFrame.add(progressPanel);

		JPanel manualPanel = new JPanel();
		JButton stopButton = new JButton("Stop");
		manualPanel.add(stopButton);
		manualPanel.setVisible(false);

		executingFrame.add(manualPanel);

		executingFrame.pack();
		executingFrame.setVisible(true);

		// fixed rounds
		if (endingOption.equals("feste Anzahl")) {

			progressBar.setMaximum(rounds);
			progressBar.setMinimum(0);

			for (int i = 0; i < rounds; i++) {
				Future<ArrayList<Review>> future = executorService
						.submit(matcher);
				while (!future.isDone()) {
					// wait till finished
				}
				try {
					reviews = future.get();
					// result
					if (reviews != null) {
						progressBar.setValue(rounds);
						System.out.println("SOLUTION FOUND");
						foundSolution = true;
						break;

						// shuffle and try again
					} else {
						progressBar.setValue(progressBar.getValue() + 1);
						RandomFunctions.shuffleReviews(plan);
					}
				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(null,
							"Fehler bei der Ausführung.", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

				progressBar.setValue(i);
				future.cancel(true);
			}

		}

		// fixed time
		else if (endingOption.equals("feste Zeit")) {

			int minutesToWait = hours * 60 + minutes;

			progressBar.setMaximum(minutesToWait);
			progressBar.setMinimum(0);

			Date currentDate = new Date();
			Date plannedDate = new Date();

			plannedDate.setHours(plannedDate.getHours() + hours);
			plannedDate.setMinutes(plannedDate.getMinutes() + minutes);

			Date startDate = new Date();

			// wait till wished date arrived
			while (currentDate.before(plannedDate)) {
				Future<ArrayList<Review>> future = executorService
						.submit(matcher);
				while (!future.isDone()) {
					// wait till finished
				}
				try {
					reviews = future.get();
					// result
					if (reviews != null) {
						progressBar.setValue(minutesToWait);
						System.out.println("SOLUTION FOUND");
						foundSolution = true;
						break;

						// shuffle and try again
					} else {
						progressBar.setValue(progressBar.getValue() + 1);
						RandomFunctions.shuffleReviews(plan);
					}
				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(null,
							"Fehler bei der Ausführung.", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				currentDate = new Date();
				int minuteDifference = currentDate.getMinutes()
						- startDate.getMinutes();
				progressBar.setValue(minuteDifference);
				future.cancel(true);
			}

		}

		// manually
		else {
			manualPanel.setVisible(true);
			progressPanel.setVisible(false);

			stopButton.addActionListener(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					stopped = true;

				}

			});

			while (!stopped) {
				Future<ArrayList<Review>> future = executorService
						.submit(matcher);
				while (!future.isDone()) {
					// wait till finished
				}
				try {
					reviews = future.get();
					// result
					if (reviews != null) {
						System.out.println("SOLUTION FOUND");
						foundSolution = true;
						break;

						// shuffle and try again
					} else {
						RandomFunctions.shuffleReviews(plan);
					}
				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(null,
							"Fehler bei der Ausführung.", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

				future.cancel(true);
			}

		}

		executingFrame.dispose();

		if (!foundSolution) {

			showNoSolutionFrame();

		} else {
			showSolutionFrame();
		}
	}

	private void showSolutionFrame() {

		final JFrame solutionFrame = new JFrame();
		solutionFrame.setLayout(new GridLayout(2, 1));

		JLabel optionLabel = new JLabel(
				"Es wurde eine Lösung gefunden. Sie haben folgende Möglichkeiten: ");
		solutionFrame.add(optionLabel);

		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new GridLayout(1, 3));
		solutionFrame.add(optionPanel);

		JButton saveButton = new JButton("Ergebnis speichern");
		optionPanel.add(saveButton);

		saveButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				File file = IODialog.showSaveDialog();

				if (file != null) {
					solutionFrame.dispose();
					FileReader fr = new FileReader();
					fr.printResult(reviews, file.getAbsolutePath());
				}
			}

		});

		JButton emailButton = new JButton("Email versenden");
		optionPanel.add(emailButton);

		emailButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				solutionFrame.dispose();
				EmailDelivery.deliverEmail(reviews);
			}

		});

		JButton cancelButton = new JButton("Abbrechen");
		optionPanel.add(cancelButton);

		cancelButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				solutionFrame.dispose();

			}

		});

		solutionFrame.setVisible(true);
		solutionFrame.pack();
	}

	private void showNoSolutionFrame() {
		final JFrame noSolutionFrame = new JFrame();
		noSolutionFrame.setLayout(new GridLayout(5, 1));

		JLabel textLabel = new JLabel();
		noSolutionFrame.add(textLabel);
		textLabel.setText("Konnte keine Lösung finden. Möglich Optionen: ");

		JLabel lineOneLabel = new JLabel();
		noSolutionFrame.add(lineOneLabel);
		lineOneLabel.setText("- Bieten Sie mehr Slots/Räume an ");

		JLabel lineTwoLabel = new JLabel();
		noSolutionFrame.add(lineTwoLabel);
		lineTwoLabel
				.setText("- Ändern Sie die Einstellungen zu Notar oder Moderator");

		JLabel lineThreeLabel = new JLabel();
		noSolutionFrame.add(lineThreeLabel);
		lineThreeLabel
				.setText("- Führen Sie die Berechnung erneut mit mehr Wiederholungen durch");

		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new GridLayout(1, 2));

		JButton cancelButton = new JButton("Beenden");
		optionPanel.add(cancelButton);

		noSolutionFrame.add(optionPanel);
		noSolutionFrame.setVisible(true);
		noSolutionFrame.pack();

		cancelButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				noSolutionFrame.dispose();
			}

		});

		JButton againButton = new JButton("Erneut versuchen");
		optionPanel.add(againButton);

		againButton.addActionListener(new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				noSolutionFrame.dispose();
				
				// show main frame again
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Gui frame = new Gui();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

		});
	}

}
