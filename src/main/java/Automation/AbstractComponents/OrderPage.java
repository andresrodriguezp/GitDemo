package Automation.AbstractComponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Automation.pageobjects.placeOrder;

public class OrderPage extends AbstractComponents {
	
	//here im creating a local variable "driver"
	
	WebDriver driver;
	
	//here im creating a constructor
	//constructor is used for intitialaze variables in the current class
	//in order our local variavle "driver" has knowledge of the main driver created in the testcase, we initialize is using the constructor and using
	//the main driver as an input of the constructor
	public OrderPage(WebDriver driver)
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
	
	//initializing webelement for click the checkouut button"
	@FindBy(css= "tr td:nth-child(3)")
	List<WebElement> orders;
	
	//this is a method for check if the order item correct
	public Boolean orderItems(String productName)
	{
		Boolean match = orders.stream().anyMatch(orderproduct-> orderproduct.getText().equalsIgnoreCase(productName));
		return match;
		}
	
}
