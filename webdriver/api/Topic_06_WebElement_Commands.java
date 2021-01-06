package api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_WebElement_Commands {
	WebDriver driver;
	By emailTxtBy = By.id("mail");
	By educationTxtAreaBy = By.id("edu");
	By ageUnder18BtnBy = By.id("under_18");
	
	By jobRole1DropdownBy =By.id("job1");
	By jobRole2DropdownBy =By.id("job2");
	By developmentChkBy =By.id("development");
	By slider01By=By.id("slider-1");
	
	By passwordTxtBy =By.id("password");
	By ageDisableRadioBtnBy =By.id("radio-disabled");
	By biographyTxtBy =By.id("bio");
	By jobRole3DropdownBy =By.id("job3");
	By interestDisableChkBy =By.id("check-disbaled");
	By slider02By=By.id("slider-2");
	
	By languageJavaChkBy = By.id("java");
	
	//Common function
	public boolean isElementDisplayed(By by) {
		if (driver.findElement(by).isDisplayed()) {
			System.out.println("Element is displayed");
			return true;
		}else {
			System.out.println("Element is not displayed");
			return false;
		}
	}
	
	public boolean isElementSelected(By by) {
		if (driver.findElement(by).isSelected()) {
			System.out.println("Element is selected");
			return true;
		}else {
			System.out.println("Element is not selected");
			return false;
		}
	}
	
	public boolean isElementEnable(By by) {
		if (driver.findElement(by).isEnabled()) {
			System.out.println("Element is enable");
			return true;
		}else {
			System.out.println("Element is disable");
			return false;
		}
	}


	//@Test
	public void TC_01_Element_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.navigate().refresh();
		if(isElementDisplayed(emailTxtBy)) {
			driver.findElement(emailTxtBy).sendKeys("Automation Testing");
		}
		if(isElementDisplayed(educationTxtAreaBy)) {
			driver.findElement(educationTxtAreaBy).sendKeys("Automation Testing");
		}
		if(isElementDisplayed(ageUnder18BtnBy)) {
			driver.findElement(ageUnder18BtnBy).click();
		}
	}
	
	//@Test
	public void TC_02_Element_Enable_Disable() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.navigate().refresh();
		Assert.assertTrue(isElementEnable(emailTxtBy));
		Assert.assertTrue(isElementEnable(ageUnder18BtnBy));
		Assert.assertTrue(isElementEnable(educationTxtAreaBy));
		Assert.assertTrue(isElementEnable(jobRole1DropdownBy));
		Assert.assertTrue(isElementEnable(jobRole2DropdownBy));
		Assert.assertTrue(isElementEnable(developmentChkBy));
		Assert.assertTrue(isElementEnable(slider01By));
		
		Assert.assertFalse(isElementEnable(passwordTxtBy));
		Assert.assertFalse(isElementEnable(ageDisableRadioBtnBy));
		Assert.assertFalse(isElementEnable(biographyTxtBy));
		Assert.assertFalse(isElementEnable(jobRole3DropdownBy));
		Assert.assertFalse(isElementEnable(interestDisableChkBy));
		Assert.assertFalse(isElementEnable(slider02By));
	}
	
	//@Test
	public void TC_03_Element_Selected() throws InterruptedException{
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.navigate().refresh();
		driver.findElement(ageUnder18BtnBy).click();
		driver.findElement(languageJavaChkBy).click();
		Thread.sleep(3000);
		Assert.assertTrue(isElementSelected(ageUnder18BtnBy));
		Assert.assertTrue(isElementSelected(languageJavaChkBy));
		
		driver.findElement(ageUnder18BtnBy).click();
		driver.findElement(languageJavaChkBy).click();
		Thread.sleep(3000);
		Assert.assertTrue(isElementSelected(ageUnder18BtnBy));
		Assert.assertFalse(isElementSelected(languageJavaChkBy));
	}
	
	@Test
	public void TC_04_Validate_Register_Form() throws InterruptedException {
		driver.get("https://login.mailchimp.com/signup/");
		driver.navigate().refresh();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("new_username")).sendKeys("Automation test");
		Assert.assertFalse(isElementEnable(By.id("create-account")));
		
		//lower case
		driver.findElement(By.id("new_password")).sendKeys("auto");
		Thread.sleep(2000);
		Assert.assertFalse(isElementEnable(By.id("create-account")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed'and text()='One lowercase character']")));
		
		//special character
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("auto@$");
		Thread.sleep(2000);
		Assert.assertFalse(isElementEnable(By.id("create-account")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed'and text()='One lowercase character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='special-char completed'and text()='One special character']")));
		
		//upper case
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Auto@#");
		Thread.sleep(3000);
		Assert.assertFalse(isElementEnable(By.id("create-account")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed'and text()='One lowercase character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='special-char completed'and text()='One special character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed'and text()='One uppercase character']")));
		
		//8 characters
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Automation@");
		Thread.sleep(2000);
		Assert.assertFalse(isElementEnable(By.id("create-account")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed'and text()='One lowercase character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='special-char completed'and text()='One special character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed'and text()='One uppercase character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']")));
		
		//number
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("aumation@1");
		Thread.sleep(2000);
		Assert.assertFalse(isElementEnable(By.id("create-account")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed'and text()='One lowercase character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='special-char completed'and text()='One special character']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='8-char completed' and text()='8 characters minimum']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='number-char completed'and text()='One number']")));
		
		//valid password
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Automation@1");
		Thread.sleep(2000);
		Assert.assertTrue(isElementEnable(By.id("create-account")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//h4[text()=\"Your password is secure and you're all set!\"]")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed'and text()='One lowercase character']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='special-char completed'and text()='One special character']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed'and text()='One uppercase character']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='8-char completed'and text()='8 characters minimum']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='number-char completed'and text()='One number']")));
	}
	

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
