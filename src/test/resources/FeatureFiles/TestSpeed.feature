Feature: Check the Internet Speed 

  Scenario: Check the Internet Speed in a particular time interval
  	Given User is on HomePage of URL "https://www.speedtest.net/"
    When Validate that home page heading is displayed
    Then User checks Internet Speed in a particular time interval
    And User click on the Result button and download the Result
    Then Validate that the result file is downloaded
		And User mail the result file from user "xyz@gmail.com" and password "Xyz@1"
