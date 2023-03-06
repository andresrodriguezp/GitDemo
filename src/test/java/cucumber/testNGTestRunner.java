package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//in order to execute our test using different options like the ones in testng, we will need to use @cucumberoptions
// for example we can define which test feature to run by using features
//for example for define which test definitions to run we can use glue
//for example for being able to read the results, we can use monochrome
//for generate report of the execution we can use plugin plus key:value
//for indicate which scenarios to run, we can use the tags attribute
@CucumberOptions(features="src/test/java/cucumber",glue="Automation.stepDefinitions",monochrome=true,tags="@Regression",plugin= {"html:target/cucumber.html"})

//in order cucumber be able to use testng assertions we will need to extend from testngclass
public class testNGTestRunner extends AbstractTestNGCucumberTests {

}
