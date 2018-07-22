package com.crossover.e2e;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;




public class GMailTest extends TestCase {
	private WebDriver driver;
	private Properties properties = new Properties();
	public String projectLocation= null;
	public void setUp() throws Exception {
		projectLocation   = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectLocation + "/chromedriver/chromedriver");
		driver = new ChromeDriver();
		properties.load(new FileReader(new File("test.properties")));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void tearDown() throws Exception {
		//    driver.quit();
	}

	@Test
	public void testSendEmail() throws Exception {
		driver.get("https://mail.google.com/");
		WebElement userElement = driver.findElement(By.id("identifierId"));
		userElement.sendKeys(properties.getProperty("username"));

		driver.findElement(By.id("identifierNext")).click();

		Thread.sleep(1000);

		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys(properties.getProperty("password"));
		driver.findElement(By.id("passwordNext")).click();

		Thread.sleep(5000);

		WebElement composeElement = driver.findElement(By.xpath("//*[@role='button' and (.)='COMPOSE']"));
		composeElement.click();

		driver.findElement(By.name("to")).clear();
		driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String subject = "Test Crossover "+timestamp;
		String message = "Gmail test for crossover!!!";
		System.out.println(10);
		driver.findElement(By.name("subjectbox")).sendKeys(subject);
		driver.findElement(By.xpath("//*[@role='textbox' and @aria-label='Message Body']")).sendKeys(message);
		driver.findElement(By.xpath("//*[@aria-label='Attach files']")).sendKeys(projectLocation+"/attachment.txt");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
		Thread.sleep(5000);
		driver.navigate().refresh();
		Thread.sleep(5000);
		String ele = "//span/b[text()='"+subject+"']";
		driver.findElement(By.xpath(ele)).click();
		WebElement ele_body=driver.findElement(By.xpath("//div[text()='"+message+"']"));
		Assert.assertTrue(ele_body.isDisplayed());
		

	}


}
