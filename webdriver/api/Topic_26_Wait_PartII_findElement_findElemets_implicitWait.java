package api;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_26_Wait_PartII_findElement_findElemets_implicitWait {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		//driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", ".\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}
    //@Test
	public void TC_01_Find_Element() {
		driver.get("https://demo.nopcommerce.com/login");
		// 1 element
		System.out.println("1 - Start: " + getDateTimeNow());
		driver.findElement(By.xpath("//input[@name='Email']")).isDisplayed();
		System.out.println("1 - End: " + getDateTimeNow());

		// > 1 element
		System.out.println("2 - Start: " + getDateTimeNow());
		driver.findElement(By.xpath("//div[@class='sublist-toggle']/parent::li/a")).click();
		System.out.println("2 - End: " + getDateTimeNow());

		// 0 element
		System.out.println("3 - Start: " + getDateTimeNow());
		try {
			driver.findElement(By.xpath("//input[@name='email']")).isDisplayed();
		} catch (Exception e) {
			System.out.println("3 - End: " + getDateTimeNow());
			throw e;
		}
	}
	@Test
	public void TC_02_Find_Elements() {
		
		List<WebElement> elements;
		driver.get("https://demo.nopcommerce.com/register");

		// 1 element
		System.out.println("1 - Start: " + getDateTimeNow());
		elements = driver.findElements(By.xpath("//input[@name='Email']"));
		System.out.println("Element size = " + elements.size());
		System.out.println("1 - End: " + getDateTimeNow());

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// > 1 element
		System.out.println("2 - Start: " + getDateTimeNow());
		elements = driver.findElements(By.xpath("//div[@class='sublist-toggle']/parent::li/a"));
		System.out.println("Element size = " + elements.size());
		System.out.println("2 - End: " + getDateTimeNow());

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// 0 element
		System.out.println("3 - Start: " + getDateTimeNow());
		elements = driver.findElements(By.xpath("//input[@name='email']"));
		System.out.println("Element size = " + elements.size());
		System.out.println("3 - End: " + getDateTimeNow());

		elements.get(0).isDisplayed();
	}
    //@Test
	public void TC_03_Find_Elements() {
		driver.get("https://automationfc.github.io/multiple-fields/");

		List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
		System.out.println("Checkbox size = " + checkboxes.size());

		for (WebElement checkbox : checkboxes) {
			checkbox.click();
		}
	}

	//@Test
	public void TC_04_Implicit_Wait() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("http://juliemr.github.io/protractor-demo/");

		driver.findElement(By.xpath("//input[@ng-model='first']")).sendKeys("5");
		driver.findElement(By.xpath("//input[@ng-model='second']")).sendKeys("5");
		driver.findElement(By.id("gobutton")).click();

		// Đang đi tìm element vs thẻ h2 có text = 10
		Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='10']")).isDisplayed());

		// Đang đi tìm element vs thẻ h2
		// Assert.assertEquals(driver.findElement(By.tagName("h2")).getText(), "10");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public String getDateTimeNow() {
		Date date = new Date();
		return date.toString();
	}

}