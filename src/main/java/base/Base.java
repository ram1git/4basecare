package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import listeners.Webdriverlistener;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;




public class Base {
	public static WebDriver driver;
	public FileInputStream fis;
	public Properties prop;
	public WebDriverWait w;
	public Webdriverlistener lis;
	public EventFiringWebDriver evd;
	public String path="log4j.properties";
	public static final Logger log=Logger.getLogger(Base.class.getName());
	public String browser="firefox";
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	
	
	

	
	
	public Base(){
		
	//	w=new WebDriverWait(driver, 5);
		try {
			fis =new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
			System.out.println(System.getProperty("user.dir")+"\\config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		prop=new Properties();
		try {
			prop.load(fis);
			
			System.out.println(prop.getProperty("url"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PropertyConfigurator.configure(path);
		
		log.info(Base.class.getName().toString()+"is getting executed");
		
	}
	
  public void init(){
	 
	  if(browser=="firefox"){
		  
		driver=new FirefoxDriver();
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		
		evd=new EventFiringWebDriver(driver);
		lis=new Webdriverlistener();
		
		evd.register(lis);
		
		driver=evd;
		
		
		
		log.info(Base.class.getClass().getName()+"is getting executed");
		
		  
	  }else{
		  
		  System.out.println("chrome driver is getting executed");
	  }
	  
	  
	  
  }
	
  
  static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "\\src\\main\\java\\screenshots\\screen" + formater.format(calendar.getTime()) + ".html", false);
		
	}
  
  
  public void getresult(ITestResult result) {
		
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + " test is pass");
			
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}
  
  
  public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "\\src\\main\\java\\screenshots\\";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
  
  
  @AfterMethod()
	public void afterMethod(ITestResult result) {
	  System.out.println("aftermethod is executed");
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		
		System.out.println("before method is executed");
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}
	
	@AfterClass(alwaysRun = true)
	public void endTest() {
		log.info("browser closed");
		extent.endTest(test);
		extent.flush();
	}

	
	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		test.log(LogStatus.INFO, data);
	}
	
}
