package com.dhlee.copy;

import java.util.ArrayList;
import java.util.HashMap;

public class CopyObjectTest {

	public CopyObjectTest() {
		// TODO Auto-generated constructor stub
	}

	public static void testString() {
		String str = "abc";
		String strCopy = str;
		str = "def";
		System.out.println("orgn : " + str);
		System.out.println("copy : " + strCopy);
	}
	
	
	public static void testPojo() {
		User user = new User();
		user.setId(1);
		user.setName("dhlee");
		
		User cloned = null;
		try {
			cloned = (User)user.clone();
		} catch (CloneNotSupportedException e) {
			;
		}
		user.setId(2);
		user.setName("dhlee2");
		
		System.out.println("orgn : " + user);
		System.out.println("copy : " + cloned); 
	}
	
	public static void testHashMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		HashMap<String, String> cloned = (HashMap)map.clone();
		
		map.put("1", "2");
		System.out.println("orgn : " + map.get("1"));
		System.out.println("copy : " + cloned.get("1")); 
	}
	
	public static void testArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		ArrayList<String> cloned = (ArrayList)list.clone();
		
		list.set(0, "2");
		System.out.println("orgn : " + list.get(0));
		System.out.println("copy : " + cloned.get(0)); 
	}
	
	public static void testHashMapObject() {
		HashMap<String, User> map = new HashMap<String, User>();
		User user = new User();
		user.setId(1);
		user.setName("dhlee");
		map.put("1", user);
		HashMap<String, User> cloned = (HashMap)map.clone();
		
		user.setId(2);
		user.setName("dhlee2");
		System.out.println("orgn : " + map.get("1"));
		System.out.println("copy : " + cloned.get("1")); 
	}
	
	public static void main(String[] args) {
		System.out.println("test String copy");
		testString();
		System.out.println("test HashMap copy");
		testHashMap();
		System.out.println("test Pojo copy");
		testPojo();
		System.out.println("test HashMapObject copy");
		testHashMapObject();

	}

}
