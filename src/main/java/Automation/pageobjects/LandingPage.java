package Automation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Automation.AbstractComponents.AbstractComponents;

//by using extends im saying this class it can use all methods, attributes defined there without create an object
//where the AbstractComponents is the parent class
public class LandingPage extends AbstractComponents {
	
	//here im creating a local variable "driver"
	
	WebDriver driver;
	
	//here im creating a constructor
	//constructor is used for intitialaze variables in the current class
	//in order our local variavle "driver" has knowledge of the main driver created in the testcase, we initialize is using the constructor and using
	//the main driver as an input of the constructor
	public LandingPage(WebDriver driver)
	{
		//for sent object or attributes from a child class to a parent we call use "super"
		//in this scenario the child class is "LandingPage" and the parent class is "AbstractComponents"
		// all childs classes needs to return the same information to parent class
		super(driver);
		//here we are going to initialize our local variable "driver"
		this.driver = driver;
		//in order to use pagefactory we will need to initialize the elements here, giving knowledge of the main driver from the testcases
		PageFactory.initElements(driver, this);
		
	}
	
	/* WebElement useremail = driver.findElement(By.id("userEmail"));*/
	
	//There is also another way to initialize the WebElemnt, using "@pagefactory"
	@FindBy(id= "userEmail")
	WebElement useremail;
	
	@FindBy(id= "userPassword")
	WebElement userPassword;
	
	@FindBy(id= "login")
	WebElement login;
	
	@FindBy(css= "[class*='flyInOut']")
	WebElement errorMessage;
	
	//method for set the data in the landing page
	public ProductCatalogue LogginApplication (String email, String password )
	{
		useremail.clear();
		useremail.sendKeys(email);
		userPassword.clear();
		userPassword.sendKeys(password);
		login.click();
		// because we know that after click the login button we will go to the other page
		// here we create an object for this other pageobject in order to make easier the core and dont create it in the testcase
		ProductCatalogue productscatalogue = new ProductCatalogue(driver);
		return productscatalogue;
	}
	
	public String getErrorMessage()
	{
		waitForElement(errorMessage);
		String message = errorMessage.getText();
		return message;
		
	}
	
	//method for go to the landing page
	public void goTo ()
	{
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
	
	}
