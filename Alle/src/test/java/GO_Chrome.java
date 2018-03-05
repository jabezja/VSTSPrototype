import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class GO_Chrome {
	
	private WebDriver driver;

	@Test
	public void setUp()  {

		System.setProperty("webdriver.chrome.driver", "C://Drivers//chromedriver//win64//chromedriver.exe");
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("disable-infobars"); 
		options.addArguments("start-maximized");
		options.addArguments("ignore-certifcate-errors");
		options.addArguments("test-type");
		driver = new ChromeDriver(options);		
	    String baseUrl = "https://harvester.co.uk/";
	    String expectedTitle = "Harvester Restaurants.*";
	    
	    driver.get(baseUrl);
	    
	    checkPageIsReady();
	    System.out.println("Navigated to webpage "+ driver.getCurrentUrl());
	    
	    driver.findElement(By.xpath("//a[contains(.,'Accept & Close')]")).click();
	    


	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    // time of the process of navigation and page load
	    double loadTime = (Double) js.executeScript(
	        "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
	    System.out.print("Timetaken for the homepage to load");
	    System.out.println(loadTime + " seconds");
	    
	    try 
	    {
	    
	  //  driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
	   
	    String actualTitle = driver.getTitle();
	    System.out.println("Title : "+ actualTitle);
	  
	    
		   WebElement bookTable =  driver.findElement(By.cssSelector("img[alt='Book your table online']"));
		   bookTable.click();
		   Thread.sleep(5000);
		   
		   String currentTitle = driver.getTitle();
		   System.out.println("Title1 : "+ currentTitle);
		   
		   WebElement bookingLocation =  driver.findElement(By.id("location-tablebooking"));
		   bookingLocation.clear();
		   
		  // js.executeScript("document.getElementById('location-tablebooking').focus();");
		   // js.executeScript("document.getElementById('location-tablebooking').value = 'B7 5SA';");
			   
			   
		   bookingLocation.sendKeys("B7 5SA");
		   bookingLocation.sendKeys(Keys.DOWN);
		   bookingLocation.sendKeys(Keys.RETURN);
		   Thread.sleep(5000);
		 
		   WebElement chooseDate =  driver.findElement(By.xpath("//a[@class='main-btn continue-to-date']"));
		   chooseDate.click();
		   
	   	   currentTitle = driver.getTitle();
		   System.out.println("Title2 : "+ currentTitle);
		   
		   WebElement chooseNumber =  driver.findElement(By.xpath("//select[@id='adults-tablebooking']"));
		   
		   Select selectBox = new Select(chooseNumber);
		   selectBox.selectByValue("1");
		   Thread.sleep(2000);

		   WebElement chooseDate1 =  driver.findElement(By.cssSelector(".ui-datepicker-calendar > tbody:nth-child(2) > tr:nth-child(5) > td:nth-child(4) > a:nth-child(1)"));
		   js.executeScript("arguments[0].click()", chooseDate1);
		   
		   Thread.sleep(5000);

		   WebElement chooseMeal = driver.findElement(By.id("mealtime-tablebooking"));
		   chooseMeal.sendKeys("Lunch");
		   
		   WebElement chooseTime =  driver.findElement(By.xpath("//a[@class='main-btn forward-btn show-available-times']"));
		   chooseTime.click();
		   
		   
		   Thread.sleep(2000);
		   
		   currentTitle = driver.getTitle();
		   System.out.println("Title3 : "+ currentTitle);

		   Thread.sleep(2000);
		   
		   WebElement TimeSlot = driver.findElement(By.id("tablebooking_timeslot_134500"));
		   
		   js.executeScript("arguments[0].click()", TimeSlot);
		   
		  // TimeSlot.click();
		   
		   WebElement Review = driver.findElement(By.cssSelector(".continue-to-review"));
		   
		   Review.click();

		   currentTitle = driver.getTitle();
		   System.out.println("Title4 : "+ currentTitle);
		   
	    }
		catch (Exception e)
		
		{
			WebElement errormessage = driver.findElement(By.id("logonErrorMessage"));
			System.out.print("SignIn is unsuccessfull with user");
				
		}

	}
    private static DesiredCapabilities getChromeDesiredCapabilities() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-b2c-security");
        chromeOptions.addArguments("--test-type");
        capabilities.setCapability("chrome.verbose", false);
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return capabilities;
    }
	 public void checkPageIsReady() {
		  
		  JavascriptExecutor js = (JavascriptExecutor)driver;
		  
		  
		  //Initially bellow given if condition will check ready state of page.
		  if (js.executeScript("return document.readyState").toString().equals("complete")){ 
		   System.out.println("Page Is loaded.");}
	 } 




}
