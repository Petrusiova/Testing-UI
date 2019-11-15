import io.qameta.allure.Step;
import io.qameta.allure.Stories;
import org.junit.*;
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
