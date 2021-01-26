package com.atc.sample.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.atc.sample.common.utils.CommonUtils;
import com.atc.sample.common.utils.Result;
import com.atc.sample.project.exception.BlockTestCase;

import static com.atc.sample.common.utils.CommonUtils.printLog;

import java.util.List;

public class HomePage {

	private WebDriver driver = null;

	public enum NavigateToOptions {
		MY_ADDRESSES("LINKTEXT", "My addresses", "Login - My Store"),
		WOMEN_SUMMERDRESSES("TOPMENU", "WOMEN>DRESSES>SUMMER DRESSES", "Summer Dresses - My Store");

		private String validationType;
		private String validationPoint;
		private String peformAction;

		private NavigateToOptions(String validationType, String validationPoint, String peformAction) {
			this.validationType = validationType;
			this.validationPoint = validationPoint;
			this.peformAction = peformAction;
		}

	}

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void signOut() {
		driver.findElement(By.linkText("Sign out")).click();
	}

	public void clickOnTheProfileAndNavigateToOrderHistory() throws BlockTestCase {
		try {
			driver.findElement(By.xpath("//header[@id='header']/div[2]/div/div/nav/div/a/span")).click();
			driver.findElement(By.xpath("//div[@id='center_column']/div/div/ul/li/a/span")).click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlockTestCase("Exception in clickOnTheProfileAndNavigateToOrderHistory");
		}

	}

	public void navigateTo(NavigateToOptions option) throws BlockTestCase {
		Result result = new Result();
		printLog("Navigation to method " + option);
		try {
			if (option.validationType == "LINKTEXT") {
				driver.findElement(By.linkText(option.validationPoint)).click();
			} else if (option.validationType == "XPATH") {
				driver.findElement(By.xpath(option.validationPoint)).click();
			} else if (option.validationType == "TOPMENU") {
				// *[@id=\"block_top_menu\"]/ul/li[1]/ul/li[2]/ul/li[3]/a
				WebElement parentElement = driver.findElement(By.id("block_top_menu"));
				parentElement = CommonUtils.navigateToMenusInList(driver, parentElement, option.validationPoint);
				if (parentElement != null) {
					parentElement.click();
				}
			}
			String actual = driver.getTitle();
			printLog("Actual title :" + actual + " Expected Title :" + option.peformAction);
			if (option.peformAction.equalsIgnoreCase(actual)) {
				result.setStatus(true);
				result.setSuccessMsg("Successfully navigated to" + option.toString());
			} else {
				result.setStatus(false);
				result.setErrorMsg("Failed to navigate to" + option.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setErrorMsg("Exception in  navigate to" + option.toString());
		}

		printLog("Is Navigation success " + result.isStatus());
		if (!result.isStatus()) {
			throw new BlockTestCase(result.getErrorMsg());
		}
	}
}
