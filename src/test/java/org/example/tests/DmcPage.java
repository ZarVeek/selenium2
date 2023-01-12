package org.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DmcPage extends StartPage {
    private static final String PATH_SEND_APP = "//a[@class=\"action-item btn--basic\"]";
    private static final String PATH_NAME = "//input[@name=\"userName\"]";
    private static final String PATH_PHONE = "//input[@name=\"userTel\"]";
    private static final String PATH_MAIL = "//input[@name=\"userEmail\"]";
    private static final String PATH_ADDRESS = "//input[@type=\"text\"]";
    private static final String PATH_CHECK_BOX = "//span[text() = \"Я соглашаюсь с условиями\"]";
    private static final String PATH_SUBMIT = "//button[@class=\"form__button-submit btn--basic\"]";
    private static final String CHECK_MAIL = "//span[text()=\"Введите корректный адрес электронной почты\"]";

    public void sendApplication(WebDriver driver) {
        closeFrame("//*[@id = 'fl-546299']", "//button[@class=\"close\"]", driver);
        driver.findElement(By.xpath(PATH_SEND_APP)).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(driver.findElement(By.xpath("//h2[text() = \"Оперативно перезвоним\"]")).getText().contains("Оперативно перезвоним"),"Не получилось перейти Добровольное медицинское страхование");
    }
    public void fillFields(WebDriver driver, List<String> arr){
        driver.findElement(By.xpath(PATH_NAME)).sendKeys(arr.get(0));
        driver.findElement(By.xpath(PATH_PHONE)).sendKeys(arr.get(1));
        driver.findElement(By.xpath(PATH_MAIL)).sendKeys(arr.get(2));
        driver.findElement(By.xpath(PATH_ADDRESS)).sendKeys(arr.get(3));
    }

    public void checkForCorrectValue(WebDriver driver, List<String> arr) {
        arr.set(1, "+7 (" + arr.get(1).substring(0, 3) + ") " + arr.get(1).substring(3, 6) + "-" +
                arr.get(1).substring(6));
        List<String> list = new ArrayList<>();
        list.add(driver.findElement(By.xpath(PATH_NAME)).getAttribute("value"));
        list.add(driver.findElement(By.xpath(PATH_PHONE)).getAttribute("value"));
        list.add(driver.findElement(By.xpath(PATH_MAIL)).getAttribute("value"));
        list.add(driver.findElement(By.xpath(PATH_ADDRESS)).getAttribute("value"));
        System.out.println(list);
        assertEquals(arr, list, "поля не заполнены введенными значениями");
    }

    public void clickAccept(WebDriver driver, WebDriverWait wait) {
        scrollTo(driver, driver.findElement(By.xpath(PATH_CHECK_BOX)));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(PATH_CHECK_BOX)));
        driver.findElement(By.xpath(PATH_CHECK_BOX)).click();
    }
    public void clickSubmit(WebDriver driver) {
        scrollTo(driver, driver.findElement(By.xpath(PATH_SUBMIT)));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(By.xpath(PATH_SUBMIT)).click();

    }
    public static void scrollTo(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();", element);
    }
    public void checkErrorForMail(WebDriver driver) {
        assertEquals("Введите корректный адрес электронной почты", driver.findElement(By.xpath(CHECK_MAIL)).getText(), "ошибки нет(");
    }
}
