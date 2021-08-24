package pageObjects;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import helper.DateHelper;
import helper.NoInternetHelper;
import helper.TryCatchHelper;
import helper.WaitHelper;

import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePageObjects {
	
	WaitHelper waitHelper;
	public WebDriver driver;	
	public HomePageObjects(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitHelper = new WaitHelper(driver);
	}
	
	TryCatchHelper tc = new TryCatchHelper();
	DateHelper dh = new DateHelper();
	NoInternetHelper ni = new NoInternetHelper();
	Scanner scanner = new Scanner(new InputStreamReader(System.in));
	
	
	ArrayList<String> errorTimes = new ArrayList<String>();
	 
	private int count = 0;
	private int intervalTimeInMiliSec = 0;
	private int intervalTimeInSec = 0;
	private int waitTimeInMiliSec = 0;
	//private int waitTimeInSec =0;
	private Instant startTime ;
	private Instant stopTime;

	@FindBy(xpath="//a[@class='masthead-logo']")
	private WebElement heading;
	
	@FindBy(xpath="//span[@class='start-text']")
	private WebElement GoButton;
	
	@FindBy(xpath="//span[@class='result-data-large number result-data-value upload-speed']/div")
	private WebElement uploadSpeedvalue;
	
	@FindBy(xpath="//div[@class='result-area result-area-nav result-area-nav-right'] //a[@href='/results']")
	private WebElement results;
	
	@FindBy(xpath="//button[@id='export-results-button']")
	private WebElement exportResult;
	
	
	@FindBy(xpath="//div[@class='test-status error']")
	private WebElement connectionError;
	
	@FindBy(xpath="//a[@class='notification-dismiss close-btn']")
	private WebElement connectionErrorCloseButton;
			
	
	public void takeIp() {
	    String totalTime = System.getProperty("totalTimeInHour");
	    float totalTimeInHourInFloat = Float.parseFloat(totalTime);
	    System.out.println("User Input of total monitoring time: " + totalTimeInHourInFloat);

	    String intervalTime = System.getProperty("intervalTimeInMin");
	    int intervalTimeInMinInInt = Integer.parseInt(intervalTime);
	    System.out.println("User input of the interval time: " + intervalTimeInMinInInt);
	    
	    float c = (totalTimeInHourInFloat*60)/intervalTimeInMinInInt;
	    count = (int) Math.floor(c);
	    System.out.println("count"+count);
	    intervalTimeInSec = intervalTimeInMinInInt*60;
		intervalTimeInMiliSec = intervalTimeInSec*1000;
		//waitTimeInSec = intervalTimeInSec/2;
		waitTimeInMiliSec = intervalTimeInMiliSec/2;
		
	}
	
	public String getTodayDate(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");  
		LocalDateTime now = LocalDateTime.now(); 
		now.minusSeconds(intervalTimeInSec);
		String todayDate = dtf.format(now);
		return todayDate; 
	}

	public void validatehomePageHeading() {
		
		if(ni.internetHelper()) {
			tc.elementDisplayed(heading);
		}else {
			System.out.println("Internet is not connected");
		}
			
	}
		
		
		
		 

	public void GoButtonClick() throws InterruptedException, IOException {
		for(int i = 1; i<=count;)
		{
			if(ni.internetHelper()) {
				if(GoButton.isDisplayed()) {
			   		GoButton.click();
			   		startTime = Instant.now();
			   		
			   	}else {
			   		break;
			   	}
				if(!(waitHelper.waitForURL(intervalTimeInSec))){
					errorTimes.add(this.getTodayDate());
					i++;
					driver.navigate().refresh();
		    		//Thread.sleep(5000);
				    	  						
				}else {
					if(i==count){
						break;
					}else {
						stopTime = Instant.now();
						long timeElapsed = Duration.between(startTime, stopTime).toMillis();
						Thread.sleep(intervalTimeInMiliSec-timeElapsed);
						System.out.println("Result Display time " + timeElapsed+ "milisec");
						i++;
					}
				}
					
				}
			else {
				errorTimes.add(this.getTodayDate());
				Thread.sleep(intervalTimeInMiliSec);
				i++;
				driver.navigate().refresh();
    			//Thread.sleep(5000);
				
				
			}
		}
	}
	

	
	public void clickResults() throws InterruptedException {
		
		if(waitHelper.waitForURL(intervalTimeInSec)){
			results.click();
		}else {
			if(ni.internetHelper()) {
				System.out.println("internet is connected but result button is not loaded");
	    	  }
	    	  else {
	    		    System.out.println("Internet is not connected");
			   		
			   		errorTimes.add(dh.getTodayDate());
				}
			
		}
		
	}
	
	
	
	  public void Handle_Dynamic_Webtable() throws FileNotFoundException {
		  
		  if(waitHelper.waitForElement(exportResult, 10)) {
	       
		  String fileName = System.getProperty("user.dir")+"/InternetSpeedTestReport.xlsx";
	        FileOutputStream fos = new FileOutputStream(fileName);                                
	       
	        XSSFWorkbook wkb = new XSSFWorkbook();      
	        XSSFSheet sheet1 = wkb.createSheet("SpeedData");

	        WebElement mytable = driver.findElement(By.xpath("//table[@id='results-history-table']"));
	       
	        List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
	        int rows_count= rows_table.size();
	        
	        
	       System.out.println("Number of Rows " + rows_count);
	        XSSFRow excelRow1 = sheet1.createRow(0);
	        List<WebElement> head_row = rows_table.get(0).findElements(By.tagName("th"));
            int Head_count = (head_row.size()-1);
        
           
                for(int i=0;i<Head_count;i++) {
                    XSSFCell excelCell = excelRow1.createCell(i);
                    excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    String celtext = head_row.get(i).getText();
                    excelCell.setCellValue(celtext);
                    
            }
	        

	        for (int row = 1; row < rows_count; row++) {
	           
	            XSSFRow excelRow = sheet1.createRow(row);
	           
	            	List<WebElement> Columns_row =  rows_table.get(row).findElements(By.tagName("td"));
	            	
	                int columns_count = (Columns_row.size()-1);
	              
	                for (int column = 0; column < columns_count; column++) {
	                	
	                    XSSFCell excelCell = excelRow.createCell(column);
	                    excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);
	                   
	                    String celtext1 = Columns_row.get(column).getText();
	                    if (column==0) {
	                    String finalCellValue = celtext1.substring(0, 10)+ " "+celtext1.substring(11);
	                    excelCell.setCellValue(finalCellValue);
	                    }else {
	                    	excelCell.setCellValue(celtext1);
	                    }
	                    
	                }
                    } 
	   
	     
	        if (!(errorTimes.isEmpty())) {
	        
	        	for(int k =0; k<errorTimes.size();k++)
            	{
	        		XSSFRow excelRow2 = sheet1.createRow(k+rows_count);
                	 for (int column = 0; column < Head_count; column++) {
 	                    XSSFCell excelCell = excelRow2.createCell(column);
 	                    excelCell.setCellType(XSSFCell.CELL_TYPE_STRING);
 	                    if(column==0) {
 	                    	String celtext = errorTimes.get(k);
	 	                    excelCell.setCellValue(celtext);
 	                    }else {
 	                    String celtext = "No Internet Connection";
 	                    excelCell.setCellValue(celtext);
 	                    
 	                    }
                	 }
            	 }
	        	
	        }
	       
	        try {
	            fos.flush();
	            wkb.write(fos);
	         
	            fos.close();
	           
	       } catch (FileNotFoundException e) {
	           e.printStackTrace();
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
		  }else {
		  
		  if(ni.internetHelper()) {
    		  
			  System.out.println("internet is connected but export result button is not loaded");
    	  }
    	  else {
    		    System.out.println("Internet is not connected");
			}
		  }
	    }

	
	
	
	
	
	
	    
	
	
	
	
	public void mailResult(final String senderMailId, final String senderMailPassword) {
		
		// Create object of Property file
		Properties props = new Properties();
 
		// this will set host of server- you can change based on your requirement 
		props.put("mail.smtp.host", "smtp.gmail.com");
 
		// set the port of socket factory 
		props.put("mail.smtp.socketFactory.port", "465");
 
		// set socket factory
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
 
		// set the authentication to true
		props.put("mail.smtp.auth", "true");
 
		// set the port of SMTP server
		props.put("mail.smtp.port", "465");
 
		// This will handle the complete authentication	
		
		
		Session session = Session.getDefaultInstance(props,
 
				new javax.mail.Authenticator() {
 
					protected PasswordAuthentication getPasswordAuthentication() {
						
						    
					return new PasswordAuthentication(senderMailId,senderMailPassword);

					}
 
				});
 
		try {
 
			// Create object of MimeMessage class
			Message message = new MimeMessage(session);
			
			String receiverMailId = System.getProperty("receiverMail");
 
			// Set the from address
			message.setFrom(new InternetAddress(receiverMailId));
 
			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiverMailId));
            
                        // Add the subject link
			message.setSubject("Internet Speed Test Report");
 
			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();
 
			// Set the body of email
			messageBodyPart1.setText("Hi,\n\nPlease find attached the internet speed report.\n\nPallavi");
 
			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
 
			// Mention the file which you want to send
			String filename = System.getProperty("user.dir")+"/InternetSpeedTestReport.xlsx";
 
			// Create data source and pass the filename
			DataSource source = new FileDataSource(filename);
 
			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));
 
			// set the file
			messageBodyPart2.setFileName(filename);
 
			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();
 
			// add body part 1
			multipart.addBodyPart(messageBodyPart2);
 
			// add body part 2
			multipart.addBodyPart(messageBodyPart1);
 
			// set the content
			message.setContent(multipart);
 
			// finally send the email
			Transport.send(message);
 
			System.out.println("=====Email Sent=====");
 
		} catch (MessagingException e) {
 
			throw new RuntimeException(e);
 
		}
 
	}
		 
}

