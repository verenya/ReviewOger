package gui;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import logic.Matcher;

public class ExecutingFrame {
	
	public void showFrame(Matcher matcher){
		ExecutorService es = Executors.newCachedThreadPool();
		Future<Integer> fo = es.submit(matcher);
		while(!fo.isDone()){
			//wait till finished
		}
		
		try {
			System.out.println(fo.get());
			//TODO
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
