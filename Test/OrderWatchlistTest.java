import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class OrderWatchlistTest {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
    }

    @Test
    public void checkIfWatchlistOrderedProperly() {
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.searchResult("game", 1);
        demo.addMovieToWatchlist("click text");
        demo.searchResult("harry", 1);
        demo.addMovieToWatchlist("click text");
        demo.searchResult("harry", 2);
        demo.addMovieToWatchlist("click text");
        demo.searchResult("matrix", 1);
        demo.addMovieToWatchlist("click text");
        demo.searchResult("lord", 1);
        demo.addMovieToWatchlist("click text");
        demo.refreshPage();
        demo.goToWatchlist();
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("The Walking Dead");
                    add("Game of Thrones");
                    add("Harry Potter and the Philosopher's Stone");
                    add("Harry Potter and the Deathly Hallows: Part 2");
                    add("The Matrix");
                    add("The Lord of the Rings: The Fellowship of the Ring");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        demo.orderWatchlistByOption("Alphabetical");
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("Game of Thrones");
                    add("Harry Potter and the Deathly Hallows: Part 2");
                    add("Harry Potter and the Philosopher's Stone");
                    add("The Lord of the Rings: The Fellowship of the Ring");
                    add("The Matrix");
                    add("The Walking Dead");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        demo.orderWatchlistByOption("IMDb Rating");
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("Game of Thrones");
                    add("The Lord of the Rings: The Fellowship of the Ring");
                    add("The Matrix");
                    add("The Walking Dead");
                    add("Harry Potter and the Deathly Hallows: Part 2");
                    add("Harry Potter and the Philosopher's Stone");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        demo.orderWatchlistByOption("Popularity");
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("Game of Thrones");
                    add("The Walking Dead");
                    add("Harry Potter and the Philosopher's Stone");
                    add("The Matrix");
                    add("The Lord of the Rings: The Fellowship of the Ring");
                    add("Harry Potter and the Deathly Hallows: Part 2");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        demo.orderWatchlistByOption("Number of Ratings");
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("The Lord of the Rings: The Fellowship of the Ring");
                    add("The Matrix");
                    add("Game of Thrones");
                    add("The Walking Dead");
                    add("Harry Potter and the Deathly Hallows: Part 2");
                    add("Harry Potter and the Philosopher's Stone");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        demo.orderWatchlistByOption("Release Date");
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("Harry Potter and the Deathly Hallows: Part 2");
                    add("Game of Thrones");
                    add("The Walking Dead");
                    add("The Lord of the Rings: The Fellowship of the Ring");
                    add("Harry Potter and the Philosopher's Stone");
                    add("The Matrix");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        demo.orderWatchlistByOption("Runtime");
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("The Lord of the Rings: The Fellowship of the Ring");
                    add("Harry Potter and the Philosopher's Stone");
                    add("The Matrix");
                    add("Harry Potter and the Deathly Hallows: Part 2");
                    add("Game of Thrones");
                    add("The Walking Dead");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        demo.orderWatchlistByOption("Date Added");
        Assert.assertEquals(
                new ArrayList<>() {{
                    add("The Lord of the Rings: The Fellowship of the Ring");
                    add("The Matrix");
                    add("Harry Potter and the Deathly Hallows: Part 2");
                    add("Harry Potter and the Philosopher's Stone");
                    add("Game of Thrones");
                    add("The Walking Dead");
                }}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
        for (int i = 0; i < 2; i++) {
            demo.clearWatchlist();
            demo.refreshPage();
        }
        Assert.assertEquals(
                new ArrayList<>() {{}}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
