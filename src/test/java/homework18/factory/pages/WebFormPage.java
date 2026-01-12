package homework18.factory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class WebFormPage extends BasePage {

    @FindBy(id = "my-text-id")
    private WebElement textInput;

    @FindBy(name = "my-password")
    private WebElement passwordInput;

    @FindBy(name = "my-textarea")
    private WebElement textarea;

    @FindBy(name = "my-select")
    private WebElement selectElement;

    @FindBy(name = "my-disabled")
    private WebElement disabledInput;

    @FindBy(name = "my-readonly")
    private WebElement readonlyInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    public WebFormPage(WebDriver driver) {
        super(driver);
    }

    public void enterText(String text) {
        sendKeysToElement(textInput, text);
    }

    public void enterTextarea(String text) {
        sendKeysToElement(textarea, text);
    }

    public void selectOption(String value) {
        waitForElementVisible(selectElement);
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(value);
    }

    public void submitForm() {
        clickElement(submitButton);
    }

    public String getTextInputValue() {
        waitForElementVisible(textInput);
        return textInput.getAttribute("value");
    }

    public String getTextareaValue() {
        waitForElementVisible(textarea);
        return textarea.getAttribute("value");
    }

    public String getSelectedOption() {
        waitForElementVisible(selectElement);
        Select dropdown = new Select(selectElement);
        return dropdown.getFirstSelectedOption().getText();
    }

    public boolean isDisabledInputEnabled() {
        waitForElementVisible(disabledInput);
        return disabledInput.isEnabled();
    }

    public String getReadonlyInputValue() {
        waitForElementVisible(readonlyInput);
        return readonlyInput.getAttribute("value");
    }
}


