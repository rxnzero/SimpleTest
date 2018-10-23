package com.dhlee.parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelTest {

	public ParallelTest() {
	}
	
	public static void main(String[] argv) {
		ParallelTest test = new ParallelTest();
		try {
			test.oarallelExecute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void oarallelExecute() throws Exception {
		List<String> testSample = Arrays.asList("가", "나", "다", "라", "마", "바", "사", "후후", "1", "10", "100", "-100");
		
		// 4개의 Thread를 가진 ThreadPool생성
		ExecutorService threadPool = Executors.newFixedThreadPool(4);
		
		// Thread들이 비동기로 수행되면 그 결과를 담을 Futrure 객체
		List<Future<Video>> futures = new ArrayList<Future<Video>>();
		for (final String sample : testSample) {
			// callable 객체를 통해 어떤 일을 수행할지 결정한다.
			Callable<Video> callable = new Callable<Video>() {
				@Override
				public Video call() throws Exception {
					System.out.println("Time : "+ new Date() + " -Thread Name : " + Thread.currentThread().getName() + " - Text : " + sample);
					
					// 테스트겸 3번만 0.5초간 중지
					if (Thread.currentThread().getName().equals("pool-1-thread-3")) {
						Thread.sleep(50);
					}
					
					Video video = new Video();
					video.setDialog(sample);
					return video;
				}
			};
			
			// 생성된 callable들을 threadpool에서 수행시키고 결과는 Future 목록에 담는다.
			futures.add(threadPool.submit(callable));
		}
		
		// 수행중인 callable들이 다 끝나면 threadpool을 종료시킨다.(반드시 해야함)
		// 자동으로 제거되지 않는다.
		// showdownNow()는 수행중인 callable이 있더라도 인터럽트시켜 강제 종료한다.
		threadPool.shutdown();
		
		List<Video> results = new ArrayList<Video>(); 
		for (Future<Video> future : futures) {
			// future에 담긴 결과 객체를 받아 List에 담는다.
			results.add(future.get());
		}
		
		// 수행 소비 시간에 상관없이 futures에 담긴 순서대로 출력된다.
		for (Video result : results) {
			System.out.println(result.getDialog());
		}
	}	
}
