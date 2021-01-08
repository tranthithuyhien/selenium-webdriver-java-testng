package api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Handle_Default_Dropdown {
	WebDriver driver;
	Select select;
	String firstName, lastName, email, company, password;
	String date, month, year;

	By genderMaleBy = By.id("gender-male");
	By firstNameBy = By.id("FirstName");
	By lastNameBy = By.id("LastName");
	By dateBy = By.name("DateOfBirthDay");
	By monthBy = By.name("DateOfBirthMonth");
	By yearBy = By.name("DateOfBirthYear");
	By emailBy = By.id("Email");
	By companyBy = By.id("Company");

	public String genarateEmail() {
		Random rand = new Random();
		return "bill" + rand.nextInt(9999) + "@github.io";
	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		firstName = "Bill";
		lastName = "Laden";
		email = genarateEmail();
		company = "Anqueda";
		password = "12124335";

		date = "3";
		month = "March";
		year = "1985";
	}

	//@Test
	public void TC_01_New_Customer() {
		driver.get("https://demo.nopcommerce.com/register");
		driver.findElement(By.className("ico-register")).click();
		driver.findElement(genderMaleBy).click();
		driver.findElement(firstNameBy).sendKeys(firstName);
		driver.findElement(lastNameBy).sendKeys(lastName);

		select = new Select(driver.findElement(dateBy));
		select.selectByVisibleText(date);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);

		select = new Select(driver.findElement(monthBy));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(yearBy));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		driver.findElement(By.id("Email")).sendKeys(email);
		driver.findElement(By.id("Company")).sendKeys(company);
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
		driver.findElement(By.id("register-button")).click();

		// check register successfully
		Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");

		// Navigate to My Account link
		driver.findElement(By.className("ico-account")).click();

		// verify data in My Account page
		Assert.assertTrue(driver.findElement(genderMaleBy).isSelected());
		Assert.assertEquals(driver.findElement(firstNameBy).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(lastNameBy).getAttribute("value"), lastName);

		select = new Select(driver.findElement(dateBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		select = new Select(driver.findElement(monthBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);
		select = new Select(driver.findElement(yearBy));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		Assert.assertEquals(driver.findElement(emailBy).getAttribute("value"), email);
		Assert.assertEquals(driver.findElement(companyBy).getAttribute("value"), company);
	}

	@Test
	public void TC_02_Multiple_Dropdown() {
		driver.get("https://automationfc.github.io/basic-form/");
		select= new Select(driver.findElement(By.name("user_job2")));
		select.selectByVisibleText("Mobile");
		select.selectByVisibleText("Desktop");
		select.selectByVisibleText("Security");
		
		List<String> itemText= new ArrayList<String>();
		itemText.add("Mobile");
		itemText.add("Desktop");
		itemText.add("Security");

		List<WebElement> itemSelected= select.getAllSelectedOptions();
		
		List<String> itemSelectedText= new ArrayList<String>();
		
		//verify 4 items selected
		Assert.assertEquals(itemSelected.size(), 3);
//		for (int j = 0; j < itemSelected.size(); j++) {
//			Assert.assertTrue(itemSelected.get(j).isSelected());
//			System.out.println(itemSelected.get(j).getText());
//		}
		for (WebElement item : itemSelected) {
			itemSelectedText.add(item.getText());
			System.out.println(item.getText());
		}
		
		Assert.assertTrue(itemSelectedText.contains("Mobile"));
		Assert.assertTrue(itemSelectedText.contains("Desktop"));
		Assert.assertTrue(itemSelectedText.contains("Security"));
		
		Assert.assertEquals(itemText, itemSelectedText);
	}

	@Test
	public void TC_03_Edit_Customer() {

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
