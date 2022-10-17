package com.dhlee.string;

import java.text.NumberFormat;
import java.util.regex.Pattern;

public class CheckStringPerformanceTest {
	
	public CheckStringPerformanceTest() {
	}

	public static void main(String[] args) throws Exception {
		int iter = 10000000;
		int intervalSecs = 1;
		String message = "function(message.group1.group1.group1.group1.group1.group1.field +";
		long t = 0; 
		boolean check = false;
		
//		String iterDot = NumberFormat.getInstance().format(iter);
//		System.out.println(">> Start iters : " + iterDot);
		System.out.printf(">> Start iters : %,d%n",iter);
		
// too slow.
//		testPattern : 2532-> false
//		testSimpleTuning1 : 27-> false
//		testSimpleTuning2 : 203-> false
//		testSimpleTuning3: 62-> false
//		
		t = System.currentTimeMillis();
		
		for(int i=0; i<iter;i++) {
			check = testPattern(message);
		}
		System.out.println("testPattern : " +(System.currentTimeMillis() - t) + "-> " +check);
		
		Thread.sleep(intervalSecs *1000);
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			check = testSimpleTuning1(message);
		}
		System.out.println("testSimpleTuning1 : " +(System.currentTimeMillis() - t) + "-> " +check);
		
		Thread.sleep(intervalSecs *1000);
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			check = testSimpleTuning2(message);
		}
		System.out.println("testSimpleTuning2 : " +(System.currentTimeMillis() - t) + "-> " +check);
		
		Thread.sleep(intervalSecs *1000);
		char[] chars = message.toCharArray(); // performance reduction ?
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			check = testSimpleTuning3(chars);
		}
		System.out.println("testSimpleTuning3: " +(System.currentTimeMillis() - t) + "-> " +check);
	}
	
	static Pattern simpleInstructionPattern = Pattern.compile("[¤¡-¤¾°¡-ÆRa-zA-Z0-9_\\-\\.¡¤\\[\\]]+");
	public static boolean testPattern(String message) {
		if (simpleInstructionPattern.matcher(message).matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	static char[] findChars = {'(', '+', ' '};
	public static boolean testSimpleTuning1(String message) {
		for(int i=0; i<findChars.length; i++) {
			if(message.indexOf(findChars[i]) > -1) {
				return false;
			}
		}
		return true;
	}

	public static boolean testSimpleTuning2(String message) {
		return testSimpleTuning3( message.toCharArray() ); 
	}
	
	public static boolean testSimpleTuning3(char[] chars) {
		for(int i=0; i<chars.length; i++) {
			if( chars[i] == '(' || chars[i] == '+' || chars[i] == ' ') return false;
		}
		return true;
	}
}
