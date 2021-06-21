import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PrintLastDays {
	
	 
	public PrintLastDays() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ParseException {
		
		// 기준일
		String date = "20180209";
		// 남길 날자
		int days = 15;
		
		int [] findDays = new int[31];
		
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
		cal.setTime(sdf.parse(date));
		
		System.out.println(">>>> Current Date : " +date);
		
		// Do not delete current day.
		int monDay = cal.get(Calendar.DAY_OF_MONTH);
		findDays[monDay-1] = 1;
		
		for(int i=0; i<days; i++) {
			cal.add(Calendar.DAY_OF_YEAR, -1);
			monDay = cal.get(Calendar.DAY_OF_MONTH);
			System.out.println("prev Day = " +monDay);
			findDays[monDay-1] = 1;
		}
		
		System.out.println(">>>> find days");
		for(int i=0; i<findDays.length;i++) {
			if(findDays[i] == 1) {
				System.out.println(i+1);
			}
		}
		
		System.out.println(">>>> remove days");
		for(int i=0; i<findDays.length;i++) {
			if(findDays[i] == 0) {
				System.out.println(i+1);
			}
		}
		
	}

}
