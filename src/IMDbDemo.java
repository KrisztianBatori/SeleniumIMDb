import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class IMDbDemo {

    private WebDriver driver = new ChromeDriver();
    private String mainPage = "https://www.imdb.com";

    public WebDriver getDriver() {
        return driver;
    }

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

    public void searchResult(String word, int nthResult) {
        ((JavascriptExecutor) driver).executeScript(String.format("document.getElementById(\"navbar-query\").value = \"%s\"", word));
        driver.findElement(By.cssSelector("#navbar-query")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(String.format("#navbar-suggestionsearch > div:nth-child(%s) > a", nthResult))).click();
    }

    public void addMovieToWatchlist(String method) {
        if (method.equals("click button")) {
            driver.findElement(By.cssSelector("#title-overview-widget > div.vital > div.title_block > div > " +
                    "div.titleBar > div.primary_ribbon > div.ribbonize > div")).click();
        }
        else if (method.equals("click text")) {
            driver.findElement(By.cssSelector("#title-overview-widget > div.plot_summary_wrapper > " +
                    "div.wlb-title-main-details > span > div")).click();
        }
    }

    public void goToWatchlist() {
        ((JavascriptExecutor) driver).executeScript("document.querySelector('#navUserMenu > div').style.display=\"block\"");
        driver.findElement(By.cssSelector("#navUserMenu > div > div:nth-child(2) > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.cssSelector("#main > div > div > a:nth-child(3)")).click();
    }

    public void goToUserActivity() {
        ((JavascriptExecutor) driver).executeScript("document.querySelector('#navUserMenu > div').style.display=\"block\"");
        driver.findElement(By.cssSelector("#navUserMenu > div > div:nth-child(2) > ul > li:nth-child(1) > a")).click();
    }

    public void goToUserRatings() {
        ((JavascriptExecutor) driver).executeScript("document.querySelector('#navUserMenu > div').style.display=\"block\"");
        driver.findElement(By.cssSelector("#navUserMenu > div > div:nth-child(2) > ul > li:nth-child(3) > a")).click();
    }

    public void removeMovieFromWatchlistByName(String movie) {
        if (IMDbDemoChecks.checkIfHasMovieOnWatchlist(driver, movie)) {

            driver
                    .findElement(By.cssSelector("#center-1-react > div > div:nth-child(3)"))
                    .findElements(By.tagName("img"))
                    .stream()
                    .filter(webElement -> webElement.getAttribute("alt").equals(movie))
                    .findFirst()
                    .get()
                    .findElement(By.xpath("./.."))
                    .findElement(By.xpath("./.."))
                    .findElement(By.tagName("div"))
                    .click();

        }
    }

    public void removeMovieFromWatchlistByNumber(int number) {
        number -= 1;
        driver
                .findElement(By.cssSelector("#center-1-react > div > div:nth-child(3)"))
                .findElements(By.tagName("img"))
                .get(number)
                .findElement(By.xpath("./.."))
                .findElement(By.xpath("./.."))
                .findElement(By.tagName("div"))
                .click();
    }

    public void clearWatchlist() {
        driver
                .findElement(By.cssSelector("#center-1-react > div > div:nth-child(3)"))
                .findElements(By.tagName("img"))
                .forEach(webElement -> {
                            webElement.findElement(By.xpath("./.."))
                                .findElement(By.xpath("./.."))
                                .findElement(By.tagName("div"))
                                .click();
                            }
                );
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void closeBrowser() {
        driver.quit();
    }

    public void orderWatchlistByOption(String option) {
        WebElement sortSelector = driver.findElement(By.cssSelector("#lister-sort-by-options"));
        new Select(sortSelector).selectByVisibleText(option);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.attributeContains(driver.findElement(By.cssSelector("#center-1-react > div > div.lister-controls > " +
                        "div:nth-child(3)")), "class", "working"));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.not(
                        ExpectedConditions.attributeContains(driver.findElement(By.cssSelector("#center-1-react > div > div.lister-controls > " +
                                "div:nth-child(3)")), "class", "working"))
                );
    }

    public void clearHistory() {
        driver.findElement(By.linkText("Clear your history")).click();
    }

    public static void main(String[] args) {
        IMDbDemo demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.searchResult("wal", 1);
        demo.searchResult("game", 1);
        demo.goToUserRatings();
        System.out.println(
                IMDbDemoChecks.getAllRecentMovie(demo.getDriver())
        );
        demo.goToWatchlist();
        System.out.println(
                IMDbDemoChecks.getAllRecentMovie(demo.getDriver())
        );
        demo.searchResult("matrix", 1);
        demo.clearHistory();
        demo.refreshPage();
    }
}
