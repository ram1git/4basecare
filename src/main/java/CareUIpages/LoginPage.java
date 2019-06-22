package CareUIpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import base.Base;

public class LoginPage extends Base {
	
	
	@FindBy(xpath="//input[contains(@id,'username') and @name='username']")
    WebElement logusrname;
	
	
	@FindBy(xpath="//input[contains(@id,'password') and @name='password']")
    WebElement logpasswd;
	
	@FindBy(xpath="//span[contains(text(),'Sign in')]")
    WebElement submit;
	
	@FindBy(xpath="//div[contains(text(),'The credentials you provided is incorrect')]")
	WebElement wrong;
	
	
	
	public LoginPage(){
		
		PageFactory.initElements(driver, this);
		System.out.println("elements are getting initialsised");
		
	}
	
	
	public Admin login(String u,String pass) throws InterruptedException{
	  
		 Thread.sleep(3000);
		logusrname.sendKeys(u);
		logpasswd.sendKeys(pass);
		submit.click();
		
		/*if(wrong.isDisplayed()){
			logusrname.clear();
			logpasswd.clear();
			logusrname.sendKeys(u);
			logpasswd.sendKeys(pass);
			submit.click();	
			
			
		}else{
			logusrname.clear();
			logpasswd.clear();
			logusrname.sendKeys(u);
			logpasswd.sendKeys(pass);
			submit.click();	
			
		}*/
		
		return new Admin();
		
	}
	

}
