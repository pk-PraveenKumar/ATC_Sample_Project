package com.atc.sample.project.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.atc.sample.common.utils.CommonUtils;
import com.atc.sample.common.utils.Result;
import com.atc.sample.project.exception.BlockTestCase;

public class BaseDressesPage {

	private WebDriver driver = null;

	public enum SIZE {
		S, M, L;
	}

	public enum COLOR {
		WHITE("color_8"), BLACK("color_11"), ORANGE("color_13"), BLUE("color_14"), GREEN("color_15"),
		YELLOW("color_16");

		private String colorId;

		private COLOR(String colorId) {
			this.colorId = colorId;
		}

		public String getColorId() {
			return colorId;
		}
	}

	public enum VIEW_OPTION {
		LIST("//li[@id='list']/a/i"), GRID("//li[@id='grid']/a/i");

		private String xpath;

		public void setXpath(String xpath) {
			this.xpath = xpath;
		}

		public String getXpath() {
			return xpath;
		}

		private VIEW_OPTION(String xpath) {
			this.xpath = xpath;
		}

	};

	public BaseDressesPage(WebDriver driver) {
		this.driver = driver;
	}

	public void changeView(VIEW_OPTION view) {
		WebElement element = driver.findElement(By.xpath(view.getXpath()));
		if (element.isSelected()) {

		} else {
			element.click();
		}
	}

	public void clickOnTheItemByPositionToQuickView(int position) throws InterruptedException, BlockTestCase {
		Result result = new Result();
		if (CommonUtils.checkIfElementExist(driver, By.xpath("//*[@id='center_column']/ul/li"))) {
			List<WebElement> allItems = driver.findElements(By.xpath("//*[@id='center_column']/ul/li"));
			if (allItems.size() >= position) {
				WebElement element = allItems.get(position - 1);
				Actions actions = new Actions(driver);
				actions.moveToElement(element);
				actions.perform();
				// Thread.sleep(5000);
				element.findElement(By.xpath("./div/div/div[1]/div/a[2]/span")).click();
				Thread.sleep(5000);
				driver.switchTo().frame(0);
				if (CommonUtils.checkIfElementExist(driver, By.xpath("//*[@id='quantity_wanted']"), 5, 3)) {
					result.setStatus(true);
					result.setSuccessMsg("Successfully clicked on the element");
				}
				driver.switchTo().parentFrame();
			} else {
				result.setErrorMsg("Item available is less than the expected. Items available " + allItems.size()
						+ " Expection position " + position);
				CommonUtils.printLog("Item available is less than the expected. Items available " + allItems.size()
						+ " Expection position " + position);
			}

		} else {
			result.setErrorMsg("No object available for purchase.");
			CommonUtils.printLog("No object available ");
		}

		CommonUtils.printLog("Is Item clicked on from the menu " + result.isStatus());
		if (!result.isStatus()) {
			throw new BlockTestCase(result.getErrorMsg());
		}
	}

	public boolean checkIfStockAvailable() {
		// div[@id='center_column']/ul/li[2]/div/div/div[2]/span/span

		return false;
	}

	public void addItemFromQuickView(Integer quantity, SIZE size, COLOR color, boolean checkOut) throws BlockTestCase {
		Result result = new Result();
		try {
			driver.switchTo().frame(0);
			if (CommonUtils.checkIfElementExist(driver, By.xpath("//*[@id=\"quantity_wanted_p\"]"), 2, 3)) {
				// Clicking on the quantity text box
				CommonUtils.clickClearAndSendKey(driver, By.xpath("//*[@id=\"quantity_wanted\"]"), quantity.toString());
				// Ordinal starts with zero, but size are mapped as 1 = S, 2 = M, 3 = L
				new Select(driver.findElement(By.id("group_1"))).selectByValue(String.valueOf(size.ordinal() + 1));
				// Clicking on color, if color not available default color will be the selection
				CommonUtils.clickIfElementExist(driver, By.id(color.getColorId()));
				// Clicking on the Addcart button
				driver.findElement(By.xpath("//p[@id='add_to_cart']/button/span")).click();
				Thread.sleep(5000);
				if (checkOut) {
					driver.findElement(By.xpath("//div[@id='layer_cart']/div/div[2]/div[4]/a/span/i")).click();
				} else {
					driver.findElement(By.xpath("//div[@id='layer_cart']/div/div[2]/div[4]/span/span/i")).click();
				}
				result.setStatus(true);
				result.setSuccessMsg("Successfully added to cart.");
				Thread.sleep(2000);
			} else {
				result.setStatus(false);
				result.setErrorMsg("Currently not in Quick view of the product.Failed to purchase order");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			CommonUtils.printLog("Failed to purchase from quickview. Exception - " + exception.getMessage());
			result.setErrorMsg("Failed to purchase from quickview. Exception - " + exception.getMessage());
		} finally {
			driver.switchTo().parentFrame();

		}
		CommonUtils.printLog("Is Item added to cart from QuickView " + result.isStatus());
		if (!result.isStatus()) {
			throw new BlockTestCase(result.getErrorMsg());
		}
	}

}
