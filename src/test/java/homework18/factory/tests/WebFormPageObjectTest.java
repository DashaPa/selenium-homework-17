package homework18.factory.tests;

import homework18.factory.WebDriverFactory;
import homework18.factory.pages.WebFormPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class WebFormPageObjectTest {
    private WebDriver driver;
    private WebFormPage webFormPage;

    @BeforeEach
    void setUp() {
        driver = WebDriverFactory.createDriver("chrome");
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        webFormPage = new WebFormPage(driver);
    }

    @Test
    void testWebFormTitle() {
        // проверяем заголовок h1
        WebElement title = driver.findElement(By.className("display-6"));
        assertThat(title.getText()).isEqualTo("Web form");
    }

    @Test
    void testTextInput() {
        webFormPage.enterText("Hello Selenium");
        assertThat(webFormPage.getTextInputValue()).isEqualTo("Hello Selenium");
    }

    @Test
    void testTextarea() {  // убрала throws InterruptedException
        webFormPage.enterTextarea("Line 1\nLine 2");
        assertThat(webFormPage.getTextareaValue()).isEqualTo("Line 1\nLine 2");
    }

    @Test
    void testSelectDropdown() {
        webFormPage.selectOption("Two");
        assertThat(webFormPage.getSelectedOption()).isEqualTo("Two");
    }

    @Test
    void testDisabledInput() {
        assertThat(webFormPage.isDisabledInputEnabled()).isFalse();
    }

    @Test
    void testReadonlyInput() {
        assertThat(webFormPage.getReadonlyInputValue()).isEqualTo("Readonly input");
    }

    @Test
    void testHeader() {
        assertThat(webFormPage.getHeader().isDisplayed()).isTrue();
    }

    @Test
    void testFormSubmission() {
        webFormPage.enterText("Test");
        webFormPage.submitForm();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("submitted-form.html"));
        assertThat(driver.getCurrentUrl()).contains("submitted-form.html");}

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}




