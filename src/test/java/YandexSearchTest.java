import org.junit.Test;

public class YandexSearchTest extends BasePage {

    @Test
    public void open() {
        getChromeDriver().get("http://www.yandex.ru");
    }


}
