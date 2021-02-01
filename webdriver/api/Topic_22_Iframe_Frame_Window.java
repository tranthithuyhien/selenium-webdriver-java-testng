package api;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class Topic_22_Iframe_Frame_Window {
	WebDriver driver;
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Iframe() {
		driver.get("https://automationfc.com/");
		sleepInSecond(5);
		//switch vao iframe cua facebook
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@title, 'fb:page Facebook Social Plugin')]")));
		Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Automation FC']")).getText(), "Automation FC");
		Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Automation FC']/parent::div//following-sibling::div")).getText(), "2,114 likes");
		String likeText = driver.findElement(By.xpath("//a[@title='Automation FC']/parent::div//following-sibling::div")).getText();
		
		int likeNumber = Integer.parseInt(likeText.split(" ")[0].replace(",", ""));
		System.out.println(likeNumber);
		assertThat(likeNumber, greaterThan(1900));
		
		//switch to top window (inluding all iframe)
		driver.switchTo().defaultContent();
		
		Assert.assertEquals(driver.findElement(By.xpath("//a[@title='Automation FC Blog — ']")).getText(), "AUTOMATION FC BLOG");
		
		//switch to youtube iframe
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='YouTube video player']")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='movie_player']")).isDisplayed());
	}

	//@Test
	public void TC_02_Windows() {
		driver.get("https://kyna.vn/");

		// Lấy ra id tại tab có driver đang đứng (active)
		String parentID = driver.getWindowHandle();
		System.out.println("Parent ID = " + parentID);

		// Click to 'Vietnamwork' link at pre-footer
		clickToElementByJS("//img[@alt='vietnamworks.com']");

		switchToWindowByTitle("Tuyển dụng, việc làm, tìm việc làm nhanh mới nhất | VietnamWorks");

		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Tìm kiếm công việc mơ ước']")).isDisplayed());
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.vietnamworks.com/?utm_source=from_kyna");

		// Switch về tab parent
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		Assert.assertEquals(driver.getCurrentUrl(), "https://kyna.vn/");

		// Click to 'Vietnamwork Learning' link at pre-footer
		clickToElementByJS("//img[@alt='enterprise.vietnamworkslearning.com']");

		switchToWindowByTitle("Trang chủ | Vietnamworks Learning for Enterprise");
		Assert.assertTrue(driver.findElement(By.cssSelector(".ga_login_header")).isDisplayed());

		// Switch về tab parent
		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		Assert.assertEquals(driver.getCurrentUrl(), "https://kyna.vn/");

		// Click to 'Facebook' link at footer
		clickToElementByJS("//img[@alt='facebook']");

		switchToWindowByTitle("Kyna.vn - Trang chủ | Facebook");
		Assert.assertTrue(driver.findElement(By.id("email")).isDisplayed());

		areAllTabClosedWithoutParent(parentID);
	}

	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath(locator)));
	}

	public void switchToWindowByID(String parentID) {
		// Lấy ra tất cả các ID của window/ tab đang có
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp for-each để duyệt qua từng ID
		for (String runWindow : allWindows) {

			// Kiểm tra cái ID nào mà khác vs parent (A)
			if (!runWindow.equals(parentID)) {

				// Switch vào ID (cửa sổ/ tab) đó
				driver.switchTo().window(runWindow);

				// Thoát khỏi vòng lặp khi thỏa mãn điều kiện rồi
				break;
			}
		}
	}

	public void switchToWindowByTitle(String pageTitle) {
		// Lấy ra tất cả các ID của window/ tab đang có
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp duyệt qua các ID này
		for (String runWindows : allWindows) {

			// Switch vào từng cửa sổ/ tab trước
			driver.switchTo().window(runWindows);

			// Lấy ra cái title của tab đó
			String currentPageTitle = driver.getTitle();

			// Kiểm tra xem title của page nào bằng vs title mong muốn (truyền vào)
			if (currentPageTitle.equals(pageTitle)) {

				// Thoát khỏi vòng lặp
				break;
			}
		}
	}

	public boolean areAllTabClosedWithoutParent(String parentID) {
		// Lấy ra tất cả ID
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp duyệt qua từng ID
		for (String runWindows : allWindows) {

			// Nếu như ID khác vs parent
			if (!runWindows.equals(parentID)) {

				// Switch vào
				driver.switchTo().window(runWindows);

				// Close đi
				driver.close();
			}
		}

		driver.switchTo().window(parentID);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}