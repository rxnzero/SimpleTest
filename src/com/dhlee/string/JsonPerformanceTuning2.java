package com.dhlee.string;

import java.util.Spliterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonPerformanceTuning2 {

	public JsonPerformanceTuning2() {

	}
	public static final Pattern pattern = Pattern.compile("\\[([0-9]+)\\]");
	public static  String FIELD_SEPARATOR = ".";
	
	public static String denomalizeXPath(String xpath) {
        String itemPath = xpath;
        if (!itemPath.endsWith("]")) {
            itemPath = itemPath + "[0]";
        }
        itemPath = itemPath.replaceAll("([¤¡-¤¾°¡-ÆRa-zA-Z0-9_]+)(\\"+FIELD_SEPARATOR+")", "$1[0]$2");
        itemPath = itemPath.replaceFirst("\\[[0-9]+\\]", "");
//      System.out.println(String.format("%s : %s", xpath, itemPath));
        return itemPath;
    }

	public static String denomalizeXPath2(String xpath) {
        StringBuffer sb = new StringBuffer();
        String[] parts = xpath.split("\\"+FIELD_SEPARATOR);
        for(int i=0; i<parts.length; i++) {
        	if(i == 0) {
        		sb.append(parts[i]);
        	}
        	else {
        		if(parts[i].charAt(parts[i].length()-1) ==']') {
        			sb.append(parts[i]);
        		}
        		else {
        			sb.append(parts[i]).append("[0]");
        		}
        	}
        	if(i < parts.length-1) {
        		sb.append(FIELD_SEPARATOR);
        	}
        }
        String itemPath = sb.toString();
//        System.out.println(String.format("%s : %s", xpath, itemPath));
        return itemPath;
    }
	
//	denomalizeXPath  run  1,000,000 times : 2,672 ms
//	denomalizeXPath2 run  1,000,000 times : 240 ms
	
	public static void main(String[] argv) {
//		CRS72001S0_REQ2 : CRS72001S0_REQ2
//		CRS72001S0_REQ2.Document : CRS72001S0_REQ2.Document[0]
//		CRS72001S0_REQ2.Document.BoolValue : CRS72001S0_REQ2.Document[0].BoolValue[0]
//		CRS72001S0_REQ2.Document.AddInfo[0] : CRS72001S0_REQ2.Document[0].AddInfo[0]
//		CRS72001S0_REQ2.Document.AddInfo[0] : CRS72001S0_REQ2.Document[0].AddInfo[0]
//		CRS72001S0_REQ2.Document.AddInfo[1] : CRS72001S0_REQ2.Document[0].AddInfo[1]
//		CRS72001S0_REQ2.Document.AddInfo[1] : CRS72001S0_REQ2.Document[0].AddInfo[1]
//		CRS72001S0_REQ2.Document.AddInfo[2] : CRS72001S0_REQ2.Document[0].AddInfo[2]
//		CRS72001S0_REQ2.Document.AddInfo[2] : CRS72001S0_REQ2.Document[0].AddInfo[2]
//		CRS72001S0_REQ2.Document.Price[0] : CRS72001S0_REQ2.Document[0].Price[0]
//		CRS72001S0_REQ2.Document.Price[0] : CRS72001S0_REQ2.Document[0].Price[0]
//		CRS72001S0_REQ2.Document.Price[1] : CRS72001S0_REQ2.Document[0].Price[1]
//		CRS72001S0_REQ2.Document.Price[1] : CRS72001S0_REQ2.Document[0].Price[1]
//		CRS72001S0_REQ2.Document.Price[2] : CRS72001S0_REQ2.Document[0].Price[2]
//		CRS72001S0_REQ2.Document.Price[2] : CRS72001S0_REQ2.Document[0].Price[2]
//		CRS72001S0_REQ2.Document.Amt : CRS72001S0_REQ2.Document[0].Amt[0]
//		CRS72001S0_REQ2.Document.Amt.Ccy : CRS72001S0_REQ2.Document[0].Amt[0].Ccy[0]
//		CRS72001S0_REQ2.Document.Amt.InstdAmt : CRS72001S0_REQ2.Document[0].Amt[0].InstdAmt[0]
//		CRS72001S0_REQ2.Document.InData : CRS72001S0_REQ2.Document[0].InData[0]
//		CRS72001S0_REQ2.Document.InData.CuniqnoType : CRS72001S0_REQ2.Document[0].InData[0].CuniqnoType[0]
//		CRS72001S0_REQ2.Document.InData.Cuniqno : CRS72001S0_REQ2.Document[0].InData[0].Cuniqno[0]
		int runs = 1000000;
		String testStr = "CRS72001S0_REQ2.Document.AddInfo[2]";
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
