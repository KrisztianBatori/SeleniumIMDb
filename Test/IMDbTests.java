import org.junit.*;

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
        Assert.assertTrue(demo.checkUser(System.getProperty("USER_NAME")));
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        Assert.assertTrue(demo.checkUser(System.getProperty("USER_NAME")));
        demo.logout();
        demo.login("selenium@selenium.com", "12345678");
        Assert.assertFalse(demo.checkUser(System.getProperty("USER_NAME")));
        Assert.assertTrue(demo.checkUser("SeleniumTest"));
    }

    @Test
    public void checkIfRightPageFromResultIsShown() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        for (int i = 0; i < 3; i++) {
            demo.searchResult("game", 1);
            Assert.assertTrue(demo.checkTitleAndRatings("Game of Thrones", "9.5"));
        }
        demo.searchResult("wal", 1);
        Assert.assertTrue(demo.checkTitleAndRatings("The Walking Dead", "8.3"));
        demo.searchResult("game", 2);
        Assert.assertFalse(demo.checkTitleAndRatings("Game of Thrones", "9.5"));
        demo.searchResult("wal", 3);
        Assert.assertFalse(demo.checkTitleAndRatings("The Walking Dead", "8.3"));
    }

    @Test
    public void checkIfMovieAddedToWatchlist() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.goToWatchlist();
        Assert.assertFalse(demo.checkIfHasMovieOnWatchlist(""));
        Assert.assertFalse(demo.checkIfHasMovieOnWatchlist("The Walking Dead"));
        demo.searchResult("game", 1);
        demo.addMovieToWatchlist("click button");
        demo.goToWatchlist();
        Assert.assertTrue(demo.checkIfHasMovieOnWatchlist("Game of Thrones"));
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.goToWatchlist();
        Assert.assertTrue(demo.checkIfHasMovieOnWatchlist("The Walking Dead"));
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.goToWatchlist();
        Assert.assertTrue(demo.checkIfHasMovieOnWatchlist("The Walking Dead"));
        Assert.assertTrue(demo.checkIfHasMovieOnWatchlist("Game of Thrones"));
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
        demo.goToWatchlist();
        Assert.assertEquals(4, demo.getWatchlistSize());
        demo.removeMovieFromWatchlistByNumber(3);
        demo.refreshPage();
        Assert.assertEquals(3, demo.getWatchlistSize());
        demo.removeMovieFromWatchlistByName("The Walking Dead");
        demo.refreshPage();
        Assert.assertFalse(demo.checkIfHasMovieOnWatchlist("The Walking Dead"));
        demo.clearWatchlist();
        demo.refreshPage();
        Assert.assertEquals(0, demo.getWatchlistSize());
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.goToWatchlist();
        demo.removeMovieFromWatchlistByNumber(1);
        demo.refreshPage();
        Assert.assertEquals(0, demo.getWatchlistSize());
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
