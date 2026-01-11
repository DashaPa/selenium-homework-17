package homework17;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumInfiniteScrollTests {
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
    void testInfiniteScroll() {
        driver.get(Constants.BASE_URL + "infinite-scroll.html");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By pLocator = By.tagName("p");
        List<WebElement> paragraphs = driver.findElements(pLocator);
        int initialCount = paragraphs.size();
        System.out.println("Начальное количество параграфов: " + initialCount);

        // скролл вниз
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        // ждем новые параграфы
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, initialCount));
        int newCount = driver.findElements(pLocator).size();
        System.out.println("Новое количество параграфов: " + newCount);
        assertThat(newCount).isGreaterThan(initialCount);
    }
}
