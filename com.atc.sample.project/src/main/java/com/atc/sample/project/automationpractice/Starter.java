package com.atc.sample.project.automationpractice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.atc.sample.common.utils.CommonUtils;
import com.atc.sample.common.utils.NewAccountInfo;
import com.atc.sample.common.utils.NewAccountInfo.COUNTRY;
import com.atc.sample.common.utils.NewAccountInfo.GENDER;
import com.atc.sample.common.utils.NewAccountInfo.STATE;
import com.atc.sample.common.utils.Result;
import com.atc.sample.project.exception.BlockTestCase;
import com.atc.sample.project.pages.BaseDressesPage;
import com.atc.sample.project.pages.BaseDressesPage.SIZE;
import com.atc.sample.project.pages.BaseDressesPage.VIEW_OPTION;
import com.atc.sample.project.pages.HomePage;
import com.atc.sample.project.pages.LoginPage;
import com.atc.sample.project.pages.ShoppingCartPage;
import com.atc.sample.project.pages.SummerDressesPage;

public class Starter {

	public static final String dir = System.getProperty("user.dir");
	public static WebDriver driver = null;
	private static final String baseUrl = "http://automationpractice.com/index.php";
	private static final String expectedTitle = "My Store";

	public static void beforeMethod() throws BlockTestCase {
		// To get current directory
		CommonUtils.printLog("current dir = " + dir);
		System.setProperty("webdriver.chrome.driver", dir + "/src/main/resources/lib/chromedriver");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		String actualTitle = "";
		// launch website url
		CommonUtils.printLog("1. Login into the application");
		driver.get(baseUrl);
		// get the actual value of the title
		actualTitle = driver.getTitle();
		System.out.println("Actual Title -" + actualTitle);

		if (!actualTitle.contentEquals(expectedTitle)) {
			throw new BlockTestCase("Failed to load url " + baseUrl);
		}
	}

	public static NewAccountInfo getNewInfoDetail() {
		NewAccountInfo accountInfo = new NewAccountInfo();
		accountInfo.setGender(GENDER.MR);
		accountInfo.setCustomerFirstName("Praveen");
		accountInfo.setCustomerLastName("Kumar P");
		accountInfo.setEmailId("hello@12.com");
		accountInfo.setPassword("12345");
		accountInfo.setDateOfBirth("30/12/1990");
		accountInfo.setAdditionalInformation("Additional Information");
		accountInfo.setSignUpForNewletter(true);
		accountInfo.setReceiveSpecialOffer(true);
		accountInfo.setCompany("ATC");
		accountInfo.setAddress1("1st lane street");
		accountInfo.setAddress2("New Street");
		accountInfo.setCity("New york");
		accountInfo.setState(STATE.NEW_YORK);
		accountInfo.setPostalCode(10001);
		accountInfo.setCountry(COUNTRY.UNITED_STATES);
		accountInfo.setPhoneNumber(9003232915l);
		accountInfo.setMobilePhone(9003232911l);
		accountInfo.setAlias("2nd lane street, Newyork");
		return accountInfo;
	}

	public static void test() {
		try {
			beforeMethod();
			CommonUtils.printLog("Successfully launched application");
			HomePage homePage = new HomePage(driver);
			CommonUtils.printLog("2. Navigate to 'My Addresses' and add a new address");
			homePage.navigateTo(HomePage.NavigateToOptions.MY_ADDRESSES);
			CommonUtils.printLog("Successfully launched 'My Addresses' and add a new address");
			CommonUtils.printLog("3. Fill all the informations(not only the mandatory)");
			CommonUtils.printLog("4. Click 'Save'");
			LoginPage loginPage = new LoginPage(driver);
			loginPage.createNewAccount(getNewInfoDetail());
			CommonUtils.printLog("Successfully created new Address and saved");

			CommonUtils.printLog("5. Navigate to 'Women' --> Summer dresses");
			homePage.navigateTo(HomePage.NavigateToOptions.WOMEN_SUMMERDRESSES);
			CommonUtils.printLog("Successfully navigated to 'Women' --> Summer dresses");

			CommonUtils.printLog("6. The Items would be in 'Grid view'. Change it to 'List View'.");
			CommonUtils.printLog("7. Click on the first item to view.");
			CommonUtils.printLog("8. Increase the quantity to 5");
			CommonUtils.printLog("9. Change the size to 'L'");
			CommonUtils.printLog("10.Select any colour.");
			CommonUtils.printLog("11.Add to cart");
			CommonUtils.printLog(
					"12.Click 'Continue shopping' and repeat the same for the other 2 items as well under the summer dresses.");
			SummerDressesPage summerDressPage = new SummerDressesPage(driver);
			summerDressPage.changeView(VIEW_OPTION.LIST);
			for (int counter = 1; counter <= 3; counter++) {
				summerDressPage.clickOnTheItemByPositionToQuickView(counter);
				summerDressPage.addItemFromQuickView(5, SIZE.L, BaseDressesPage.COLOR.YELLOW, counter == 3);
			}
			CommonUtils.printLog(
					"Successfully switched to list view, all items are added to cart with items changed quantity and size as mentioned");

			CommonUtils.printLog("13. Proceed to checkout and complete the payment");
			ShoppingCartPage shoppingCart = new ShoppingCartPage(driver);
			shoppingCart.placeOrder("BankWire");
			homePage = new HomePage(driver);
			CommonUtils.printLog("Successfully placed the order");

			CommonUtils.printLog("14. Move to your profile and check 'order history and details'");
			homePage.clickOnTheProfileAndNavigateToOrderHistory();
			CommonUtils.printLog("Successfully clicked on order history and viewed the detail");

			CommonUtils.printLog("15. Capture screenshot of the order history");
			CommonUtils.saveSnapShot(driver, "OrderDetail");
			CommonUtils.printLog("Successfully captured image");

			CommonUtils.printLog("16. Sign out from the application");
			homePage.signOut();
			CommonUtils.printLog("Successfully signed out of application");

		} catch (BlockTestCase testException) {

			CommonUtils.printErrorLog("Execution Failed with error message" + testException.getMessage());
			CommonUtils.saveSnapShot(driver, "ErrorImage");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.close();
			}

		}

	}

	public static void main(String args[]) {
		CommonUtils
				.printLog("################################## Starting Execution ##################################");
		test();
		CommonUtils.printLog("################################## Ended Execution ##################################");
	}

}
