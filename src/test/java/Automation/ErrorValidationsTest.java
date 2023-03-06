package Automation;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Automation.TestComponents.BaseTest;
import Automation.TestComponents.Retry;
import Automation.pageobjects.ConfirmationPage;
import Automation.pageobjects.ItemsAdded;
import Automation.pageobjects.ProductCatalogue;
import Automation.pageobjects.placeOrder;

//we are extedning for the main Basetest for be able to use their methods, variables without create an object for the class
public class ErrorValidationsTest extends BaseTest {
	
	public String productname = "ZARA COAT 3";
	//here we are making use of testNG for run our testcases
	// for run testcases in groups we can use the parameter groups for run them
	// for rerun the failed testcases we use retryAnalizer
	
	@Test(groups={"ErrorHandling"},retryAnalyzer=Retry.class)
	public void errorvalidations() throws IOException {
		// TODO Auto-generated method stub
		
		//here we are going to enter the data in our loging page using the method created in the pageobject class LandingPage
		landingpage.LogginApplication("andresautomation@gmail.com", "Bucaramanga_2");
		String message = landingpage.getErrorMessage();
		Assert.assertEquals("Incorrect email or password.", message);
		}
	
	//here we are creating a testcase for validate an incorrect item added to the cart
	@Test
	public void productErrorValidation() throws IOException {
		
		//here we are going to enter the data in our loging page using the method created in the pageobject class LandingPage
		ProductCatalogue productscatalogue = landingpage.LogginApplication("andresautomation1@gmail.com", "Bucaramanga_1");
		
		// here we are getting the list of products
		List<WebElement> products = productscatalogue.getProductList();
		
		//here we are filtering for get the correct item , adding it to the cart and waiting for the item to be added
		productscatalogue.addProductToCar(productname);
		
		//here we are clicking for going to the cart
		ItemsAdded itemsadded =productscatalogue.goingToCar();
				
		// here we will see the list of the items and ensure the item we selected is here

		Boolean match = itemsadded.correctItems("ZARA COAT 33");
		Assert.assertFalse(match);
		
		System.out.println("updating something");
		System.out.println("updating something else");
	}

}
