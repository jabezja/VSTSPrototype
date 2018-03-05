import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/*
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
*/

public class GO_IE {
	
	private WebDriver driver;
	
	@SuppressWarnings("deprecation")
	@Test
	public void setUp() throws Exception {

	
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
	   	capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
	   	capabilities.setCapability("ignoreProtectedModeSettings",true);
	
		System.setProperty("webdriver.ie.driver", "C:\\Drivers\\iedriver\\win64\\IEDriverServer.exe");
		driver = new InternetExplorerDriver(capabilities);
		
		
	    String baseUrl = "http://www.harvester.co.uk";
	    String expectedTitle = "Harvester Restaurants.*";
	    
	    driver.get(baseUrl);
	    
	    checkPageIsReady();
	    System.out.println("Navigated to webpage "+ driver.getCurrentUrl());

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    // time of the process of navigation and page load
	    double loadTime = (Double) js.executeScript(
	        "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
	    System.out.print("Timetaken for the homepage to load");
	    System.out.println(loadTime + " seconds");
	    
	    
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
	    String actualTitle = driver.getTitle();
	    System.out.println("Title : "+ actualTitle);
	  
		   WebElement bookTable =  driver.findElement(By.cssSelector("img[alt='Book your table online']"));
		   js.executeScript("arguments[0].click()", bookTable);
		   Thread.sleep(5000);
		   
		   String currentTitle = driver.getTitle();
		   System.out.println("Title1 : "+ currentTitle);
		   
		   WebElement bookingLocation =  driver.findElement(By.id("location-tablebooking"));
		   
		   //js.executeScript("arguments[0].click()", bookingLocation);
		   
		   WebElement chooseDate =  driver.findElement(By.xpath("//a[@class='main-btn continue-to-date']"));
		   
		   js.executeScript("document.getElementById('location-tablebooking').focus();");
		   Thread.sleep(3000);
		   js.executeScript("$('#"+"location-tablebooking"+"').val('"+"B7 5SA"+"');");
		  Thread.sleep(3000);
		   Actions action = new Actions(driver);
		   Thread.sleep(3000);
		   action.moveToElement(bookingLocation).sendKeys("B7 5SA").sendKeys(Keys.DOWN).sendKeys(Keys.RETURN).perform();

		   Thread.sleep(5000);
		 
		  // WebElement chooseDate =  driver.findElement(By.xpath("//a[@class='main-btn continue-to-date']"));
		   js.executeScript("arguments[0].click()", chooseDate);
		   
	   	   currentTitle = driver.getTitle();
		   System.out.println("Title2 : "+ currentTitle);
		   Thread.sleep(3000);
		   
		   WebElement chooseNumber =  driver.findElement(By.xpath("//select[@id='adults-tablebooking']"));
		   
		   Select selectBox = new Select(chooseNumber);
		   selectBox.selectByValue("3");

		   WebElement chooseDate1 =  driver.findElement(By.cssSelector(".ui-datepicker-calendar > tbody:nth-child(2) > tr:nth-child(5) > td:nth-child(4) > a:nth-child(1)"));
		   
		   
		   WebElement chooseMeal = driver.findElement(By.xpath("//select[@id='mealtime-tablebooking']"));
		   Select selectBox1 = new Select(chooseMeal);
		   selectBox1.selectByValue("Lunch");
		   Thread.sleep(2000);
		   
		   WebElement chooseTime =  driver.findElement(By.xpath("//a[@class='main-btn forward-btn show-available-times']"));
		   js.executeScript("arguments[0].click()", chooseTime);
		   //chooseTime.click();
		   
		   
		   Thread.sleep(3000);
		   
		   currentTitle = driver.getTitle();
		   System.out.println("Title3 : "+ currentTitle);

		   Thread.sleep(3000);
		   
		   WebElement TimeSlot = driver.findElement(By.id("tablebooking_timeslot_150000"));
		  
		   js.executeScript("arguments[0].click()", TimeSlot);
		   
		  //TimeSlot.click();
		   
		   Thread.sleep(3000);
		   
		   WebElement Review = driver.findElement(By.cssSelector(".continue-to-review"));
		   //Review.click();
		   js.executeScript("arguments[0].click()", Review);
		  
		   currentTitle = driver.getTitle();
		   System.out.println("Title4 : "+ currentTitle);
		   driver.close();
		   driver.quit();
		
	}
	
	 public void checkPageIsReady() {
		  
		  JavascriptExecutor js = (JavascriptExecutor)driver;
		  
		  
		  //Initially bellow given if condition will check ready state of page.
		  if (js.executeScript("return document.readyState").toString().equals("complete")){ 
		   System.out.println("Page Is loaded.");}
	 } 
	 
	    private static DesiredCapabilities getInternetExploreDesiredCapabilities() {
	        System.setProperty("webdriver.ie.driver", "C:\\Drivers\\iedriver\\win64\\IEDriverServer.exe");

	        DesiredCapabilities capabilities = DesiredCapabilities
	                .internetExplorer();
	        
	        

	   	 capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
	   	capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
	   	capabilities.setCapability("requireWindowFocus", true);
	        capabilities
	                .setCapability(
	                        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
	                        true);
	        capabilities.setVersion("11");
	        return capabilities;
	    }




}
