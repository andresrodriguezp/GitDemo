package Automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Automation.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// here with the use of webdrivermanager we will import the chromedriver
		WebDriverManager.chromedriver().setup();
		//here we are creating an object for crhomedriver into the Webdriver interface
		WebDriver driver = new ChromeDriver();
		
		String productname = "ZARA COAT 3";
		// here we are putting an implicitwait globaly
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		//goint to the URL
		driver.get("https://rahulshettyacademy.com/client/");
		
		//enetring the data for loging
		driver.findElement(By.id("userEmail")).sendKeys("andresautomation@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Bucaramanga_1");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		//grabbing the elements for iterate them and decide which one to add cart
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//here we are filtering for get the correct item
		WebElement prod = products.stream().filter(product-> product.findElement(By.cssSelector("b")).getText().equals(productname)).findFirst().orElse(null);
		
		//here we are going to click in the addproduct botton
		// here we are making use of parent/child relationship and also because buttons are two, with the last-of-type, we choose the last one
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//here we will use explicit wait for wait the item be added
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		//here we are clicking for going to the cart
		driver.findElement(By.cssSelector("button[routerlink=\"/dashboard/cart\"]")).click();
		
		// here we will see the list of the items and ensure the item we selected is here
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cartSection h3")));
		List<WebElement> cardproducts = driver.findElements(By.cssSelector(".cartSection h3"));
		
		Boolean match = cardproducts.stream().anyMatch(cardproduct-> cardproduct.getText().equalsIgnoreCase(productname));
		Assert.assertTrue(match);
		
		//here we are clicking in the checkout
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions action = new Actions(driver);
		
		action.sendKeys(driver.findElement(By.cssSelector("input[placeholder= 'Select Country']")), "co").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		//here we are selecting the country
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		//checking if the tittle match
		String confirmation = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmation.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.quit();
		


	}

}
