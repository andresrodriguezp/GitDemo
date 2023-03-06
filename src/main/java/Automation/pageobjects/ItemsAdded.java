package Automation.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Automation.AbstractComponents.AbstractComponents;

//by using extends im saying this class it can use all methods, attributes defined there without create an object
//where the AbstractComponents is the parent class
public class ItemsAdded extends AbstractComponents {
	
	//here im creating a local variable "driver"
	
	WebDriver driver;
	
	//here im creating a constructor
	//constructor is used for intitialaze variables in the current class
	//in order our local variavle "driver" has knowledge of the main driver created in the testcase, we initialize is using the constructor and using
	//the main driver as an input of the constructor
	public ItemsAdded(WebDriver driver)
	{
		//for sent object or attributes from a child class to a parent we call use "super"
		//in this scenario the child class is "LandingPage" and the parent class is "AbstractComponents"
		// all childs classes needs to return the same information to parent class
		super(driver);
		//here we are going to initialize our local variable "driver"
		this.driver=driver;
		//in order to use pagefactory we will need to initialize the elements here, giving knowledge of the main driver from the testcases
		PageFactory.initElements(driver, this);
		
	}
	
	/* WebElement useremail = driver.findElement(By.id("userEmail"));*/
	
	//There is also another way to initialize the WebElemnt, using "@pagefactory"
	@FindBy(id= ".cartSection h3")
	List<WebElement> addedItems;
	
	//initializing webelement for click the checkouut button"
	@FindBy(css= ".totalRow button")
	WebElement checkout;
	
	By itemsAdded = By.cssSelector(".cartSection h3");
	
	// this is a method for retrieve the items added
	public List<WebElement> itemsAdded()
	{
		waitForElementToAppear(itemsAdded);
		List<WebElement> cardproducts = driver.findElements(By.cssSelector(".cartSection h3"));
		return cardproducts;
	}
	
	//this is a method for check if the items added are correct
	public Boolean correctItems(String productName)
	{
		List<WebElement> cardproducts = itemsAdded();
		Boolean match = cardproducts.stream().anyMatch(cardproduct-> cardproduct.getText().equalsIgnoreCase(productName));
		return match;
		}
	
	// this ia a method for click in the checkout
	public placeOrder checkout()
	{
		checkout.click();
		placeOrder placeorder = new placeOrder(driver); 
		return placeorder;
	}
	}
