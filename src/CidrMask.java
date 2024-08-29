import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CidrMask {

	public CidrMask() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String cidrNotation = "192.168.0.0/24";
		CidrMask test = new CidrMask();
		test.calculate(cidrNotation);

	}

	private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
    private static final String SLASH_FORMAT = IP_ADDRESS + "/(\\d{1,3})";
    private static final Pattern addressPattern = Pattern.compile(IP_ADDRESS);
    private static final Pattern cidrPattern = Pattern.compile(SLASH_FORMAT);
    private static final int NBITS = 32;

    private int netmask = 0;
    private int address = 0;
    private int network = 0;
    private int broadcast = 0;
    
    public static String integerToBitString(int number) {
        StringBuilder bitString = new StringBuilder();
        
        // Iterate over each bit in the integer
        for (int i = 31; i >= 0; i--) { // Assuming 32-bit integers (integers in Java are 32-bit signed)
            // Extract the i-th bit using bitwise AND operation with a mask
            int bit = (number >> i) & 1;
            
            // Append the bit to the bit string
            bitString.append(bit);
        }
        
        return bitString.toString();
    }
    
    private int rangeCheck(int value, int begin, int end) {
        if (value >= begin && value <= end) { // (begin,end]
            return value;
        }

        throw new IllegalArgumentException("Value [" + value + "] not in range ["+begin+","+end+"]");
    }
    
    private int matchAddress(Matcher matcher) {
        int addr = 0;
        for (int i = 1; i <= 4; ++i) {
            int n = (rangeCheck(Integer.parseInt(matcher.group(i)), 0, 255));
            System.out.printf("part: %03d %s\n", n, integerToBitString(n));
            addr |= ((n & 0xff) << 8*(4-i));            
            System.out.printf("addr : %14d %s\n", addr, integerToBitString(addr));
        }
        System.out.printf("addr : %14d %s\n", addr, integerToBitString(addr));
        return addr;
    }
    
	private void calculate(String mask) {
        Matcher matcher = cidrPattern.matcher(mask);

        if (matcher.matches()) {
            address = matchAddress(matcher);

            /* Create a binary netmask from the number of bits specification /x */
            int cidrPart = rangeCheck(Integer.parseInt(matcher.group(5)), 0, NBITS);
            for (int j = 0; j < cidrPart; ++j) {
                netmask |= (1 << 31 - j);
            }
            System.out.printf("cidrPart : %d netmask   : %14d %s\n", cidrPart, netmask, integerToBitString(netmask));
            
            /* Calculate base network address */
            network = (address & netmask);
            System.out.printf("cidrPart : %d network   : %14d %s\n", cidrPart, network, integerToBitString(network));
            /* Calculate broadcast address */
            broadcast = network | ~(netmask);
            System.out.printf("cidrPart : %d broadcast : %14d %s\n", cidrPart, broadcast, integerToBitString(broadcast));
        } else {
            throw new IllegalArgumentException("Could not parse [" + mask + "]");
        }
    }
}
