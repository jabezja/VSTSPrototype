import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Popups {
	
	 private String alertFileLocation = "file:///C:/Popup/alert.html";
	 private WebDriver driver;

	   @Test
       public void triggerBothPopups() throws Exception {
		   
			System.setProperty("webdriver.chrome.driver", "C://Drivers//chromedriver//win64//chromedriver.exe");
			ChromeOptions options = new ChromeOptions(); 
			options.addArguments("disable-infobars"); 
			options.addArguments("start-maximized");
			options.addArguments("ignore-certifcate-errors");
			options.addArguments("test-type");
			driver = new ChromeDriver(options);	
			
           driver.get(alertFileLocation);

           WebElement generateAlertOne = driver.findElement(By.id("js_alert"));
           WebElement generateAlertTwo = driver.findElement(By.id("html_alert"));

          generateAlertOne.click();
           driver.switchTo().alert().accept();
           
           File screenshot = new File("screenshot.png");
           FileOutputStream screenshotStream = new FileOutputStream(screenshot);
           screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
           screenshotStream.close();
           System.out.println("Screenshot saved at: " + screenshot.getAbsolutePath());

           WebElement closeHtmlAlertButton = driver.findElement(By.cssSelector(".ui-button"));
          
           generateAlertTwo.click();
                    closeHtmlAlertButton.click();
       }

}
