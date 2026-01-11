package homework17;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumIframesTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testIFrames() {
        driver.get(Constants.BASE_URL + "iframes.html");

        // проверяем заголовок на основной странице
        WebElement mainHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        assertThat(mainHeader.getText()).as("Проверка заголовка главной страницы").isEqualTo("Hands-On Selenium WebDriver with Java");
        // находим iframe и ждем его загрузки
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("my-iframe"));
        // ждем загрузки и ищем элемент <p> с текстом
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"), "Lorem ipsum"));
        // текст из body
        WebElement body = driver.findElement(By.tagName("body"));
        String iframeText = body.getText();
        // проверяем текст
        assertThat(iframeText).as("Проверка текста внутри iframe").contains("Lorem ipsum");
        // возврат на основную страницу
        driver.switchTo().defaultContent();

        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("footer.footer")));
        assertThat(footer.isDisplayed()).as("Проверка, что footer на основной странице отображается").isTrue();
    }

    @Test
    void testScrollInsideIframe() {
        driver.get(Constants.BASE_URL + "iframes.html");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("my-iframe"));
        // упрощенная проверка
        wait.until(d -> {
            String bodyText = driver.findElement(By.tagName("body")).getText();
            return !bodyText.isEmpty();
        });
        //получаем тело iframe
        WebElement body = driver.findElement(By.tagName("body"));
        String initialText = body.getText();

        // проверка начального текста
        assertThat(initialText).as("В iframe должен быть текст").contains("Lorem ipsum");
        // скролл вниз
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        // ждем скролла, пауза
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // проверка, что мы проскроллили (используем Number вместо Long/Double, тест ругался)
        Object scrollY = js.executeScript("return window.pageYOffset;");
        double scrollPosition = ((Number) scrollY).doubleValue();
        assertThat(scrollPosition).as("Должны были проскроллить iframe").isGreaterThan(0.0);
        driver.switchTo().defaultContent(); //возвращаемся
    }
}