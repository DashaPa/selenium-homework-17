package homework18.factory.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(id = "success")
    private WebElement successMessage;

    @FindBy(id = "invalid")
    private WebElement errorMessage;

    @FindBy(className = "display-6")
    private WebElement pageTitle;

    public LoginPage(WebDriver driver) {
        super(driver);
        waitForElementVisible(pageTitle);
    }

    public void login(String username, String password) {
        sendKeysToElement(usernameInput, username);  // метод BasePage
        sendKeysToElement(passwordInput, password);
        clickElement(submitButton);
    }
    // ждём появления сообщения
    public boolean isSuccessMessageDisplayed() {
        try {
            waitForElementVisible(successMessage);
            return successMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(d -> errorMessage.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessageText() {
        return successMessage.getText();
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }

    public String getPageTitleText() {
        return pageTitle.getText();
    }

}
