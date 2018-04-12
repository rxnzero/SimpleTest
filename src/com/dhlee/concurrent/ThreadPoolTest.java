package com.dhlee.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public ThreadPoolTest() {

	}

	public static void main(String[] args) {
		String[] idArray = new String[100];
		
		for(int i=0; i< idArray.length; i++) {
			idArray[i] = "" + i;
		}
		ExecutorService executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		
		for (int idx = 0; idx < idArray.length; idx++) {
			int p = idx;
			executor.execute(new Runnable()
			{
				public void run()
				{					 
//					System.out.println("Thread " + Thread.currentThread().getName() + " Start");
					try {
						System.out.println(p +" : Run Thread " + Thread.currentThread().getName() + " = " +idArray[p]);
						Thread.sleep(1000);
					} catch (Exception e) {
					}
//					System.out.println("Thread " + Thread.currentThread().getName() + " End");
				}
			});
		}
		
		executor.shutdown();
	}

}
