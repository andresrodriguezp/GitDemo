package Automation.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

//by implementing the iretry class it will rerun a testcase when it fails
public class Retry implements IRetryAnalyzer {

	/// with this variable we are indicating how many times to be re run, in this scenario is 1
	int count = 0;
	int maxtimes = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		// here is the logic for re run the failed testcase
		if (count<maxtimes)
		{
			count ++;
			return true;
		}
		
		return false;
		
	}

}
