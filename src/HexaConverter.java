import java.io.UnsupportedEncodingException;

public class HexaConverter {
    private static final char[] HEX_CHAR_ARRAY = 
    	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    
	public static String byte2Hex(byte[] abyte) {
        StringBuilder buf = new StringBuilder();
        int len = abyte.length;

        int high = 0;
        int low = 0;
        for (int i = 0; i < len; i++) {
            high = ((abyte[i] & 0xf0) >> 4);
            low = (abyte[i] & 0x0f);
            buf.append(HEX_CHAR_ARRAY[high]);
            buf.append(HEX_CHAR_ARRAY[low]);
        }
        return buf.toString();
    }
	
    public static void main(String[] args) throws UnsupportedEncodingException {
    	byte[] bytes = {
    			(byte)0x30
    			,(byte)0x30
    			,(byte)0x30
    			,(byte)0x35
    			,(byte)0x33
    			,(byte)0x02
    			,(byte)0x10
    			,(byte)0xF2
    			,(byte)0x24
    			,(byte)0x46
    			,(byte)0x81
    			,(byte)0x0A
    			,(byte)0x70
    			,(byte)0x84
    			,(byte)0x08
    			,(byte)0x80};
    	System.out.println(byte2Hex(bytes));
    	
    	byte[] newBytes = new String(bytes, "utf-8").getBytes("ms949");
    	System.out.println(byte2Hex(newBytes));
    	
    	newBytes = new String(bytes, "utf-8").getBytes("utf-8");
    	System.out.println(byte2Hex(newBytes));
    }
    
}
