package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_Css_Part_III {
	WebDriver driver;
	By myAccountBtnBy = By.xpath("//div[@class='footer']//a[text()='My Account']");
	By emailBy = By.id("email");
	By passwordBy = By.name("login[password]");
	By loginBtnBy = By.xpath("//button[@title='Login']");
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/");
	}

	@Test
	public void TC_01_Login_Empty_Eamil_And_Password() {
		driver.findElement(myAccountBtnBy).click();
		driver.findElement(emailBy).sendKeys("");
		driver.findElement(passwordBy).sendKeys("");
		driver.findElement(loginBtnBy).click();
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),
				"This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),
				"This is a required field.");
	}

	@Test
	public void TC_02_Login_Envalid_Email() {
		driver.findElement(myAccountBtnBy).click();
		driver.findElement(emailBy).sendKeys("123434234@12312.123123");
		driver.findElement(passwordBy).sendKeys("123456");
		driver.findElement(loginBtnBy).click();
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),
				"Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Login_Envalid_Password() {
		driver.findElement(myAccountBtnBy).click();
		driver.findElement(emailBy).sendKeys("automation@gmail.com");
		driver.findElement(passwordBy).sendKeys("123");
		driver.findElement(loginBtnBy).click();
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),
				"This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),
				"This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),
				"Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void TC_04_Login_Incorrect_Password() {
		driver.findElement(myAccountBtnBy).click();
		driver.findElement(emailBy).sendKeys("automation@gmail.com");
		driver.findElement(passwordBy).sendKeys("123123123");
		driver.findElement(loginBtnBy).click();
		
			Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(),
					"Invalid login or password.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
