package com.dhlee.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SystemMonitoring {

	public SystemMonitoring() {

	}

	private static void printUsage() {
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
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
		System.out.println("Memory Free Space : " + String.format("%.2f",
				getBeanMonitoringValue(osBean, "getFreePhysicalMemorySize") / 1024 / 1024 / 1024));
		System.out.println("Memory Total Space : " + String.format("%.2f",
				getBeanMonitoringValue(osBean, "getTotalPhysicalMemorySize") / 1024 / 1024 / 1024));
	}

	public static void main(String[] args) {

		try {
        	for(int i=0;i<10;i++){
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
