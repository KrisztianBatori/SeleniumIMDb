import org.junit.*;

import java.util.ArrayList;

public class IMDbTests {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
    }

    @Test
    public void checkIfRightUserHasLogged() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        Assert.assertTrue(IMDbDemoChecks.checkUser(demo.getDriver(), System.getProperty("USER_NAME")));
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        Assert.assertTrue(IMDbDemoChecks.checkUser(demo.getDriver(), System.getProperty("USER_NAME")));
        demo.logout();
        demo.login("selenium@selenium.com", "12345678");
        Assert.assertFalse(IMDbDemoChecks.checkUser(demo.getDriver(), System.getProperty("USER_NAME")));
        Assert.assertTrue(IMDbDemoChecks.checkUser(demo.getDriver(), "SeleniumTest"));
    }

    @Test
    public void checkIfWatchlistOrderedProperly() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
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

    @Test
    public void checkIfRightPageFromResultIsShown() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        for (int i = 0; i < 3; i++) {
            demo.searchResult("game", 1);
            Assert.assertTrue(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "Game of Thrones", "9.5"));
        }
        demo.searchResult("wal", 1);
        Assert.assertTrue(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "The Walking Dead", "8.3"));
        demo.searchResult("game", 2);
        Assert.assertFalse(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "Game of Thrones", "9.5"));
        demo.searchResult("wal", 3);
        Assert.assertFalse(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "The Walking Dead", "8.3"));
    }

    @Test
    public void checkIfMovieAddedToWatchlist() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.goToWatchlist();
        Assert.assertFalse(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), ""));
        Assert.assertFalse(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        demo.searchResult("game", 1);
        demo.addMovieToWatchlist("click button");
        demo.goToWatchlist();
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "Game of Thrones"));
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.goToWatchlist();
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.goToWatchlist();
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "Game of Thrones"));
        demo.clearWatchlist();
    }

    @Test
    public void editWatchlistProperly() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.searchResult("matrix", 1);
        demo.addMovieToWatchlist("click button");
        demo.searchResult("world", 2);
        demo.addMovieToWatchlist("click text");
        demo.searchResult("throne", 3);
        demo.addMovieToWatchlist("click button");
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.refreshPage();
        demo.goToWatchlist();
        Assert.assertEquals(4, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
        demo.removeMovieFromWatchlistByNumber(3);
        demo.refreshPage();
        Assert.assertEquals(3, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
        demo.removeMovieFromWatchlistByName("The Walking Dead");
        demo.refreshPage();
        Assert.assertFalse(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        demo.clearWatchlist();
        demo.refreshPage();
        Assert.assertEquals(0, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.goToWatchlist();
        demo.removeMovieFromWatchlistByNumber(1);
        demo.refreshPage();
        Assert.assertEquals(0, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
