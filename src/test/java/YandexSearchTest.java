import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class YandexSearchTest extends BasePage {
    ChromeDriver chromeDriver = getChromeDriver();

//    @Before
//    public void init() {
//
//    }

    @Test
    public void open() {
        chromeDriver.get("http://www.yandex.ru");
        driverStatus();
    }

    @Step
    public void driverStatus(){
        Assert.assertFalse(chromeDriver.getCurrentUrl().isEmpty());
    }

    @After
    public void closeBrowser(){
        chromeDriver.close();
    }


}
