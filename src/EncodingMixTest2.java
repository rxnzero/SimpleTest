import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class EncodingMixTest2 {

	public EncodingMixTest2() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String helloString = "안녕하세요A";
		System.out.println("Source : " + helloString);
		
		byte[] utf8Bytes = helloString.getBytes(Charset.forName("utf-8"));
		System.out.println("utf-8 - bytes length : " + utf8Bytes.length);
		String decodedFromUtf8 = new String(utf8Bytes, "utf-8");
		System.out.println("utf-8 - length : " + decodedFromUtf8.length());
		System.out.println("String from utf-8 : " + decodedFromUtf8);

		System.out.println();
		
		byte[] euckrBytes = decodedFromUtf8.getBytes("euc-kr");
		
		System.out.println("euc-kr - bytes length : " + euckrBytes.length);
		String decodedFromEucKr = new String(euckrBytes, "euc-kr");
		System.out.println("euc-kr - length : " + decodedFromEucKr.length());
		System.out.println("String from euc-kr : " + decodedFromEucKr);
		
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
		
	}

}
