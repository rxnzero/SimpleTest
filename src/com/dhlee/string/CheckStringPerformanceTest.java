package com.dhlee.string;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class CheckStringPerformanceTest {
	private static char FIELD_SEPARATOR = '.';
	
	public CheckStringPerformanceTest() {
	}

	public static void main(String[] args) throws Exception {
//		testReplaceAsteriskToIndex();
		testPattern();
//		testInternalNomallizeFullName();
//		testSplit();
	}
	
	private static void testInternalNomallizeFullName() {
		int iter = 1000000;
		String instruction ="message.group1.group2.group3.group4.group5.group6.array[*].field1";
		
		String result = null;
		long t = 0;
		
//internalNomalizeFullName1 : message.group1.group2.group3.group4.group5.group6.array[*].field1 
//		=> message[0].group1[0].group2[0].group3[0].group4[0].group5[0].group6[0].array[*].field1, elapsed ms = 1000, iter = 1,000,000
//internalNomalizeFullName2 : message.group1.group2.group3.group4.group5.group6.array[*].field1 
//		=> message[-1].group1[0].group2[0].group3[0].group4[0].group5[0].group6[0].array[*].field1[0], elapsed ms = 734, iter = 1,000,000

		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			result = internalNomalizeFullName(instruction);
		}
		System.out.printf("internalNomalizeFullName1 : %s => %s, elapsed ms = %d, iter = %,d%n"
				, instruction, result, (System.currentTimeMillis() - t) , iter);
		
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			result = internalNomalizeFullName2(instruction);
		}
		System.out.printf("internalNomalizeFullName2 : %s => %s, elapsed ms = %d, iter = %,d%n"
				, instruction, result, (System.currentTimeMillis() - t) , iter);
		
		// BEST
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			result = internalNomalizeFullName3(instruction);
		}
		System.out.printf("internalNomalizeFullName3 : %s => %s, elapsed ms = %d, iter = %,d%n"
				, instruction, result, (System.currentTimeMillis() - t) , iter);
	}
	
    private static String internalNomalizeFullName(String s) {
		for (int i = 0; -1 != (i = s.indexOf(FIELD_SEPARATOR, i)); i++)
			if (']' != s.charAt(i - 1))
				s = s.substring(0, i) + "[0]"+FIELD_SEPARATOR + s.substring(i + 1);
        return s;
	}
    
    
    private static String internalNomalizeFullName3(String source) {
    	char spliter = '.';
    	StringBuilder sb = new StringBuilder();
    	CharSequence chars = source;
    	int start = 0, end = 0;
    	int partCount = 0;
    	int i=0;
    	for(; i<chars.length(); i++) {
    		if(chars.charAt(i) == spliter) {
    			end = i;
    			sb.append(chars.subSequence(start, end).toString());
    			if(chars.charAt(i-1) != ']') {
    				if(partCount == 0) sb.append("[-1]");
    				else sb.append("[0]");
    			}
    			sb.append(spliter);
    			partCount++;
    			start = end+1;
    			i = i+1;
    		}
    	}
    	
    	if(end == 0) {
    		sb.append(chars.toString());    		
    	}
    	else {
    		sb.append(chars.subSequence(start, chars.length()).toString());
    	}
    	
    	if(chars.charAt(i-1) != ']') {
			sb.append("[0]");
		}
    	
    	return sb.toString();
    }
    
