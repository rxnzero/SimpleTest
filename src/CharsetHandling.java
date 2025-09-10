import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.ConcurrentHashMap;

public class CharsetHandling {
	private static ConcurrentHashMap<Character, Character> replaceMap = new ConcurrentHashMap<>();
	
	private static void initMap() {
		replaceMap.put('샆', '샵');
        replaceMap.put('똠', '돔');        
	}
	public static void main(String[] args) {
		initMap();
		
		String charsetName = "EUC-KR";
		String input = "더샆똠방각하1";
		System.out.println("테스트 문자열 : " + input);
		testConvert(charsetName, input);
		charsetName = "ms949";
		testConvert(charsetName, input);
	}
	
	private static void testConvert(String charsetName, String input) {
		Charset eucKr = Charset.forName(charsetName);
        CharsetEncoder encoder = eucKr.newEncoder();

        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (encoder.canEncode(c)) {
                result.append(c);
            } else {
                // 지원되지 않는 문자를 "샵"으로 변환
            	Character change = replaceMap.get(c);
            	if(change == null) {
            		result.append(c);
            	}
            	else {
            		result.append(change);
            	}
            }
        }

        String converted = result.toString();
        byte[] eucKrBytes = converted.getBytes(eucKr);
        String eucKrString = new String(eucKrBytes, eucKr);

        System.out.println(charsetName + " 변환된 문자열 : " + eucKrString);
    }
}
