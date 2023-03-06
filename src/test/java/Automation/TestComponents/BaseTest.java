package Automation.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Automation.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	// here we are creating a public variable for the driver be access everywhere in this class
	public WebDriver driver;
	
	//creating public object of type Landingpage for it can be access directly from childclasses without necesssity of create any object
	public LandingPage landingpage;
	
	//here we are creating a method for initialize the driver and give the common waits and wondows size
	public WebDriver initializeDriver() throws IOException
	{
		
		//we can create a properties file with some global variables to be used in every testecase
		//for do it we will make use of the properties class and fileinputstream class
		// first we will convert our file with the global variables into inputstream file
		
		//here we are creating an object of the class, and as and input we should indicate the location of the properties file
		//additionally in order to share the framework and that it be used by many people, we can't hardoce our local path, we nned to make it global
		//using system.getproperty, we can set that. it will automatically set the user default project location
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Automation\\Resources\\GlobalData.properties");
		
		// now we will create object for the properties class
		Properties properties = new Properties();
		// here we ill load our inputstreamfile as an input for the properties
		properties.load(fis);
		
		//here we are getting property value from maven
		//using java ternary operator, this is like an if else, here checking if browser has value use the one from maven else use the one from the global file
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : properties.getProperty("browser");
		
		// here we extracting the values for the properties set in the property file, in this case is the browser to use, we are storing it into a variable
		//String browserName = properties.getProperty("browser");
		
		//here we are setting conditions for indicate which browser to use
		
		if(browserName.contains("chrome"))
		{	
			//setting chrome options for run headless mode in chrome
			//first we create an object of chrome options
			ChromeOptions options = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		
		// setting a conditional for run in headless mode it contains work headless
		if(browserName.contains("headless"))
		{
		//adding headless argument for run headless mode
		options.addArguments("headless");
		}
		//here we are creating an object for crhomedriver into the Webdriver interface
		driver = new ChromeDriver(options);
		
		//in the case the test be executed in headlessmode we will need to maximaze the screen in order all elements be visible
		driver.manage().window().setSize(new Dimension(1440,990));
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		
		// here we are putting an implicitwait globaly
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	//here we are creting a method for read some input data from json, and convert it to hasmap
	public List<HashMap<String,String>> getJsonDataToMap(String filePath) throws IOException
	{
		//here we are importing a json file and converting it to string
		//we are creating a new file which is located in the location provider that is the user space plus the project location
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		// now we are going to convert the string to hashmap
		// first we create an object to the class object mapper, this class allows us to convert from json to javaobjects
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
		return data;
	}
	
	//Here we are going to create a method for take an screenshot
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException
	{
		
		//here we are typecasting the driver for be able to use the screenshot methods and take the screen shot
		TakesScreenshot ts = (TakesScreenshot)driver;
		//here we are taking the screenshot and puting in an output file
		File source = ts.getScreenshotAs(OutputType.FILE);
		//here we are saving put file into an specific location
		File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
	}
	
	//here we are creating a methid for launch the application
	//additionally putting the anotation beforemethod for ensure this part be executed before run anytestcase
	//for ensure beforemethod be always executed never mind if we set any filter in the xml, we will put parameter alwaysRun with value true
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		//here we are creating and object for the class that is taking care of the landing page
		landingpage = new LandingPage(driver); 
		//here we are using a method in our pageobject class for open the url
		landingpage.goTo();
		return landingpage;
	}
	
	//here we are creating a method for close the page after the testcase be executed
	//using aftermethod anottation for ensure it be executed after the testcase
	//for ensure beforemethod be always executed never mind if we set any filter in the xml, we will put parameter alwaysRun with value true
	@AfterMethod(alwaysRun=true)
	public void closePage()
	{
		driver.quit();
	}
	
	
}
