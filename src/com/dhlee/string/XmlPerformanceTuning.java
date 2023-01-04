package com.dhlee.string;

import java.util.Spliterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlPerformanceTuning {

	public XmlPerformanceTuning() {

	}
	public static final Pattern pattern = Pattern.compile("\\[([0-9]+)\\]");
	public static  String FIELD_SEPARATOR = ".";
	
	public static String denomalizeXPath(String xpath) {
        String itemPath = xpath.substring(1).replaceAll("/", "\\.");

        if (!itemPath.endsWith("]")) {
            itemPath = itemPath + "[1]";
        }
        // ÇÑ±Û Ãß°¡, middot Ãß°¡
        itemPath = itemPath.replaceAll("([¤¡-¤¾°¡-ÆRa-zA-Z0-9_¡¤]+)(\\"+ FIELD_SEPARATOR+")", "$1[1]$2");
        itemPath = itemPath.replaceFirst("\\[[0-9]+\\]", "");

        Matcher m = pattern.matcher(itemPath);
        
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            m.appendReplacement(sb,
                "[" + Integer.toString(Integer.parseInt(m.group(1)) - 1) + "]");
        }

        m.appendTail(sb);
        itemPath = sb.toString();
//        System.out.println(String.format("%s : %s", xpath, itemPath));
        return itemPath;
    }

	public static String denomalizeXPath2(String xpath) {
        StringBuffer sb = new StringBuffer();
        String[] parts = xpath.split("/");
        for(int i=1; i<parts.length; i++) {
        	if(parts.length < 1) continue;
        	if(i == 1) {
        		sb.append(parts[i]);
        	}
        	else {
        		if(parts[i].charAt(parts[i].length()-1) ==']') {
        			int sPos = parts[i].indexOf("[");
        			String idx = parts[i].substring(sPos+1, parts[i].length()-1);
        			sb.append(parts[i].substring(0,sPos))
        			.append("[")
        			.append(Integer.parseInt(idx)-1)
        			.append("]");
        		}
        		else {
        			sb.append(parts[i]).append("[0]");
        		}
        	}
        	
        	if(i  < parts.length-1) {
        		sb.append(FIELD_SEPARATOR);
        	}
        }
        String itemPath = sb.toString();
//        System.out.println(String.format("%s : %s", xpath, itemPath));
        return itemPath;
    }
	
//	denomalizeXPath  run  1,000,000 times : 8,256 ms
//	denomalizeXPath2 run  1,000,000 times : 881 ms

	public static void main(String[] argv) {
//		/CRS72001S0_REQ1/Document : CRS72001S0_REQ1.Document[0]
//		/CRS72001S0_REQ1/Document/InData : CRS72001S0_REQ1.Document[0].InData[0]
//		/CRS72001S0_REQ1/Document/Amt : CRS72001S0_REQ1.Document[0].Amt[0]
//		/CRS72001S0_REQ1/Document/Amt/InstdAmt : CRS72001S0_REQ1.Document[0].Amt[0].InstdAmt[0]
//		/CRS72001S0_REQ1/Document/AddInfo : CRS72001S0_REQ1.Document[0].AddInfo[0]
//		/CRS72001S0_REQ1/Document/AddInfo[2] : CRS72001S0_REQ1.Document[0].AddInfo[1]
//		/CRS72001S0_REQ1/Document/AddInfo[3] : CRS72001S0_REQ1.Document[0].AddInfo[2]
//		/CRS72001S0_REQ1/Document/Price : CRS72001S0_REQ1.Document[0].Price[0]
//		/CRS72001S0_REQ1/Document/Price[2] : CRS72001S0_REQ1.Document[0].Price[1]
//		/CRS72001S0_REQ1/Document/Price[3] : CRS72001S0_REQ1.Document[0].Price[2]
//		/CRS72001S0_REQ1/Document/BoolValue : CRS72001S0_REQ1.Document[0].BoolValue[0]
		int runs = 1000000;
		String testStr = "/CRS72001S0_REQ1/Document/±×·ì1[1]/±×·ì2[2]/±×·ì3[3]/Group1[1]/Group2[2]/Group3[3]/AddInfo[3]";
		long c = 0;
		c = System.currentTimeMillis();
		for(int i=0; i< runs; i++)
			denomalizeXPath(testStr);
		System.out.println(String.format("denomalizeXPath  run %,10d times : %,d ms", runs, (System.currentTimeMillis() -c)) );
		c = System.currentTimeMillis();
		for(int i=0; i< runs; i++)
			denomalizeXPath2(testStr);
		System.out.println(String.format("denomalizeXPath2 run %,10d times : %,d ms", runs, (System.currentTimeMillis() -c)) );
	}
}
