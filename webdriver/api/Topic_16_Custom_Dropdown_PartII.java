package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_16_Custom_Dropdown_PartII {
	WebDriver driver;
	WebDriverWait explicitWait;
	Select select;
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		explicitWait = new WebDriverWait(driver, 30);
	}

	public void selectItemInCustomDropdown(String parentXpath, String allItemXpath, String expectedValueItem) {
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
	
	public void selectItemInEditDropdown(String parentXpath, String allItemXpath, String expectedValueItem) {
		driver.findElement(By.xpath(parentXpath)).sendKeys(expectedValueItem);

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
	
	public void selectMultiItemInDropdown(String parentXpath, String allItemXpath, String[] expectedValueItem) {
		// 1: click vào cái dropdown cho nó xổ hết tất cả các giá trị ra
		driver.findElement(By.xpath(parentXpath)).click();

		// 2: chờ cho tất cả các giá trị trong dropdown được load ra thành công
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemXpath)));

		List<WebElement> allItems = driver.findElements(By.xpath(allItemXpath));

		// Duyệt qua hết tất cả các phần tử cho đến khi thỏa mãn điều kiện
		for (WebElement childElement : allItems) {

			// "January", "April", "July"
			for (String item : expectedValueItem) {

				if (childElement.getText().equals(item)) {
					// 3: scroll đến item cần chọn (nếu như item cần chọn có thể nhìn thấy thì ko cần scroll)
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
					sleepInSecond(2);

					// 4: click vào item cần chọn
					jsExecutor.executeScript("arguments[0].click();", childElement);
					// childElement.click();
					sleepInSecond(2);

					List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
					System.out.println("Item selected = " + itemSelected.size());
					if (expectedValueItem.length == itemSelected.size()) {
						break;
					}
				}
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
 
	public String getHiddenText(String cssLocator) {
		return (String) jsExecutor.executeScript("return document.querySelector(\"" + cssLocator + "\").text");
	}

	public boolean areItemSelected(String[] itemSelectedText) {
		List<WebElement> itemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
		int numberItemSelected = itemSelected.size();

		String allItemSelectedText = driver.findElement(By.xpath("//button[@class='ms-choice']/span")).getText();
		System.out.println("Text da chon = " + allItemSelectedText);

		if (numberItemSelected <= 3 && numberItemSelected > 0) {
			for (String item : itemSelectedText) {
				if (allItemSelectedText.contains(item)) {
					break;
				}
			}
			return true;
		} else {
			return driver.findElement(By.xpath("//button[@class='ms-choice']/span[text()='" + numberItemSelected + " of 12 selected']")).isDisplayed();
		}
	}
	
	// @Test
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

	//@Test
	// error
	public void TC_02_Angular() {
		driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");
		
		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']//span[contains(@class,'e-search-icon')]",
				"//ul[@id='games_options']/li", "Badminton");
		sleepInSecond(2);
		Assert.assertEquals(getHiddenText("\"ejs-dropdownlist[id='games'] option\""), "Badminton");

		selectItemInCustomDropdown("//ejs-dropdownlist[@id='games']//span[contains(@class,'e-search-icon')]",
				"//ul[@id='games_options']/li", "Cricket");
		sleepInSecond(2);
		Assert.assertEquals(getHiddenText("\"ejs-dropdownlist[id='games'] option\""), "Cricket");
	}
	
	//@Test
	public void TC_03_React() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItemInCustomDropdown("//div[@role='listbox']/i","//div[@class='visible menu transition']//span", "Elliot Fu");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text()='Elliot Fu']")).isDisplayed());

		selectItemInCustomDropdown("//div[@role='listbox']/i","//div[@class='visible menu transition']//span", "Stevie Feliciano");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text()='Stevie Feliciano']")).isDisplayed());
	}
	
	//@Test 
	// su dung: getText().trim() de cat space dau cuoi
	public void TC_04_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		
		selectItemInCustomDropdown("//div[@class='btn-group']","//ul[@class='dropdown-menu']/li/a", "Second Option");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='btn-group']")).getText(),"Second Option") ;

		selectItemInCustomDropdown("//div[@class='btn-group']","//ul[@class='dropdown-menu']/li/a", "Third Option");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='btn-group']")).getText(),"Third Option") ;
	}

	//@Test 
	public void TC_05_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		
		selectItemInEditDropdown("//input[@class='search']","//div[@class='visible menu transition']//span", "Afghanistan");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(),"Afghanistan") ;

		selectItemInEditDropdown("//input[@class='search']","//div[@class='visible menu transition']//span", "American Samoa");
		sleepInSecond(2);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(),"American Samoa") ;
	}
	
	@Test
	public void TC_06_Advanced() {
		driver.get("http://multiple-select.wenzhixin.net.cn/examples#basic.html");
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		String[] month = { "January", "April", "July" };
		selectMultiItemInDropdown("//option/parent::select/following-sibling::div", "//option/parent::select/following-sibling::div//label/span", month);
		Assert.assertTrue(areItemSelected(month));

		driver.navigate().refresh();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		String[] months = { "January", "April", "July", "September" };

		selectMultiItemInDropdown("//option/parent::select/following-sibling::div", "//option/parent::select/following-sibling::div//label/span", months);
		Assert.assertTrue(areItemSelected(months));
	}


	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}