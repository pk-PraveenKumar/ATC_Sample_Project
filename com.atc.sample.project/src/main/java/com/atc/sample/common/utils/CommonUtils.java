package com.atc.sample.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.atc.sample.project.automationpractice.Starter;

public class CommonUtils {

	// LOG4j can be used
	public static void printLog(String str) {
		System.out.println(str);
	}

	public static void printErrorLog(String str) {
		System.err.println(str);
	}

	public static boolean checkIfElementExist(WebDriver driver, By by) {
		boolean isPresent = false;
		try {
			isPresent = driver.findElements(by) != null && !driver.findElements(by).isEmpty();
		} catch (NullPointerException exception) {
			printLog("Exception " + exception.getMessage());
			exception.printStackTrace();
		}

		return isPresent;
	}

	public static void saveSnapShot(WebDriver driver, String imageName) {
		File capturedScreen = null;
		try {
			capturedScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage bufferredImages = ImageIO.read(capturedScreen);
			String outputDir = Starter.dir + "/src/main/resources/screenshot";
			File outputDirectory = new File(outputDir);
			if (!outputDirectory.exists()) {
				outputDirectory.mkdirs();
			}

			ImageIO.write(bufferredImages, "PNG", new File(outputDirectory, imageName + ".png"));

			CommonUtils.printLog("Image saved in " + outputDir + imageName + ".png");
		} catch (IOException e1) {
			printLog("Failed to capture the broad band Web UI..!!!! ");
		}
	}

	public static boolean checkIfElementExist(WebDriver driver, By by, int waitTimeInSec, int frequency) {
		boolean isPresent = false;
		// Converting to milli seconds
		try {
			int counter = 0;
			do {

				isPresent = checkIfElementExist(driver, by);
				counter++;
				if (!isPresent) {
					printLog("Going to retry validation " + counter);
					Thread.sleep(waitTimeInSec * 1000);
				}
			} while (!isPresent && counter < frequency);
		} catch (Exception exception) {
			printLog("Exception " + exception.getMessage());
			exception.printStackTrace();
		}

		return isPresent;
	}

	public static boolean clickIfElementExist(WebDriver driver, By by) {
		boolean isPresent = false;
		if (checkIfElementExist(driver, by)) {
			driver.findElement(by).click();
			isPresent = true;
		} else {
			printLog("Button not found");
		}
		return isPresent;
	}

	public static WebElement navigateToMenusInList(WebDriver driver, WebElement parentElement, String menuOptions) {
		List<WebElement> list = parentElement.findElements(By.xpath("./ul/li"));
		System.out.println(list.size() + " menu options " + menuOptions);
		String[] navigations = menuOptions.split(">");
		WebElement selectedElement = null;
		if (navigations.length > 0) {
			for (WebElement element : list) {
				System.out.println(element.findElement(By.xpath("./a")).getAttribute("title"));
				if (element.findElement(By.xpath("./a")).getAttribute("title").equalsIgnoreCase(navigations[0])) {
					Actions builder = new Actions(driver);
					builder.moveToElement(element).build().perform();
					if (navigations.length > 1) {
						selectedElement = navigateToMenusInList(driver, element,
								menuOptions.substring(menuOptions.indexOf(">") + 1));
					} else {
						selectedElement = element;
					}
					break;
				}
			}
		} else {
			selectedElement = parentElement;
		}
		return selectedElement;
	}

	public static void clickClearAndSendKey(WebDriver driver, By by, String sendKey) {
		try {
			driver.findElement(by).click();
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(sendKey);

		} catch (NullPointerException exception) {
			printLog("Exception " + exception.getMessage());
			exception.printStackTrace();
		}
	}

	public static void clickElementIfVisible(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		element.click();
	}

	public static void waitForTextToAppear(WebDriver driver, String searchText, By byElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(byElement, searchText));
		} catch (Exception exception) {
			printLog("Exception in waitForTextToAppear: " + exception.getMessage());
			exception.printStackTrace();
		}
	}

	public static boolean waitForByElementToAppear(WebDriver driver, By byElement) {
		boolean isPresent = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			// wait.until(ExpectedConditions.textToBePresentInElementLocated(byElement));
			wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
			isPresent = true;
		} catch (Exception exception) {
			printErrorLog("Exception in waitForByElementToAppear: " + exception.getMessage());
		}
		return isPresent;
	}
}