//spilt : elapsed ms = 471, iter = 1,000,000
//0 - message
//1 - group1
//2 - group2
//3 - group3
//4 - group4
//5 - group5
//6 - group6
//7 - array[*]
//8 - field1
//-------------------------------------
//spilt2 : elapsed ms = 722, iter = 1,000,000
//0 - message
//1 - group1
//2 - group2
//3 - group3
//4 - group4
//5 - group5
//6 - group6
//7 - array[*]
//8 - field1
    private static void testSplit() {
		String instruction ="message.group1.group2.group3.group4.group5.group6.array[*].field1";
		int iter = 1000000;
		String[] splited = null;
		int i=0;
		long t;
		
		t = System.currentTimeMillis();
		for(i=0; i<iter; i++)
			splited = spilt(instruction, '.');
		System.out.printf("spilt : elapsed ms = %d, iter = %,d%n"
				, (System.currentTimeMillis() - t) , iter);
		
		i=0;
		for(String s: splited) {
			System.out.printf("%d - %s%n", i++, s);
		}
		
		System.out.println("-------------------------------------");
		
		t = System.currentTimeMillis();
		for(i=0; i<iter; i++)
			splited = spilt2(instruction, ".");
		System.out.printf("spilt2 : elapsed ms = %d, iter = %,d%n"
				, (System.currentTimeMillis() - t) , iter);
		i=0;
		for(String s: splited) {
			System.out.printf("%d - %s%n", i++, s);
		}
    }
    
    private static String[] spilt2(String source, String spliter) {
    	StringTokenizer st = new StringTokenizer(source, spliter);
    	String[] arr = new String[st.countTokens()];
    	for(int i=0; i<arr.length; i++) {
    		arr[i] = st.nextToken();
    	}
    	return arr;
    }    
    
    private static String[] spilt(String source, char spliter) {
    	String[] spilted = null;
    	List<String> list = new ArrayList<String>();
    	CharSequence chars = source;
    	int start = 0, end = 0;
    	for(int i=0; i<chars.length(); i++) {
    		if(chars.charAt(i) == spliter) {
    			end = i;
    			list.add(chars.subSequence(start, end).toString());
    			start = end+1;
    			i = i+1;
    		}
    	}
    	
    	if(end == 0) {
    		list.add(chars.toString());
    	}
    	else {
    		list.add(chars.subSequence(start, chars.length()).toString());
    	}
    	
    	return (String[])list.toArray(new String[0]);
    }
    
    private static String internalNomalizeFullName2(String s) {
    	StringBuilder sb = new StringBuilder();
    	String[] parts = spilt(s, FIELD_SEPARATOR);;
    	for(int i=0; i < parts.length; i++) {
    		if(i > 0) sb.append(FIELD_SEPARATOR);
    		sb.append(parts[i]);
    		if ( ']' != parts[i].charAt(parts[i].length()-1) ) {
    			if(i ==0) sb.append("[-1]");
    			else sb.append("[0]");
    			
    		}
    	}
    	return sb.toString();
	}
    
	private static void testReplaceAsteriskToIndex() {	
		int iter = 100000;
		String instruction ="*abc[*].d*ef[*].ghi*) + (*jk[*].lm[*].no";
		String result = null;
		long t = 0;
		
		// BEST
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			result = replaceAsteriskToIndex(instruction, 1);
		}
		System.out.printf("replaceAsteriskToIndex1 : %s => %s, elapsed ms = %d, iter = %d%n"
				, instruction, result, (System.currentTimeMillis() - t) , iter);
		
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			result = replaceAsteriskToIndex2(instruction, 1);
		}
		System.out.printf("replaceAsteriskToIndex2 : %s => %s, elapsed ms = %d, iter = %d%n"
				, instruction, result, (System.currentTimeMillis() - t) , iter);
	}
	
	private static final char[] FUNCTION_SEPARATORS = { ' ', ',' };
	
	private static String replaceAsteriskToIndex(String src, int idx) {
//    	System.out.println("SRC : " + src);
        String index = Integer.toString(idx);
        char[] orgChars = src.toCharArray();
        char[] settingChars = index.toCharArray();
        StringBuilder sb = new StringBuilder(orgChars.length);

        boolean doChange = true;
        boolean isChanged = false;

        for(int i = 0; i < orgChars.length;i++) {
            char c = orgChars[i];
            if (doChange && c == '*'
                    && i - 1 > -1 && i + 1 < orgChars.length
                    && orgChars[i - 1] == '[' && orgChars[i + 1] == ']' ) {
                isChanged = true;
                doChange = false;

                for (char settingChar : settingChars) {
                    sb.append(settingChar);
                }
            } else {
                sb.append(c);
            }
            for(char functionSeparator : FUNCTION_SEPARATORS){
                if(c == functionSeparator) {
                    doChange = true;
                    break;
                }
            }
        }

        if(!isChanged) {
            throw new NullPointerException("º¯È¯ÀÎÀÚ '[*]' °¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
        }
//        System.out.println("CHG : " + sb.toString());
        return sb.toString();
    }
		
	private static String replaceAsteriskToIndex2(String src, int idx) {
        String rep = "["+idx+"]";
        
        StringBuilder sb = new StringBuilder();
        String[] parts = src.split("[+]");
        int start = 0;
        int pos = -1;
        for(int i=0; i<parts.length; i++) {
        	
//        	System.out.println("pos = " +pos);
        	if(i > 0) {
        		sb.append("+");
        	}
        	sb.append(parts[i].replaceFirst("\\[[*]\\]", rep));        	
        }        
        return sb.toString();
    }
	
	private static String replaceAsteriskToIndex3(String src, int idx) {
        StringBuilder sb = new StringBuilder();
        String[] parts = src.split("[+]");
        int start = 0;
        int pos = -1;
        for(int i=0; i<parts.length; i++) {
        	pos = parts[i].indexOf("[*]");
//        	System.out.println("pos = " +pos);
        	if(i > 0) {
        		sb.append("+");
        	}
        	if(pos > 0) {
        		sb.append(parts[i].substring(0, pos));
        		sb.append("[" + idx + "]");
        		sb.append(parts[i].substring(pos+3));
        	}
        	else {
        		sb.append(parts[i]);
        	}
        	
        }        
        return sb.toString();
    }
	
	public static void testPattern() throws Exception {
		int iter = 1000000;
		int intervalSecs = 1;
		String message = "addedfunction(message.group1.group1.group1.group1.group1.group1.field)";
//		message = "addedfunction.message.group1.group1.group1.group1.group1.group1.field +";
		long t = 0; 
		boolean check = false;
		
//		String iterDot = NumberFormat.getInstance().format(iter);
//		System.out.println(">> Start iters : " + iterDot);
		System.out.printf(">> Start iters : %,d%n",iter);
		
// too slow.
//		>> Start iters : 1,000,000
//		testPattern : 438-> false
//		testSimpleTuning0 : 4-> false
//		testSimpleTuning1 : 10-> false
//		testSimpleTuning2 : 24-> false
//		testSimpleTuning3: 16-> false

//		
		t = System.currentTimeMillis();
		
		for(int i=0; i<iter;i++) {
			check = testPattern(message);
		}
		System.out.println("testPattern : " +(System.currentTimeMillis() - t) + "-> " +check);
		
		// BEST
		Thread.sleep(intervalSecs *1000);
		t = System.currentTimeMillis();
		for(int i=0; i<iter;i++) {
			check = testSimpleTuning0(message);
		}
		System.out.println("testSimpleTuning0 : " +(System.currentTimeMillis() - t) + "-> " +check);
		
		// BEST
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
			check = testSimpleTuning3(message);
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
	static char[] findChars0 = {')', '+', ' '};
	
	public static boolean testSimpleTuning1(String message) {
		for(int i=0; i<findChars.length; i++) {
			if(message.indexOf(findChars[i]) > -1) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean testSimpleTuning0(String message) {
		if(message.charAt(message.length()-1) == ')') return false;
		for(int i=0; i<findChars.length; i++) {
			if(message.indexOf(findChars[i]) > -1) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean testSimpleTuning2(String message) {
		char[] chars = message.toCharArray();
		for(int i=0; i<chars.length; i++) {
			if( chars[i] == '(' || chars[i] == '+' || chars[i] == ' ') return false;
		}
		return true;
	}
	
	public static boolean testSimpleTuning3(String message
			) {
		CharSequence chars =message; 
		for(int i=0; i<chars.length(); i++) {
			if( chars.charAt(i) == '(' || chars.charAt(i) == '+' || chars.charAt(i) == ' ') return false;
		}
		return true;
	}
}
