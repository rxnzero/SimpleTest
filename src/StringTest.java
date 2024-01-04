import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class StringTest {

	public StringTest() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static String extractLayoutName(String path) {
		
		String layoutName = null;
		int sPos = 0;
		int ePos = 0;
		if(path == null || path.length()  == 0)
			return null;
		
		sPos = path.lastIndexOf("(");
		if(sPos < 0) {
			sPos = 0;
		}
		else if(sPos > 0) {
			sPos = sPos+1;
		}
		
		ePos = path.indexOf(".");
		if(ePos < 0) sPos = 0;
		
		if(ePos > 0 && ePos > sPos) {
			layoutName = path.substring(sPos, ePos);
			layoutName = layoutName.trim();
		}
		return layoutName;
	}
	
	public static void spiltTest() {
		String fileName = "abc.txt";
		String[] temp = fileName.split("\\."); // fileName.split("[.]");
    	String tableName = temp[0]; 
    	System.out.println( fileName +"=> "+ tableName);
	}

	public static void multiLangTest() {
		try {
			String multi = "¸Þ½ÃÁö";
			byte[] bytes = multi.getBytes("utf-8");
			System.out.println(new String(bytes, "encode"));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		System.out.println( extractLayoutName("SRC_LAYOUT1.a.b.c" ));
//		System.out.println( extractLayoutName("max( SRC_LAYOUT2.a.b.c)") );
//		System.out.println( extractLayoutName("sub( max( SRC_LAYOUT3.a.b.c), 10, 5 )") );
		
//		HashMap map = new  HashMap();
//		String value = null;
//		Iterator it = map.values().iterator();
//		if(it.hasNext()) value = (String) it.next();
//		
//		System.out.println(value);
//		System.out.println(
//		String.format("%s - Current adapter stopped forcely %s, skip adapter status change to %s", "TestAdapter", true, true)
//		);
		
		try {
			ConcurrentHashMap<String, String> monitors = new ConcurrentHashMap<>();
			
			monitors.put("4", "1");
			monitors.put("1", "1");
			monitors.put("5", "1");
			monitors.put("6", "1");
			
			Iterator<String> it = monitors.keySet().iterator();
	
			ArrayList<String> keyList = new ArrayList<>();
			while(it.hasNext()) {
				String key = it.next();
				if (key != null) {
					keyList.add(key);
				}
			}
			
			String[] finalVOkey = keyList.toArray(new String[0]);
	
			if (finalVOkey.length > 0) {
				Arrays.sort(finalVOkey);			
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		System.out.println("Done...");
	}
}
