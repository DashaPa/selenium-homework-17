package homework17;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumWebStorageTests {
    ChromeDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testLocalStorage() {
        driver.get(Constants.BASE_URL + "web-storage.html");

        WebStorage webStorage = (WebStorage) driver;
        LocalStorage localStorage = webStorage.getLocalStorage();

        // добавляем данные и проверяем
        localStorage.setItem("user", "testUser");
        localStorage.setItem("theme", "dark");
        assertThat(localStorage.getItem("user")).isEqualTo("testUser");
        assertThat(localStorage.size()).isGreaterThanOrEqualTo(2);
        // очищаем
        localStorage.clear();
        assertThat(localStorage.size()).isEqualTo(0);

    }

    @Test
    void testSessionStorage() {
        driver.get(Constants.BASE_URL + "web-storage.html");

        WebStorage webStorage = (WebStorage) driver;
        SessionStorage sessionStorage = webStorage.getSessionStorage();

        // выводим все элементы
        sessionStorage.keySet().forEach(key ->
                System.out.println("Key: " + key + " = " + sessionStorage.getItem(key))
        );
        // добавляем новые и проверяем
        sessionStorage.setItem("pageView", "1");
        sessionStorage.setItem("sessionId", "abc123");
        assertThat(sessionStorage.getItem("pageView")).isEqualTo("1");
        assertThat(sessionStorage.size()).isGreaterThanOrEqualTo(2);

    }
}
