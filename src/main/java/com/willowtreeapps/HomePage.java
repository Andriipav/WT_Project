package com.willowtreeapps;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created on 5/23/17.
 */
public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void clickOnCorrectPerson() {
		sleep(6000);
		String correctName = driver.findElement(By.id("name")).getText();
		String xpathCorrectPerson = WebElementsHelper
				.selectedPerson(correctName);
		sleep(6000);
		driver.findElement(By.xpath(xpathCorrectPerson)).click();
	}

	public void clickOnWrongPerson() {
		String correctName = driver.findElement(By.id("name")).getText();

		List<WebElement> namesSetElements = driver
				.findElements(By.className("photo"));
		for (int i = 0; i < namesSetElements.size(); i++) {
			String name = namesSetElements.get(i)
					.findElement(By.className("name")).getText();
			if (!correctName.equals(name)) {
				namesSetElements.get(i).click();
				break;
			}
		}
	}

	public void validateClickingFirstPhotoIncreasesTriesCounter() {
		// Wait for page to load
		sleep(6000);

		int count = Integer.parseInt(
				driver.findElement(By.className("attempts")).getText());

		driver.findElement(By.className("photo")).click();

		sleep(6000);

		int countAfter = Integer.parseInt(
				driver.findElement(By.className("attempts")).getText());

		Assert.assertTrue(countAfter > count);

	}

	public void validateNameAndPhotosChange() {
		sleep(6000);
		String whoIsName1 = driver.findElement(By.id("name")).getText();
		List<String> imgSet1 = new ArrayList<String>();

		List<WebElement> imgSetTemp = driver
				.findElements(By.xpath("//*[@class='photo']/img"));
		for (int i = 0; i < imgSetTemp.size(); i++) {
			imgSet1.add(imgSetTemp.get(i).getAttribute("src"));
		}

		clickOnCorrectPerson();

		sleep(6000);
		String whoIsName2 = driver.findElement(By.id("name")).getText();

		List<WebElement> imgSet2 = driver
				.findElements(By.xpath("//*[@class='photo']/img"));

		Assert.assertNotEquals(whoIsName1, whoIsName2);

		for (int i = 0; i < imgSet1.size(); i++) {
			Assert.assertFalse(
					imgSet1.get(i).equals(imgSet2.get(i).getAttribute("src")));
		}

	}

	public void validateStreakCounter() {
		// Wait for page to load
		sleep(6000);

		int count = Integer
				.parseInt(driver.findElement(By.className("streak")).getText());

		clickOnCorrectPerson();

		sleep(3000);

		int countAfter = Integer
				.parseInt(driver.findElement(By.className("streak")).getText());

		Assert.assertEquals(countAfter, count + 1);

	}

	public void validateStreakCounterReset() {
		// Wait for page to load
		sleep(6000);
		clickOnCorrectPerson();
		sleep(3000);
		clickOnCorrectPerson();
		sleep(3000);
		clickOnCorrectPerson();
		sleep(3000);
		int count = Integer
				.parseInt(driver.findElement(By.className("streak")).getText());
		Assert.assertNotEquals(0, count);

		clickOnWrongPerson();
		sleep(3000);
		int countAfter = Integer
				.parseInt(driver.findElement(By.className("streak")).getText());

		Assert.assertEquals(countAfter, 0);

	}

	public void validateTitleIsPresent() {
		WebElement title = driver.findElement(By.cssSelector("h1"));
		Assert.assertTrue(title != null);
	}
}
