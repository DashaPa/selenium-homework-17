package homework18.factory.tests;

import homework18.factory.WebDriverFactory;
import homework18.factory.pages.LoginPage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

class LoginFormTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        driver = WebDriverFactory.createDriver("chrome");
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
        loginPage = new LoginPage(driver);
    }

    @Test
    void testSuccessfulLogin() {
        loginPage.login("user", "user");
        // проверяем текст сообщения (не Form submitted)
        assertThat(loginPage.isSuccessMessageDisplayed()).isTrue();
        assertThat(loginPage.getSuccessMessageText()).contains("Login successful");
    }

    @Test
    void testFailedLogin() {
        loginPage.login("wrong", "wrong");
        assertThat(loginPage.isErrorMessageDisplayed()).isTrue();
        assertThat(loginPage.getErrorMessageText()).contains("Invalid credentials");
    }
    @Test
    void testPageTitle() {
        assertThat(loginPage.getPageTitleText()).isEqualTo("Login form");
    }

    @Test
    void testHeaderIsDisplayed() {
        assertThat(loginPage.getHeader().isDisplayed()).isTrue();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}