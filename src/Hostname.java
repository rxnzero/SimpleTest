import java.net.InetAddress;
import java.net.UnknownHostException;

public class Hostname {

	public Hostname() {
	}

	public static void main(String[] args) {
		try {
			System.out.println("hostname ["+InetAddress.getLocalHost().getHostName()+"]") ;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
