import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class EncodingMixTest {

	public EncodingMixTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String helloString = "안녕하세요.";
		System.out.println("Source : " + helloString);
		
		
		// String 을 euc-kr 로 인코딩.
		byte[] euckrBytes = helloString.getBytes(Charset.forName("euc-kr"));
		System.out.println();
		System.out.println("euc-kr - bytes length : " + euckrBytes.length);
		String decodedFromEucKr = new String(euckrBytes, "euc-kr");
		System.out.println("euc-kr - length : " + decodedFromEucKr.length());
		System.out.println("String from euc-kr : " + decodedFromEucKr);

		// String 을 utf-8 로 인코딩.
		byte[] utf8Bytes = decodedFromEucKr.getBytes("utf-8");

		System.out.println();
		System.out.println("utf-8 - bytes length : " + utf8Bytes.length);
		String decodedFromUtf8 = new String(utf8Bytes, "utf-8");
		System.out.println("utf-8 - length : " + decodedFromUtf8.length());
		System.out.println("String from utf-8 : " + decodedFromUtf8);

		ByteBuffer bf = ByteBuffer.allocate(1024);
		
		bf.put(euckrBytes);
		bf.put(utf8Bytes);
		
		byte[] mixedBytes = bf.array();
		
		byte[] eucBytes = new byte[euckrBytes.length];
		byte[] utfBytes = new byte[utf8Bytes.length];
		
		System.arraycopy(mixedBytes, 0, eucBytes, 0, eucBytes.length);
		System.arraycopy(mixedBytes, eucBytes.length, utfBytes, 0, utfBytes.length);
		
		System.out.println();
		System.out.println("eucBytes : " + new String(eucBytes, "euc-kr") );
		System.out.println("utfBytes : " + new String(utfBytes, "utf-8") );
		
		System.out.println("mixedBytes euc-kr: " + new String(mixedBytes, 0, eucBytes.length, "euc-kr") );
		System.out.println("mixedBytes utf-8: " + new String(mixedBytes, eucBytes.length, utfBytes.length, "utf-8") );
		
	}

}
