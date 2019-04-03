import org.openqa.selenium.By;
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

    public boolean checkUser(String user) {
        return user.equals(driver.findElement(By.cssSelector("#nbusername")).getText());
    }

    public void closeBrowser() {
        driver.quit();
    }

    public static void main(String[] args) {
        IMDbDemo demo = new IMDbDemo();
        demo.login();
        System.out.println(
                demo.checkUser(System.getProperty("USER_NAME"))
        );
        demo.closeBrowser();
    }
}
