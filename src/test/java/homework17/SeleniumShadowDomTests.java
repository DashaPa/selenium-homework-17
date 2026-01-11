package homework17;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeleniumShadowDomTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void start() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testShadowDom() {
        driver.get(BASE_URL + "shadow-dom.html");

        // из лекции: элемент не доступен напрямую, получаем доступ к изолированному Shadow Dom
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("p")));
        //находим элемент, содержащий Shadow DOM
        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));
        //одна проверка содержимого элемента
        assertEquals("Hello Shadow DOM", textElement.getText());
    }

}