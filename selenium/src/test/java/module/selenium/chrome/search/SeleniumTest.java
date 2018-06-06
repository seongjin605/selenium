package module.selenium.chrome.search;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.framework.TestCase;


/**
 * @author 박성진
 * 크롬 드라이버 오류 : --ignore-certificate-errors
 * 이 오류가 생성시 크롬 드라이버를 검색 후,  최신 버전으로 다운로드 받고 경로 설정
 * {@link http://chromedriver.chromium.org/downloads}
 */
public class SeleniumTest extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationsErrors=new StringBuffer();
	
	@Before
	public void setUp() throws Exception{
		//자신의 데스크탑에 다운로드 받은 크롬 드라이브 경로 설정
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\psj\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		baseUrl="https://www.google.com";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("=chromedriver start=");
	}
	
	@Test
	public void testDriver() throws Exception{
		driver.get(baseUrl+"/");
		 //크롬이 실행될때 대기시간을 고려해서 스케줄러의 정확도를 위해 현재 실행중인 스레드를 지정된 밀리 초 동안 일시적으로 실행 중지 되도록 스케줄링 설정
		 Thread.sleep(2000);
		  WebElement searchBox = driver.findElement(By.name("q"));
		  searchBox.sendKeys("이글루시큐리티");
		  searchBox.submit();
	}
	
	@After
	public void tearDown() throws Exception{
		Thread.sleep(5000);
		driver.quit();
		String verificationErrorString=verificationsErrors.toString();
		if(!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
