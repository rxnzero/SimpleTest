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
		List<String> testSample = Arrays.asList("��", "��", "��", "��", "��", "��", "��", "����", "1", "10", "100", "-100");
		
		// 4���� Thread�� ���� ThreadPool����
		ExecutorService threadPool = Executors.newFixedThreadPool(4);
		
		// Thread���� �񵿱�� ����Ǹ� �� ����� ���� Futrure ��ü
		List<Future<Video>> futures = new ArrayList<Future<Video>>();
		for (final String sample : testSample) {
			// callable ��ü�� ���� � ���� �������� �����Ѵ�.
			Callable<Video> callable = new Callable<Video>() {
				@Override
				public Video call() throws Exception {
					System.out.println("Time : "+ new Date() + " -Thread Name : " + Thread.currentThread().getName() + " - Text : " + sample);
					
					// �׽�Ʈ�� 3���� 0.5�ʰ� ����
					if (Thread.currentThread().getName().equals("pool-1-thread-3")) {
						Thread.sleep(50);
					}
					
					Video video = new Video();
					video.setDialog(sample);
					return video;
				}
			};
			
			// ������ callable���� threadpool���� �����Ű�� ����� Future ��Ͽ� ��´�.
			futures.add(threadPool.submit(callable));
		}
		
		// �������� callable���� �� ������ threadpool�� �����Ų��.(�ݵ�� �ؾ���)
		// �ڵ����� ���ŵ��� �ʴ´�.
		// showdownNow()�� �������� callable�� �ִ��� ���ͷ�Ʈ���� ���� �����Ѵ�.
		threadPool.shutdown();
		
		List<Video> results = new ArrayList<Video>(); 
		for (Future<Video> future : futures) {
			// future�� ��� ��� ��ü�� �޾� List�� ��´�.
			results.add(future.get());
		}
		
		// ���� �Һ� �ð��� ������� futures�� ��� ������� ��µȴ�.
		for (Video result : results) {
			System.out.println(result.getDialog());
		}
	}	
}
