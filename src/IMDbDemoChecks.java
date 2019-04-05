import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class IMDbDemoChecks {
    public static int getWatchlistSize(WebDriver driver) {
        return driver.findElement(By.cssSelector("#center-1-react > div > div.lister-controls > div.nav > " +
                "div.nav-left > div > span:nth-child(1)")).getText().charAt(0) - 48;
    }

    public static boolean checkIfHasMovieOnWatchlist(WebDriver driver, String movie) {
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

    public static boolean checkTitleAndRatings(WebDriver driver, String title, String ratings) {
        return driver.findElement(
                By.cssSelector("#title-overview-widget > div.vital > div.title_block > div > div.titleBar > " +
                        "div.title_wrapper > h1")
        ).getText().contains(title) &&
                driver.findElement(
                        By.cssSelector("#title-overview-widget > div.vital > div.title_block > div > " +
                                "div.ratings_wrapper > div.imdbRating > div.ratingValue > strong > span")
                ).getText().equals(ratings);
    }

    public static boolean checkUser(WebDriver driver, String user) {
        return user.equals(driver.findElement(By.cssSelector("#nbusername")).getText());
    }

    public static List<String> getAllWatchlistMovie(WebDriver driver) {
        return driver
                .findElement(By.cssSelector("#center-1-react > div > div:nth-child(3)"))
                .findElements(By.tagName("img"))
                .stream()
                .map(webElement -> webElement.getAttribute("alt"))
                .collect(Collectors.toList());
    }

    public static List<String> getAllRecentMovie(WebDriver driver) {
        return driver
                .findElement(By.cssSelector("#rvi-div > div > div.items"))
                .findElements(By.xpath(".//*[@id!=\"\"]"))
                .stream()
                .map(webElement -> webElement.findElement(By.tagName("img")).getAttribute("alt"))
                .collect(Collectors.toList());
    }

}
