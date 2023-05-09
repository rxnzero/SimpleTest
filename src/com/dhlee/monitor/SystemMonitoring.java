package com.dhlee.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SystemMonitoring {
	static OperatingSystemMXBean osm = ManagementFactory.getOperatingSystemMXBean();
	public SystemMonitoring() {

	}

	private static void printUsage() {
		for (Method method : osm.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(osm);
				} catch (Exception e) {
					value = e;
				} // try
				System.out.println(method.getName() + " = " + value);
			} // if
		} // for
	}

	private static double getBeanMonitoringValue(OperatingSystemMXBean oBean, String methodName) {
		double doubleValue = 0;
		for (Method method : oBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith(methodName) && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(oBean);
					if (value != null) {
						if (value instanceof Double)
							doubleValue = (Double) value;
						if (value instanceof Long)
							doubleValue = ((Long) value).doubleValue();
					}
					break;
				} catch (Exception e) {
					value = e;
				} // try
				System.out.println(method.getName() + " = " + value);
			} // if
		} // for
		return doubleValue;
	}

	private static void printSystemUsage() {

		OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
		System.out.println(
				"CPU Usage : " + String.format("%.2f", getBeanMonitoringValue(osBean, "getSystemCpuLoad") * 100));
		System.out.println(
				"CPU Usage : " + String.format("%.2f", getBeanMonitoringValue(osBean, "getCpuLoad") * 100));
		System.out.println(
				"CPU JVM Usage : " + String.format("%.2f", getBeanMonitoringValue(osBean, "getProcessCpuLoad") * 100));
		System.out.println("Memory Free Space G : " + String.format("%.2f",
				getBeanMonitoringValue(osBean, "getFreePhysicalMemorySize") / 1024 / 1024 / 1024));
		System.out.println("Memory Total Space G : " + String.format("%.2f",
				getBeanMonitoringValue(osBean, "getTotalPhysicalMemorySize") / 1024 / 1024 / 1024));
	}
	
	// only for Oracle JVM
	public static void main(String[] args) {

		try {
//        	for(int i=0;i<10;i++){
			while(true) {
        		System.out.println("\n>> System Info.");
//			printUsage();
        		printSystemUsage();
                Thread.sleep(2000);
            }
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
