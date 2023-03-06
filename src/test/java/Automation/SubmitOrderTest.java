package Automation;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Automation.AbstractComponents.OrderPage;
import Automation.TestComponents.BaseTest;
import Automation.pageobjects.ConfirmationPage;
import Automation.pageobjects.ItemsAdded;
import Automation.pageobjects.LandingPage;
import Automation.pageobjects.ProductCatalogue;
import Automation.pageobjects.placeOrder;
import io.github.bonigarcia.wdm.WebDriverManager;

//we are extedning for the main Basetest for be able to use their methods, variables without create an object for the class
public class SubmitOrderTest extends BaseTest {
	
	
	//this test is for submit the orders and validate they were successfully submitted
	//here we are making use of testNG for run our testcases
	//additionally we are attaching a testcase to a dataprovider
	@Test(dataProvider="getData", groups = {"Purchase"})
	public void submitOrder(HashMap<String,String> Input) throws IOException {
		// TODO Auto-generated method stub
		
	    String country = "co";
		
		//here we are going to enter the data in our loging page using the method created in the pageobject class LandingPage
		ProductCatalogue productscatalogue = landingpage.LogginApplication(Input.get("email"), Input.get("password"));
		
		// here we are getting the list of products
		List<WebElement> products = productscatalogue.getProductList();
		
		//here we are filtering for get the correct item , adding it to the cart and waiting for the item to be added
		productscatalogue.addProductToCar(Input.get("productName"));
		
		//here we are clicking for going to the cart
		ItemsAdded itemsadded =productscatalogue.goingToCar();
				
		// here we will see the list of the items and ensure the item we selected is here

		Boolean match = itemsadded.correctItems(Input.get("productName"));
		Assert.assertTrue(match);
		
				
		//here we are clicking in the checkout
		placeOrder placeorder = itemsadded.checkout();
		
		//here we are selecting the country for pay
		placeorder.SelectingCountry(country);
		
		//here we are paying the order
		ConfirmationPage confirmationpage = placeorder.placeOrder();

		
		//checking if the tittle match
		String confirmation = confirmationpage.confirmationPaid();
		Assert.assertTrue(confirmation.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	//this test is for verify the orders that were submitted
	// we are putting a dependency that this test depends on another
	@Test(dependsOnMethods={"submitOrder"})
	public void OrderHistoryTest()
	{
		String productname = "ZARA COAT 3";
		//here we are going to enter the data in our loging page using the method created in the pageobject class LandingPage
		ProductCatalogue productscatalogue = landingpage.LogginApplication("andresautomation@gmail.com", "Bucaramanga_1");
		OrderPage orderpage = productscatalogue.goingToOrders();
		Assert.assertTrue(orderpage.orderItems(productname));
			
	}
	
	
	// here we are creating a data provider, basically is the data to be used in the testcases
	@DataProvider
	public Object[][] getData() throws IOException
	{
				List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//Automation//data//PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
		
		// we are going to create a 2 dimension array with type object, object is a type that will allows us to store any kind of value like int, string, etc..
				//we are also going to return the values for they to be used in the testcases as an input
				
				//here we are going to inport a json with the input data
				
				
				/*//we can use a HasMap which is basically a table with key value columns
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", "andresautomation@gmail.com");
				map.put("password", "Bucaramanga_1");
				map.put("productName", "ZARA COAT 3");
				
				HashMap<String, String> map1 = new HashMap<String, String>();
				map1.put("email", "andresautomation1@gmail.com");
				map1.put("password", "Bucaramanga_1");
				map1.put("productName", "ADIDAS ORIGINAL");
				
				return new Object[][] {{map},{map1}};*/
				
				// or we can just create the object without hasmap
				
				// return new Object[][] {{"andresautomation@gmail.com","Bucaramanga_1","ZARA COAT 3"}, {"andresautomation1@gmail.com","Bucaramanga_1","ADIDAS ORIGINAL"}}

	}

}
