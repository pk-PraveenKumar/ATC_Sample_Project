package com.atc.sample.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.atc.sample.common.utils.CommonUtils;
import com.atc.sample.common.utils.NewAccountInfo;
import com.atc.sample.common.utils.Result;
import com.atc.sample.project.exception.BlockTestCase;

import static com.atc.sample.common.utils.CommonUtils.printLog;

public class LoginPage {

	private WebDriver driver = null;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void createNewAccount(NewAccountInfo accountInfo) throws BlockTestCase {
		Result result = new Result();
		try {

			if (CommonUtils.checkIfElementExist(driver, By.id("email_create"))) {

				driver.findElement(By.id("email_create")).click();
				driver.findElement(By.id("email_create")).clear();
				driver.findElement(By.id("email_create")).sendKeys(accountInfo.getEmailId());

				printLog("Going to create new account for " + accountInfo.getEmailId());

				driver.findElement(By.xpath("//button[@id='SubmitCreate']/span")).click();
				if (CommonUtils.waitForByElementToAppear(driver, By.xpath("//div[@id='create_account_error']/ol/li"))
						&& CommonUtils.checkIfElementExist(driver,
								By.xpath("//div[@id='create_account_error']/ol/li"))) {
					String errorMessage = driver.findElement(By.xpath("//div[@id='create_account_error']/ol/li"))
							.getText();
					printLog("Error while creating new email Id - " + errorMessage);
					result.setErrorMsg(errorMessage);

				} else {

					driver.findElement(By.id(accountInfo.getGender().getValue())).click();

					CommonUtils.clickClearAndSendKey(driver, By.id("customer_firstname"),
							accountInfo.getCustomerFirstName());

					CommonUtils.clickClearAndSendKey(driver, By.id("customer_lastname"),
							accountInfo.getCustomerLastName());
					CommonUtils.clickClearAndSendKey(driver, By.id("passwd"), accountInfo.getPassword());
					CommonUtils.clickClearAndSendKey(driver, By.id("company"), accountInfo.getCompany());
					CommonUtils.clickClearAndSendKey(driver, By.id("address1"), accountInfo.getAddress1());
					CommonUtils.clickClearAndSendKey(driver, By.id("address2"), accountInfo.getAddress2());
					CommonUtils.clickClearAndSendKey(driver, By.id("city"), accountInfo.getCity());
					driver.findElement(By.id("id_state")).click();
					new Select(driver.findElement(By.id("id_state")))
							.selectByVisibleText(accountInfo.getState().getValue());

					CommonUtils.clickClearAndSendKey(driver, By.id("postcode"), accountInfo.getPostalCode().toString());
					CommonUtils.clickClearAndSendKey(driver, By.id("other"), accountInfo.getAdditionalInformation());

					if (accountInfo.isSignUpForNewletter()) {
						driver.findElement(By.id("newsletter")).click();
					}
					if (accountInfo.isReceiveSpecialOffer()) {
						driver.findElement(By.id("optin")).click();
					}

					String date = accountInfo.getDateOfBirth();
					String[] dateInfo = date.split("/");
					driver.findElement(By.id("days")).click();
					Thread.sleep(2000);
					new Select(driver.findElement(By.id("days"))).selectByValue(dateInfo[0]);
					driver.findElement(By.id("months")).click();
					new Select(driver.findElement(By.id("months"))).selectByValue(dateInfo[1]);
					driver.findElement(By.id("years")).click();
					new Select(driver.findElement(By.id("years"))).selectByValue(dateInfo[2]);

					driver.findElement(By.id("id_country")).click();

					CommonUtils.clickClearAndSendKey(driver, By.id("phone"), accountInfo.getPhoneNumber().toString());
					CommonUtils.clickClearAndSendKey(driver, By.id("phone_mobile"),
							accountInfo.getMobilePhone().toString());
					CommonUtils.clickClearAndSendKey(driver, By.id("alias"), accountInfo.getAlias());
					CommonUtils.clickIfElementExist(driver, By.id("submitAccount"));

					result.setStatus(true);
					result.setSuccessMsg("Successfully created new account");
				}

			} else {
				result.setErrorMsg("Create new account button not present !!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setErrorMsg("Exception in createNewAccount for " + accountInfo.getEmailId());
		}

		CommonUtils.printLog("Is New Id created" + result.isStatus());
		if (!result.isStatus()) {
			throw new BlockTestCase(result.getErrorMsg());
		}
	}

}
