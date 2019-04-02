import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class IMDbDemo {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com");
        ((ChromeDriver) driver).findElementByCssSelector("#imdb-signin-link").click();
        ((ChromeDriver) driver).findElementByCssSelector("#signin-options > div > div:nth-child(2) > a:nth-child(1)").click();
    }
}
