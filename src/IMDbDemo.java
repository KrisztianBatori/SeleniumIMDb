import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class IMDbDemo {

    private WebDriver driver = new ChromeDriver();
    private String mainPage = "https://www.imdb.com";

    IMDbDemo() {
        driver.get(mainPage);
    }

    public void login(String email, String password) {
        driver.findElement(By.cssSelector("#imdb-signin-link")).click();
        driver.findElement(By.cssSelector("#signin-options > div > div:nth-child(2) > a:nth-child(1)")).click();
        driver.findElement(By.cssSelector("#ap_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#ap_password")).sendKeys(password);
        driver.findElement(By.cssSelector("#signInSubmit")).click();
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
