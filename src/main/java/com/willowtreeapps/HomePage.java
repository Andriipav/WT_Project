package com.willowtreeapps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created on 5/23/17.
 */
public class HomePage extends BasePage {

	// method that returns 5 random numbers from 0 to 4
	public static ArrayList<Integer> setArrayRandomNumber() {

		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Random randomGenerator = new Random();
		while (numbers.size() < 5) {

			int random = randomGenerator.nextInt(5);
			if (!numbers.contains(random)) {
				numbers.add(random);
			}
		}
		return numbers;
	}

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void checkTriesAndCorrectAnswersCounters() {
		int randomNumber = 0;
		int index = 0;
		boolean flag = true;
		int correctCounter = 0;
		int clicksQty = 0;
		List<Integer> numbersArray = new ArrayList<Integer>();

		sleep(6000);
		// Getting name from page, use it to validate correct clicks
		String whoIsName = driver.findElement(By.id("name")).getText();
		// checking counter, use it in assertion
		int countBeforeTries = Integer.parseInt(
				driver.findElement(By.className("attempts")).getText());
		int countBeforeCorrect = Integer.parseInt(
				driver.findElement(By.className("correct")).getText());
		// check that we don't click on correct answer
		while (whoIsName.equals(driver.findElement(By.id("name")).getText())) {

			if (flag) {
				numbersArray = setArrayRandomNumber();
				flag = false;
			}
			// click on random photo
			randomNumber = numbersArray.get(index);
			driver.findElement(
					By.xpath("//div[@data-n='" + randomNumber + "']")).click();
			sleep(6000);
			clicksQty++;
			// check whether we click on correct answer
			if (!whoIsName
					.equals(driver.findElement(By.id("name")).getText())) {
				whoIsName = driver.findElement(By.id("name")).getText();
				flag = true;
				index = 0;
				correctCounter++;

			} else {
				index++;
			}
			// hardcoded number of random clicks
			if (clicksQty == 10) {
				break;
			}

		}
		Assert.assertEquals(countBeforeTries + 10, Integer.parseInt(
				driver.findElement(By.className("attempts")).getText()));

		Assert.assertEquals(countBeforeCorrect + correctCounter,
				Integer.parseInt(
						driver.findElement(By.className("correct")).getText()));
	}

	public void clickOnCorrectPerson() {
		sleep(6000);
		String correctName = driver.findElement(By.id("name")).getText();
		// insert value from copy "who is " and insert in in xpath thus we get
		// right picture to click on
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

		driver.findElement(By.xpath("//div[@data-n='0']")).click();

		sleep(6000);

		int countAfter = Integer.parseInt(
				driver.findElement(By.className("attempts")).getText());

		Assert.assertTrue(countAfter > count);

	}

	public void validateNameAndPhotosChange() {
		// Implicit waiter should be used here, sorry for that. left as is
		// because of time constrains.
		sleep(6000);
		// Create 2 lists and compare them
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
