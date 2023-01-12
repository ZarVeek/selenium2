package org.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartPage {
    public void buttonCompanies(WebDriver driver) {
        WebElement buttonCompanies = driver.findElement(By.xpath("//a[@href = \"/for-companies\"]"));
        buttonCompanies.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        closeFrame("//*[@id = 'fl-616371']", "//*[contains(@data-fl-track, \"click-close-login\")]", driver);
    }

    public void buttonHealth(WebDriver driver) {
        WebElement buttonHealth = driver.findElement(By.xpath("//span [@class=\"padding\" and text() = \"Здоровье\"]"));
        buttonHealth.click();
        assertTrue(buttonHealth.findElement(By.xpath("./..")).getAttribute("class").contains("item text--second active"), "Не получилось нажать кнопку здоровье");
    }

    public void healthInsurance(WebDriver driver) {
        WebElement healthInsurance = driver.findElement(By.xpath("//a[@href=\"/for-companies/zdorove/dobrovolnoe-meditsinskoe-strakhovanie\" and text() = \"Добровольное медицинское страхование\"]"));
        healthInsurance.click();
        WebElement titleHealthInsurance = driver.findElement(By.xpath("//h1"));
        assertEquals("Добровольное медицинское страхование", titleHealthInsurance.getText(), "Не получилось перейти");
    }


    public void closeFrame(String xPath, String elem, WebDriver driver) {
        try {
            Thread.sleep(1500);
            driver.switchTo().frame(waitElement(xPath, driver));
            driver.findElement(By.xpath(elem)).click();
            driver.switchTo().defaultContent();
        } catch (Exception ignored) {
            driver.switchTo().defaultContent();
        }
    }

    private WebElement waitElement(String elem, WebDriver driver) {
        return new WebDriverWait(driver, 1).until(ExpectedConditions.presenceOfElementLocated(By.xpath(elem)));
    }
}
