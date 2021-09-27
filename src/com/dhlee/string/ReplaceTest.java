package com.dhlee.string;

public class ReplaceTest {

	public ReplaceTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String str = "> TEST <";
		System.out.println("source= [" +str+"]");
		testReplace(str);
		str = ">   <";
		System.out.println("source= [" +str+"]");
		testReplace(str);
		str = ">\t\t\t<";
		System.out.println("source= [" +str+"]");
		testReplace(str);

	}
	
	public static void testReplace(String str) {
		System.out.println("ReplaceSpace= [" +testReplaceSpace(str)+"]");
		System.out.println("ReplaceTab= [" +testReplaceSpace(str)+"]");
	}
	
	public static String testReplaceSpace(String str) {	
		str = str.replaceAll(">\\s+<", "");
		return str;
	}
	
	public static String testReplaceTab(String str) {	
		str = str.replaceAll(">\t+<", "");
		return str;
	}
}
