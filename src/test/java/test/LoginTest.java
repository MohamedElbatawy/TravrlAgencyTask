package test;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import data.Loadproperties;
import pages.HomePage;
import pages.LoginPage;
import pages.SignUpPage;

public class LoginTest extends TestBase {
	
	Faker fakeData = new Faker();
	SignUpPage signUpPageObject;
	HomePage homePAgeObject;
	LoginPage loginPageObject;
	
    String firstName = fakeData.name().firstName().substring(0,1).toUpperCase(); 
	String lastName = fakeData.name().lastName().substring(0,1).toUpperCase(); 
	String email = fakeData.internet().emailAddress(); 
	String phone = fakeData.number().digits(11).toString();
	String password = fakeData.number().digits(8).toString();
	
	/*String firstName=Loadproperties.userData.getProperty("firstName");
	String lastName=Loadproperties.userData.getProperty("lastName");
	String phone=Loadproperties.userData.getProperty("phone");
	String email=Loadproperties.userData.getProperty("email");
	String password=Loadproperties.userData.getProperty("password");*/
	
	String signUpURL=Loadproperties.userData.getProperty("url");
	
	
	public void checkNames() {
		if (firstName.equals(lastName)==true)
			 lastName = fakeData.name().lastName().substring(0,1).toUpperCase(); 
	}

	@Test(priority = 1, alwaysRun = true)
	public void signUpProcess(Method method) throws InterruptedException {
		loginPageObject=new LoginPage(driver);
		signUpPageObject=new SignUpPage(driver);
		driver.navigate().to(signUpURL);
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		signUpPageObject.signUpSteps(firstName,lastName,phone,email,password,password);
		Assert.assertEquals("Hi,"+" "+firstName+" "+lastName, signUpPageObject.welcomeStatment.getText());
		System.out.println(signUpPageObject.welcomeStatment.getText());
	}
	
	@Test(priority = 2,dependsOnMethods = {"signUpProcess"})
	public void logOut(Method method) {
		homePAgeObject=new HomePage(driver);
		homePAgeObject.logOutFromAccount();
	}

	
	@Test(priority = 3,dependsOnMethods = {"logOut"})
	public void login(Method method) {
		loginPageObject=new LoginPage(driver);
		loginPageObject.logIn(email, password);
		Assert.assertEquals("Hi,"+" "+firstName+" "+lastName, signUpPageObject.welcomeStatment.getText());
		System.out.println(signUpPageObject.welcomeStatment.getText());
	}
	
	
}

