
@tag
Feature: Purchase the order from Ecommerce Website

 
 Background:
 Given I landed on Ecommerce Page
   
 @Regression
 Scenario Outline: Positive test of submitting the order
 Given Logged in with username <name> and password <password>
 When I add product <productName> from cart
 And Checkout <productName> and submit the order
 Then "THANKYOU FOR THE ORDER." message is displayed in the confirmation page

    Examples: 
      | name  											| password 			| productName    |
      | andresautomation@gmail.com  | Bucaramanga_1	| ZARA COAT 3    |
      | andresautomation1@gmail.com | Bucaramanga_1	| ADIDAS ORIGINAL|
