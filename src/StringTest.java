
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println( extractLayoutName("SRC_LAYOUT1.a.b.c" ));
		System.out.println( extractLayoutName("max( SRC_LAYOUT2.a.b.c)") );
		System.out.println( extractLayoutName("sub( max( SRC_LAYOUT3.a.b.c), 10, 5 )") );
		
	}

}
