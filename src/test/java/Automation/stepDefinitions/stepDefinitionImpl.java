package Automation.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Automation.TestComponents.BaseTest;
import Automation.pageobjects.ConfirmationPage;
import Automation.pageobjects.ItemsAdded;
import Automation.pageobjects.LandingPage;
import Automation.pageobjects.ProductCatalogue;
import Automation.pageobjects.placeOrder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepDefinitionImpl extends BaseTest {
	
	public LandingPage landingpage;
	public ProductCatalogue productscatalogue;
	public ConfirmationPage confirmationpage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingpage = launchApplication();
	}
	
	// putting (.+) we are indicating that for the given step we will receive 2 characteres never mind what in username and password of any kind
	// this is a regular expresion and for this we will need to start with a ^ and finish with a $
	@Given("^Logged in with username (.+) and password (.+)$")
	// here in the method we are catching the username and password received from the submitorder featurefile, the values are always received in order
	public void Logged_in_with_username_and_password(String username, String password)
	{
		//here we are going to enter the data in our loging page using the method created in the pageobject class LandingPage
		productscatalogue = landingpage.LogginApplication(username, password);
	}
	
	@When("^I add product (.+) from cart$")
	public void I_add_product_from_cart(String productName)
	{
		// here we are getting the list of products
		List<WebElement> products = productscatalogue.getProductList();
				
		//here we are filtering for get the correct item , adding it to the cart and waiting for the item to be added
		productscatalogue.addProductToCar(productName);
	}
	
	@And("^Checkout (.+) and submit the order$")
	public void Checkout_and_submit_the_order(String productName)
	{
		//here we are clicking for going to the cart
				ItemsAdded itemsadded =productscatalogue.goingToCar();
						
				// here we will see the list of the items and ensure the item we selected is here

				Boolean match = itemsadded.correctItems(productName);
				Assert.assertTrue(match);
				
						
				//here we are clicking in the checkout
				placeOrder placeorder = itemsadded.checkout();
				
				//here we are selecting the country for pay
				placeorder.SelectingCountry("co");
				
				//here we are paying the order
				confirmationpage = placeorder.placeOrder();
	}
	
	//here by setting {string} im indicating that is the firsr position of the text im waiting a string already setted in the @Then
	@Then("{string} message is displayed in the confirmation page")
	public void message_is_displayed_in_the_confirmation_page(String string)
	{
		//checking if the tittle match
		String confirmation = confirmationpage.confirmationPaid();
		Assert.assertTrue(confirmation.equalsIgnoreCase(string));
		driver.quit();
	}
	
    @Then("^\"([^\"]*)\" message is displayed$")
    public void something_message_is_displayed(String strArg1) throws Throwable {
    	String message = landingpage.getErrorMessage();
		Assert.assertEquals(strArg1, message);
		driver.quit();
    }

}
