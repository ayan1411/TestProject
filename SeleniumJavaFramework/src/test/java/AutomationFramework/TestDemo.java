package AutomationFramework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestDemo extends ExtentReportsDemo
{
ChromeDriver driver;
public String projectPath = System.getProperty("user.dir");

@Test(dataProvider="testdata")
public void DemoProject(String username, String password) throws InterruptedException
{
	
System.setProperty("webdriver.chrome.driver", projectPath+"/Drivers/chromedriver/chromedriver.exe");
driver = new ChromeDriver();
ExtentTest test = extent.createTest("Test", "Testing login procedure");

test.log(Status.INFO, "Test Started");
driver.get("http://www.linkedin.com/");
test.log(Status.PASS, "Navigate to linkedin.com");

driver.manage().window().maximize();
test.log(Status.PASS, "Browser window maximized");

driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

driver.findElement(By.xpath("//*[@id=\"session_key\"]")).sendKeys(username);
test.log(Status.PASS, "Enter username");

driver.findElement(By.xpath("//*[@id=\"session_password\"]")).sendKeys(password);
test.log(Status.PASS, "Enter Password");

driver.findElement(By.xpath("/html/body/main/section[1]/div[2]/form/button")).click();
test.log(Status.PASS, "Sign in button clicked");

driver.close();
test.log(Status.INFO, "Test Completed");
Thread.sleep(2000);

}

@AfterMethod
void ProgramTermination()
{
driver.quit();
}

@DataProvider(name="testdata")
public Object[][] TestDataFeed()
{

ReadExcelFile config = new ReadExcelFile(projectPath+"/src/test/resources/ExcelSheet.xlsx");

int rows = config.getRowCount(0);

Object[][] credentials = new Object[rows-1][2];

for(int i=1;i<rows;i++)
{
credentials[i-1][0] = config.getData(0, i, 1);
credentials[i-1][1] = config.getData(0, i, 2);
}

return credentials;
}
}