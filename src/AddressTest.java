import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class AddressTest {

	public AddressTest() {
		// TODO Auto-generated constructor stub
	}
	

	public static void main(String[] args)  {
		try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement(); 
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
//                    System.out.println("Address: " + address);
//                    System.out.println(" - isLoopbackAddress: " + address.isLoopbackAddress());
//                    System.out.println(" - isLinkLocalAddress: " + address.isLinkLocalAddress());
//                    System.out.println(" - isAnyLocalAddress: " + address.isAnyLocalAddress());
//                    
                    if (!address.isLoopbackAddress() 
                    		&& !address.isLinkLocalAddress() 
                    		&& !address.isAnyLocalAddress()) {
                        System.out.println("HostName " + address.getHostName() 
                        + " HostAddress " + address.getHostAddress());
                    }
                }
            }
            InetAddress inetAddress = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
			
			String serverMac = new String(network.getHardwareAddress());
			System.out.println("=> serverMac " + serverMac);
			
//            System.out.println("IP 주소에 해당하는 호스트를 찾을 수 없습니다.");
        } catch (SocketException|UnknownHostException e) {
            e.printStackTrace();
        }
	}

	
}
