package homework17;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumCookiesTests {
    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get(Constants.BASE_URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testCookies() {
        driver.get(Constants.BASE_URL + "cookies.html");

        WebDriver.Options options = driver.manage();

        // проверка начальных cookies
        Set<Cookie> initialCookies = options.getCookies();
        System.out.println("Initial cookies count: " + initialCookies.size());
        assertThat(initialCookies).hasSize(2);

        Cookie usernameCookie = options.getCookieNamed("username");
        assertThat(usernameCookie.getValue()).isEqualTo("John Doe");
        System.out.println("Username cookie: " + usernameCookie.getValue());

        // добавляем новый cookie и проверяем
        Cookie newCookie = new Cookie("sessionId", "12345", "/", null);
        options.addCookie(newCookie);
        assertThat(options.getCookies()).hasSize(3);
        assertThat(options.getCookieNamed("sessionId").getValue()).isEqualTo("12345");

        // удаляем cookie
        options.deleteCookieNamed("username");
        assertThat(options.getCookies()).hasSize(2);
        // очищаем все
        options.deleteAllCookies();
        assertThat(options.getCookies()).isEmpty();
    }
}
