import java.net.URLDecoder;
import java.net.URLEncoder;

public class MultiLangTest {

	public MultiLangTest() {
		// TODO Auto-generated constructor stub
	}
	
	public static void multiLangTest() {
		String encode = "utf-8";
		try {
			String multi = "ข้อความ 메시지";
			byte[] bytes = multi.getBytes(encode);
			System.out.println(new String(bytes, encode));
			String urlEncoded = URLEncoder.encode(multi, encode);
			System.out.println(urlEncoded);
			String urlDecoded = URLDecoder.decode(urlEncoded, encode);
			System.out.println(urlDecoded);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void multiLangDecodeTest() {
		try {
			String encode = "utf-8";
			String multiUrlEncoded = 
			//"%EB%A9%94%EC%8B%9C%EC%A7%80";
			"%E0%B8%82%E0%B9%89%E0%B8%AD%E0%B8%84%E0%B8%A7%E0%B8%B2%E0%B8%A1+%EB%A9%94%EC%8B%9C%EC%A7%80";
			String decoded = URLDecoder.decode(multiUrlEncoded, encode);
			System.out.println(decoded);
			System.out.println(URLEncoder.encode(decoded, encode));
			byte[] bytes = decoded.getBytes(encode);
			String utf8Str = new String(bytes, encode);
			System.out.println(utf8Str);
			System.out.println(URLEncoder.encode(utf8Str, encode));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		multiLangTest();
		multiLangDecodeTest();
	}

}
