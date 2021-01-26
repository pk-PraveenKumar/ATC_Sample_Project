package com.atc.sample.project.pages;

import org.openqa.selenium.WebDriver;

public class SummerDressesPage extends BaseDressesPage {

	private WebDriver driver = null;

	public SummerDressesPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

}
