package com.atc.sample.project.pages;

import static com.atc.sample.common.utils.CommonUtils.printLog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.atc.sample.common.utils.CommonUtils;
import com.atc.sample.common.utils.Result;
import com.atc.sample.project.exception.BlockTestCase;

public class ShoppingCartPage {

	private WebDriver driver = null;

	// Order - My Store
	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		// this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void placeOrder(String paymentMode) throws BlockTestCase {
		Result result = new Result();
		System.out.println("." + driver.getTitle() + ".");
		try {
			if ("Order - My Store".equalsIgnoreCase(driver.getTitle())) {
				printLog("Going to click on Place Order");
				CommonUtils.clickElementIfVisible(driver, By.xpath("//div[@id='center_column']/p[2]/a/span"));
				CommonUtils.clickElementIfVisible(driver, By.xpath("//div[@id='center_column']/form/p/button/span"));
				driver.findElement(By.id("cgv")).click();
				CommonUtils.clickElementIfVisible(driver, By.xpath("//form[@id='form']/p/button/span"));
				//*[@id="HOOK_PAYMENT"]/div[2]/div/p/a
				if(paymentMode == "Cheque") {
					CommonUtils.clickElementIfVisible(driver, By.xpath("//div[@id='HOOK_PAYMENT']/div[2]/div/p/a/span"));
				}else if(paymentMode == "BankWire"){
					CommonUtils.clickElementIfVisible(driver, By.xpath("//div[@id='HOOK_PAYMENT']/div[1]/div/p/a/span"));
				}
				
				CommonUtils.clickElementIfVisible(driver, By.xpath("//p[@id='cart_navigation']/button/span"));
				String actual = driver.findElement(By.xpath("//div[@id='center_column']/p")).getText();
				System.out.println("Actual " + actual);
				result.setStatus(true);
				result.setSuccessMsg("Successfully placed order");
			} else {
				result.setStatus(false);
				result.setErrorMsg("Current page is not order page.");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			result.setStatus(false);
			result.setErrorMsg("Exception in placeOrder. Exception " + exception.getMessage());
		}
		
		CommonUtils.printLog("Is Order placed " + result.isStatus());
		if (!result.isStatus()) {
			throw new BlockTestCase(result.getErrorMsg());
		}

	}

}
