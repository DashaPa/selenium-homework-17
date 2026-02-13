package homework18.factory.tests.selenide;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideWebFormTest {

    private static final Logger log = LoggerFactory.getLogger(SelenideWebFormTest.class);
    private static final String FORM_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";

    private SelenideElement textInput;
    private SelenideElement textarea;
    private SelenideElement disabledInput;
    private SelenideElement readonlyInput;
    private SelenideElement dropdown;
    private SelenideElement submitButton;

    @BeforeEach
    void setUp() {
        Configuration.browser = "firefox";// Использую Firefox вместо Chrome,так как Chrome 145
        // требует дополнительных DevTools зависимостей
        Configuration.headless = false;
        Configuration.timeout = 10000;

        open(FORM_URL);
        log.info("Открыта страница с формой в Firefox");

        textInput = $("#my-text-id");
        textarea = $("textarea");
        disabledInput = $("input[name='my-disabled']");
        readonlyInput = $("input[name='my-readonly']");
        dropdown = $("select[name='my-select']");
        submitButton = $("button[type='submit']");
    }

    @Test
    void testPageTitle() {
        $(".display-6").shouldHave(text("Web form"));
        log.info("Заголовок страницы совпадает");
    }

    @ParameterizedTest
    @CsvSource({
            "Hello",
            "12345",
            "!@#$!",
            "Новый тест 15"
    })
    void textInputShouldAcceptVariousValues(String input) {
        textInput.setValue(input);
        textInput.shouldHave(value(input));
        log.info("Текстовое поле приняло значение: {}", input);
    }

    @Test
    void testTextInput() {
        String testText = "Hello Selenide";
        textInput.setValue(testText);
        textInput.shouldHave(value(testText));
        log.info("Текстовое поле заполнено: {}", testText);
    }

    @Test
    void testTextarea() {
        String multiLineText = "Line 1\nLine 2\nLine 3";
        textarea.setValue(multiLineText);
        textarea.shouldHave(value(multiLineText));

        String[] lines = textarea.getValue().split("\n");
        assertThat(lines).hasSize(3);
        log.info("Textarea заполнена {} строками", lines.length);
    }

    @Test
    void testTextareaVisible() {
        textarea.shouldBe(visible);
        log.info("Textarea видима");
    }

    @Test
    void testDisabledInputHasDisabledAttribute() {
        disabledInput.shouldHave(attribute("disabled"));
        log.info("Disabled поле имеет атрибут disabled");
    }

    @Test
    void testDisabledInputHasValue() {
        String originalValue = disabledInput.getValue();
        assertThat(originalValue).isNotNull();
        log.info("Disabled поле работает, значение: {}", originalValue);
    }
    @Test
    void testDisabledInputVisible() {
        disabledInput.shouldBe(visible);
        log.info("Disabled поле видимо");
    }

    @Test
    void testReadonlyInputHasReadonlyAttribute() {
        readonlyInput.shouldHave(attribute("readonly"));
        log.info("Readonly поле имеет атрибут readonly");
    }

    @Test
    void testReadonlyInputHasCorrectValue() {
        readonlyInput.shouldHave(value("Readonly input"));
        log.info("Readonly поле имеет значение: {}", readonlyInput.getValue());
    }

    @Test
    void testReadonlyInputVisible() {
        readonlyInput.shouldBe(visible);
        log.info("Readonly поле видимо");
    }

    @Test
    void testDropdownDefaultOption() {
        dropdown.shouldHave(text("Open this select menu"));
        log.info("Dropdown значение по умолчанию");
    }

    @Test
    void testDropdownSelectTwo() {
        dropdown.selectOption("Two");
        dropdown.shouldHave(text("Two"));
        log.info("Dropdown выбран: Two");
    }

    @Test
    void testDropdownVisible() {
        dropdown.shouldBe(visible);
        log.info("Dropdown видим");
    }

    @Test
    void testSubmitButtonVisibleAndEnabled() {
        submitButton.shouldBe(visible).shouldBe(enabled);
        log.info("Кнопка видима и активна");
    }

    @Test
    void testSubmitButtonText() {
        submitButton.shouldHave(text("Submit"));
        log.info("Кнопка имеет Submit");
    }

    @Test
    void testFormSubmission() {
        textInput.setValue("Test User");
        textarea.setValue("Test comment");
        dropdown.selectOption("Three");
        submitButton.click();

        $("body").shouldHave(text("Form submitted"));
        assertThat(webdriver().driver().url()).contains("submitted-form.html");
        log.info("Форма отправлена");
    }

    @Test
    void testAllElementsPresent() {
        textInput.shouldBe(visible);
        textarea.shouldBe(visible);
        disabledInput.shouldBe(visible);
        readonlyInput.shouldBe(visible);
        dropdown.shouldBe(visible);
        submitButton.shouldBe(visible);

        log.info("Все элементы формы есть на странице");
    }
}

