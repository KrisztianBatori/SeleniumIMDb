import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class IMDbDemo {

    private WebDriver driver = new ChromeDriver();
    private String mainPage = "https://www.imdb.com";

    IMDbDemo() {
        driver.get(mainPage);
    }

    public void login() {
        ((ChromeDriver) driver).findElementByCssSelector("#imdb-signin-link").click();
        ((ChromeDriver) driver).findElementByCssSelector("#signin-options > div > div:nth-child(2) > a:nth-child(1)").click();
        ((ChromeDriver) driver).findElementByCssSelector("#ap_email").sendKeys(System.getProperty("USER_EMAIL"));
        ((ChromeDriver) driver).findElementByCssSelector("#ap_password").sendKeys(System.getProperty("USER_PASSWORD"));
        ((ChromeDriver) driver).findElementByCssSelector("#signInSubmit").click();
    }


    public static void main(String[] args) {
        IMDbDemo demo = new IMDbDemo();
        demo.login();
    }
}
