import java.io.File;
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
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

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
public class Harvester{
	
	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	Date date = new Date() ;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd_HH-mm-ss") ;
	String Report = "K:\\IT\\Projects\\B2BHub\\TestReports\\SSO_"+dateFormat.format(date)+".html" ;
	String user;
	String pwd;

	
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
	
	public Harvester(String firstName,String password) {
		this.user = firstName;
		this.pwd = password;
		}
	
	@Parameters
	public static Collection testData() throws Exception {
		String[][] c = ExcelUtils.readExcelValues("C:\\Automation\\B2BHub_SSO.xlsx");
	return Arrays.asList(c);
	
	/*
	InputStream spreadsheet = new FileInputStream("C:\\Automation\\B2BHub.xlsx");
	  return new SpreadsheetData(spreadsheet).getData();
	 */ 
	}
	


	@SuppressWarnings("deprecation")
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
		
		//System.setProperty("webdriver.chrome.driver","C:\\certs\\chromedriver.exe");
		//ChromeOptions options = new ChromeOptions(); 
		//options.addArguments("disable-infobars"); 
		//driver = new ChromeDriver(options);
		
		System.setProperty("webdriver.gecko.driver","C:\\B2BHub\\B2BHubAutomation\\tools\\geckodriver\\win64\\geckodriver.exe");
		FirefoxProfile testProfile = new FirefoxProfile();
        testProfile.setAcceptUntrustedCertificates(true);
        testProfile.setAssumeUntrustedCertificateIssuer(true);
      //  testProfile.setPreference("browser.cache.memory.enable", false);
       // testProfile.setPreference("network.proxy.type", 0);
        FirefoxBinary ffBinary = new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe"));
        testProfile.setPreference("dom.file.createInChild", true);
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, testProfile);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		//capabilities.setCapability("marionette", false);
		driver = new FirefoxDriver(capabilities);
		//driver=new FirefoxDriver(Capabilities);
		//driver = new FirefoxDriver();
		 
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
	public void SSO_Validation() throws InterruptedException {

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

			    String expectedTitle = "Sign In";
			    Thread.sleep(15000);
			   
			    String actualTitle = driver.getTitle();
			    System.out.println("Title : "+ actualTitle);

			   if (actualTitle.contentEquals(expectedTitle)){
		
		    		logger.log(Status.PASS, MarkupHelper.createLabel("B2B Hub LoginPage is sucessfully loaded with title: " + actualTitle , ExtentColor.GREEN));
				    // time of the process of navigation and page load
				    double loadTime = (Double) jsf.executeScript(
				        "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
				    System.out.print("Timetaken for the login page to load");
				    System.out.println(loadTime + " seconds");
				    logger.log(Status.INFO, MarkupHelper.createLabel("Timetaken for the login page to load(Recommended is less than 1 second )  " + loadTime + " seconds" , ExtentColor.BLUE));
				    
				   // Thread.sleep(10000);
				    
					WebElement uname = driver.findElement(By.id("WC_AccountDisplay_FormInput_logonId_In_Logon_1"));
					//WebElement uname = driver.findElement(By.cssSelector("#WC_AccountDisplay_FormInput_logonId_In_Logon_1"));
					WebElement pass = driver.findElement(By.id("WC_AccountDisplay_FormInput_logonPassword_In_Logon_1"));
					
				    
					 //js.executeScript("arguments[0].click()", uname);
						//uname.clear();
					    uname.sendKeys(user);
					    
					   // js.executeScript("arguments[0].click()", pass);
					   // pass.clear();
					    pass.sendKeys(pwd);
					    
					    WebElement logmein = driver.findElement(By.id("signInButton"));
				    
					boolean active = logmein.isEnabled();
					if (active)
					{
						js = (JavascriptExecutor)driver;
						js.executeScript("arguments[0].click()", logmein);
						
						 checkPageIsReady();
						 System.out.println("Navigated to webpage "+ driver.getCurrentUrl());
						
						loadTime = (Double) jsf.executeScript(
							        "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
							    System.out.print("Timetaken for the signin action");
							    System.out.println(loadTime + " seconds");
						
						Thread.sleep(3000);
						
						
						
						
						try {
						
						WebElement account = driver.findElement(By.cssSelector("#AccountDisplay > div.signOn > section > div > div.signOnTitle.contain"));
						
						boolean logout = account.isEnabled();
						if (logout)
						{		
							logger.log(Status.PASS, MarkupHelper.createLabel("Sign In is sucessfull with user: " + user , ExtentColor.GREEN));
							logger.log(Status.INFO, MarkupHelper.createLabel("Timetaken for the sign in " + loadTime + " seconds" , ExtentColor.BLUE));
							
							Thread.sleep(3000);
							
							WebElement firstaccount = driver.findElement(By.xpath("//*[@id='loginAccountSelectionSelector']/button[1]"));
							String faccount = firstaccount.getText();
							System.out.println("Select Account " + faccount);
							
							 checkPageIsReady();
							 System.out.println("Navigated to webpage "+ driver.getCurrentUrl());
							
							js = (JavascriptExecutor)driver;
							js.executeScript("arguments[0].click()", firstaccount);
							
							loadTime = (Double) js.executeScript(
								        "return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
								    System.out.print("Timetaken for the accounts selection");
								    System.out.println(loadTime + " seconds");
							
							Thread.sleep(3000);
							
							
							WebElement accdetails = driver.findElement(By.xpath("//*[@id='accountSelectionForm']/a"));
							String saccount = accdetails.getText();
							System.out.println("Selected Account is " + saccount);
							
							if (faccount.equals(saccount))
							{
							logger.log(Status.PASS, MarkupHelper.createLabel("Correct Account has been selected : " + saccount , ExtentColor.GREEN));
							}else
							{
							logger.log(Status.FAIL, MarkupHelper.createLabel("Correct Account has not been selected : " + saccount , ExtentColor.RED));
							}
							
							WebElement signout = driver.findElement(By.xpath("//*[@id='AccountDisplay']/div[5]/header/div[2]/div[2]/a"));
							
							js = (JavascriptExecutor)driver;
							js.executeScript("arguments[0].click()", signout);
							
							logger.log(Status.PASS, MarkupHelper.createLabel("Sucessfully Signed out : ", ExtentColor.GREEN));
							
							 checkPageIsReady();
							 System.out.println("Navigated to webpage "+ driver.getCurrentUrl());
							
						} 
						}
						
						catch (NoSuchElementException e)
							
						{
							WebElement errormessage = driver.findElement(By.id("logonErrorMessage"));
							System.out.print("SignIn is unsuccessfull with user");
							logger.log(Status.PASS, MarkupHelper.createLabel("SignIn is unsuccessfull due to incorrect user or password: " , ExtentColor.GREEN));
							logger.log(Status.INFO, MarkupHelper.createLabel("Error Message : "+ errormessage.getText() , ExtentColor.BLUE));
							
						}

					}
					
				}
				else
				{
					System.out.println("Button is not enabled ");
				}

		    		} 
			    else 
		           {
			    	String actualTitle = driver.getTitle();
		            System.out.println("Login Failed");
		            logger.log(Status.FAIL, MarkupHelper.createLabel("B2B Hub LoginPage is not loaded, current title is " + actualTitle , ExtentColor.RED));
		            
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
		driver.close();
		String file1 = new String(Report);
		Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "K:\\IT\\Projects\\B2BHub\\TestReports\\Run.bat", file1}); 
			
	}

}
