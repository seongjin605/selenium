package module.selenium.doipo.test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thoughtworks.selenium.webdriven.commands.OpenWindow;

import junit.framework.TestCase;

/**
 * @author 박성진 크롬 드라이버 오류 : --ignore-certificate-errors 이 오류가 생성시 크롬 드라이버를 검색 후,
 *         최신 버전으로 다운로드 받고 경로 설정
 *         {@link http://chromedriver.chromium.org/downloads}
 */
public class DaumLoginTest extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationsErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		// 자신의 데스크탑에 다운로드 받은 크롬 드라이브 경로 설정
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\psj\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://www.inven.co.kr/archeage";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("=chromedriver start=");
	}

	@Test
	public void testDriver() throws Exception {
		driver.get(baseUrl + "/");
		// 크롬이 실행될때 대기시간을 고려해서 스케줄러의 정확도를 위해 현재 실행중인 스레드를 지정된 밀리 초 동안 일시적으로 실행 중지 되도록
		// 스케줄링 설정
		Thread.sleep(2000);
		driver.get("https://member.inven.co.kr/user/scorpio/mlogin");

		driver.findElement(By.id("user_id")).sendKeys("");// 아이디 넣기
		driver.findElement(By.id("password")).sendKeys("");// 비밀번호 넣기
		driver.findElement(By.id("loginBtn")).click();

		Thread.sleep(2000);
		driver.get("http://www.inven.co.kr/board/powerbbs.php?come_idx=2836");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//*[@id=\"listtop\"]/div[1]/ul/li[2]/a/img")).click();
		driver.findElement(By.xpath("//*[@id=\"board_write\"]/table/tbody/tr[1]/td/table[1]/tbody/tr/td[3]/input")).sendKeys("SELENIUM JAVA API TEST");
		
		
		//매우 중요!!!! iframe
		driver.switchTo().frame(driver.findElement(By.id("webeditor")));
		
		JavascriptExecutor jsDriver=(JavascriptExecutor) driver;
		
		String contents="function getElementByXpath(path) {\r\n" + 
				"  return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"getElementByXpath(\"/html/body\").append(\"SELENIUM JAVA API TEST\");\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"";
		
		jsDriver.executeScript(contents);
		
		//iframe 빠져나오기
		driver.switchTo().defaultContent();
		
		//글쓰기완료
		driver.findElement(By.xpath("//*[@id=\"bttnComplete1\"]")).click();
	
	}

	@After
	public void tearDown() throws Exception {
		Thread.sleep(5000);
		driver.quit();
		String verificationErrorString = verificationsErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
