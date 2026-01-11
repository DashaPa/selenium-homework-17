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

public class SeleniumDialogTests {
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
    void testAlertDialog() {
        driver.get(Constants.BASE_URL + "dialog-boxes.html");
        // находим кнопку для вызова alert и кликаем
        WebElement alertButton = driver.findElement(By.id("my-alert"));
        alertButton.click();
        // ждем появления alert окна и переключаемся на него, проверяем в нем текст
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertThat(alert.getText()).as("Проверка текста в alert").isEqualTo("Hello world!");
        alert.accept();
    }

    @Test
    void testConfirmDialogAccept() {
        driver.get(Constants.BASE_URL + "dialog-boxes.html");
        //confirm окно
        WebElement confirmButton = driver.findElement(By.id("my-confirm"));
        confirmButton.click();

        Alert confirm = wait.until(ExpectedConditions.alertIsPresent());
        confirm.accept();

        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("confirm-text")));
        assertThat(result.getText()).as("Проверка результата").isEqualTo("You chose: true");
    }

    @Test
    void testConfirmDialogDismiss() {
        driver.get(Constants.BASE_URL + "dialog-boxes.html");

        WebElement confirmButton = driver.findElement(By.id("my-confirm"));
        confirmButton.click();

        Alert confirm = wait.until(ExpectedConditions.alertIsPresent());
        confirm.dismiss(); //отклоняем

        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("confirm-text")));
        assertThat(result.getText()).as("Проверка результата после отмены").isEqualTo("You chose: false");
    }

    @Test
    void testPromptDialog() {
        driver.get(Constants.BASE_URL + "dialog-boxes.html");
        //prompt (диалог с вводом текста)

        WebElement promptButton = driver.findElement(By.id("my-prompt"));
        promptButton.click();

        Alert prompt = wait.until(ExpectedConditions.alertIsPresent());
        String testName = "Selenium Tester";
        prompt.sendKeys(testName);
        prompt.accept();

        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("prompt-text")));
        assertThat(result.getText()).as("Проверка,что текст отобразился").isEqualTo("You typed: " + testName);
    }

    @Test
    void testModalDialog() {
        driver.get(Constants.BASE_URL + "dialog-boxes.html");
        //Bootstrap modal не системное окно
        WebElement modalButton = driver.findElement(By.id("my-modal"));
        modalButton.click();

        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal")));
        WebElement closeButton = modal.findElement(By.xpath(".//button[text()='Close']"));
        closeButton.click();

        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("modal-text")));
        assertThat(result.getText()).as("Проверка результата после закрытия окна").isEqualTo("You chose: Close");
    }
}
