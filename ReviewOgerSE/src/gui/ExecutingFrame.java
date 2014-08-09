/**
 * This class shows a frame with a progress bar while Oger is running
 */
package gui;

import io.FileReader;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
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

	/**
	 * @param matcher
	 *            the matcher which does the calculation
	 */
	public void showFrame(Matcher matcher, String endingOption, int rounds,
			int hours, int minutes) {
		// TODO
		ExecutorService executorService = Executors.newCachedThreadPool();
		ReviewPlan plan = ReviewPlan.getInstance();

		JFrame executingFrame = new JFrame();
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

			
			stopButton.addActionListener(new AbstractAction(){

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

		if (!foundSolution) {
			JOptionPane.showMessageDialog(null, "Konnte keine Lösung finden");

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
		} else {
			//TODO show frame and ask for saving place
			FileReader fr = new FileReader();
			fr.printResult(reviews);
		}
	}

}
