import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class CharsetHandling {
    public static void main(String[] args) {
        String input = "더샆";
        Charset eucKr = Charset.forName("EUC-KR");
        CharsetEncoder encoder = eucKr.newEncoder();

        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (encoder.canEncode(c)) {
                result.append(c);
            } else {
                // 지원되지 않는 문자를 "샵"으로 변환
                result.append("샵");
            }
        }

        String converted = result.toString();
        byte[] eucKrBytes = converted.getBytes(eucKr);
        String eucKrString = new String(eucKrBytes, eucKr);

        System.out.println("변환된 문자열: " + eucKrString);
    }
}
