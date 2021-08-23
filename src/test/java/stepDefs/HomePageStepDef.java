package stepDefs;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePageObjects;
import testBase.TestBase;

public class HomePageStepDef extends TestBase{
	
	HomePageObjects hp = new HomePageObjects(driver);
	
	
	
	@Given("^User is on HomePage of URL \"([^\"]*)\"$")
	public void user_is_on_HomePage_of_URL(String url) {
		driver.get(url);
		
	}
	@When("^Validate that home page heading is displayed$")
	public void validate_that_home_page_heading_is_displayed() {
		hp.validatehomePageHeading();
	}
	@Then("^User checks Internet Speed in a particular time interval$")
	public void user_checks_Internet_Speed_in_a_particular_time_interval() throws InterruptedException, IOException {
		hp.takeIp();
		hp.GoButtonClick();
	}
	@And("^User click on the Result button and download the Result$")
	public void user_click_on_the_Result_button_and_download_the_Result() throws InterruptedException {
		hp.clickResults();
	}
	@Then("^Validate that the result file is downloaded$")
	public void validate_that_the_result_file_is_downloaded() throws InterruptedException, FileNotFoundException {
		hp.Handle_Dynamic_Webtable();
	}
	@And("^User mail the result file from user \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void user_mail_the_result_file_from_user_and_password(String senderMailId, String senderMailPassword) {
		hp.mailResult(senderMailId, senderMailPassword);
	}

}
