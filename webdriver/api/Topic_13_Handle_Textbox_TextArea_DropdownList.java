package api;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.server.browserlaunchers.DoNotUseProxyPac;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Handle_Textbox_TextArea_DropdownList {
	WebDriver driver;
	String email, userID, password, loginPageUrl, customerId;
	String name, dobInput, dobOuput, address, city, state, pin, telephone;
	String editAdd, editCity, editState, editPin, editTelephone, editEmail;

	By nameBy = By.name("name");
	By dobBy = By.name("dob");
	By addrBy = By.name("addr");
	By cityBy = By.name("city");
	By stateBy = By.name("state");
	By pinnoBy = By.name("pinno");
	By telephonenoBy = By.name("telephoneno");
	By emailidBy = By.name("emailid");

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4/");
		loginPageUrl = driver.getCurrentUrl();

		name = "Donald Trump";
		dobInput = "01/02/1956";
		dobOuput = "1956-01-02";
		address = "912 village center";
		city = "saint louis";
		state = "missorius";
		pin = "978594";
		telephone = "0986755884";
		email = genarateEmail();

		editAdd = "77146 American Center";
		editCity = "Lexington";
		editState = "Kentuckey";
		editPin = "958236";
		editTelephone = "0594855768";
		editEmail = genarateEmail();
		// register
		driver.findElement(By.xpath("//a[text()='here']")).click();
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("btnLogin")).click();

		userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		password = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();

		// login
		driver.get(loginPageUrl);
		driver.findElement(By.name("uid")).sendKeys(userID);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("btnLogin")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//marquee[@class='heading3']")).getText(),
				"Welcome To Manager's Page of Guru99 Bank");
	}


	@Test
	public void TC_01_New_Customer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		// new
		driver.findElement(nameBy).sendKeys(name);
		driver.findElement(dobBy).sendKeys(dobInput);
		driver.findElement(addrBy).sendKeys(address);
		driver.findElement(cityBy).sendKeys(city);
		driver.findElement(stateBy).sendKeys(state);
		driver.findElement(pinnoBy).sendKeys(pin);
		driver.findElement(telephonenoBy).sendKeys(telephone);
		driver.findElement(emailidBy).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys("123456");

		driver.findElement(By.name("sub")).click();

		Assert.assertEquals(driver.findElement(By.className("heading3")).getText(),
				"Customer Registered Successfully!!!");
		// verify
		Assert.assertEquals(
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				dobOuput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
				address);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				telephone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				email);

		customerId = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();

	}

	@Test
	public void TC_02_Edit_Customer(){
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerId);
		driver.findElement(By.name("AccSubmit")).click();

		// verify value at Edit Customer page matching with value at New Customer
		Assert.assertEquals(driver.findElement(nameBy).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(dobBy).getAttribute("value"), dobOuput);
		Assert.assertEquals(driver.findElement(addrBy).getText(), address);
		Assert.assertEquals(driver.findElement(cityBy).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(stateBy).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(pinnoBy).getAttribute("value"), pin);
		Assert.assertEquals(driver.findElement(telephonenoBy).getAttribute("value"), telephone);
		Assert.assertEquals(driver.findElement(emailidBy).getAttribute("value"), email);

		// Edit at Edit Customer
		driver.findElement(addrBy).clear();
		driver.findElement(addrBy).sendKeys(editAdd);
		driver.findElement(cityBy).clear();
		driver.findElement(cityBy).sendKeys(editCity);
		driver.findElement(stateBy).clear();
		driver.findElement(stateBy).sendKeys(editState);
		driver.findElement(pinnoBy).clear();
		driver.findElement(pinnoBy).sendKeys(editPin);
		driver.findElement(telephonenoBy).clear();
		driver.findElement(telephonenoBy).sendKeys(editTelephone);
		driver.findElement(emailidBy).clear();
		driver.findElement(emailidBy).sendKeys(editEmail);

		driver.findElement(By.name("sub")).click();

		Assert.assertEquals(driver.findElement(By.className("heading3")).getText(),
				"Customer details updated Successfully!!!");
		// verify
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText(),
				customerId);
		Assert.assertEquals(
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(),
				dobOuput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
				editAdd);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(),
				editCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				editState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(),
				editPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				editTelephone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				editEmail);
	}

	public String genarateEmail() {
		Random rand = new Random();
		return "donald" + rand.nextInt(9999) + "@gihub.io";
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
