package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_WebBrowser_Commands {
	WebDriver driver;

	@Test
	public void TC_01_Url() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/login/");
	    driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
	    Assert.assertEquals(driver.getCurrentUrl(),"http://live.demoguru99.com/index.php/customer/account/create/");
	}
	
	@Test
	public void TC_02_Title() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		Assert.assertEquals(driver.getTitle(),"Customer Login");
	    driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
	    Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
	}
	
	@Test
	public void TC_03_Navigate() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		Assert.assertEquals(driver.getCurrentUrl(),
				"http://live.demoguru99.com/index.php/customer/account/create/");
		
		driver.navigate().back();
		Assert.assertEquals(driver.getCurrentUrl(),
				"http://live.demoguru99.com/index.php/customer/account/login/");
	
		driver.navigate().forward();
		Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
		
		
	}
	@Test
	public void TC_04_PageSource() {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		String loginPageSource = driver.getPageSource();
		Assert.assertTrue(loginPageSource.contains("Login or Create an Account"));
		
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		String registerPageSource = driver.getPageSource();
		Assert.assertTrue(registerPageSource.contains("Create an Account"));
	}


	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
