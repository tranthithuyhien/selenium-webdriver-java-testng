package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Custom_Dropdown_PartI {
	WebDriver driver;
	WebDriverWait explicitWait;// tuong minh

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);// ngam dinh
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
		// Tradition for (Performance cao)
//		for (int i = 0; i < allItems.size(); i++) {
//			String actualValueItem= allItems.get(i).getText();
//			//kiểm tra nó có bằng với cái text cần tìm hay k
//			if(actualValueItem.equals(expectedValueItem)) {
//				//nếu như có thì click vào- thoát khỏi vòng lặp
//				allItems.get(i).click();
//				break;
//			}
//		}

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

	@Test
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

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}