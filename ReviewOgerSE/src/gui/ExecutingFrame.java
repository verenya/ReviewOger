/**
 * This class shows a frame with a progress bar while Oger is running
 */
package gui;

import io.FileReader;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	/**
	 * @param matcher
	 *            the matcher which does the calculation
	 */
	public void showFrame(Matcher matcher, String endingOption, int rounds,
			int hours, int minutes) {
		// TODO
		ExecutorService executorService = Executors.newCachedThreadPool();
		ReviewPlan plan = ReviewPlan.getInstance();

		JFrame f = new JFrame();
		f.setSize(300, 100);
		f.setTitle("Berechne");
		JPanel progressPanel = new JPanel();

		JProgressBar progressBar = new JProgressBar(1, 100);
		progressBar.setValue(0);

		progressPanel.add(progressBar);

		f.add(progressPanel);
		f.pack();
		f.setVisible(true);

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

				progressBar.setValue(i);

			}
		}

		// fixed time
		else if (endingOption.equals("feste Zeit")) {

		}

		// manually
		else {

		}
		FileReader fr = new FileReader();
		fr.printResult(reviews);
	}
	
	//TODO zweiter Durchlauf startet nicht

}
