package helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NoInternetHelper {
	
	public boolean internetHelper() {
		try {
	         URL url = new URL("http://www.google.com");
	         URLConnection connection = url.openConnection();
	         connection.connect();
	        // System.out.println("Internet is connected");
	         return true;
	      } catch (MalformedURLException e) {
	        // System.out.println("Internet is not connected");
	         return false;
	      } catch (IOException e) {
	         //System.out.println("Internet is not connected");
	         return false;
	      }
		
	}

}
