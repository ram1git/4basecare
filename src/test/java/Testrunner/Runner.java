package Testrunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;






import CareUIpages.Admin;
import CareUIpages.LoginPage;
import base.Base;

public class Runner extends Base {
public Admin adm=null;	
public LoginPage log=null;

	
	
	public Runner(){
		
		super();
		
	}

	
	@BeforeTest
	
	
	public void beforeTest(){
		
		init();
		 log=new LoginPage();
		
	}
	
	
	@Test(dataProvider="dp",priority=1)
	
	
	public void logintest(String username,String password) throws InterruptedException{
	
	//	AdminLogIn log1=new AdminLogIn();
adm=log.login(username,password);
	//cu=adm.manage();
driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//header[starts-with(@class,'jss')]/descendant::div/descendant::div/descendant::button[@title='Profile']")).click();


		driver.findElement(By.xpath("//li[starts-with(@class,'jss')]")).click();
		
	}
	
	
	@DataProvider(name="dp")
	
	
	public String[][] dp() {

		String[][] data = new String[2][2];

		data[0][0] = "+919481938078";
		data[0][1] = "cd@123";
		data[1][0] = "+919481938078";
		data[1][1] = "cd@123";
		
		return data;
	}
	
	
	
	
@Test(dataProvider="dp1",priority=2)
	
	
	public void logintest1(String username,String password) throws InterruptedException{
	
	//	AdminLogIn log1=new AdminLogIn();
adm=log.login(username,password);
	//cu=adm.manage();

		
	}
	
	
	@DataProvider(name="dp1")
	
	
	public String[][] dp1() {

		String[][] data = new String[1][2];

		data[0][0] = "+919481938079";
		data[0][1] = "cd@1234";
		
		
		return data;
	}
	
	
	
	
	}
	

