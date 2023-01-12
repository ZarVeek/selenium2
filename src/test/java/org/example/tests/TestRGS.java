package org.example.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TestRGS {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void before(){
        String browser = "chrome";
        switch (browser){
            case("chrome"):
                System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case("firefox"):
                System.setProperty("webdriver.gecko.driver", "src/test/resources/webdriver/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case("iex"):
                System.setProperty("webdriver.ie.driver", "src/test/resources/webdriver/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 2, 1000);
    }
    @org.junit.jupiter.api.Test
    public void testCase(){
        //BASE_PAGE
        driver.get("http://www.rgs.ru");
        StartPage startPage = new StartPage();
        startPage.buttonCompanies(driver);
        startPage.buttonHealth(driver);

        startPage.healthInsurance(driver);
    }
    @ParameterizedTest(name = "{index}")
    @ArgumentsSource(CustomArgumentProvider.class)
    void checkForFilledFields(String name, String phone, String mail, String address){
        //DMC_PAGE
        List<String> arr = new ArrayList<>();
        Collections.addAll(arr, name, phone, mail, address);
        driver.get("https://www.rgs.ru/for-companies/zdorove/dobrovolnoe-meditsinskoe-strakhovanie");
        DmcPage dmcPage = new DmcPage();
        dmcPage.sendApplication(driver);
        dmcPage.fillFields(driver, arr);
        dmcPage.checkForCorrectValue(driver, arr);
        dmcPage.clickAccept(driver, wait);
        dmcPage.clickSubmit(driver);
        dmcPage.checkErrorForMail(driver);
    }
    static class CustomArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            FormRegistration fR = new FormRegistration();
            fR.generateData1();
            List<String> arr1 = fR.getListValue();
            fR.generateData2();
            List<String> arr2 = fR.getListValue();
            fR.generateData3();
            List<String> arr3 = fR.getListValue();

            return Stream.of(
                    Arguments.of(arr1.get(0),arr1.get(1),arr1.get(2),arr1.get(3)),
                    Arguments.of(arr2.get(0),arr2.get(1),arr2.get(2),arr2.get(3)),
                    Arguments.of(arr3.get(0),arr3.get(1),arr3.get(2),arr3.get(3))
            );
        }
    }

    @AfterAll
    public static void after(){
        driver.quit();
    }
}
