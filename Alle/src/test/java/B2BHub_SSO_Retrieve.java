import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.junit.runner.JUnitCore;

@RunWith(value = Parameterized.class)
public class B2BHub_SSO_Retrieve{
	
	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	Date date = new Date() ;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd_HH-mm-ss") ;
	String Report = "K:\\IT\\Projects\\B2BHub\\TestReports\\SSO_"+dateFormat.format(date)+".html" ;
	String user;
	String emailid;

	
	public static void main(String[] args) {
	    
		/*System.out.println("In main method");
	    JUnitCore jCore = new JUnitCore();
	    jCore.run(StoreLocator.class);
	    */
		
	     JUnitCore.main("StoreLocator"); 
	}
	
	 public void checkPageIsReady() {
		  
		  JavascriptExecutor js = (JavascriptExecutor)driver;
		  
		  
		  //Initially bellow given if condition will check ready state of page.
		  if (js.executeScript("return document.readyState").toString().equals("complete")){ 
		   System.out.println("Page Is loaded.");}
	 } 
	
	public B2BHub_SSO_Retrieve(String firstName,String email) {
		this.user = firstName;
		this.emailid = email;
		}
	
	@Parameters
	public static Collection testData() throws Exception {
		String[][] c = ExcelUtils.readExcelValues("C:\\Automation\\B2BHub_SSO_Retrieve.xlsx");
	return Arrays.asList(c);
	
	/*
	InputStream spreadsheet = new FileInputStream("C:\\Automation\\B2BHub.xlsx");
	  return new SpreadsheetData(spreadsheet).getData();
	 */ 
	}
	


	@Before
	public void setUp() throws Exception {
		
		htmlReporter = new ExtentHtmlReporter(Report);
		extent = new ExtentReports ();
		extent.attachReporter(htmlReporter);
		
		htmlReporter.config().setDocumentTitle("B2B Hub Portal");
		htmlReporter.config().setReportName("Sign In Function Validation");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		logger = extent.createTest("B2B Hub Sign In Validation");
		
		System.setProperty("webdriver.chrome.driver","C:\\BDD_Selenium\\celesioAutomation\\celesioAutomation\\tools\\chromedriver\\win32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions(); 
		options.addArguments("disable-infobars"); 
		driver = new ChromeDriver(options);
	    String baseUrl = "https://systestp-b2b.celesiogroup.com/";
	    String expectedTitle = "The UK’s Leading B2B Wholesaler | AAH Pharmaceuticals";
	    
	    driver.get(baseUrl);
	    
	    checkPageIsReady();
	    System.out.println("Navigated to webpage "+ driver.getCurrentUrl());

	    final JavascriptExecutor js = (JavascriptExecutor) driver;
	    // time of the process of navigation and page load
	    double loadTime = (Double) js.executeScript(
	        "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
	    System.out.print("Timetaken for the homepage to load");
	    System.out.println(loadTime + " seconds");
	    
	    
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
	    String actualTitle = driver.getTitle();
	    System.out.println("Title : "+ actualTitle);
	    
	    if (actualTitle.contentEquals(expectedTitle)){
	    	
            System.out.println("Setup Passed!");
    		logger.log(Status.PASS, MarkupHelper.createLabel("B2B Hub HomePage is sucessfully loaded with title: " + actualTitle , ExtentColor.GREEN));
    		} 
	    else 
           {
            System.out.println("Setup Failed");
            System.out.println(actualTitle);
            logger.log(Status.FAIL, MarkupHelper.createLabel("B2B Hub HomePage is not loaded, current title is " + actualTitle , ExtentColor.RED));
        }
		
	}
	
	@Test
	public void SSO_Retrieve() {

	try {
		
		//user = "501G13806145C";
		//pwd = "T";
		WebElement signin = driver.findElement(By.id("AAHPortalHeaderButtonsSignIn"));
		boolean enable = signin.isEnabled();

			if (enable)
			{
			   final JavascriptExecutor jsf = (JavascriptExecutor) driver;
			   JavascriptExecutor js = (JavascriptExecutor)driver;
			   js.executeScript("arguments[0].click()", signin);
			   checkPageIsReady();
			    String actualTitle = driver.getTitle();
			    System.out.println("Title : "+ actualTitle);
			    String expectedTitle = "Sign In";
			    Thread.sleep(3000);
			    
			    if (actualTitle.contentEquals(expectedTitle)){
		
		    		logger.log(Status.PASS, MarkupHelper.createLabel("B2B Hub LoginPage is sucessfully loaded with title: " + actualTitle , ExtentColor.GREEN));
				    // time of the process of navigation and page load
				    double loadTime = (Double) jsf.executeScript(
				        "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
				    System.out.print("Timetaken for the login page to load");
				    System.out.println(loadTime + " seconds");
				    logger.log(Status.INFO, MarkupHelper.createLabel("Timetaken for the login page to load(Recommended is less than 1 second )  " + loadTime + " seconds" , ExtentColor.BLUE));
				    
					//WebElement funame = driver.findElement(By.cssSelector("#WC_AccountDisplay_links_5"));
					WebElement funame = driver.findElement(By.partialLinkText("Forgotten username"));
					
					//WebElement fpass = driver.findElement(By.cssSelector("#WC_AccountDisplay_links_1"));
		
					   js.executeScript("arguments[0].click()", funame);
					   checkPageIsReady();
					   
					   WebElement email = driver.findElement(By.xpath("//*[@id='logonId']"));
					   WebElement remaind = driver.findElement(By.xpath("//*[@id='sendReminderButton']"));
					   email.sendKeys(emailid);
				    
					   js.executeScript("arguments[0].click()", remaind);
					   checkPageIsReady();
					   
					   logger.log(Status.PASS, MarkupHelper.createLabel("Forgot username functionality has worked " , ExtentColor.GREEN));
					   
					   Thread.sleep(3000);
					   WebElement fpass = driver.findElement(By.partialLinkText("Forgotten password"));
					   js.executeScript("arguments[0].click()", fpass);
					   checkPageIsReady();
					   
					 

					   WebElement ruser = driver.findElement(By.xpath("//*[@id='logonId']"));
					   WebElement remaindpass = driver.findElement(By.xpath("//*[@id='sendReminderButton']"));
					   ruser.sendKeys(user);
					   js.executeScript("arguments[0].click()", remaindpass);
					   checkPageIsReady();
					   
					   logger.log(Status.PASS, MarkupHelper.createLabel("Forgot password functionality has worked " , ExtentColor.GREEN));
					   
			    }
			    
			}
	}
	catch (Exception e)
	
	{
		System.out.println("Exception occurs : "+ e.getMessage());
	}
	
		 
	}
	

	
	@After
	
	public void tearDown() throws Exception {
		
		extent.flush();
		//driver.close();
		String file1 = new String(Report);
		Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "K:\\IT\\Projects\\B2BHub\\TestReports\\Run.bat", file1}); 
			
	}

}
