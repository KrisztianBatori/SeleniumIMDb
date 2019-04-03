import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.nio.file.Path;

public class IMDbDemo {

    private WebDriver driver = new ChromeDriver();
    private String mainPage = "https://www.imdb.com";

    IMDbDemo() {
        driver.get(mainPage);
    }

    public void login(String email, String password) {
        ((ChromeDriver) driver).findElementByCssSelector("#imdb-signin-link").click();
        ((ChromeDriver) driver).findElementByCssSelector("#signin-options > div > div:nth-child(2) > a:nth-child(1)").click();
        ((ChromeDriver) driver).findElementByCssSelector("#ap_email").sendKeys(email);
        ((ChromeDriver) driver).findElementByCssSelector("#ap_password").sendKeys(password);
        ((ChromeDriver) driver).findElementByCssSelector("#signInSubmit").click();
    }

    public void logout() {
        ((JavascriptExecutor) driver).executeScript("document.querySelector('#navUserMenu > div').style.display=\"block\"");
        driver.findElement(By.cssSelector("#nblogout")).click();
    }

    public boolean checkUser(String user) {
        return user.equals(driver.findElement(By.cssSelector("#nbusername")).getText());
    }

    public void closeBrowser() {
        driver.quit();
    }

    public static void main(String[] args) {
        IMDbDemo demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.logout();
    }
}
