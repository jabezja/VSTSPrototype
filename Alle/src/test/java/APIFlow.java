
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

import static io.restassured.RestAssured.*;

public class APIFlow {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    int returnCode = given().when().baseUri("https://devtestp-b2b.celesiogroup.com/").get().getStatusCode();
	    if (returnCode == 200) {
	       // webDriver.get(url);
	    	System.out.println(returnCode);
	    } else {
	    	System.out.println(returnCode);
	    };

	}

}
