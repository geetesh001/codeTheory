package businessScenario;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ReportValidation {
	
	@Test(dataProvider="reportValidationDP",dataProviderClass=dataProvider.DP_ReportValidation.class)
	public void reportValidation(String userName,String pwd,String dataEntrydt,String reportDt,String reading,String exptMin,
			String exptMax,String exptAvg) throws Exception
	{
		try{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resource\\drivers\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://damp-sands-8561.herokuapp.com/");
		
		//login
		driver.findElement(By.id("user_email")).sendKeys(userName);
		driver.findElement(By.id("user_password")).sendKeys(pwd);
		driver.findElement(By.xpath("//input[@name='commit']")).click();
		
		//Entering data
		WebElement levelLink = driver.findElement(By.xpath("//a[text()='Levels']"));
		explicitWait(driver,levelLink,5);
		levelLink.click();
		
		String[] value=reading.split(",");
		for(int i=0;i<value.length && i<4;i++){
		dataEntry(driver,dataEntrydt,value[i]);
		}
		
		
		//generating reports
		WebElement reportLink = driver.findElement(By.xpath("//a[text()='Reports']"));
		explicitWait(driver,reportLink,5);
		reportLink.click();
		
		driver.findElement(By.xpath("//a[text()='Daily']")).click();
		
		dailyReportvalidation(driver,reportDt,Double.valueOf(exptMin),Double.valueOf(exptMax),Double.valueOf(exptAvg));
		
		driver.quit();
		}catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}
	
	public void dataEntry(WebDriver driver,String dt,String data) throws InterruptedException
	{
		WebElement addNewButton = driver.findElement(By.xpath("//a[text()='Add new']"));
		explicitWait(driver,addNewButton,5);
		addNewButton.click();
		
		//If data entry date is mentioned
		if(!dt.equalsIgnoreCase("null")){
			String[] part=dt.split("-");
			WebElement yearElement = driver.findElement(By.xpath("//select[@id='entry_recorded_at_1i']"));
			explicitWait(driver,yearElement,5);
			selectValue(yearElement,part[0]);
			Thread.sleep(500);
			WebElement monthElement = driver.findElement(By.xpath("//select[@id='entry_recorded_at_2i']"));
			selectValue(monthElement,part[1]);
			Thread.sleep(500);
			WebElement dayElement = driver.findElement(By.xpath("//select[@id='entry_recorded_at_3i']"));
			selectValue(dayElement,part[2]);
		}
		WebElement dataEntry = driver.findElement(By.id("entry_level"));
		explicitWait(driver,dataEntry,5);
		dataEntry.sendKeys(data);
		driver.findElement(By.xpath("//input[@name='commit']")).click();	
	}
	
	public void dailyReportvalidation(WebDriver driver,String dt,Double exptMin,Double exptMax,Double exptAvg) throws InterruptedException
	{
		Thread.sleep(4000);
		String min=driver.findElement(By.xpath("//table//td[text()='"+dt+"']/following-sibling::td[3]")).getText();
		String max=driver.findElement(By.xpath("//table//td[text()='"+dt+"']/following-sibling::td[4]")).getText();
		String avg=driver.findElement(By.xpath("//table//td[text()='"+dt+"']/following-sibling::td[5]")).getText();
		
		System.out.println("min: "+min +" max: "+max+ " avg "+avg);
		SoftAssert asrt=new SoftAssert();
		asrt.assertEquals(Double.parseDouble(min), exptMin, 1e-5);
		asrt.assertEquals(Double.parseDouble(max), exptMax, 1e-5);
		asrt.assertEquals(Double.parseDouble(avg), exptAvg, 1e-5);
		asrt.assertAll();
	}
	
	public void explicitWait(WebDriver driver,WebElement element,int time)
	{
		WebDriverWait wait=new WebDriverWait(driver,time);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void selectValue(WebElement element,String value)
	{
		Select select=new Select(element);
		select.selectByVisibleText(value);
	}

}
