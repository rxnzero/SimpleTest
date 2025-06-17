import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNamePattternGenerater {

	public static void main(String[] args) {
		String pattern = "AZZ22-%d{yyyyMMdd}-%d{hhmmss}.enc"; 
		System.out.println( pattern + " -> " + updateDatePattern(pattern) );
	}
	
	public static String updateDatePattern(String patterm) {
		String str[] = patterm.split("%d");
		String converted = "";
		for(int i=0; i<str.length; i++){
			if(i<1){
				converted = str[i];
			}else{
				String newMsg = "%d"+str[i];
				Matcher matcher = Pattern.compile("%d\\{([a-zA-Z \\-]+)\\}").matcher(newMsg);
				if (matcher.find()) {
					String datePattern = matcher.group(1);
					DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(datePattern);
					converted = converted + newMsg.replaceAll("%d\\{([a-zA-Z \\-]+)\\}", dateFormat.format(ZonedDateTime.now()));
				}
			}
		}
		return converted;
	}
}
