package com.dhlee.memory;

import java.util.Hashtable;
import java.util.Map;

public class MemoryLeak {
	public static void main(String[] args) {

		Map<Key, String> map = new Hashtable<Key, String>(1000);

		int counter = 0;

		while (true) {
			// creates duplicate objects due to bad Key class
			map.put(new Key("dummyKey"), "value");
			counter++;
			
			if (counter % 1000 == 0) {
				map.remove(new Key("dummyKey"));
				System.out.println("Map size: " + map.size());
				System.out.println("Free memory after count " + counter + " is " + getFreeMemory() + "MB");
				sleep(1000);
//				map.clear(); 
			}
		}
	}
	
	public static void sleep(long sleepFor) {
		try {
			Thread.sleep(sleepFor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory() / (1024 * 1024);
	}

	// memory leak case : inner class key without hashcode() or equals()
	static class Key {
		private String key;
		public Key(String key) {
			this.key = key;
		}
	}

// right impl.
//	static class Key {
//		private String key;
//
//		public Key(String key) {
//			this.key = key;
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			if (obj instanceof Key) {
//				return key.equals(((Key) obj).key);
//			} else {
//				return false;
//			}
//		}
//
//		@Override
//		public int hashCode() {
//			return key.hashCode();
//		}
//	}
//

}
