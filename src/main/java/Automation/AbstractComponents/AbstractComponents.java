package Automation.AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Automation.pageobjects.ItemsAdded;

public class AbstractComponents {
	
	//here we are creating a local driver for use it using the information received from the main driver
	WebDriver driver;
// here we are creating a constructor for receive objects or attributes
	// in this scenario we are receiving "driver"
	public AbstractComponents(WebDriver driver) {
		
		this.driver = driver;
		//in order to use pagefactory we will need to initialize the elements here, giving knowledge of the main driver from the testcases
		PageFactory.initElements(driver, this);

		
	}
	
	//initialializing the WebElemnt for going to cart
	@FindBy(css= "button[routerlink=\"/dashboard/cart\"]")
	WebElement toCar;
	
	//initialializing the WebElemnt for going to orders
	@FindBy(css= "button[routerlink='/dashboard/myorders']")
	WebElement toOrders;
	
	//here we are defining a method for wait for an element to appear
	//the input of the method is a By, because here we dont use a webelement here we indicate by what to locate the element
	public void waitForElementToAppear(By findBy)
	{
		//here we are creating object for explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	//here we are creating a method for wait until for an element to appear looking by WebElement
	public void waitForElement(WebElement element)
	{
		//here we are creating object for explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToDissapear (WebElement animating)
	{
		//here we are creating object for explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(animating));
	}
	
	//here we are cresting a method for going to the car, we are putting it here because is global
	public ItemsAdded goingToCar()
	{
		toCar.click();
		//because we know that when we go to car we will open a new page
		//here we will create an object of the new pageobject in order to make it easrier and dont create it in the testcase
		ItemsAdded itemsadded = new ItemsAdded(driver);
		return itemsadded;
	}
	
	public OrderPage goingToOrders()
	{
		toOrders.click();
		OrderPage orderpage = new OrderPage(driver);
		return orderpage;
	}

}
