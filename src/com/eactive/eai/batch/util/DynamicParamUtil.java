package com.eactive.eai.batch.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DynamicParamUtil {
	
	public static String datePrefix = "$DATE";
	
	public static void main(String[] args) {
		String paramValue = "$DATE";
		System.out.println(paramValue + " = " + generateDateParam(paramValue));
		paramValue = "$DATE-1";
		System.out.println(paramValue + " = " + generateDateParam(paramValue));
		paramValue = "$DATE+1";
		System.out.println(paramValue + " = " + generateDateParam(paramValue));
		paramValue = "$DATE+A";
		System.out.println(paramValue + " = " + generateDateParam(paramValue));
		paramValue = "$DATE + 2";
		System.out.println(paramValue + " = " + generateDateParam(paramValue));
	}
	
	public static String generateDateParam(String paramValue) {
		String dateStr =  paramValue;
		
		
		String spilter = null;
		try {
			if(paramValue.startsWith(datePrefix)) {
				if(paramValue.indexOf("-") > 0) {
					spilter = "-";
				}
				else if(paramValue.indexOf("+") > 0) {
					spilter = "\\+";
				}
				
				if(spilter != null) {
					String[] partParams = paramValue.split(spilter);
					if(partParams.length == 2) {
						
						int day = Integer.parseInt(partParams[1].trim());
						if(spilter.equals("-")) {
							dateStr = getDayAddedDate(-day);
						}
						else {
							dateStr = getDayAddedDate(day);
						}
					}
				}
				else if(paramValue.equals("$DATE")) {
					dateStr = getCurrentDate();
				}
			}
		}
		catch(Exception ex) {
			return paramValue;
		}
		return dateStr;
	}
	
	public static String getCurrentDate() {
    	SimpleDateFormat	SDF_YYYYMMDD = new SimpleDateFormat( "yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        return (SDF_YYYYMMDD).format(calendar.getTime()); 
    }
	
    public static String getDayAddedDate(int day) {
    	SimpleDateFormat	SDF_YYYYMMDD = new SimpleDateFormat( "yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);

        return (SDF_YYYYMMDD).format(calendar.getTime()); 
    }
}
