package helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateHelper {
		
		
		public String getTodayDate(){
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");  
			LocalDateTime now = LocalDateTime.now();  
			String todayDate = dtf.format(now);
			return todayDate; 
		}

}
