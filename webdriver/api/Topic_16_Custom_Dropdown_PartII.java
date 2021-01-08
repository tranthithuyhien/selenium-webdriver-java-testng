package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Custom_Dropdown_PartII {
	WebDriver driver;
	WebDriverWait explicitWait;// tuong minh
	Select select;
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);// ngam dinh
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		explicitWait = new WebDriverWait(driver, 30);
	}

	public void selectItemInCustomDropdown(String parentXpath, String allItemXpath, String expectedValueItem) {
		// Click vào 1 element bất kì của dropdown để cho nó xổ hết tất cả các item ra
		driver.findElement(By.xpath(parentXpath)).click();

		// Chờ cho all các item được load lên
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));

		// lưu nó lại vào 1 list chứa những item
		List<WebElement> allItems = driver.findElements(By.xpath(allItemXpath));

		// co the viet gop
		// List<WebElement> allItems =
		// explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));

		// lấy ra text của từng element
		for (WebElement item : allItems) {
			if (item.getText().equals(expectedValueItem)) {
				item.click();
				break;
			}
		}
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getAngularSelectedValueByJS() {
		return (String) jsExecutor.executeScript("return document.querySelector(\"ejs-dropdownlist[id='games'] option\").text");
	}

	//@Test
	public void TC_01_JQuery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "3");
		sleepInSecond(2);
		Assert.assertEquals(driver
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"3");

		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "5");
		sleepInSecond(2);
		Assert.assertEquals(driver
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"5");

		selectItemInCustomDropdown("//span[@id='number-button']", "//ul[@id='number-menu']//div", "10");
		sleepInSecond(2);
		Assert.assertEquals(driver
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"10");
	}

	@Test
	public void TC_02_Angular() {
		driver.get(
				"https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");
		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']//span[contains(@class,'e-search-icon')]",
				"//div[@id='games_options']/li", "Badminton");
		sleepInSecond(2);
		Assert.assertEquals(getAngularSelectedValueByJS(),"Badminton");

		// 3 cach de kiem tra; getText, isDisplayed, getFirstSelectedOption
//		select = new Select(driver.findElement(By.xpath("//select[@name='games']")));
//		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Badminton");

		
		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']//span[contains(@class, 'e-search-icon')]",
				"//div[@id='games_options']/li", "Cricket");
		sleepInSecond(2);
		Assert.assertEquals(getAngularSelectedValueByJS(),"Cricket");;

		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']//span[contains(@class, 'e-search-icon')]",
				"//div[@id='games_options']/li", "Snooker");
		sleepInSecond(2);
		Assert.assertEquals(getAngularSelectedValueByJS(),"Snooker");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}