import jdk.jfr.Timespan;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

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

    public void removeMovieFromWatchlistByName(String movie) {
        if (checkIfHasMovieOnWatchlist(movie)) {

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

    public boolean checkUser(String user) {
        return user.equals(driver.findElement(By.cssSelector("#nbusername")).getText());
    }

    public boolean checkTitleAndRatings(String title, String ratings) {
        return driver.findElement(
                By.cssSelector("#title-overview-widget > div.vital > div.title_block > div > div.titleBar > " +
                        "div.title_wrapper > h1")
                ).getText().contains(title) &&
                driver.findElement(
                        By.cssSelector("#title-overview-widget > div.vital > div.title_block > div > " +
                                "div.ratings_wrapper > div.imdbRating > div.ratingValue > strong > span")
                ).getText().equals(ratings);
    }

    public boolean checkIfHasMovieOnWatchlist(String movie) {
        if (driver.findElement(By.cssSelector("#center-1-react > div > div.lister-controls > div.nav > " +
                "div.nav-left > div > span:nth-child(1)")).getText().charAt(0) == '0') {
            return false;
        }
        else {
            return driver
                    .findElement(By.cssSelector("#center-1-react > div > div:nth-child(3)"))
                    .findElements(By.tagName("h3"))
                    .stream()
                    .filter(webElement -> webElement.getText().equals(movie))
                    .count() >= 1;
        }
    }

    public void closeBrowser() {
        driver.quit();
    }

    public static void main(String[] args) {
        IMDbDemo demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.goToWatchlist();
        demo.removeMovieFromWatchlistByName("The Walking Dead");
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click button");
        demo.goToWatchlist();
        demo.removeMovieFromWatchlistByName("Game of Thrones");
        demo.searchResult("game", 1);
        demo.addMovieToWatchlist("click text");
        demo.goToWatchlist();
    }
}
