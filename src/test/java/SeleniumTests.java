/*
 * Copyright (c) 2016. LICENCED BY (c) Jari Van Melckebeke, all rights reserved, use only with permission of Jari Van Melckebeke
 */

import org.junit.*;
import org.neo4j.ogm.session.Session;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * @author Jari Van Melckebeke
 * @since 02.09.16
 */
@SpringBootApplication
@Transactional
@ComponentScan("org.jarivm.relationGraph")
public class SeleniumTests {
	public static final String USERNAME = "N00bface";
	public static final String ACCESS_KEY = "fbe21e01-aeb0-46ef-baab-b5e4aafc3b7b";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

	private static Session s;
	private static WebDriver driver;
	private static ConfigurableApplicationContext context;

	@BeforeClass
	public static void mainSetUp() throws MalformedURLException {
		context = SpringApplication.run(SeleniumTests.class);
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows XP");
		caps.setCapability("version", "43.0");

		WebDriver driver = new RemoteWebDriver(new URL(URL), caps);

		driver.get("localhost:2907");
		System.out.println(driver.getTitle());
	}

	@AfterClass
	public static void mainTearDown() {
		assertNotNull(s);
		s.query("MATCH (n) where n.name CONTAINS 'test' detach delete n;", new HashMap<>());
		driver.close();
		driver.quit();
		context.close();
	}

	@Before
	public void setUp() {
		System.out.println(context.getBeanFactory().getBeanNamesIterator().toString());
		s = (Session) context.getBean("getSession");
	}

	@After
	public void tearDown() {
	}


	@Test
	public void testGeneralConnectionToNetwork() {
		driver.navigate().to("http://jarivanmelckebeke.be/index.php");
		assertEquals(1, driver.findElements(By.className("block")).size());
	}

	@Test
	public void testSpringConnection() {
		driver.navigate().to("localhost:2904/login");
		assertEquals(driver.getTitle(), "Login Page");
	}

	@Test
	public void testCreateClient() {
		driver.navigate().to("localhost:2904/user/create/client");
		login();
		assertEquals("New Entity", driver.getTitle());
		driver.findElement(By.id("name")).sendKeys("mock test");
		driver.findElement(By.id("experience")).sendKeys("15");
		Select selectBox = new Select(driver.findElement(By.id("sector")));
		selectBox.selectByIndex(0);
		driver.findElement(By.name("submit")).click();
		assertEquals(driver.getTitle(), "User home");
	}

	public void login() {
		if (driver.getTitle().equals("Login Page")) {
			assertEquals("Login Page", driver.getTitle());
			driver.findElement(By.name("username")).sendKeys("admin");
			driver.findElement(By.name("password")).sendKeys("root");
			driver.findElement(By.name("submit")).click();
		}
	}

	@Test
	public void testCreateEmployee() {
		driver.navigate().to("localhost:2904/user/create/employee");
		login();
		assertEquals("New Entity", driver.getTitle());
		driver.findElement(By.id("surname")).sendKeys("Van Melckebeke");
		driver.findElement(By.id("name")).sendKeys("test");
		driver.findElement(By.id("email")).sendKeys("aab@gmail.com");
		Select selectBox = new Select(driver.findElement(By.id("gender")));
		selectBox.selectByIndex(0);
		driver.findElement(By.id("age")).sendKeys("15");
		driver.findElement(By.id("experience")).sendKeys("5");
		driver.findElement(By.id("submit")).click();
		assertEquals(driver.getTitle(), "User home");
	}

	@Test
	public void testCreateProject() {
		driver.navigate().to("localhost:2904/user/create/project");
		login();
		assertEquals("New Entity", driver.getTitle());
		driver.findElement(By.id("name")).sendKeys("test");
		driver.findElement(By.id("cost")).sendKeys("150");
		driver.findElement(By.id("version")).sendKeys("1.5.9");
		Select selectBox = new Select(driver.findElement(By.id("client")));
		selectBox.selectByVisibleText("Colruyt");
		driver.findElements(By.name("employeesCollaborated")).get(0).click();
		driver.findElements(By.name("roles")).get(0).sendKeys("a role");
		driver.findElement(By.id("submit")).click();
		assertEquals(driver.getTitle(), "User home");
	}

}