/**
 * This class shows a frame with a progress bar while Oger is running
 */
package gui;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import logic.Matcher;

public class ExecutingFrame {

	/**
	 * @param matcher
	 *            the matcher which does the calculation
	 */
	public void showFrame(Matcher matcher) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<String> future = executorService.submit(matcher);
		while (!future.isDone()) {
			// wait till finished
		}

		try {
			System.out.println(future.get());
			// TODO
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JProgressBar progressBar = new JProgressBar(1, 100);
		JFrame f = new JFrame();
		f.add(progressBar);
		f.pack();
		f.setVisible(true);
	}

}
