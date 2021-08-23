# InternetSpeedCheck

An automated tool is created to measure internet speed based on the website: https://www.speedtest.net/ 

It is a Maven based framework using Selenium Cucumber BDD Framework and TestNg

Pre-requisites:
Java
Maven
Eclipse

Eclipse Plugins:
Maven
Cucumber
TestNg

This Framework can be run in Headless mode
	OS:  Windows, MAC, Linux
	Browser: Chrome, Firefox

To run this Automation Script:
	Parameter to be provided in TestSpeed.feature file: Mention the sender's email Id and password.(i.e. And User mail the result file from user "xyz@gmail.com" and password "Xyz@1")
	Parameter to be provided in mvn command: Browser, Total time of Test(in hour), Interval Time(in Minute)(Interval time must be greater that 2 min(based on internet speed)), Receiver’s Email.

This test can be run with below sample mvn command from project Home Path.
	mvn test -DbrowserName="chrome" -DtotalTimeInHour="0.167" -DintervalTimeInMin=“2" -DreceiverMail=“abc@gmail.com"

This tool is Creating a xlsx file with below values(saved in Project Home Path) and sending an email attaching this file.
DATE / TIME	
PING ms	
DOWNLOAD Mbps	
UPLOAD Mbps
LOCATION	
PROVIDER

Please refer the attached PPT for more details.