package pages;

import org.openqa.selenium.WebElement;

public class CompTechPage extends BasePage {

    public void chooseSection(String section) {
        WebElement ourSection = getChromeDriver().findElementByXPath(".//a[text() = '" + section + "']");
        checkElementOnPage(ourSection);
        ourSection.click();
    }
}
