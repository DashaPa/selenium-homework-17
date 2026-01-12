package homework18.factory.pages.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Footer {
    @FindBy(tagName = "footer")
    private WebElement footerElement;


    public boolean isDisplayed() {
        try {
            return footerElement != null && footerElement.isDisplayed();
        } catch (Exception e) {
            // если элемента нет на странице - возвращаем false
            return false;
        }
    }

    }

